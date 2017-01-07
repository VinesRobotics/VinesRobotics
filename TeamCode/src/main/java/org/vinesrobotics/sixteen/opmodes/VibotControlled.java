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
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
    Controller main;
    Controller turret;

    MotorDeviceGroup lmot;
    MotorDeviceGroup rmot;
    DcMotor itk;
    Camera cmr = null;

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
            lmot.setDirection(DcMotorSimple.Direction.REVERSE);
        }catch (Exception e){}

        List<HardwareElement> right = robot.getDevicesWithAllKeys("right","drive");
        rmot = new MotorDeviceGroup();
        try {
        for (HardwareElement he : right) {
            rmot.addDevice((DcMotor)he.get());
        }
        rmot.setDirection(DcMotorSimple.Direction.FORWARD);
        }catch (Exception e){}

        itk = robot.getDeviceWithKeys("intake","motor");

        try {
            cmr = Camera.getCamera();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LEL","I'm sorry dave, I'm afraid I can't do that for you.",e);
            Logging.log("Unfortunately, support for controllers has been dropped.");
            return;
        }

        Controllers ctrls = Controllers.getControllerObjects(this);
        this.main = ctrls.a();
        this.turret = ctrls.b();
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

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    Image image = null;
    @Override
    public void loop() {
        if (died) return;

        if (cmr.isReady()) {
            try {
                cmr.queueCapture();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LEL","I'm sorry dave, I'm afraid I can't do that for you.",e);
                Logging.log("Unfortunately, support for controllers has been dropped.");
                return;
            }
            image = cmr.image;
        } else {
            telemetry.addLine( "Camera not loaded" );
        }

        if (image != null) {
            Bitmap bmp = CameraUtils.bitmapFromImage(image);
            try {
                ImageSaver.insertImage(Utils.getApp().getContentResolver(), bmp, String.valueOf(getRuntime()), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ControllerState main = this.main.getControllerState();
        main.update();
        ControllerState turret = this.turret.getControllerState();
        turret.update();

        Vec2D<Double> left;
        Vec2D<Double> right;

        left = main.joy(Joystick.LEFT);
        right = main.joy(Joystick.RIGHT);

        lmot.setPower(left.b());
        rmot.setPower(right.b());

        double itkpw = main.btnVal(Button.RT) - main.btnVal(Button.LT);

        itk.setPower( itkpw );

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
