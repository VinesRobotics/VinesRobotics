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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import net.sf.tweety.logics.commons.syntax.NumberTerm;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.pl.sat.*;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;

import org.vinesrobotics.sixteen.hardware.Hardware;
import org.vinesrobotics.sixteen.hardware.HardwareElement;
import org.vinesrobotics.sixteen.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.sixteen.utils.Logging;
import org.vinesrobotics.sixteen.utils.Utils;

import java.security.InvalidKeyException;
import java.util.List;

@Autonomous(name="Autonomous",group="Vines")
public class VibotAutonomous extends OpMode {

    Hardware robot  = new Hardware();

    MotorDeviceGroup lmot;
    MotorDeviceGroup rmot;
    DcMotor itk;

    @Override
    public void init() {
        Logging.setTelemetry(telemetry);

        try {
            robot.registerHardwareKeyName("intake");
        } catch (InvalidKeyException e) {}
        robot.initHardware(hardwareMap);

        List<HardwareElement> lefts = robot.getDevicesWithAllKeys("left","drive");
        lmot = new MotorDeviceGroup();
        for (HardwareElement he : lefts) {
            lmot.addDevice((DcMotor)he.get());
        }

        List<HardwareElement> right = robot.getDevicesWithAllKeys("right","drive");
        rmot = new MotorDeviceGroup();
        for (HardwareElement he : right) {
            rmot.addDevice((DcMotor)he.get());
        };
        itk = robot.getDeviceWithKeys("intake","motor");

    }

    public void start(){
        lmot.setPower(-1);
        rmot.setPower(1);
        itk.setPower(1);
        Utils.getDeltaTime(this.getRuntime());
    }

    @Override
    public void loop() {
        double delta = Utils.getDeltaTime(this.getRuntime());
        Logging.log(String.valueOf(delta));

        SatSolver ss = new Sat4jSolver();
        Proposition ps = new Proposition();
        ps.addArgument(new NumberTerm(1));
        ps.setPredicate(new Predicate());
        System.out.println(ss.isConsistent(ps));

    }
}
