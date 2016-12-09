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

/**
 * A simple test of a pair of motors
 */
@Autonomous(name="Test Two Motors", group ="Tests")
@Disabled
public class TestDriveLinear extends LinearOpMode
    {
    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void runOpMode() throws InterruptedException
        {
        motorLeft = this.hardwareMap.dcMotor.get("motorLeft");
        motorRight = this.hardwareMap.dcMotor.get("motorRight");

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runMotorsBothDirections(1.0);

        Thread.sleep(500);
        runMotorsBothDirections(0.1);

        Thread.sleep(500);
        runMotorsBothDirections(1.0);
        }

    void runMotorsBothDirections(double power) throws InterruptedException
        {
        motorLeft.setPower(-power);
        motorRight.setPower(power);

        Thread.sleep(2000);
        motorLeft.setPower(0);
        motorRight.setPower(0);

        Thread.sleep(500);
        motorLeft.setPower(power);
        motorRight.setPower(-power);

        Thread.sleep(2000);
        motorLeft.setPower(0);
        motorRight.setPower(0);
        }
    }
