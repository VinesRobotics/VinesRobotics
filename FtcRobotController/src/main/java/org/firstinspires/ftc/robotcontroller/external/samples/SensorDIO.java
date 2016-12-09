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
import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDeviceInterfaceModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/*
 * This is an example LinearOpMode that shows how to use the digital inputs and outputs on the
 * the Modern Robotics Device Interface Module.  In addition, it shows how to use the Red and Blue LED
 *
 * This op mode assumes that there is a Device Interface Module attached, named 'dim'.
 * On this DIM there is a digital input named 'digin' and an output named 'digout'
 *
 * To fully exercise this sample, connect pin 3 of the digin connector to pin 3 of the digout.
 * Note: Pin 1 is indicated by the black stripe, so pin 3 is at the opposite end.
 *
 * The X button on the gamepad will be used to activate the digital output pin.
 * The Red/Blue LED will be used to indicate the state of the digital input pin.
 * Blue = false (0V), Red = true (5V)
 * If the two pins are linked, the gamepad will change the LED color.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
*/
@TeleOp(name = "Sensor: DIM DIO", group = "Sensor")
@Disabled
public class SensorDIO extends LinearOpMode {

final int BLUE_LED_CHANNEL = 0;
final int RED_LED_CHANNEL = 1;

  @Override
  public void runOpMode() {

    boolean               inputPin;             // Input State
    boolean               outputPin;            // Output State
    DeviceInterfaceModule dim;                  // Device Object
    DigitalChannel        digIn;                // Device Object
    DigitalChannel        digOut;               // Device Object

    // get a reference to a Modern Robotics DIM, and IO channels.
    dim = hardwareMap.get(DeviceInterfaceModule.class, "dim");   //  Use generic form of device mapping
    digIn  = hardwareMap.get(DigitalChannel.class, "digin");     //  Use generic form of device mapping
    digOut = hardwareMap.get(DigitalChannel.class, "digout");    //  Use generic form of device mapping

    digIn.setMode(DigitalChannelController.Mode.INPUT);          // Set the direction of each channel
    digOut.setMode(DigitalChannelController.Mode.OUTPUT);

    // wait for the start button to be pressed.
    telemetry.addData(">", "Press play, and then user X button to set DigOut");
    telemetry.update();
    waitForStart();

    while (opModeIsActive())  {

        outputPin = gamepad1.x ;        //  Set the output pin based on x button
        digOut.setState(outputPin);
        inputPin = digIn.getState();    //  Read the input pin

        // Display input pin state on LEDs
        if (inputPin) {
            dim.setLED(RED_LED_CHANNEL, true);
            dim.setLED(BLUE_LED_CHANNEL, false);
        }
        else {
            dim.setLED(RED_LED_CHANNEL, false);
            dim.setLED(BLUE_LED_CHANNEL, true);
        }

        telemetry.addData("Output", outputPin );
        telemetry.addData("Input", inputPin );
        telemetry.addData("LED",   inputPin ? "Red" : "Blue" );
        telemetry.update();
    }
  }
}
