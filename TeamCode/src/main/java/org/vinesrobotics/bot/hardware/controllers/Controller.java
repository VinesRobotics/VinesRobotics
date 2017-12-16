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

import com.qualcomm.robotcore.hardware.Gamepad;

import org.vinesrobotics.bot.hardware.controllers.enums.Button;
import org.vinesrobotics.bot.hardware.controllers.enums.CalibrationMode;
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick;
import org.vinesrobotics.bot.utils.Logging;
import org.vinesrobotics.bot.utils.Reflection;
import org.vinesrobotics.bot.utils.Vec2D;

/**
 * A representation (using a more object-oriented API) of the content of the {@link Gamepad} class
 */
public class Controller {
    // the gamepad referenced
    private Gamepad gamepad;
    // the internal name of the controller
    private String name = "";

    // Calibration mode. This really should be nothing other than {link @CalibrationMode::SIMPLE}.
    private CalibrationMode ctype = CalibrationMode.SIMPLE;

    // COMPLEX mode ignore values
    private float lzx = 0.0f;
    private float lzy = 0.0f;
    private float rzx = 0.0f;
    private float rzy = 0.0f;

    /**
     * A null constructor. Used with the {@link NullControllerState}
     */
    protected Controller () {
        this(null, null);
    }

    /**
     * A constructor to create an object that references the gamepad with the specified name.
     *
     * @param gp the {@link Gamepad} object to pull data from
     * @param name the name of the controller
     */
    protected Controller (Gamepad gp, String name) {
        gamepad = gp;
        this.name = name;
        if (gp != null)
            gamepad.setJoystickDeadzone(0.0f);
    }

    /**
     * Calibrates controller.
     * Should never be used.
     */
    @Deprecated
    public void calibrate() {
        calibrate(CalibrationMode.SIMPLE);
    }

    /**
     * Calibrates controller using one of 2 modes
     * Should never be used.
     *
     * @param type Mode
     */
    @Deprecated
    public void calibrate(CalibrationMode type) {

        // SIMPLE calibration mode
        if (type == CalibrationMode.SIMPLE) {

            Logging.log("Please leave the joysticks on gamepad " + name + " in the neutral position.");

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
            }

            float ma = Math.max(gamepad.left_stick_x,gamepad.left_stick_y);
            float mb = Math.max(gamepad.right_stick_x,gamepad.right_stick_y);

            gamepad.setJoystickDeadzone( Math.max(ma,mb) + 0.001f );

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
     *
     * @param stick Joystick to get the value of
     * @return value of the joystick in (X,Y)
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
     * Gets x button value; if x boolean, true is 1, false is 0; if float, then the float.
     *
     * @param b Button to check, value property modified
     * @return Same object as passed in, but with value property modified
     */
    public Button getButton(Button b) {

        // Check if button is analog, look at ButtonType for what is and isn't
        if (!b.type().isAnalog()) {
            b.value = Reflection.getFieldValue(b.f.value,gamepad) ? 1 : 0 ;
        } else {
            b.value = Reflection.getFieldValue(b.f.value, gamepad);
        }

        return b;
    }

    // Reference to previous controller state
    private ControllerState controlState;

    /**
     * Get the current state of the controller.
     *
     * @return a {@link ControllerState}. May or may not be the same object as the previous time it
     * was called.
     */
    public ControllerState getControllerState() {

        // If this is the first call, prev is a {link @NullControllerState}
        // else clone the previous
        ControllerState prev = (controlState == null)? new NullControllerState() : controlState.clone();

        // update or create a controller state to return
        if (controlState == null) controlState = new ControllerState(this);
        else controlState.update();

        // assign the prev field to the calculated value
        controlState.prev = prev;

        return controlState;
    }

}
