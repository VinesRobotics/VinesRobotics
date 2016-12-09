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

import android.hardware.Sensor;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.*;

/**
 * This class demonstrates how to manually register opmodes.
 *
 * Note: It is NOT required to manually register OpModes, but this method is provided to support teams that
 * want to have centralized control over their opmode lists.
 * This sample is ALSO handy to use to selectively register the other sample opmodes.
 * Just copy/paste this sample to the TeamCode module and then uncomment the opmodes you wish to run.
 *
 * How it works:
 * The registerMyOpmodes method will be called by the system during the automatic registration process
 * You can register any of the opmodes in your App. by making manager.register() calls inside registerMyOpmodes();
 *
 * Note:
 * Even though you are performing a manual Registration, you should set the Annotations on your classes so they
 * can be placed into the correct Driver Station OpMode list...  eg:
 *
 * @Autonomous(name="DriveAndScoreRed", group ="Red")
 * or
 * @TeleOp(name="FastDrive", group ="A-Team")
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Then uncomment and copy the manager.register() call to register as many of your OpModes as you like.
 * You can even use it to temporarily register samples directly from the robotController/external/samples folder.
 */
public class ConceptRegisterOpModes
{

  @OpModeRegistrar
  public static void registerMyOpModes(OpModeManager manager) {
    // Un-comment any line to enable that sample.
    // Or add your own lines to register your Team opmodes.

    // Basic Templates
    // manager.register("Iterative Opmode",       TemplateOpMode_Iterative.class);
    // manager.register("Linear Opmode",          TemplateOpMode_Linear.class);

    // Driving Samples
    // manager.register("Teleop POV",             PushbotTeleopPOV_Linear.class);
    // manager.register("Teleop Tank",            PushbotTeleopTank_Iterative.class);
    // manager.register("Auto Drive Gyro",        PushbotAutoDriveByGyro_Linear.class);
    // manager.register("Auto Drive Encoder",     PushbotAutoDriveByEncoder_Linear.class);
    // manager.register("Auto Drive Time",        PushbotAutoDriveByTime_Linear.class);
    // manager.register("Auto Drive Line",        PushbotAutoDriveToLine_Linear.class);
    // manager.register("K9 Telop",               K9botTeleopTank_Linear.class);

    // Sensor Samples
    // manager.register("AdaFruit IMU",           SensorAdafruitIMU.class);
    // manager.register("AdaFruit IMU Cal",       SensorAdafruitIMUCalibration.class);
    // manager.register("AdaFruit Color",         SensorAdafruitRGB.class);
    // manager.register("DIM DIO",                SensorDIO.class);
    // manager.register("HT Color",               SensorHTColor.class);
    // manager.register("LEGO Light",             SensorLEGOLight.class);
    // manager.register("LEGO Touch",             SensorLEGOTouch.class);
    // manager.register("MR Color",               SensorMRColor.class);
    // manager.register("MR Gyro",                SensorMRGyro.class);
    // manager.register("MR IR Seeker",           SensorMRIrSeeker.class);
    // manager.register("MR ODS",                 SensorMROpticalDistance.class);

    //  Concept Samples
    // manager.register("Null Op",                ConceptNullOp.class);
    // manager.register("Compass Calibration",    ConceptCompassCalibration.class);
    // manager.register("DIM as Indicator",       ConceptDIMAsIndicator.class);
    // manager.register("I2C Address Change",     ConceptI2cAddressChange.class);
    // manager.register("Ramp Motor Speed",       ConceptRampMotorSpeed.class);
    // manager.register("Scan Servo",             ConceptScanServo.class);
    // manager.register("Telemetry",              ConceptTelemetry.class);
    // manager.register("Vuforia Navigation",     ConceptVuforiaNavigation.class);
  }
}
