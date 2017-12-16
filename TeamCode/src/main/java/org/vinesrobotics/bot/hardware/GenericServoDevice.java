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

package org.vinesrobotics.bot.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class GenericServoDevice extends GenericHardwareDevice implements Servo {
    /**
     * Does nothing.
     *
     * @return self
     */
    @Override
    public ServoController getController() {
        return this;
    }

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public int getPortNumber() {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @param direction
     */
    @Override
    public void setDirection(Direction direction) {
        //.out.println("Direction changed to " + direction.toString() + "; not doing anything");
    }

    /**
     * Does nothing.
     *
     * @return Direction.REVERSE
     */
    @Override
    public Direction getDirection() {
        return Direction.REVERSE;
    }

    /**
     * Does nothing.
     *
     * @param position
     */
    @Override
    public void setPosition(double position) {
        //System.out.println("Position changed to " + position + "; not doing anything");
    }

    /**
     * Does nothing.
     *
     * @return Double.NaN
     */
    @Override
    public double getPosition() {
        return Double.NaN;
    }

    /**
     * Does nothing.
     *
     * @param min
     * @param max
     */
    @Override
    public void scaleRange(double min, double max) {

    }
}
