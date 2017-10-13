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

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Reflection {

    /**
     * Gets Field variable from class by name.
     * @see Class#getDeclaredField(String)
     * @param clz Class to retrieve from
     * @param name Name ov field
     * @return Field variable or null
     */
    public static Field getField(Class<?> clz, String name) {
        try {
            return clz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Gets the value of a particular field from a given object
     * @param field The Field type
     * @param src The source object
     * @return The value or null if an error occurs
     */
    public static <T> T getFieldValue(Field field, Object src) {
        try {
            return (T) field.get(src);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static final String[] TYPE_NAME_PREFIX = {"class ","interface "};

    /**
     * Gets the name of a class specified by a {@link Type}
     * @param type The {@link Type} to get the name of
     * @return the name of the {@link Type} or an empty string if type is null
     */
    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        for (String prefix : TYPE_NAME_PREFIX) {
            if (className.startsWith(prefix)) {
                className = className.substring(prefix.length());
            }
        }
        return className;
    }

    /**
     * Gets a {@link Class<?>} object from a {@link Type} object
     * @param type the {@link Type} object
     * @return a {@link Class<?>} represented by the type
     * @throws ClassNotFoundException
     * @see #getClassName(Type)
     * @see Class#forName(String)
     */
    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

}
