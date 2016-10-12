package org.vinesrobotics.sixteen;

import com.qualcomm.robotcore.hardware.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vines HS Robotics on 10/7/2016.
 */

public class Hardware {
    protected ArrayList<HardwareDevice> devices = new ArrayList<>();

    protected Map<String,Integer> keyMaps = new HashMap<>();
    protected ArrayList<String> keys = new ArrayList<>();
    {
        keys.add("left");
        keys.add("right");
        keys.add("drive");
        keys.add("servo");
        keys.add("sensor");
    }

    public void registerHardwareKeyName(String key){

    }

    public void initHardware(HardwareMap hwm,String splitRegex){
        // Iterate over all hardware devices
        for (HardwareDevice dkv : hwm.getAll(HardwareDevice.class)){
            // Add device to local ID list
            devices.add(dkv);

            // Add device to appropriate key lists
            String name = dkv.getDeviceName();
            String[] nsplit = name.split(splitRegex);
        }
    }
}
