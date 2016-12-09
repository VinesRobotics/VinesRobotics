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
import com.qualcomm.robotcore.hardware.configuration.HiTechnicConstants;

/**
 * Drives in a straight line at 1.0 power but with various max speed levels.
 */
@Autonomous(name="Test Max Speed", group ="Tests")
@Disabled
public class TestMaxSpeed extends LinearOpMode
    {
    // All hardware variables can only be initialized inside the main() function,
    // not here at their member variable declarations.
    DcMotor motorLeft = null;
    DcMotor motorRight = null;

    @Override
    public void runOpMode() throws InterruptedException
        {
        // Initialize our hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names you assigned during the robot configuration
        // step you did in the FTC Robot Controller app on the phone.
        this.motorLeft = this.hardwareMap.dcMotor.get("motorLeft");
        this.motorRight = this.hardwareMap.dcMotor.get("motorRight");

        // Configure the knobs of the hardware according to how you've wired your robot.
        DcMotor.RunMode mode = DcMotor.RunMode.RUN_USING_ENCODER;
        this.motorLeft.setMode(mode);
        this.motorRight.setMode(mode);

        // One of the two motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorLeft.setDirection(DcMotor.Direction.REVERSE);

        // Wait until we've been given the ok to go
        this.waitForStart();

        // Drive in a line at various speeds
        for (int degreesPerSecond = 300; degreesPerSecond <= 1000; degreesPerSecond += 100)
            {
            int ticksPerSecond = ticksPerSecFromDegsPerSec(degreesPerSecond);
            this.motorLeft.setMaxSpeed(ticksPerSecond);
            this.motorRight.setMaxSpeed(ticksPerSecond);

            telemetry.addData("deg/s", degreesPerSecond);
            telemetry.addData("ticks/s", ticksPerSecond);
            updateTelemetry(telemetry);

            // Drive for a while, then stop
            this.motorLeft.setPower(1);
            this.motorRight.setPower(1);

            long msDeadline = System.currentTimeMillis() + 3000;
            while (System.currentTimeMillis() < msDeadline)
                {
                Thread.yield();
                telemetry.addData("deg/s",   degreesPerSecond);
                telemetry.addData("ticks/s", ticksPerSecond);
                telemetry.addData("left",    motorLeft.getCurrentPosition());
                telemetry.addData("right",   motorRight.getCurrentPosition());
                updateTelemetry(telemetry);
                }

            this.motorRight.setPower(0);
            this.motorLeft.setPower(0);

            Thread.sleep(3000);
            }
        }

    /** Computes the number of encoder ticks per second that correspond to given desired
     * rotational rate. To perform this computation, the number of encoder ticks per second
     * is required, which is motor dependent. Be sure to adjust the values here accordingly
     * for the motors you are actually using. */
    int ticksPerSecFromDegsPerSec(int degreesPerSecond)
        {
        final int encoderTicksPerRevolution = HiTechnicConstants.TETRIX_MOTOR_TICKS_PER_REVOLUTION; // assume Tetrix motors; change if you use different motors!
        final int degreesPerRevolution      = 360;

        return encoderTicksPerRevolution * degreesPerSecond / degreesPerRevolution;
        }

    }