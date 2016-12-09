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

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of calibrating a MR Compass
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 *   This code assumes there is a compass configured with the name "compass"
 *
 *   This code will put the compass into calibration mode, wait three seconds and then attempt
 *   to rotate two full turns clockwise.  This will allow the compass to do a magnetic calibration.
 *
 *   Once compete, the program will put the compass back into measurement mode and check to see if the
 *   calibration was successful.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Concept: Compass Calibration", group="Concept")
@Disabled
public class ConceptCompassCalibration extends LinearOpMode {

    /* Declare OpMode members. */
    HardwarePushbot     robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    CompassSensor       compass;

    final static double     MOTOR_POWER   = 0.2; // scale from 0 to 1
    static final long       HOLD_TIME_MS  = 3000;
    static final double     CAL_TIME_SEC  = 20;

    @Override
    public void runOpMode() {

        /* Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // get a reference to our Compass Sensor object.
        compass = hardwareMap.compassSensor.get("compass");

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to cal");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Set the compass to calibration mode
        compass.setMode(CompassSensor.CompassMode.CALIBRATION_MODE);
        telemetry.addData("Compass", "Compass in calibration mode");
        telemetry.update();

        sleep(HOLD_TIME_MS);  // Just do a sleep while we switch modes

        // Start the robot rotating clockwise
        telemetry.addData("Compass", "Calibration mode. Turning the robot...");
        telemetry.update();
        robot.leftMotor.setPower(MOTOR_POWER);
        robot.rightMotor.setPower(-MOTOR_POWER);

        // run until time expires OR the driver presses STOP;
        runtime.reset();
        while (opModeIsActive() && (runtime.time() < CAL_TIME_SEC)) {
            idle();
        }

        // Stop all motors and turn off claibration
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        compass.setMode(CompassSensor.CompassMode.MEASUREMENT_MODE);
        telemetry.addData("Compass", "Returning to measurement mode");
        telemetry.update();

        sleep(HOLD_TIME_MS);  // Just do a sleep while we switch modes

        // Report whether the Calibration was successful or not.
        if (compass.calibrationFailed())
            telemetry.addData("Compass", "Calibrate Failed. Try Again!");
        else
            telemetry.addData("Compass", "Calibrate Passed.");
        telemetry.update();
    }
}
