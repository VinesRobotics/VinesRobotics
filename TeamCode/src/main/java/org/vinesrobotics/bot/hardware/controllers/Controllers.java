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

package org.vinesrobotics.bot.hardware.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.vinesrobotics.bot.hardware.controllers.enums.CalibrationMode;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

/**
 * A manager class for both controllers available to the {@link OpMode}.
 */
public class Controllers {

    /**
     * Generates new Controllers object linked to an {@link OpMode}.
     *
     * @param omode {@link OpMode} to link to
     * @return the generated Controllers object
     */
    public static Controllers getControllerObjects(OpMode omode) {
        return new Controllers(omode.gamepad1, omode.gamepad2);
    }

    // The {@link Controller} referencing gamepad 1
    private Controller gpa = null;
    // The {@link Controller} referencing gamepad 2
    private Controller gpb = null;

    /**
     * Initializes the Controllers object with two {@link Gamepad}s.
     *
     * @param one gamepad 1
     * @param two gamepad 2
     */
    private Controllers(Gamepad one, Gamepad two) {
        if (one != null)
            gpa = new Controller(one,"1");
        if (two != null)
            gpb = new Controller(two,"2");
    }

    /**
     * Gets reference to Controller "A"
     *
     * @return reference to Controller "A"
     */
    public Controller a(){
        return gpa;
    }

    /**
     * Gets reference to Controller "B"
     *
     * @return reference to Controller "B"
     */
    public Controller b(){
        return gpb;
    }

    /**
     * Gets an array containing both controller.
     *
     * @return Array with both controllers
     */
    public Controller[] getControllers() {
        return new Controller[]{gpa,gpb};
    }

    /**
     * Calibrates both controllers with {@link CalibrationMode#SIMPLE}
     */
    @Deprecated
    public void calibrate() {
        calibrate(CalibrationMode.SIMPLE);
    }

    /**
     * Calibrates both controllers with the same {@link CalibrationMode}
     * @param caltyp The {@link CalibrationMode} to use
     */
    @Deprecated
    public void calibrate(CalibrationMode caltyp) {
        a().calibrate(caltyp);
        b().calibrate(caltyp);
    }

}
