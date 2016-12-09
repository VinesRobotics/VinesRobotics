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

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;

/**
 * The {@link SensorMRCompass} op mode provides a demonstration of the
 * functionality provided by the Modern Robotics compass sensor.
 *
 * The op mode assumes that the MR compass is configured with a name of "compass".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * @see <a href="http://www.modernroboticsinc.com/compass">MR Compass Sensor</a>
 */
@Autonomous(name = "Sensor: MR compass", group = "Sensor")
@Disabled   // comment out or remove this line to enable this opmode
public class SensorMRCompass extends LinearOpMode {

    ModernRoboticsI2cCompassSensor compass;
    ElapsedTime                    timer = new ElapsedTime();

    @Override public void runOpMode() {

        // get a reference to our compass
        compass = hardwareMap.get(ModernRoboticsI2cCompassSensor.class, "compass");

        telemetry.log().setCapacity(20);
        telemetry.log().add("The compass sensor operates quite well out-of-the");
        telemetry.log().add("box, as shipped by the manufacturer. Precision can");
        telemetry.log().add("however be somewhat improved with calibration.");
        telemetry.log().add("");
        telemetry.log().add("To calibrate the compass once the opmode is");
        telemetry.log().add("started, make sure the compass is level, then");
        telemetry.log().add("press 'A' on the gamepad. Next, slowly rotate the ");
        telemetry.log().add("compass in a full 360 degree circle while keeping");
        telemetry.log().add("it level. When complete, press 'B'.");

        // wait for the start button to be pressed
        waitForStart();
        telemetry.log().clear();

        while (opModeIsActive()) {

            // If the A button is pressed, start calibration and wait for the A button to rise
            if (gamepad1.a && !compass.isCalibrating()) {

                telemetry.log().clear();
                telemetry.log().add("Calibration started");
                telemetry.log().add("Slowly rotate compass 360deg");
                telemetry.log().add("Press 'B' when complete");
                compass.setMode(CompassSensor.CompassMode.CALIBRATION_MODE);
                timer.reset();

                while (gamepad1.a && opModeIsActive()) {
                    doTelemetry();
                    idle();
                }
            }

            // If the B button is pressed, stop calibration and wait for the B button to rise
            if (gamepad1.b && compass.isCalibrating()) {

                telemetry.log().clear();
                telemetry.log().add("Calibration complete");
                compass.setMode(CompassSensor.CompassMode.MEASUREMENT_MODE);

                if (compass.calibrationFailed()) {
                    telemetry.log().add("Calibration failed");
                    compass.writeCommand(ModernRoboticsI2cCompassSensor.Command.NORMAL);
                }

                while (gamepad1.a && opModeIsActive()) {
                    doTelemetry();
                    idle();
                }
            }

            doTelemetry();
        }
    }

    protected void doTelemetry() {

        if (compass.isCalibrating()) {

            telemetry.addData("compass", "calibrating %s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");

        } else {

            // getDirection() returns a traditional compass heading in the range [0,360),
            // with values increasing in a CW direction
            telemetry.addData("heading", "%.1f", compass.getDirection());

            // getAcceleration() returns the current 3D acceleration experienced by
            // the sensor. This is used internally to the sensor to compute its tilt and thence
            // to correct the magnetometer reading to produce tilt-corrected values in getDirection()
            Acceleration accel = compass.getAcceleration();
            double accelMagnitude = Math.sqrt(accel.xAccel*accel.xAccel + accel.yAccel*accel.yAccel + accel.zAccel*accel.zAccel);
            telemetry.addData("accel", accel);
            telemetry.addData("accel magnitude", "%.3f", accelMagnitude);

            // getMagneticFlux returns the 3D magnetic field flux experienced by the sensor
            telemetry.addData("mag flux", compass.getMagneticFlux());
        }

        // the command register provides status data
        telemetry.addData("command", "%s", compass.readCommand());

        telemetry.update();
    }
}
