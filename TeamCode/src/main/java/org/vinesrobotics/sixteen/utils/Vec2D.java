package org.vinesrobotics.sixteen.utils;

/**
 * Created by Vines HS Robotics on 11/4/2016.
 *
 * Copyright
 */

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
