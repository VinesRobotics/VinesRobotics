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

package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.ServoController;

public class GenericHardwareDevice implements HardwareDevice,AnalogSensor,DcMotorController,ServoController {

    /**
     * Returns the sensor's current value as a raw voltage level. Note that for
     * Returns the light level voltage as reported by the sensor. Note that returned values
     * INCREASE as the light energy INCREASES.
     *
     * @return unscaled / unclipped voltage level reported by the device
     */
    @Override
    public double readRawVoltage() {
        return 0;
    }

    /**
     * Set the current motor mode. {@link DcMotor.RunMode}
     *
     * @param motor port of motor
     * @param mode  run mode
     */
    @Override
    public void setMotorMode(int motor, DcMotor.RunMode mode) {

    }

    /**
     * Get the current motor mode. Returns the current "run mode".
     *
     * @param motor port of motor
     * @return run mode
     */
    @Override
    public DcMotor.RunMode getMotorMode(int motor) {
        return null;
    }

    /**
     * Set the current motor power
     *
     * @param motor port of motor
     * @param power from -1.0 to 1.0
     */
    @Override
    public void setMotorPower(int motor, double power) {

    }

    /**
     * Get the current motor power
     *
     * @param motor port of motor
     * @return scaled from -1.0 to 1.0
     */
    @Override
    public double getMotorPower(int motor) {
        return 0;
    }

    /**
     * Sets the maximum targetable motor speed when the indicated motor is running in one of the
     * PID motor modes.
     *
     * @param motor                 the motor in question
     * @param encoderTicksPerSecond the new maximum targetable motor speed, in units of encoder
     *                              ticks per second
     * @see DcMotor#setMaxSpeed(int)
     */
    @Override
    public void setMotorMaxSpeed(int motor, int encoderTicksPerSecond) {

    }

    /**
     * Returns the current maximum targetable motor speed when the indicated motor is running
     * in one of the PID modes
     *
     * @param motor the motor in question
     * @return the current maximum targetable speed of that motor, in units of encoder ticks per second
     * @see DcMotor#getMaxSpeed()
     */
    @Override
    public int getMotorMaxSpeed(int motor) {
        return 0;
    }

    /**
     * Is the motor busy?
     *
     * @param motor port of motor
     * @return true if the motor is busy
     */
    @Override
    public boolean isBusy(int motor) {
        return false;
    }

    /**
     * Sets the behavior of the motor when zero power is applied.
     *
     * @param motor
     * @param zeroPowerBehavior the behavior of the motor when zero power is applied.
     */
    @Override
    public void setMotorZeroPowerBehavior(int motor, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {

    }

    /**
     * Returns the current zero power behavior of the motor.
     *
     * @param motor
     * @return the current zero power behavior of the motor.
     */
    @Override
    public DcMotor.ZeroPowerBehavior getMotorZeroPowerBehavior(int motor) {
        return null;
    }

    /**
     * Is motor power set to float?
     *
     * @param motor port of motor
     * @return true of motor is set to float
     */
    @Override
    public boolean getMotorPowerFloat(int motor) {
        return false;
    }

    /**
     * Set the motor target position. This takes in an integer, which is not scaled.
     * <p>
     * Motor power should be positive if using run to position
     *
     * @param motor    port of motor
     * @param position range from Integer.MIN_VALUE to Integer.MAX_VALUE
     */
    @Override
    public void setMotorTargetPosition(int motor, int position) {

    }

    /**
     * Get the current motor target position
     *
     * @param motor port of motor
     * @return integer, unscaled
     */
    @Override
    public int getMotorTargetPosition(int motor) {
        return 0;
    }

    /**
     * Get the current motor position
     *
     * @param motor port of motor
     * @return integer, unscaled
     */
    @Override
    public int getMotorCurrentPosition(int motor) {
        return 0;
    }

    /**
     * Enables all of the servos connected to this controller
     */
    @Override
    public void pwmEnable() {

    }

    /**
     * Disables all of the servos connected to this controller
     */
    @Override
    public void pwmDisable() {

    }

    /**
     * Returns the enablement status of the collective set of servos connected to this controller
     *
     * @return the enablement status of the collective set of servos connected to this controller
     */
    @Override
    public PwmStatus getPwmStatus() {
        return null;
    }

    /**
     * Set the position of a servo at the given channel
     *
     * @param servo    channel of servo
     * @param position from 0.0 to 1.0
     */
    @Override
    public void setServoPosition(int servo, double position) {

    }

    /**
     * Get the position of a servo at a given channel
     *
     * @param servo channel of servo
     * @return position, scaled from 0.0 to 1.0
     */
    @Override
    public double getServoPosition(int servo) {
        return 0;
    }

    /**
     * Returns an indication of the manufacturer of this device.
     *
     * @return the device's manufacturer
     */
    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    /**
     * Returns a string suitable for display to the user as to the type of device
     *
     * @return device manufacturer and name
     */
    @Override
    public String getDeviceName() {
        return "GENERIC DEVICE";
    }

    /**
     * Get connection information about this device in a human readable format
     *
     * @return connection info
     */
    @Override
    public String getConnectionInfo() {
        return "";
    }

    /**
     * Version
     *
     * @return get the version of this device
     */
    @Override
    public int getVersion() {
        return 0;
    }

    /**
     * Resets the device's configuration to that which is expected at the beginning of an OpMode.
     * For example, motors will reset the their direction to 'forward'.
     */
    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    /**
     * Closes this device
     */
    @Override
    public void close() {

    }
}
