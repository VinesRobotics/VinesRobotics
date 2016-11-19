package org.vinesrobotics.sixteen.hardware.controllers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.vinesrobotics.sixteen.hardware.controllers.enums.Button;
import org.vinesrobotics.sixteen.hardware.controllers.enums.CalibrationMode;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Joystick;
import org.vinesrobotics.sixteen.utils.Logging;
import org.vinesrobotics.sixteen.utils.Reflection;
import org.vinesrobotics.sixteen.utils.Vec2D;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 *
 * Copyright (c) 2016 Vines High School Robotics Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

public class Controller {
    private Gamepad gamepad;
    private String name = "";

    private CalibrationMode ctype = CalibrationMode.SIMPLE;

    // COMPLEX mode ignore values
    private float lzx = 0.0f;
    private float lzy = 0.0f;
    private float rzx = 0.0f;
    private float rzy = 0.0f;

    protected Controller (Gamepad gp, String name) {
        gamepad = gp;
        this.name = name;
        gamepad.setJoystickDeadzone(0.0f);
    }

    /**
     * Calibrates controller
     *
     */
    public void calibrate() {
        calibrate(CalibrationMode.SIMPLE);
    }

    /**
     * Calibrates controller using one of 2 modes
     *
     * Probably should be left unused
     *
     * @param type Mode
     */
    public void calibrate(CalibrationMode type) {

        // SIMPLE calibration mode
        if (type == CalibrationMode.SIMPLE) {

            Logging.log("Please leave the joysticks on gamepad " + name + " in the neutral position.");

            float ma = Math.max(gamepad.left_stick_x,gamepad.left_stick_y);
            float mb = Math.max(gamepad.right_stick_x,gamepad.right_stick_y);

            gamepad.setJoystickDeadzone( Math.max(ma,mb) + 0.01f );

            Logging.log("Done! ");

        }

        // COMPLEX calibration mode
        if (type == CalibrationMode.COMPLEX) {

            Logging.log("Please leave the joysticks on gamepad " + name + " in the neutral position.");

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                return;
            }

            Logging.log("Calibrating...");

            gamepad.setJoystickDeadzone(0.0f);

            lzx = gamepad.left_stick_x;
            lzy = gamepad.left_stick_y;
            rzx = gamepad.right_stick_x;
            rzy = gamepad.right_stick_y;

            float ma = Math.min(lzx,lzy);
            float mb = Math.max(rzx,rzy);

            gamepad.setJoystickDeadzone( Math.min(ma,mb) );

            Logging.log("Done! ");

        }

    }

    /**
     * Gets value of specified joystick
     * @param stick Joystick to get the value of
     * @return value
     */
    public Vec2D<Float> getJoystick(Joystick stick) {

        float x = 0.0f;
        float y = 0.0f;

        // Get the data for the right joystick
        if (stick == Joystick.RIGHT) {
            x = gamepad.right_stick_x;
            y = gamepad.right_stick_y;
        }

        // Get the data for the left joystick
        if (stick == Joystick.LEFT) {
            x = gamepad.left_stick_x;
            y = gamepad.left_stick_y;
        }

        // Apply complex calibration
        if (ctype == CalibrationMode.COMPLEX) {

            if (stick == Joystick.RIGHT) {
                x = (x==rzx)?0:x;
                y = (y==rzy)?0:y;
            }

            if (stick == Joystick.LEFT) {
                x = (x==lzx)?0:x;
                y = (y==lzy)?0:y;
            }

        }

        // Return the vector
        return new Vec2D<>(x,y);

    }

    /**
     * Gets a button value; if a boolean, true is 1, false is 0; if Float, then the float.
     *
     * @param b Button to check, value property modified
     * @return Same object as passed in, but with value property modified
     */
    public Button getButton(Button b) {

        // Check if button is analog, look at ButtonType for what is and isn't
        if (!b.type().isAnalog()) {
            b.value = Reflection.<Boolean>getFieldValue(b.f.value,gamepad) ? 1 : 0 ;
        } else {
            b.value = Reflection.<Float>getFieldValue(b.f.value, gamepad);
        }

        return b;
    }

}
