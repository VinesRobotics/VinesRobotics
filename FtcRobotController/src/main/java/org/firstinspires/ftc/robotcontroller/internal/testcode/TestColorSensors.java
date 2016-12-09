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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name = "TestColorSensors", group = "Tests")
@Disabled
public class TestColorSensors extends LinearOpMode {

  public enum ColorSensorDevice {ADAFRUIT, HITECHNIC_NXT, MODERN_ROBOTICS_I2C};

  public ColorSensorDevice device = ColorSensorDevice.MODERN_ROBOTICS_I2C;

  ColorSensor colorSensor;
  DeviceInterfaceModule cdim;
  LED led;
  TouchSensor t;

  @Override
  public void runOpMode() throws InterruptedException {
    hardwareMap.logDevices();

    cdim = hardwareMap.deviceInterfaceModule.get("dim");
    switch (device) {
      case HITECHNIC_NXT:
        colorSensor = hardwareMap.colorSensor.get("nxt");
        break;
      case ADAFRUIT:
        colorSensor = hardwareMap.colorSensor.get("lady");
        break;
      case MODERN_ROBOTICS_I2C:
        colorSensor = hardwareMap.colorSensor.get("mr");
        break;
    }
    led = hardwareMap.led.get("led");
    t = hardwareMap.touchSensor.get("t");

    waitForStart();

    float hsvValues[] = {0,0,0};
    final float values[] = hsvValues;
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);
    while (opModeIsActive()) {

      enableLed(t.isPressed());

      switch (device) {
        case HITECHNIC_NXT:
          Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues);
          break;
        case ADAFRUIT:
          Color.RGBToHSV((colorSensor.red() * 255) / 800, (colorSensor.green() * 255) / 800, (colorSensor.blue() * 255) / 800, hsvValues);
          break;
        case MODERN_ROBOTICS_I2C:
          Color.RGBToHSV(colorSensor.red()*8, colorSensor.green()*8, colorSensor.blue()*8, hsvValues);
          break;
      }
      telemetry.addData("Clear", colorSensor.alpha());
      telemetry.addData("Red  ", colorSensor.red());
      telemetry.addData("Green", colorSensor.green());
      telemetry.addData("Blue ", colorSensor.blue());
      telemetry.addData("Hue", hsvValues[0]);

      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        }
      });
      waitOneFullHardwareCycle();
    }
  }

  private void enableLed(boolean value) {
    switch (device) {
      case HITECHNIC_NXT:
        colorSensor.enableLed(value);
        break;
      case ADAFRUIT:
        led.enable(value);
        break;
      case MODERN_ROBOTICS_I2C:
        colorSensor.enableLed(value);
        break;
    }
  }
}