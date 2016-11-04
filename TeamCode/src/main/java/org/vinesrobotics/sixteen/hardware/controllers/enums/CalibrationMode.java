package org.vinesrobotics.sixteen.hardware.controllers.enums;

/**
 * Created by Vines HS Robotics on 11/4/2016.
 */

/**
 * The calibration mode for {link @Controller} objects
 */
public enum CalibrationMode {
    /**
     * Calibrates the controller by only changing the joystick deadzone.
     */
    SIMPLE,
    /**
     * Calibrates the controller by zeroing only the value that appears at neutral.
     */
    COMPLEX
}
