package org.vinesrobotics.sixteen.hardware.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.vinesrobotics.sixteen.hardware.controllers.enums.CalibrationMode;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

public class Controllers {

    /**
     * Generates new Controllers object linked to an OpMode.
     * @param omode OpMode to link to
     * @return the generated Controllers object
     */
    public static Controllers getControllerObjects(OpMode omode) {
        return new Controllers(omode.gamepad1, omode.gamepad2);
    }

    private Controller gpa = null;
    private Controller gpb = null;

    private Controllers(Gamepad one, Gamepad two) {
        if (one != null)
            gpa = new Controller(one,"1");
        if (two != null)
            gpb = new Controller(two,"2");
    }

    /**
     * Gets a reference to Controller "A"
     * @return a reference to Controller "A"
     */
    public Controller a(){
        return gpa;
    }

    /**
     * Gets a reference to Controller "B"
     * @return a reference to Controller "B"
     */
    public Controller b(){
        return gpb;
    }

    /**
     * Gets an array containing both controllers
     *
     * @return Array with both controllers
     */
    public Controller[] getControllers() {
        return new Controller[]{gpa,gpb};
    }

    /**
     * Calibrates both controllers with {link @CalibrationMode.SIMPLE}
     */
    public void calibrate() {
        calibrate(CalibrationMode.SIMPLE);
    }

    /**
     * Calibrates both controllers with the same CalibrationType
     * @param caltyp The CalibrationType to use
     */
    public void calibrate(CalibrationMode caltyp) {
        a().calibrate(caltyp);
        b().calibrate(caltyp);
    }

}
