package org.vinesrobotics.sixteen.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron Schnoebelen on 10/7/2016.
 *
 * MIT License
 *
 * Copyright (c) 2016 Vines High School Robotics Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class Hardware {
    protected ArrayList<HardwareDevice> devices = new ArrayList<>();
    protected ArrayList<ArrayList<String>> keyMatch = new ArrayList<>();

    boolean inited = false;
    protected Map<String,ArrayList<Integer>> keyMaps = new HashMap<>();
    protected ArrayList<String> keys = new ArrayList<>();
    { // Add some default keys that might be useful.
        keys.add("left");
        keys.add("right");
        keys.add("drive");
        keys.add("servo");
        keys.add("sensor");
    }

    /**
     * Adds key to be indexed in hardware names.
     *
     * @param key Key to add to the indexed list
     * @throws InvalidKeyException if the key exists
     * @throws UnsupportedOperationException if executed after initHardware
     * @see #initHardware(HardwareMap)
     */
    public void registerHardwareKeyName(String key) throws InvalidKeyException {

        /*/---------------------------------------------------------\*\
            Init check: This doesn't do anything after init.
            It doesn't break anything, but just to make it obvious.
        \*\---------------------------------------------------------/*/

        if (!inited)

            // Double sanity check: Don't double add!
            if (!keys.contains(key))
                keys.add(key);
            else
                throw new InvalidKeyException("Don't double add keys!");

        // Gotta do something when someone does what you don't do.
        throw new UnsupportedOperationException("Keys cannot be added after hardware init");
    }

    /**
     * Initializes hardware settings and indexes the keys.
     *
     * @param hm HardwareMap to initialize with
     * @see #initHardware(HardwareMap, String)
     */
    public void initHardware(HardwareMap hm) {

        // Call with default arguments
        initHardware(hm,"[._-]");

    }

    /**
     * Initializes hardware settings and indexes the keys.
     *
     * @param hwm HardwareMap to initialize with
     * @param splitRegex The string to pass to {@link String#split(String)} to split the name for key registration
     */
    public void initHardware(HardwareMap hwm, String splitRegex){

        // Init keyMaps with all names in keys
        for (String s : keys) {
            keyMaps.put(s,new ArrayList<Integer>());
        }

        // Iterate over all hardware devices
        for (HardwareDevice dkv : hwm.getAll(HardwareDevice.class)){

            // Add device to local ID list
            int id = devices.size();
            devices.add(dkv);

            // Add corresponding keyMatch element
            keyMatch.add(new ArrayList<String>());

            // Get device keys
            String name = dkv.getDeviceName();
            String[] nsplit = name.split(splitRegex);

            // Put keys into keyMatch
            keyMatch.get(id).addAll(Arrays.asList(nsplit));

            // Put index into appropriate keyMaps element
            for (Map.Entry<String, ArrayList<Integer>> s : keyMaps.entrySet()) {
                // If key in the index list, then add it to the appropriate list.
                if (keyMatch.get(id).contains(s.getKey())) {
                    s.getValue().add(id);
                }
            }

            /*/-----------------------------------------------------------------------\*\
                KEEP IN MIND THAT ID IS THE INDEX IN devices. THIS IS VERY IMPORTANT.
            \*\-----------------------------------------------------------------------/*/
        }

        inited = true;
    }
}
