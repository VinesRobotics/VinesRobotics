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

    /**
     * Initializes an immutable {@link Range} object.
     * @param min minimum value
     * @param max maximum value
     */
    public Range(double min, double max) {
        minimum = min;
        maximum = max;
    }

    /**
     * Gets the minimum value
     * @return minimum value
     */
    public double min() {
        return minimum;
    }

    /**
     * Gets the maximum value
     * @return maximum value
     */
    public double max() {
        return maximum;
    }

    /**
     * Checks if num is within this range.
     * @param num Value to check
     * @return true if num is in this range
     */
    public boolean inRange(double num) {
        return num >= minimum && num <= maximum;
    }

    /**
     * Clamps val to this range. (eg. val < min == min, val > max == max)
     * @param val Value to clamp
     * @return clamped value
     */
    public double clamp(double val) {
        return Math.max(min(), Math.min(max(), val));
    }

    /**
     * Alias for {@link #plus(double)}
     * @see #plus(double)
     */
    public Range add(double v) { return plus(v); }

    /**
     * Returns a new range with both the maximum and minimum incremented by v
     * @param v
     * @return
     */
    public Range plus(double v) {
        return new Range(minimum+v,maximum+v);
    }

    /**
     * Returns a new range with both the maximum and minimum decremented by v
     * @param v
     * @return
     */
    public Range minus(double v) {
        return new Range(minimum-v,maximum-v);
    }

    /**
     * Returns a new range with both the maximum and minimum multiplied by v
     * @param v
     * @return
     */
    public Range mult(double v) {
        return new Range(minimum*v,maximum*v);
    }

    /**
     * Returns a new range with both the maximum and minimum divided by v
     * @param v
     * @return
     */
    public Range div(double v) {
        return new Range(minimum/v,maximum/v);
    }

    /**
     * Checks if o is the same as this
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        return o instanceof Range && max() == ((Range)o).max() && min() == ((Range)o).min();
    }

}
