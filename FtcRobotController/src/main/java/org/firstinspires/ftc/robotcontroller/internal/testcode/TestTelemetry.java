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

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.TelemetryInternal;

import java.util.Locale;

/**
 * {@link TestTelemetry} is a simple test of telemetry.
 */
@Autonomous(name = "Test Telemetry", group = "Tests")
@Disabled
public class TestTelemetry extends LinearOpMode
    {
    @Override public void runOpMode() throws InterruptedException
        {
        waitForStart();

        testNonAutoClear();
        testLogDashboardInteraction();

        while (opModeIsActive())
            {
            idle();
            }
        }

    void testNonAutoClear() throws InterruptedException
        {
        ((TelemetryInternal)telemetry).resetTelemetryForOpMode();

        telemetry.setAutoClear(false);

        Telemetry.Item counting = telemetry.addData("counting", 1).setRetained(true);
        telemetry.addData("unretained", "red");
        telemetry.addData("retained", new Func<String>() {
            @Override public String value() {
                return String.format(Locale.getDefault(), "%dms", System.currentTimeMillis());
            }});
        telemetry.addData("unretained", "green");
        telemetry.update();
        delay();

        counting.setValue(2);
        telemetry.update();
        delay();

        counting.setValue(3);
        telemetry.update();
        delay();

        telemetry.clear();
        delay();

        counting.setValue(4);
        telemetry.update();
        delay();
        }

    void testLogDashboardInteraction() throws InterruptedException
        {
        ((TelemetryInternal)telemetry).resetTelemetryForOpMode();

        int count = 0;

        telemetry.log().add("one");
        delay();

        telemetry.addData("count", count++);
        delay();

        telemetry.addData("count", count++);
        delay();

        telemetry.log().add("two");
        delay();

        telemetry.update();
        delay();

        telemetry.log().add("three (should see count 0 & 1)");
        delay();

        telemetry.addData("count", count++);
        delay();

        telemetry.log().add("four (should see count 0 & 1)");
        delay();

        telemetry.update();
        delay();

        telemetry.log().add("five (should see count 2)");
        delay();
        }

    void delay() throws InterruptedException
        {
        Thread.sleep(2000);
        }

    }
