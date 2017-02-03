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

package org.vinesrobotics.sixteen.utils;

public class Range {

    private final double minimum;
    private final double maximum;

    public Range(double min, double max) {
        minimum = min;
        maximum = max;
    }

    public double min() {
        return minimum;
    }

    public double max() {
        return maximum;
    }

    public boolean inRange(double num) {
        return num >= minimum && num <= maximum;
    }

    public double clamp(double val) {
        return Math.max(min(), Math.min(max(), val));
    }

    public Range add(double v) { return plus(v); }

    public Range plus(double v) {
        return new Range(minimum+v,maximum+v);
    }
    public Range minus(double v) {
        return new Range(minimum-v,maximum-v);
    }
    public Range mult(double v) {
        return new Range(minimum*v,maximum*v);
    }
    public Range div(double v) {
        return new Range(minimum/v,maximum/v);
    }

    @Override
    public boolean equals(Object o) {

        return o instanceof Range && max() == ((Range)o).max() && min() == ((Range)o).min();
    }

}
