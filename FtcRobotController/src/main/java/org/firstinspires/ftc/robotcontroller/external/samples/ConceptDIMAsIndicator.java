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

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This OpMode illustrates using the Device Interface Module as a signalling device.
 * The code is structured as a LinearOpMode
 *
 * This code assumes a DIM name "dim".
 *
 * There are many examples where the robot might like to signal the driver, without requiring them
 * to look at the driver station.  This might be something like a "ball in hopper" condition or a
 * "ready to shoot" condition.
 *
 * The DIM has two user settable indicator LEDs (one red one blue).  These can be controlled
 * directly from your program.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "Concept: DIM As Indicator", group = "Concept")
@Disabled
public class ConceptDIMAsIndicator extends LinearOpMode {

    static final int    BLUE_LED    = 0;     // Blue LED channel on DIM
    static final int    RED_LED     = 1;     // Red LED Channel on DIM

    // Create timer to toggle LEDs
    private ElapsedTime runtime = new ElapsedTime();

    // Define class members
    DeviceInterfaceModule   dim;

    @Override
    public void runOpMode() {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        dim = this.hardwareMap.deviceInterfaceModule.get("dim");

        // Toggle LEDs while Waiting for the start button
        telemetry.addData(">", "Press Play to test LEDs." );
        telemetry.update();

        while (!isStarted()) {
            // Determine if we are on an odd or even second
            boolean even = (((int)(runtime.time()) & 0x01) == 0);
            dim.setLED(RED_LED,   even); // Red for even
            dim.setLED(BLUE_LED, !even); // Blue for odd
            idle();
        }

        // Running now
        telemetry.addData(">", "Press X for Blue, B for Red." );
        telemetry.update();

        // Now just use red and blue buttons to set red and blue LEDs
        while(opModeIsActive()){
            dim.setLED(BLUE_LED, gamepad1.x);
            dim.setLED(RED_LED,  gamepad1.b);
            idle();
        }

        // Turn off LEDs;
        dim.setLED(BLUE_LED, false);
        dim.setLED(RED_LED,  false);
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
