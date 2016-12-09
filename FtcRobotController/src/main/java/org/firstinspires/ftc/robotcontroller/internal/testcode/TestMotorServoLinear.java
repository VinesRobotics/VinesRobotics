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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * A simple test that runs one motor and one servo at a time.
 */
@Autonomous(name = "TestMotorServo", group = "Tests")
@Disabled
public class TestMotorServoLinear extends LinearOpMode
    {
    @Override
    public void runOpMode() throws InterruptedException
        {
        DcMotor motor = this.hardwareMap.dcMotor.get("motorRight");
        Servo servo = this.hardwareMap.servo.get("servo");

        waitForStart();

        double servoPosition = 0;
        servo.setPosition(servoPosition);

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ElapsedTime elapsedTime = new ElapsedTime();
        int spinCount = 0;

        while (this.opModeIsActive())
            {
            servoPosition += 1. / 256.;
            if (servoPosition >= 1)
                servoPosition = 0;
            servo.setPosition(servoPosition);

            motor.setPower(0.15);

            spinCount++;
            double ms = elapsedTime.milliseconds();
            telemetry.addData("position", format(servoPosition));
            telemetry.addData("#spin",    format(spinCount));
            telemetry.addData("ms/spin",  format(ms / spinCount));
            this.updateTelemetry(telemetry);
            }

        motor.setPower(0);
        }

    static String format(double d)
        {
        return String.format("%.3f", d);
        }
    static String format(int i)
        {
        return String.format("%d", i);
        }
    }
