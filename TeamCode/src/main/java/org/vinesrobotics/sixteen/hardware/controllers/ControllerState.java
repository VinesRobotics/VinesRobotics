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

package org.vinesrobotics.sixteen.hardware.controllers;

import org.vinesrobotics.sixteen.hardware.controllers.enums.Button;
import org.vinesrobotics.sixteen.hardware.controllers.enums.Joystick;
import org.vinesrobotics.sixteen.utils.Axis;
import org.vinesrobotics.sixteen.utils.Vec2D;

import java.util.HashMap;

public class ControllerState {
    private HashMap<String, Float> joys = new HashMap<>();
    private HashMap<Button,Button> buttons = new HashMap<>();
    private Controller control;

    protected ControllerState(Controller cntr) {
        control = cntr;
    }

    public boolean isPressed(Button btn) {
        return buttons.get(btn).value > 0;
    }

    public double btnVal(Button btn) { return buttons.get(btn).value; }

    public double joyVal(Joystick joystick, Axis ax) {
        return joys.get(joystick.name()+ax.name());
    }

    public Vec2D<Double> joy(Joystick j) {
        return new Vec2D<>( joyVal(j,Axis.X), joyVal(j,Axis.Y) );
    }

    @Deprecated
    public void update() {
        joys.clear();
        buttons.clear();
        for (Button btn : Button.values()) {
            buttons.put( Button.valueOf( btn.name() ), control.getButton(btn) );
        }
        for (Joystick joy : Joystick.values()) {
            for (Axis ax : Axis.values()) {
                joys.put(joy.name() + ax.name(), control.getJoystick(joy).getAxis(ax) );
            }
        }
    }

}
