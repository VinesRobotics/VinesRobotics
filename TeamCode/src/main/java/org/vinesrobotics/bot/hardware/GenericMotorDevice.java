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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

/**
 * Generic implementation of DcMotor.
 */
public class GenericMotorDevice extends GenericHardwareDevice implements DcMotor {

    /**
     * Does nothing.
     *
     * @return MotorConfigurationType.getUnspecifiedMotorType()
     */
    @Override
    public MotorConfigurationType getMotorType() {
        return MotorConfigurationType.getUnspecifiedMotorType();
    }

    /**
     * Does nothing.
     *
     * @param motorConfigurationType
     */
    @Override
    public void setMotorType(MotorConfigurationType motorConfigurationType) {

    }

    /**
     * Does nothing.
     *
     * @return self
     */
    @Override
    public DcMotorController getController() {
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
     * @param zeroPowerBehavior
     */
    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {

    }

    /**
     * Does nothing.
     *
     * @return ZeroPowerBehavior.UNKNOWN
     */
    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return ZeroPowerBehavior.UNKNOWN;
    }

    /**
     * Does nothing.
     */
    @Override
    public void setPowerFloat() {

    }

    /**
     * Does nothing.
     *
     * @return false
     */
    @Override
    public boolean getPowerFloat() {
        return false;
    }

    /**
     * Does nothing.
     *
     * @param position
     */
    @Override
    public void setTargetPosition(int position) {
        //System.out.println("Target position changed to " + position + "; not doing anything");
    }

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public int getTargetPosition() {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @return false
     */
    @Override
    public boolean isBusy() {
        return false;
    }

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public int getCurrentPosition() {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @param mode
     */
    @Override
    public void setMode(RunMode mode) {
        //System.out.println("RunMode changed to " + mode.toString() + "; not doing anything");
    }

    /**
     * Does nothing.
     *
     * @return RunMode.STOP_AND_RESET_ENCODER
     */
    @Override
    public RunMode getMode() {
        return RunMode.STOP_AND_RESET_ENCODER;
    }

    /**
     * Does nothing.
     *
     * @param direction
     */
    @Override
    public void setDirection(Direction direction) {
        //System.out.println("Direction changed to " + direction.toString() + "; not doing anything");
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
     * @param power
     */
    @Override
    public void setPower(double power) {
        //System.out.println("Power changed to " + power + "; not doing anything");
    }

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public double getPower() {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @return "GENERIC MOTOR"
     */
    @Override
    public String getDeviceName() {
        return "GENERIC MOTOR";
    }
}
