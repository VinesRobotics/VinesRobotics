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

package org.vinesrobotics.bot.utils.curves;

import org.vinesrobotics.bot.utils.Range;

import java.util.HashMap;
import java.util.Map;

public class CurveCombination implements Curve {

    public Map<Range,Curve> curves = new HashMap<>();

    public void setCurve(Range r, Curve c) {
        curves.put(r,c);
    }

    public void removeCurve(Range r) {
        curves.remove(r);
    }

    public void replaceCurve(Range r, Curve c) {
        removeCurve(r);
        setCurve(r,c);
    }

    @Override
    public double getValueFor(double x) {
        double out = Double.POSITIVE_INFINITY;
        for (Map.Entry<Range, Curve> s : curves.entrySet()) {
            Range r = s.getKey();
            Curve c = s.getValue();

            if (r.inRange(x)) {
                out =  c.getValueFor(x);
            }
        }
        return out;
    }
}
