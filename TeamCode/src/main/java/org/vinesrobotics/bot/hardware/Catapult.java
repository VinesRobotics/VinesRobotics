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

package org.vinesrobotics.bot.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.vinesrobotics.bot.utils.Range;

public class Catapult {

    private DcMotor catapult;
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

    /**
     * Initializes catapult to use a certain motor and position.
     * @param mot DcMotor to use
     * @param pos Target catapult position to use
     * @param root Base position
     */
    public Catapult(DcMotor mot, int pos, int root) {
        //MotorPositionFix pfix = new MotorPositionFix(mot);
        catapult = mot;
       // DcMotor.RunMode rm = mot.getMode();
        mot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mot.setPower(1);
        mot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //pfix.setMode(rm);
        //pfix.setPowerCurve(new LinearCurve(1.d/200));
        catapult_pos = pos;
        this.root = root;
        ready();
    }

    /**
     * Resets catapult to root position
     */
    public void ready() {
        catapult.setTargetPosition(root);
    }
    private int fired = 0;
    private boolean prev_man = false;

    /**
     * Shoots! Only works while tick() is being called.
     */
    public void fire() {
        prev_man = manual;
        disableManual();
        catapult.setTargetPosition(catapult_pos);
        fired = 50;
    }

    private boolean lastReady = false;

    /**
     * The tick function. Must be called once per loop.
     */
    public void tick() {
        if (manual && new Range(-10,10).add(catapult.getTargetPosition()).inRange(catapult.getCurrentPosition())) {
            catapult.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            man_ready = true;
        }
        if (!manual && man_ready) {
            man_ready = false;
        }
        boolean tslr = false;
        if (!manual &&
                new Range(-10,10).add(catapult.getTargetPosition()).inRange(catapult.getCurrentPosition())
                && !lastReady) {
            int i = catapult.getCurrentPosition();
            catapult.setTargetPosition(i);
            tslr = true;
        }
        lastReady = tslr;
        //if (!manual)
        //    catapult.tick();
        if (fired == 1) {
            catapult.setTargetPosition(root);
            if (prev_man)
                enableManual();
        }
        if (fired > 0) fired--;
    }

    /**
     * Enables manual control mode by disabling automatic position setting.
     */
    public void enableManual() {
        catapult.setTargetPosition(root);
        manual = true;
        man_ready = false;
    }

    /**
     * Does the opposite of enableManual()
     */
    public void disableManual() {
        catapult.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        manual = false;
    }

    /**
     * Toggles manual mode using enableManual() and disableManual()
     */
    public void toggleManual() {
        if (manual) {
            disableManual();
            ready();
        }
        else
            enableManual();
    }

    /**
     * Closes connections.
     */
    @Deprecated
    public void close() {
        catapult.setTargetPosition(root);
        //catapult.deregister();
    }
}
