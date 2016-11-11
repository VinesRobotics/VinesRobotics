package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 *
 */

public class HardwareElement {

    private Hardware hardware;
    private int deviceid;

    protected HardwareElement(Hardware hw, int id) {
        hardware = hw;
        deviceid = id;
    }

    public int id(){
        return deviceid;
    }

    public HardwareDevice get(){
        return hardware.getDevice(id());
    }

}
