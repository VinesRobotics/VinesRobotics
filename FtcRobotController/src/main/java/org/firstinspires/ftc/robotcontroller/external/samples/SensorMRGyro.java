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

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

/*
 * This is an example LinearOpMode that shows how to use
 * the Modern Robotics Gyro.
 *
 * The op mode assumes that the gyro sensor
 * is attached to a Device Interface Module I2C channel
 * and is configured with a name of "gyro".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
*/
@TeleOp(name = "Sensor: MR Gyro", group = "Sensor")
@Disabled
public class SensorMRGyro extends LinearOpMode {

  @Override
  public void runOpMode() {

    ModernRoboticsI2cGyro gyro;   // Hardware Device Object
    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // Gyro integrated heading
    int angleZ = 0;
    boolean lastResetState = false;
    boolean curResetState  = false;

    // get a reference to a Modern Robotics GyroSensor object.
    gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

    // start calibrating the gyro.
    telemetry.addData(">", "Gyro Calibrating. Do Not move!");
    telemetry.update();
    gyro.calibrate();

    // make sure the gyro is calibrated.
    while (!isStopRequested() && gyro.isCalibrating())  {
      sleep(50);
      idle();
    }

    telemetry.addData(">", "Gyro Calibrated.  Press Start.");
    telemetry.update();

    // wait for the start button to be pressed.
    waitForStart();

    while (opModeIsActive())  {

      // if the A and B buttons are pressed just now, reset Z heading.
      curResetState = (gamepad1.a && gamepad1.b);
      if(curResetState && !lastResetState)  {
        gyro.resetZAxisIntegrator();
      }
      lastResetState = curResetState;

      // get the x, y, and z values (rate of change of angle).
      xVal = gyro.rawX();
      yVal = gyro.rawY();
      zVal = gyro.rawZ();

      // get the heading info.
      // the Modern Robotics' gyro sensor keeps
      // track of the current heading for the Z axis only.
      heading = gyro.getHeading();
      angleZ  = gyro.getIntegratedZValue();

      telemetry.addData(">", "Press A & B to reset Heading.");
      telemetry.addData("0", "Heading %03d", heading);
      telemetry.addData("1", "Int. Ang. %03d", angleZ);
      telemetry.addData("2", "X av. %03d", xVal);
      telemetry.addData("3", "Y av. %03d", yVal);
      telemetry.addData("4", "Z av. %03d", zVal);
      telemetry.update();
    }
  }
}
