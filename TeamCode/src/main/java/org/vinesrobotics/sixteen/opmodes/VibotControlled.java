/*
 * Copyright (c) 2016 Vines High School Robotics Team
 *
 *                            Permission is hereby granted, free of charge, to any person obtaining a copy
 *                            of this software and associated documentation files (the "Software"), to deal
 *                            in the Software without restriction, including without limitation the rights
 *                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *                            copies of the Software, and to permit persons to whom the Software is
 *                            furnished to do so, subject to the following conditions:
 *
 *                            The above copyright notice and this permission notice shall be included in all
 *                            copies or substantial portions of the Software.
 *
 *                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *                            SOFTWARE.
 */

package org.vinesrobotics.sixteen.opmodes;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.vinesrobotics.sixteen.hardware.Catapult;
import org.vinesrobotics.sixteen.hardware.Hardware;
import org.vinesrobotics.sixteen.hardware.HardwareElement;
import org.vinesrobotics.sixteen.hardware.controllers.Controller;
import org.vinesrobotics.sixteen.hardware.controllers.ControllerState;
import org.vinesrobotics.sixteen.hardware.controllers.Controllers;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Button;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Joystick;
import org.vinesrobotics.sixteen.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.sixteen.utils.Utils;
import org.vinesrobotics.sixteen.utils.camera.Camera;
import org.vinesrobotics.sixteen.utils.Logging;
import org.vinesrobotics.sixteen.utils.Vec2D;
import org.vinesrobotics.sixteen.utils.camera.CameraUtils;
import org.vinesrobotics.sixteen.utils.camera.ImageSaver;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.List;


@TeleOp(name="Controlled", group="Vines")
//@Disabled
public class VibotControlled extends OpMode {

    /* Declare OpMode members. */
    Hardware robot       = new Hardware();


    boolean died = false;

    Controllers c;
    Controller main_ct;
    //Controller turret_ct;

    MotorDeviceGroup lmot;
    MotorDeviceGroup rmot;
    DcMotor itk;
    Catapult catapult;


    final int catapult_pos = 255;
    final int catapult_root = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        Logging.setTelemetry(telemetry);

        try {
            robot.registerHardwareKeyName("intake");
        } catch (InvalidKeyException e) {}
        robot.initHardware(hardwareMap);

        List<HardwareElement> lefts = robot.getDevicesWithAllKeys("left","drive");
        lmot = new MotorDeviceGroup();
        try {
            for (HardwareElement he : lefts) {
                lmot.addDevice((DcMotor) he.get());
            }
            lmot.setDirection(DcMotorSimple.Direction.FORWARD);
        }catch (Exception e){}

        List<HardwareElement> right = robot.getDevicesWithAllKeys("right","drive");
        rmot = new MotorDeviceGroup();
        try {
        for (HardwareElement he : right) {
            rmot.addDevice((DcMotor)he.get());
        }
        rmot.setDirection(DcMotorSimple.Direction.REVERSE);
        }catch (Exception e){}

        itk = robot.getDeviceWithKeys("intake","motor");

        catapult = new Catapult((DcMotor) robot.getDeviceWithKeys("motor","catapult"), catapult_pos, catapult_root);

        Controllers ctrls = Controllers.getControllerObjects(this);
        this.main_ct = ctrls.a();
        //this.turret_ct = ctrls.b();
        //ctrls.calibrate();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        if (died) return;
    }

    boolean last_down = false;
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if (died) return;
        catapult.tick();

        ControllerState main = this.main_ct.getControllerState();
        //ControllerState turret = this.turret_ct.getControllerState();

        Vec2D<Double> left;
        Vec2D<Double> right;

        left = main.joy(Joystick.LEFT);
        right = main.joy(Joystick.RIGHT);

        lmot.setPower(left.b());
        rmot.setPower(right.b());

        double itkpw = main.btnVal(Button.RT) * ( ( main.isPressed(Button.RB) ) ? -1 : 1 );

        itk.setPower( itkpw );

        if (!catapult.isManual()) {
            if (!main.isPressed(Button.X))
                catapult.ready();
            // Use LT LB

        } else if (catapult.isManualReady() && !main.isPressed(Button.X)){
            double catpw = main.btnVal(Button.LT) * ( ( main.isPressed(Button.LB) ) ? -1 : 1 );
            catapult.catapult().setPower(catpw);
        }

        if (main.isPressed(Button.X)) catapult.fire();
        if (main.isPressed(Button.DOWN) && !last_down) catapult.toggleManual();
        last_down = main.isPressed(Button.DOWN);

        telemetry.addLine( "Values in range of -1 to +1" );
        telemetry.addData( "Speed", (-left.b()-right.b())/2 );
        telemetry.addData( "Turning Speed", (-left.b()+right.b())/2 );
        telemetry.addData( "Intake Speed", itkpw );
        updateTelemetry(telemetry);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
