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

package org.vinesrobotics.bot.hardware.groups;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.vinesrobotics.bot.utils.Range;
import org.vinesrobotics.bot.utils.curves.ClampedCurve;
import org.vinesrobotics.bot.utils.curves.Curve;
import org.vinesrobotics.bot.utils.curves.LinearCurve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ViBots on 11/3/2017.
 */

public class ServoDeviceGroup extends DeviceGroup<Servo> implements Servo {

    private List<Servo> devs = new ArrayList<>();

    @Override
    public ServoController getController() {
        for (Servo servo : devs) {
            return servo.getController();
        }return null;
    }

    @Override
    public int getPortNumber() {
        for (Servo servo : devs) {
            return servo.getPortNumber();
        } return 0;
    }

    private Direction direction = Direction.FORWARD;

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
        for (Servo servo : devs) {
            servo.setDirection(direction);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    private double pos = 0;

    @Override
    public void setPosition(double position) {
        pos = positionCurve.getValueFor(position);
        for (Servo servo : devs) {
            servo.setPosition(pos);
        }
    }

    @Override
    public double getPosition() {
        return pos;
    }

    private Curve positionCurve = new ClampedCurve(new LinearCurve(1), new Range(0,1));

    /**
     * This is the only function that is not a direct passthrough;
     * the value is scaled before being passed on
     * @param min
     * @param max
     */
    @Override
    public void scaleRange(double min, double max) {
        positionCurve = new ClampedCurve(new LinearCurve(max-min, min), new Range(0,1));
    }

    public void setPositionCurve(Curve curve) {
        positionCurve = new ClampedCurve(curve, new Range(0,1));
    }

    @Override
    public Manufacturer getManufacturer() {
        for (Servo servo : devs) {
            return servo.getManufacturer();
        }return null;
    }

    @Override
    public String getDeviceName() {
        for (Servo servo : devs) {
            return servo.getDeviceName();
        }return null;
    }

    @Override
    public String getConnectionInfo() {
        for (Servo servo : devs) {
            return servo.getConnectionInfo();
        }return null;
    }

    @Override
    public int getVersion() {
        for (Servo servo : devs) {
            return servo.getVersion();
        }return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        for (Servo servo : devs) {
            servo.resetDeviceConfigurationForOpMode();
        }
    }

    @Override
    public void close() {
        for (Servo servo : devs) {
            servo.close();
        }
    }

    @Override
    public void clear() {
        devs.clear();
    }

    public void reverseDirection() {
        for (Servo servo : devs) {
            Direction dir = servo.getDirection();
            dir = dir == Direction.FORWARD ? Direction.REVERSE : Direction.FORWARD;
            servo.setDirection(dir);
        }
    }

    @Override
    public void addDevice(Servo device) {
        devs.add(device);
    }
}
