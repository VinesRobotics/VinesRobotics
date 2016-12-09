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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.LightSensor;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a legacy (NXT-compatible) Light Sensor.
 * It assumes that the light sensor is configured with a name of "sensor_light".
 *
 * You can use the X button on gamepad1 to turn Toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Sensor: LEGO light", group = "Sensor")
@Disabled
public class SensorLEGOLight extends LinearOpMode {

  LightSensor lightSensor;  // Hardware Device Object

  @Override
  public void runOpMode() {

    // bPrevState and bCurrState represent the previous and current state of the button.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = true;

    // get a reference to our Light Sensor object.
    lightSensor = hardwareMap.lightSensor.get("sensor_light");

    // Set the LED state in the beginning.
    lightSensor.enableLed(bLedOn);

    // wait for the start button to be pressed.
    waitForStart();

    // while the op mode is active, loop and read the light levels.
    // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
    while (opModeIsActive()) {

      // check the status of the x button .
      bCurrState = gamepad1.x;

      // check for button state transitions.
      if ((bCurrState == true) && (bCurrState != bPrevState))  {

        // button is transitioning to a pressed state.  Toggle LED
        bLedOn = !bLedOn;
        lightSensor.enableLed(bLedOn);
      }

      // update previous state variable.
      bPrevState = bCurrState;

      // send the info back to driver station using telemetry function.
      telemetry.addData("LED", bLedOn ? "On" : "Off");
      telemetry.addData("Raw", lightSensor.getRawLightDetected());
      telemetry.addData("Normal", lightSensor.getLightDetected());

      telemetry.update();
    }
  }
}
