package org.vinesrobotics.sixteen.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 10/13/2016.
 *
 * MIT License
 *
 * Copyright (c) 2016 Vines High School Robotics Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class Utils {

    /**
     * Gets the similar elements of two lists
     *
     * @param la First list
     * @param lb Second list
     * @param <T> List type
     * @return List similarity
     */
    public static <T> ArrayList<T> getListSimilarity(ArrayList<T> la, ArrayList<T> lb) {

        ArrayList<T> out = new ArrayList<>(la.subList(0,la.size()));

        for ( T e : la ) {
            if ( !lb.contains(e) )
                out.remove(e);
        }

        return out;

    }

    /**
     * Gets the differences of lists.
     *
     * @param la List 1
     * @param lb List 2
     * @param <T> Type in list
     * @return List difference
     */
    public static <T> List<T> getListDifference(List<T> la, List<T> lb) {

        List<T> out = la.subList(0,la.size());

        for ( T e : la ) {
            if ( lb.contains(e) )
                out.remove(e);
        }

        for ( T e : lb ) {
            if ( !la.contains(e) )
                out.add(e);
        }

        return out;

    }

    private static double ltime = 0.0;
    public static double getDeltaTime(double time) {
        double lltime = ltime;
        ltime = time;
        return time - lltime;
    }

}
