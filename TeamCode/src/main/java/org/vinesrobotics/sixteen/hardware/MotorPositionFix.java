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
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorImpl;

import org.vinesrobotics.sixteen.utils.Range;
import org.vinesrobotics.sixteen.utils.curves.ClampedCurve;
import org.vinesrobotics.sixteen.utils.curves.Curve;
import org.vinesrobotics.sixteen.utils.curves.CurveCombination;
import org.vinesrobotics.sixteen.utils.curves.LinearCurve;
import org.vinesrobotics.sixteen.utils.curves.ParabolicCurve;

import java.util.ArrayList;
import java.util.List;

public class MotorPositionFix extends DcMotorImpl {

    private static List<MotorPositionFix> insts = new ArrayList<>();

    public static void stick() {
        for (MotorPositionFix m : insts) {
            m.tick();
        }
    }

    /**
     * Be sure to call this to release object!
     */
    public void deregister() {
        insts.remove(this);
    }

    public MotorPositionFix(DcMotor m) {
        this(m.getController(),m.getPortNumber(),m.getDirection());
    }
    public MotorPositionFix(DcMotor m, Direction d) {
        this(m.getController(),m.getPortNumber(),d);
    }
    public MotorPositionFix(DcMotorController controller, int portNumber, Direction direction) {
        super(controller, portNumber, direction);
        insts.add(this);
    }

    private Range acceptable = new Range(-10,10);
    private Curve powerCurve = null;
    {
        CurveCombination c = new CurveCombination();

        c.setCurve( new Range(Double.MIN_VALUE,10), new ParabolicCurve(.01,0,0) );
        c.setCurve( new Range(10,Double.MAX_VALUE), new LinearCurve(.1) );

       powerCurve = new ClampedCurve(c, new Range(-1,1));
    }

    private int target = 0;

    public void setPowerCurve(Curve c) {
        powerCurve = c;
    }

    @Override
    public void setTargetPosition(int position) {
        target = position;
    }

    @Override
    public int getTargetPosition() {
        return target;
    }

    @Override
    public boolean isBusy() {
        return !acceptable.plus(target).inRange(getCurrentPosition());
    }



    public void tick() {
        double val = Math.abs(target-getCurrentPosition());
        int sign = (int) ((target-getCurrentPosition())/val);
        if (isBusy())
            setPower(powerCurve.getValueFor(val)*sign);
        else
            setPower(0);
    }

    public void setAcceptableRange(Range r) {
        acceptable = r;
    }

}
