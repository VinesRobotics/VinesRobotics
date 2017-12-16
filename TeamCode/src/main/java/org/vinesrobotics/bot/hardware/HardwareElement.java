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

import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * A single element of the collection that is {@link Hardware}.
 */
public class HardwareElement {

    // the {@link Hardware} instance
    private Hardware hardware;
    // the device id
    private int deviceid;
    // the actual device
    private HardwareDevice hardev = null;

    /**
     * Initializes with a {@link Hardware} instance and an ID.
     *
     * @param hw the {@link Hardware} instance
     * @param id the ID the element is identified by
     */
    protected HardwareElement(Hardware hw, int id) {
        hardware = hw;
        deviceid = id;
    }

    /**
     * Initializes with a {@link HardwareDevice}. Used with {@link GenericHardwareDevice}s.
     *
     * @param hd the {@link HardwareDevice} to link to
     */
    protected HardwareElement(HardwareDevice hd) {
        deviceid = 0;
        hardev = hd;
    }

    /**
     * Gets the ID associated with the element. 0 if initialized with a {@link HardwareDevice}.
     *
     * @return the ID
     */
    public int id(){
        return deviceid;
    }

    /**
     * Get the {@link HardwareDevice} associated with the element.
     *
     * @return the {@link HardwareDevice}
     */
    public HardwareDevice get(){
        if (hardev != null) {
            return hardev;
        }
        return hardware.getDevice(id());
    }

}
