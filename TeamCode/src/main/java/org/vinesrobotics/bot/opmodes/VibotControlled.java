/*
 * Copyright (c) 2017 Vines High School Robotics Team
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

package org.vinesrobotics.bot.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.R;
import org.vinesrobotics.bot.hardware.Hardware;
import org.vinesrobotics.bot.hardware.HardwareElement;
import org.vinesrobotics.bot.hardware.controllers.Controller;
import org.vinesrobotics.bot.hardware.controllers.ControllerState;
import org.vinesrobotics.bot.hardware.controllers.Controllers;
import org.vinesrobotics.bot.hardware.controllers.enums.Button;
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick;
import org.vinesrobotics.bot.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.bot.hardware.robot.final_16.Catapult;
import org.vinesrobotics.bot.utils.Logging;
import org.vinesrobotics.bot.utils.Utils;
import org.vinesrobotics.bot.utils.Vec2D;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.List;


@TeleOp(name="Controlled (17)", group="Vines")
//@Disabled
public class VibotControlled extends OpMode {

    public MotorDeviceGroup leftMotors;
    public MotorDeviceGroup rightMotors;

    DcMotor itk;
    Catapult catapult;
    final int catapult_pos = -127;
    final int catapult_root = 0;

    public Hardware robot = new Hardware();
    @Override
    public void init() {
        Logging.setTelemetry(telemetry);
        try {
            robot.registerHardwareKeyName("intake");
            robot.registerHardwareKeyName("bumper");
            robot.registerHardwareKeyName("slide");
            robot.registerHardwareKeyName("claw");
        } catch (InvalidKeyException e) {}
        robot.initHardware(hardwareMap);

        List<HardwareElement> lefts = robot.getDevicesWithAllKeys("left","drive");
        leftMotors = new MotorDeviceGroup();
        try {
            for (HardwareElement he : lefts) {
                leftMotors.addDevice((DcMotor) he.get());
            }
            leftMotors.setDirection(DcMotor.Direction.FORWARD);
            leftMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }catch (Exception e){}

        List<HardwareElement> right = robot.getDevicesWithAllKeys("right","drive");
        rightMotors = new MotorDeviceGroup();
        try {
            for (HardwareElement he : right) {
                rightMotors.addDevice((DcMotor)he.get());
            }
            rightMotors.setDirection(DcMotor.Direction.REVERSE);
            rightMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }catch (Exception e){}

        itk = robot.getDeviceWithKeys("intake","motor");
        itk.setDirection(DcMotor.Direction.REVERSE);
        itk.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // old init_m
        controllers = Controllers.getControllerObjects(this);
        main_ct = controllers.a();
        lastMain = main_ct.getControllerState();
    }

    public void init_loop() {
        /*init_loop_m();*/
    }

    @Override
    public void start() {
        /*start_m();*/
        if (died) return;
    }

    private Exception error = null;
    private double ctime = 0;

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in x loop while this op mode is running
     */
    public void loop() {
        if (error != null) {
            double delta = Utils.getDeltaTime(this.getRuntime());
            ctime += delta;
            ByteArrayOutputStream sw = new ByteArrayOutputStream();
            error.printStackTrace(new PrintStream(sw));
            try {
                telemetry.addData("ERROR", sw.toString("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            updateTelemetry(telemetry);
            if (ctime >= 5) {
                Object nl = null;
                nl.toString();
            }
        } else {

            try {
                loop_m();
            } catch (Exception e) {
                error = e;
            }

        }

    }

    @Override
    public void stop() {

    }

    private boolean died = false;

    Controllers controllers;
    Controller main_ct;

    boolean catapult_debug = false;
    ControllerState lastMain = null;

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    // loop_m separation preserved to remove error checking dirtiness
    public void loop_m() {
        if (died) return;

        ControllerState main = this.main_ct.getControllerState();

        if (main.isPressed(Button.UP) && !main.last().isPressed(Button.UP)) catapult_debug = !catapult_debug;

        Vec2D<Double> left;
        Vec2D<Double> right;

        left = main.joy(Joystick.LEFT);
        right = main.joy(Joystick.RIGHT);

        leftMotors.setPower(left.y());
        rightMotors.setPower(right.y());

        if (main.isPressed(Button.RS) && main.isPressed(Button.LS)) main = null;

        telemetry.addLine( "Values in range of -1 to +1" );
        telemetry.addData( "Speed", (-left.y()-right.y())/2 );
        telemetry.addData( "Turning Speed", (-left.y()+right.y())/2 );
        updateTelemetry(telemetry);

        try {
            Utils.getContext().getResources().getText(R.string.VuForiaKey);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}