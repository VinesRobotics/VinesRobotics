package org.vinesrobotics.sixteen.hardware.controllers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.vuforia.Vec2F;

import org.vinesrobotics.sixteen.utils.Logging;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

public class Controller {

    public enum CalibrationType {
        SIMPLE,
        COMPLEX
    }

    private Gamepad gamepad;
    private String name = "";

    private CalibrationType ctype = CalibrationType.SIMPLE;

    // COMPLEX mode ignore values
    private float lzx = 0.0f;
    private float lzy = 0.0f;
    private float rzx = 0.0f;
    private float rzy = 0.0f;

    protected Controller (Gamepad gp, String name) {
        gamepad = gp;
        this.name = name;
    }

    /**
     * Calibrates controller
     *
     */
    public void calibrate() {
        calibrate(CalibrationType.COMPLEX);
    }

    /**
     * Calibrates controller using one of 2 modes
     *
     * @param type Mode
     */
    public void calibrate(CalibrationType type) {

        // SIMPLE calibration mode
        if (type == CalibrationType.SIMPLE) {

            Logging.log("Please leave the joysticks on gamepad " + name + " in the neutral position.");

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                return;
            }

            Logging.log("Calibrating...");

            float ma = Math.max(gamepad.left_stick_x,gamepad.left_stick_y);
            float mb = Math.max(gamepad.right_stick_x,gamepad.right_stick_y);

            gamepad.setJoystickDeadzone( Math.max(ma,mb) + 0.01f );

            Logging.log("Done! ");

        }

        // COMPLEX calibration mode
        if (type == CalibrationType.COMPLEX) {

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

    public enum Joystick {
        RIGHT,
        LEFT
    }

    public Vec2F getJoystick(Joystick stick) {

        float x = 0.0f;
        float y = 0.0f;

        if (stick == Joystick.RIGHT) {
            x = gamepad.right_stick_x;
            y = gamepad.right_stick_y;
        }

        if (stick == Joystick.LEFT) {
            x = gamepad.left_stick_x;
            y = gamepad.left_stick_y;
        }

        if (ctype == CalibrationType.COMPLEX) {

            if (stick == Joystick.RIGHT) {
                x = (x==rzx)?0:x;
                y = (y==rzy)?0:y;
            }

            if (stick == Joystick.LEFT) {
                x = (x==lzx)?0:x;
                y = (y==lzy)?0:y;
            }

        }

        return new Vec2F(x,y);

    }

}
