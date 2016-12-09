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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/*
 * This is an example LinearOpMode that shows how to use
 * the Modern Robotics ITR Seeker
 *
 * The op mode assumes that the IR Seeker
 * is configured with a name of "sensor_ir".
 *
 * Set the switch on the Modern Robotics IR beacon to 1200 at 180.  <br>
 * Turn on the IR beacon.
 * Make sure the side of the beacon with the LED on is facing the robot. <br>
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Sensor: MR IR Seeker", group = "Sensor")
@Disabled
public class SensorMRIrSeeker extends LinearOpMode {

  @Override
  public void runOpMode() {

    IrSeekerSensor irSeeker;    // Hardware Device Object

    // get a reference to our GyroSensor object.
    irSeeker = hardwareMap.irSeekerSensor.get("sensor_ir");

    // wait for the start button to be pressed.
    waitForStart();

    while (opModeIsActive())  {

      // Ensure we have a IR signal
      if (irSeeker.signalDetected())
      {
        // Display angle and strength
        telemetry.addData("Angle",    irSeeker.getAngle());
        telemetry.addData("Strength", irSeeker.getStrength());
      }
      else
      {
        // Display loss of signal
        telemetry.addData("Seeker", "Signal Lost");
      }

      telemetry.update();
    }
  }
}
