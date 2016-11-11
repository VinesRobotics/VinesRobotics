package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Created by Vines HS Robotics on 11/11/2016.
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
