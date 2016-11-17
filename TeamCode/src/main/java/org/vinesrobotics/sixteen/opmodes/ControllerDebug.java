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

package org.vinesrobotics.sixteen.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.vinesrobotics.sixteen.hardware.controllers.Controller;
import org.vinesrobotics.sixteen.hardware.controllers.Controllers;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Button;
import org.vinesrobotics.sixteen.hardware.controllers.enums.CalibrationMode;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Joystick;
import org.vinesrobotics.sixteen.utils.Logging;

/**
 * Created by Vines HS Robotics on 9/30/2016.
 */

@TeleOp(name="ControllerDebug",group="Vines")
//@Disabled
public class ControllerDebug extends OpMode {
    //HardwarePushbot robot       = new HardwarePushbot();
    Controllers ctrls;

    public void init(){
        Logging.setTelemetry(telemetry);

        ctrls = Controllers.getControllerObjects(this);
        ctrls.calibrate(CalibrationMode.SIMPLE);
    }
    public void loop(){
        telemetry.addData("Right one",ctrls.a().getJoystick(Joystick.RIGHT));
        telemetry.addData("Left one",ctrls.a().getJoystick(Joystick.LEFT));
        telemetry.addData("A one",ctrls.a().getButton(Button.A).value);
        telemetry.addData("B one",ctrls.a().getButton(Button.B).value);
        telemetry.addData("X one",ctrls.a().getButton(Button.X).value);
        telemetry.addData("Y one",ctrls.a().getButton(Button.Y).value);
        telemetry.addData("LB one",ctrls.a().getButton(Button.LB).value);
        telemetry.addData("RB one",ctrls.a().getButton(Button.RB).value);
        telemetry.addData("LT one",ctrls.a().getButton(Button.LT).value);
        telemetry.addData("RT one",ctrls.a().getButton(Button.RT).value);
        telemetry.addData("LS one",ctrls.a().getButton(Button.LS).value);
        telemetry.addData("RS one",ctrls.a().getButton(Button.RS).value);
        telemetry.addData("UP one",ctrls.a().getButton(Button.UP).value);
        telemetry.addData("DOWN one",ctrls.a().getButton(Button.DOWN).value);
        telemetry.addData("LEFT one",ctrls.a().getButton(Button.LEFT).value);
        telemetry.addData("RIGHT one",ctrls.a().getButton(Button.RIGHT).value);
        telemetry.addData("START one",ctrls.a().getButton(Button.START).value);
        telemetry.addData("BACK one",ctrls.a().getButton(Button.BACK).value);

        telemetry.addData("Right two",ctrls.b().getJoystick(Joystick.RIGHT));
        telemetry.addData("Left two",ctrls.b().getJoystick(Joystick.LEFT));
        telemetry.addData("A two",ctrls.b().getButton(Button.A).value);
        telemetry.addData("B two",ctrls.b().getButton(Button.B).value);
        telemetry.addData("X two",ctrls.b().getButton(Button.X).value);
        telemetry.addData("Y two",ctrls.b().getButton(Button.Y).value);
        telemetry.addData("LB two",ctrls.b().getButton(Button.LB).value);
        telemetry.addData("RB two",ctrls.b().getButton(Button.RB).value);
        telemetry.addData("LT two",ctrls.b().getButton(Button.LT).value);
        telemetry.addData("RT two",ctrls.b().getButton(Button.RT).value);
        telemetry.addData("LS two",ctrls.b().getButton(Button.LS).value);
        telemetry.addData("RS two",ctrls.b().getButton(Button.RS).value);
        telemetry.addData("UP two",ctrls.b().getButton(Button.UP).value);
        telemetry.addData("DOWN two",ctrls.b().getButton(Button.DOWN).value);
        telemetry.addData("LEFT two",ctrls.b().getButton(Button.LEFT).value);
        telemetry.addData("RIGHT two",ctrls.b().getButton(Button.RIGHT).value);
        telemetry.addData("START two",ctrls.b().getButton(Button.START).value);
        telemetry.addData("BACK two",ctrls.b().getButton(Button.BACK).value);

        telemetry.update();
    }
}
