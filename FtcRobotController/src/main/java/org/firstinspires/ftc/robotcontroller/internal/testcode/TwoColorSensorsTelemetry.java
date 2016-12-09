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

import android.graphics.Color;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A simple test of a pair of color sensors
 */
@Autonomous(name="Test Two Color Sensors", group ="Tests")
@Disabled
public class TwoColorSensorsTelemetry extends LinearOpMode
    {
    AMSColorSensor leftColorSensor;
    AMSColorSensor rightColorSensor;

    @Override
    public void runOpMode() throws InterruptedException
        {
        leftColorSensor  = (AMSColorSensor)hardwareMap.colorSensor.get("leftColorSensor");
        rightColorSensor = (AMSColorSensor)hardwareMap.colorSensor.get("rightColorSensor");

        AMSColorSensor.Parameters params = leftColorSensor.getParameters();
        // possibly change some (notably gain and / or integration time), then
        // leftColorSensor.initialize(params);

        params = rightColorSensor.getParameters();
        // possibly change some (notably gain and / or integration time), then
        // rightColorSensor.initialize(params);

        waitForStart();

        while (opModeIsActive())
            {
            int left = leftColorSensor.argb();
            int right = rightColorSensor.argb();
            telemetry.addData("left", String.format("a=%d r=%d g=%d b=%d", Color.alpha(left), Color.red(left), Color.green(left), Color.blue(left)));
            telemetry.addData("right", String.format("a=%d r=%d g=%d b=%d", Color.alpha(right), Color.red(right), Color.green(right), Color.blue(right)));
            this.updateTelemetry(telemetry);

            Thread.sleep(500);
            }

        }
    }
