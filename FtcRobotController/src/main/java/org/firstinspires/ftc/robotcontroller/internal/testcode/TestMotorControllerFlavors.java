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
package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cControllerPortDevice;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * {@link TestMotorControllerFlavors} is a simple test that explores the behavior of
 */
@Autonomous(name = "Test MC Flavors", group = "Tests")
@Disabled
public class TestMotorControllerFlavors extends LinearOpMode
    {
    // We don't require all of these motors to be attached; we'll deal with what we find
    DcMotor legacyMotor;
    DcMotor v15Motor;
    DcMotor v2Motor;

    @Override public void runOpMode() throws InterruptedException
        {
        legacyMotor = findMotor("legacy motor");
        v15Motor    = findMotor("v1.5 motor");
        v2Motor     = findMotor("v2 motor");

        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Make the encoders be something other than zero
        reportMotors();
        setPowers(0.5);
        Thread.sleep(3000);
        setPowers(0);
        reportMotors();
        Thread.sleep(3000);
        reportMotors();

        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sit there and watch the motors
        while (opModeIsActive())
            {
            reportMotors();
            idle();
            }
        }

    DcMotor findMotor(String motorName)
        {
        try {
            DcMotor motor = hardwareMap.dcMotor.get(motorName);

            // Find which device is the USB device
            RobotArmingStateNotifier usbDevice = null;
            if (motor.getController() instanceof RobotArmingStateNotifier)
                {
                usbDevice = (RobotArmingStateNotifier) motor.getController();
                }
            else if (motor.getController() instanceof I2cControllerPortDevice)
                {
                I2cControllerPortDevice i2cControllerPortDevice = (I2cControllerPortDevice)motor.getController();
                if (i2cControllerPortDevice.getI2cController() instanceof RobotArmingStateNotifier)
                    {
                    usbDevice = (RobotArmingStateNotifier) i2cControllerPortDevice.getI2cController();
                    }
                }

            // Weed out controllers that aren't actually physically there (and so in pretend state)
            if (usbDevice != null)
                {
                if (usbDevice.getArmingState() != RobotArmingStateNotifier.ARMINGSTATE.ARMED)
                    {
                    return null;
                    }
                }

            return motor;
            }
        catch (Throwable ignored)
            {
            }
        return null;
        }

    void setModes(DcMotor.RunMode mode)
        {
        if (legacyMotor != null) legacyMotor.setMode(mode);
        if (v15Motor != null)    v15Motor.setMode(mode);
        if (v2Motor != null)     v2Motor.setMode(mode);
        }

    void setPowers(double power)
        {
        if (legacyMotor != null) legacyMotor.setPower(power);
        if (v15Motor != null)    v15Motor.setPower(power);
        if (v2Motor != null)     v2Motor.setPower(power);
        }

    void reportMotors()
        {
        telemetry.addData("Motor Report", "");
        reportMotor("legacy motor: ", legacyMotor);
        reportMotor("v1.5 motor: ", v15Motor);
        reportMotor("v2 motor: ", v2Motor);
        telemetry.update();
        }

    void reportMotor(String caption, DcMotor motor)
        {
        if (motor != null)
            {
            int position = motor.getCurrentPosition();
            DcMotor.RunMode mode = motor.getMode();

            telemetry.addLine(caption)
                .addData("pos", "%d", position)
                .addData("mode", "%s", mode.toString());

            RobotLog.i("%s pos=%d mode=%s", caption, position, mode.toString());
            }
        }

    }

