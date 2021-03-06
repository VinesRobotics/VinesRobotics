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

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.vinesrobotics.bot.utils.Logging;
import org.vinesrobotics.bot.utils.Reflection;
import org.vinesrobotics.bot.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows for names to be keyed instead of hardcoded, and have easy access to all with given keys.
 */
public class Hardware {
    // device list
    private ArrayList<HardwareDevice> devices = new ArrayList<>();
    // key matches
    private ArrayList<ArrayList<String>> keyMatch = new ArrayList<>();

    // are we initialized?
    boolean inited = false;
    // mappings of keys to element lists
    protected Map<String,ArrayList<HardwareElement>> keyMaps = new HashMap<>();
    // the keys to search for
    private ArrayList<String> keys = new ArrayList<>();
    { // Add some default keys that might be useful.
        keys.add("left");
        keys.add("right");
        keys.add("drive");
        keys.add("servo");
        keys.add("sensor");
        keys.add("motor");
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
        else
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
        initHardware(hm,"[\\._\\- ]");

    }

    /**
     * Gets a device with the given ID
     *
     * @param id the ID to look up
     * @return the device associated with the ID
     */
    public HardwareDevice getDevice(int id){
        return devices.get(id);
    }

    /**
     * Initializes hardware settings and indexes the keys.
     *
     * @param hwm HardwareMap to initialize with
     * @param splitRegex The string to pass to {@link String#split(String)} to split the name for key registration
     */
    public void initHardware(HardwareMap hwm, String splitRegex){

        // SANITY CHECK
        if (inited) throw new UnsupportedOperationException("Hardware already initialized!");

        // Init keyMaps with all names in keys
        for (String s : keys) {
            keyMaps.put(s,new ArrayList<HardwareElement>());
        }

        // Iterate over all hardware devices
        for (Object odkv : hwm.getAll(Object.class)){

            try {
                HardwareDevice dkv = (HardwareDevice) odkv;

                // Add device to local ID list
                int id = devices.size();
                devices.add(dkv);

                // Add corresponding keyMatch element
                keyMatch.add(new ArrayList<String>());

                hwm.getNamesOf(dkv);
                Logging.hardwareAccess.value.addData("",hwm.getNamesOf(dkv));

                // Get device keys
                String name = hwm.getNamesOf(dkv).iterator().next();
                //Logging.log(name);
                String[] nsplit = name.split(splitRegex);

                // Put keys into keyMatch
                keyMatch.get(id).addAll(Arrays.asList(nsplit));

                // Put index into appropriate keyMaps element
                for (Map.Entry<String, ArrayList<HardwareElement>> s : keyMaps.entrySet()) {
                    // If key in the index list, then add it to the appropriate list.
                    if (keyMatch.get(id).contains(s.getKey())) {
                        s.getValue().add(new HardwareElement(this, id));
                    }
                }

                /*/-----------------------------------------------------------------------\*\
                    KEEP IN MIND THAT ID IS THE INDEX IN devices. THIS IS VERY IMPORTANT.
                \*\-----------------------------------------------------------------------/*/
            } catch (Exception e) {
                OutputStream os = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(os);

                e.printStackTrace(ps);

                Logging.log("ERROR: " + os.toString());
            }

        }

        inited = true;
    }

    /**
     * Gets list of IDs associated with x particular registered search key
     *
     * @param key Key to search for
     * @return {@link List<HardwareElement>} of devices with that mapping
     */
    public List<HardwareElement> searchByRegisteredKey(String key) {

        // SANITY CHECK
        if (!inited) throw new UnsupportedOperationException("Hardware not initialized!");

        // Get and return list associated with key (null if nonexistant)
        return keyMaps.get(key);

    }

    /**
     * Checks if device has particular key attached. Note that this can check for any key, not just
     * registered ones. A key is defined by the delimiters passed into the init function.
     *
     * @param id ID of device to check
     * @param key Key to check
     * @return Has key associated.
     */
    public boolean hasKey(HardwareElement id, String key) {

        // SANITY CHECK
        if (!inited) throw new UnsupportedOperationException("Hardware not initialized!");

        // It's x simple check, so doesn't really need explaining
        return keyMatch.get(id.id()).contains(key);

    }

    /**
     * Get all devices that have all specified keys
     * @param keys Keys to use as x filter
     * @return A list of {@link HardwareElement}s that match the criteria
     */
    public <T extends HardwareDevice> List<HardwareElement> getDevicesWithAllKeys(String... keys){

        // SANITY CHECK
        if (!inited) throw new UnsupportedOperationException("Hardware not initialized!");
        if (keys.length < 1) throw new IllegalArgumentException("Needs at least one key to check for");

        // Convert varargs to List
        List<String> kys = Arrays.asList(keys);

        // Get similarity between loaded keys and args
        // (Get filtered key list)
        ArrayList<String> prim = Utils.getListSimilarity(new ArrayList<>(kys),this.keys);
        if (prim.size() < 1) throw new IllegalArgumentException("Needs at least one listed key to check for");

        List<HardwareElement> outa = new ArrayList<>();
        List<HardwareElement> out = new ArrayList<>();

        // Initialize out with full list of compatible keys
        outa.addAll(keyMaps.get(prim.get(0)));
        out.addAll(keyMaps.get(prim.get(0)));

        // Remove first
        prim.remove(0);

        // Filter out elements that don't have all keys
        for (HardwareElement k : outa) {
            for (String s : prim) {
                if (!hasKey(k,s)) {
                    out.remove(k);
                    break;
                }
            }
        }

        // Ensure output is always populated with something
        if(out.size() == 0) {
            try {
                Class<?> pty = Reflection.getClass(
                        getClass().getDeclaredMethod("getDeviceWithKeys", String[].class)
                                .getTypeParameters()[0].getBounds()[0]
                );

                if (pty.isAssignableFrom(DcMotorSimple.class)) {
                    return new ArrayList<>(Arrays.asList(new HardwareElement(new GenericMotorDevice())));
                }

                if (pty.isAssignableFrom(Servo.class)) {
                    return new ArrayList<>(Arrays.asList(new HardwareElement(new GenericServoDevice())));
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<>(Arrays.asList(new HardwareElement(new GenericHardwareDevice())));
        }

        return out;

    }

    /**
     * Gets the first found device with all input keys
     * @see Hardware#getDevicesWithAllKeys(String...)
     *
     * @param keys The keys to
     * @param <T> The type to cast to. It may fail, this method errors in that case.
     * @return the device
     */
    public <T extends HardwareDevice> T getDeviceWithKeys(String... keys) {
        List<HardwareElement> hes = getDevicesWithAllKeys(keys);

        if (hes.get(0).get() instanceof GenericHardwareDevice) {
            do {
                try {
                    Class<?> pty = Reflection.getClass(
                            getClass().getDeclaredMethod("getDeviceWithKeys", String[].class)
                                    .getTypeParameters()[0].getBounds()[0]
                    );

                    if (pty.isAssignableFrom(DcMotorSimple.class)) {
                        return (T) new GenericMotorDevice();
                    }

                    if (pty.isAssignableFrom(Servo.class)) {
                        return (T) new GenericServoDevice();
                    }

                    break;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            } while (false);
        }

        return (T) hes.get(0).get();
    }
}
