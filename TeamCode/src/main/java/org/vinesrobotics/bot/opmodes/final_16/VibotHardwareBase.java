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

package org.vinesrobotics.bot.opmodes.final_16;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.vinesrobotics.bot.hardware.robot.final_16.Catapult;
import org.vinesrobotics.bot.hardware.Hardware;
import org.vinesrobotics.bot.hardware.HardwareElement;
import org.vinesrobotics.bot.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.bot.utils.Logging;
import org.vinesrobotics.bot.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;

public abstract class VibotHardwareBase extends OpMode {

    public MotorDeviceGroup leftMotors;
    public MotorDeviceGroup rightMotors;

    DcMotor itk;
    Catapult catapult;
    final int catapult_pos = -127;
    final int catapult_root = 0;

    public Hardware robot = new Hardware();

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
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

        catapult = new Catapult((DcMotor) robot.getDeviceWithKeys("catapult","motor"),catapult_pos,catapult_root);
        catapult.catapult().setDirection(DcMotor.Direction.FORWARD);

        init_m();
    }

    public void init_loop() {
        init_loop_m();
    }

    public abstract void init_m();
    public abstract void init_loop_m();
    public abstract void start_m();

    @Override
    public void start() {
        //a1_pos = arm_1.getPosition();
        //arm_1.setPosition(Utils.limitTo(1-a1_pos,0,1));
        //a2_pos = arm_2.getPosition();
        //arm_2.setPosition(Utils.limitTo(1-a2_pos,0,1));
        start_m();
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

    protected abstract void loop_m();

    public void stop_m() {}

    @Override
    public void stop() {

        stop_m();
    }
}
