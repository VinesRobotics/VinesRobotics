/*
 * Copyright (c) 2017 Vines High School Robotics Team
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

package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.vinesrobotics.sixteen.utils.Utils;

public class Catapult {

    private MotorPositionFix catapult;
    private int catapult_pos = 255;
    private int root = 0;
    private boolean manual = false;
    private boolean man_ready = false;

    public boolean isManual(){
        return manual;
    }
    public boolean isManualReady(){
        return man_ready;
    }
    public DcMotor catapult() {
        return catapult;
    }

    public Catapult(DcMotor mot, int pos, int root) {
        MotorPositionFix pfix = new MotorPositionFix(mot);
        catapult = pfix;
        DcMotor.RunMode rm = mot.getMode();
        mot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pfix.setMode(rm);
        catapult_pos = pos;
        this.root = root;
        ready();
    }

    public void ready() {
        catapult.setTargetPosition(root);
    }
    private int fired = 0;
    private boolean prev_man = false;
    public void fire() {
        prev_man = manual;
        disableManual();
        catapult.setTargetPosition(catapult_pos);
        fired = 50;
    }
    public void tick() {
        if (manual && catapult.getCurrentPosition() == catapult.getTargetPosition()) {
            man_ready = true;
        }
        if (!manual)
            catapult.tick();
        if (fired == 1) {
            catapult.setTargetPosition(root);
            if (prev_man)
                enableManual();
        }
        if (fired > 0) fired--;
    }
    public void enableManual() {
        catapult.setTargetPosition(root);
        manual = true;
        man_ready = false;
    }
    public void disableManual() {
        manual = false;
    }
    public void toggleManual() {
        if (manual) {
            disableManual();
            ready();
        }
        else
            enableManual();
    }
    public void close() {
        catapult.setTargetPosition(root);
        catapult.deregister();
    }
}
