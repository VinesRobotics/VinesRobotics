/*
 * Copyright (c) 2017 Vines High School Robotics Team
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

import org.vinesrobotics.bot.hardware.controllers.enums.Button;
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick;
import org.vinesrobotics.bot.utils.Axis;
import org.vinesrobotics.bot.utils.Vec2D;

/**
 * Created by ViBots on 11/29/2017.
 */

/**
 * A zeroed controller state. Used as the previous field for a {link @ControllerState} the first time
 * it is read.
 */
public class NullControllerState extends ControllerState {

    /**
     * Basic default constructor.
     */
    protected NullControllerState() {
        super(new Controller());
    }

    @Override
    public boolean isPressed(Button btn) {
        return false;
    }

    @Override
    public double btnVal(Button btn) {
        return 0;
    }

    @Override
    public ControllerState last() {
        return this;
    }

    @Override
    public Vec2D<Double> joy(Joystick j) {
        return new Vec2D<>(0d,0d);
    }

    @Override
    protected void update() {

    }

    @Override
    public double joyVal(Joystick joystick, Axis ax) {
        return 0;
    }

    @Override
    public ControllerState clone() {
        return this;
    }
}
