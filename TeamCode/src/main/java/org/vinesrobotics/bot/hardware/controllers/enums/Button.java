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

package org.vinesrobotics.bot.hardware.controllers.enums;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.vinesrobotics.bot.utils.Reflection;

import java.lang.reflect.Field;

/**
 * Button enum. Used to retrieve the pressed buttons on a controller. Can have either a boolean or floating point value.
 */
public enum Button {
    A(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "a")),
    B(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "b")),
    X(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "x")),
    Y(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "y")),
    RS(ButtonSide.RIGHT,ButtonType.STICK, Reflection.getField(Gamepad.class, "right_stick_button")),
    LS(ButtonSide.LEFT,ButtonType.STICK, Reflection.getField(Gamepad.class, "left_stick_button")),
    RB(ButtonSide.RIGHT,ButtonType.BUMPER, Reflection.getField(Gamepad.class, "right_bumper")),
    LB(ButtonSide.LEFT,ButtonType.BUMPER, Reflection.getField(Gamepad.class, "left_bumper")),
    RT(ButtonSide.RIGHT,ButtonType.TRIGGER, Reflection.getField(Gamepad.class, "right_trigger")),
    LT(ButtonSide.LEFT,ButtonType.TRIGGER, Reflection.getField(Gamepad.class, "left_trigger")),
    START(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "start")),
    BACK(ButtonSide.NA,ButtonType.BUTTON, Reflection.getField(Gamepad.class, "back")),
    LEFT(ButtonSide.NA,ButtonType.DPAD, Reflection.getField(Gamepad.class, "dpad_left")),
    RIGHT(ButtonSide.NA,ButtonType.DPAD, Reflection.getField(Gamepad.class, "dpad_right")),
    UP(ButtonSide.NA,ButtonType.DPAD, Reflection.getField(Gamepad.class, "dpad_up")),
    DOWN(ButtonSide.NA,ButtonType.DPAD, Reflection.getField(Gamepad.class, "dpad_down"));

    // Represents the side of the controller the button is on. Only used for sticks, bumpers and triggers.
    private ButtonSide s;
    // Represents the type of the button (button, stick, bumper, trigger d-pad)
    private ButtonType t;
    // A float version of the value between 0 and 1 (0 or 1 for boolean values)
    public float value = 0;

    /**
     * Gets the {link @ButtonSide} of the button, if applicable.
     * @return ButtonSide
     */
    public ButtonSide side() {
        return s;
    }
    /**
     * Gets the {link @ButtonType} of button
     * @return type
     */
    public ButtonType type() {
        return t;
    }

    // A field to allow classes in the controllers package to access a sort-of private or protected field
    public org.vinesrobotics.bot.hardware.controllers.LocalControl<Field> f;

    /**
     * The constructor. Used to define the values for each enum value.
     * @param side The side that the button is on. NA for no relevant side.
     * @param type The type of button.
     * @param field The {link @Field} to access to get the value of the button.
     */
    Button(ButtonSide side, ButtonType type, Field field) {
        s = side;
        t = type;
        f = new org.vinesrobotics.bot.hardware.controllers.LocalControl<>(field);
    }

    /**
     * Gives a string representation of the Button object.
     * Return in form '%{ENUM name}%(%{value}%)'.
     * @return String form of this enum instance.
     */
    public String toString(){
        return super.toString() + "(" + value + ")";
    }

}

