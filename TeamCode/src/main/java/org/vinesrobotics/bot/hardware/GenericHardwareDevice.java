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

import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

/**
 * A generic non-functional implementation of a hardware device, including an AnalogSensor,
 * DcMotorController, and ServoController
 */
public class GenericHardwareDevice implements HardwareDevice,AnalogSensor,DcMotorController,
        ServoController {

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public double readRawVoltage() {
        return 0;
    }

    /**
     * Does nothing.
     * @param i
     * @param motorConfigurationType
     */
    @Override
    public void setMotorType(int i, MotorConfigurationType motorConfigurationType) {

    }

    /**
     * Does nothing.
     * @param i
     * @return MotorConfigurationType.getUnspecifiedMotorType()
     */
    @Override
    public MotorConfigurationType getMotorType(int i) {
        return MotorConfigurationType.getUnspecifiedMotorType();
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @param mode
     */
    @Override
    public void setMotorMode(int motor, DcMotor.RunMode mode) {

    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return null
     */
    @Override
    public DcMotor.RunMode getMotorMode(int motor) {
        return null;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @param power
     */
    @Override
    public void setMotorPower(int motor, double power) {

    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return 0
     */
    @Override
    public double getMotorPower(int motor) {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return false
     */
    @Override
    public boolean isBusy(int motor) {
        return false;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @param zeroPowerBehavior
     */
    @Override
    public void setMotorZeroPowerBehavior(int motor, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {

    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return null
     */
    @Override
    public DcMotor.ZeroPowerBehavior getMotorZeroPowerBehavior(int motor) {
        return null;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return false
     */
    @Override
    public boolean getMotorPowerFloat(int motor) {
        return false;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @param position
     */
    @Override
    public void setMotorTargetPosition(int motor, int position) {

    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return 0
     */
    @Override
    public int getMotorTargetPosition(int motor) {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @param motor
     * @return 0
     */
    @Override
    public int getMotorCurrentPosition(int motor) {
        return 0;
    }

    /**
     * Does nothing.
     */
    @Override
    public void pwmEnable() {

    }

    /**
     * Does nothing.
     */
    @Override
    public void pwmDisable() {

    }

    /**
     * Does nothing.
     *
     * @return null
     */
    @Override
    public PwmStatus getPwmStatus() {
        return null;
    }

    /**
     * Does nothing.
     *
     * @param servo
     * @param position
     */
    @Override
    public void setServoPosition(int servo, double position) {

    }

    /**
     * Does nothing.
     *
     * @param servo
     * @return 0
     */
    @Override
    public double getServoPosition(int servo) {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @return null
     */
    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    /**
     * Does nothing.
     *
     * @return "GENERIC DEVICE"
     */
    @Override
    public String getDeviceName() {
        return "GENERIC DEVICE";
    }

    /**
     * Does nothing.
     *
     * @return ""
     */
    @Override
    public String getConnectionInfo() {
        return "";
    }

    /**
     * Does nothing.
     *
     * @return 0
     */
    @Override
    public int getVersion() {
        return 0;
    }

    /**
     * Does nothing.
     */
    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    /**
     * Does nothing.
     */
    @Override
    public void close() {

    }
}
