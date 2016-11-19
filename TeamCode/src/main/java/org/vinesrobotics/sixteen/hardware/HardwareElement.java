package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Copyright
 */

public class HardwareElement {

    private Hardware hardware;
    private int deviceid;
    private HardwareDevice hardev = null;

    protected HardwareElement(Hardware hw, int id) {
        hardware = hw;
        deviceid = id;
    }

    protected HardwareElement(HardwareDevice hd) {
        deviceid = 0;
        hardev = hd;
    }

    public int id(){
        return deviceid;
    }

    public HardwareDevice get(){
        if (hardev != null) {
            return hardev;
        }
        return hardware.getDevice(id());
    }

}
