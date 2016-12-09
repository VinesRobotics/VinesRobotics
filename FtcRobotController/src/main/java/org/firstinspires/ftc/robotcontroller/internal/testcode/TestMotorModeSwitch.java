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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * This simple test stress-tests the motor mode switch logic in both the SDK
 * and in the motor controller firmware.
 */
@TeleOp(name = "TestMotorModeSwitch", group = "Tests")
@Disabled
public class TestMotorModeSwitch extends LinearOpMode
    {
    DcMotor motor;

    public void runOpMode() throws InterruptedException
        {
        motor = hardwareMap.dcMotor.get("v1.5 motor");

        DcMotor.RunMode[] modes = new DcMotor.RunMode[]
            {
            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1
            DcMotor.RunMode.RUN_USING_ENCODER,          // 2
            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3

            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1
            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3
            DcMotor.RunMode.RUN_USING_ENCODER,          // 2

            DcMotor.RunMode.RUN_USING_ENCODER,          // 2
            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1
            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3

            DcMotor.RunMode.RUN_USING_ENCODER,          // 2
            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3
            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1

            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3
            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1
            DcMotor.RunMode.RUN_USING_ENCODER,          // 2

            DcMotor.RunMode.STOP_AND_RESET_ENCODER,     // 3
            DcMotor.RunMode.RUN_USING_ENCODER,          // 2
            DcMotor.RunMode.RUN_WITHOUT_ENCODER,        // 1
            };

        waitForStart();

        int count = 0;
        while (opModeIsActive())
            {
            DcMotor.RunMode mode = modes[count % modes.length];

            motor.setMode(mode);
            telemetry.addData("count", "%d", count++);
            telemetry.addData("mode", "%s", motor.getMode());
            telemetry.update();
            idle();
            }
        }
    }
