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

/**
 * An enum representing the type of button and any metadata necessary.
 */
public enum ButtonType {
    /**
     * A button on the D-Pad
     */
    DPAD(false),
    /**
     * A bumper.
     */
    BUMPER(false),
    /**
     * A trigger.
     */
    TRIGGER(true),
    /**
     * A stick button (when you push down).
     */
    STICK(false),
    /**
     * Any other button.
     */
    BUTTON(false);

    /**
     * Whether or not the type is analog or not. The only analog type if the trigger.
     */
    boolean analog = false;

    /**
     * @return whether or not the type is analog or not.
     */
    public boolean isAnalog() {
        return analog;
    }

    /**
     * The constructor. Used to set whether or not an option is analog.
     * @param analog Whether or not the type is analog;
     */
    ButtonType(boolean analog) {
        this.analog = analog;
    }

}
