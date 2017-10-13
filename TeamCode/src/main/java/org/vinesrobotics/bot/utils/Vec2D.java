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

package org.vinesrobotics.bot.utils;

public class Vec2D<T> {

    private T v1;
    private T v2;

    /**
     * Initializes an empty Vec2D
     */
    public Vec2D() {
        v1 = null;
        v2 = null;
    }

    /**
     * Initializes the Vec2D with the two values
     * @param val1 First value in the vector
     * @param val2 Second value in the vector
     */
    public Vec2D(T val1, T val2) {
        v1 = val1;
        v2 = val2;
    }

    /**
     * Gets the first value in the vector
     * @return the first value
     */
    public T a() {
        return v1;
    }
    /**
     * Gets the second value in the vector
     * @return the second value
     */
    public T b() {
        return v2;
    }

    /**
     * Sets the first value
     * @param newv New value
     */
    public void a(T newv) {
        v1 = newv;
    }
    /**
     * Sets the second value
     * @param newv New value
     */
    public void b(T newv) {
        v2 = newv;
    }

    /**
     * Gets value for a given Axis, where Axis#X is a() and Axis#Y is b()
     *
     * @param ax Axis to retrieve
     * @return Axis value
     */
    public T getAxis(Axis ax) {
        if (ax.equals(Axis.X))
            return a();
        else
            return b();
    }

    /**
     * Converts the Vec2D to a {link @String} using the following format:
     *  a:'$1',b:'$2'
     * where $1 is the first value and $2 is the second
     * @return the stringified form of the vector
     */
    @Override
    public String toString() {
        return "a:'" + v1.toString() + "',b:'" + v2.toString() + "'";
    }

}
