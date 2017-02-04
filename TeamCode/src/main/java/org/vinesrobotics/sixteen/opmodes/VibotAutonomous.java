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
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.vinesrobotics.sixteen.hardware.Hardware;
import org.vinesrobotics.sixteen.hardware.HardwareElement;
import org.vinesrobotics.sixteen.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.sixteen.utils.Logging;
import org.vinesrobotics.sixteen.utils.Utils;

import java.security.InvalidKeyException;
import java.util.List;

@Autonomous(name="Autonomous",group="Vines")
public class VibotAutonomous extends VibotHardwareBase {

    MotorDeviceGroup fwd;

    @Override
    public void init_m() {
        Logging.setTelemetry(telemetry);
        fwd = new MotorDeviceGroup();
        fwd.addDevice(lmot);
        fwd.addDevice(rmot);
    }

    @Override
    public void init_loop_m() {

    }

    public void start_m(){
        //lmot.setPower(-1);
        //rmot.setPower(1);
        //itk.setPower(1);
        Utils.getDeltaTime(this.getRuntime());
    }
    double ctime = 0;

    @Override
    public void loop_m() {
        double delta = Utils.getDeltaTime(this.getRuntime());
        ctime += delta;
        Logging.log(String.valueOf(Math.floor(ctime)));

        // Start
        if (Utils.checkInRange(ctime,0,1.5)) {
            fwd.setPower(-1);
        }
        if (Utils.checkInRange(ctime,1.5,2.9)) {
            fwd.setPower(-1);
        }
        if (ctime > 2.9) {
            fwd.setPower(0);
        }

    }

}
