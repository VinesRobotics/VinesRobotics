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

package org.vinesrobotics.bot.opmodes;


import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.vinesrobotics.bot.utils.Range;
import org.vinesrobotics.bot.utils.opencv.ColorBlobDetector;
import org.vinesrobotics.bot.utils.opencv.OpenCvManager;

/**
 * Created by ViBots on 11/30/2017.
 */

public class VibotAutonomous extends VibotControlled {

    public enum AutoPosition {
        RedBack, BlueBack, RedFront, BlueFront, None
    }

    private OpenCvManager cvmanager = new OpenCvManager();
    private ColorBlobDetector redBlobDet = new ColorBlobDetector();
    private ColorBlobDetector redDarkBlobDet = new ColorBlobDetector();
    private ColorBlobDetector blueBlobDet = new ColorBlobDetector();

    private AutoPosition Position = AutoPosition.None;

    public VibotAutonomous(AutoPosition pos) {
        Position = pos;
    }

    @Override
    public void init_spec() {
        clawServos.setPosition(clawServoMax);
        jewelArmServos.setPosition(1);
        switch (Position) {
            case BlueBack: {
                leftMotors.reverseDirection();
                rightMotors.reverseDirection();
            }
            break;
            case RedBack: {
            }
            break;

            case BlueFront: {
                leftMotors.reverseDirection();
                rightMotors.reverseDirection();
            }
            break;
            case RedFront: {
            }
            break;
        }

        cvmanager.initCV();
        redBlobDet.setColorRadius(new Scalar(25,96, 127));
        redBlobDet.setHsvColor(new Scalar(255,255,255));
        redDarkBlobDet.setColorRadius(new Scalar(25,96, 127));
        redDarkBlobDet.setHsvColor(new Scalar(0,255,255));
        blueBlobDet.setColorRadius(new Scalar(15 ,96, 127));
        blueBlobDet.setHsvColor(new Scalar(150, 255, 255));
        cvmanager.registerBlobDetector(redDarkBlobDet);
        cvmanager.registerBlobDetector(redBlobDet);
        cvmanager.registerBlobDetector(blueBlobDet);

        /*
       VuforiaManager.init();

       telemetry.addLine("Vu inited");
       telemetry.update();*/
    }

    private enum AutoState {
        AUTO_START(0,.001),
        ADJUST_SLIDE(.001 , 1),
        MOVE_JEWEL(1,3.5),
        RESET_JEWEL(3.5,4),
        CRYPTO_SAFEZONE(4,Double.POSITIVE_INFINITY),;

        public Range timeRange;

        AutoState(double minTime, double maxTime) {
            timeRange = new Range(minTime, maxTime);
        }
    }

    private AutoState currentState = AutoState.AUTO_START;
    private double stateOffset = 0;

    private int realTurnDir = 0;

    @Override
    public void loop_m(double delta) {
        if (!currentState.timeRange.inRange(ctime))
            // update state based on time
            for (AutoState states : AutoState.values())
                if (states.timeRange.inRange(ctime)) {
                    currentState = states;
                    stateOffset = 0;
                }
        stateOffset += delta;

        switch (currentState) {
            case AUTO_START:
                jewelArmServos.setPosition(-1);
                clawServos.setPosition(clawServoMin);
                break;
            case ADJUST_SLIDE:
                slidePosition = .3;
                break;
            case MOVE_JEWEL:
                double directionPow = .2;
                int turnDir = 0; // 1 == left, -1 == right

                if (realTurnDir == 0) {

                    double redx = redBlobDet.centerOfAll.x;
                    double redx2 = redDarkBlobDet.centerOfAll.x;
                    double blux = blueBlobDet.centerOfAll.x;

                    if (redx == Double.NaN) redx = redx2;

                    double wrongcol = Double.NaN;
                    double rightcol = Double.NaN;

                    switch (Position) {
                        case RedBack:
                        case RedFront:
                            wrongcol = redx;
                            rightcol = blux;
                            break;
                        case BlueBack:
                        case BlueFront:
                            wrongcol = redx;
                            rightcol = blux;
                            break;
                    }

                    int split = 300;

                    if (wrongcol == Double.NaN && rightcol == Double.NaN) {
                        turnDir = 0; // Ensure nothing is done
                    }
                    else
                    {
                        if ((wrongcol < split || wrongcol == Double.NaN)
                                && (rightcol > split || rightcol == Double.NaN))
                            turnDir = 1;
                        if ((wrongcol > split || wrongcol == Double.NaN)
                                && (rightcol < split || rightcol == Double.NaN))
                            turnDir = -1;
                    }

                    /*
                    if (wrongcol > rightcol)
                        turnDir = -1;
                    if (wrongcol < rightcol)
                        turnDir = 1;
                     */


                    realTurnDir = turnDir;
                }

                // figure out which way to turn and turn

                telemetry.addData("realTurnDir", realTurnDir);

                double half_point = currentState.timeRange.size() / 2d;

                if (stateOffset < half_point) {
                    leftMotors.setPower(realTurnDir * directionPow);
                    rightMotors.setPower(realTurnDir * directionPow);
                } else if (stateOffset >= half_point) {
                    jewelArmServos.setPosition(1);
                    directionPow *= 2;
                    leftMotors.setPower(-realTurnDir * directionPow);
                    rightMotors.setPower(-realTurnDir * directionPow);
                }

                break;
            case RESET_JEWEL:
                jewelArmServos.setPosition(1);
                break;
            case CRYPTO_SAFEZONE:
                leftMotors.setPower(0);
                rightMotors.setPower(0);

                double timingConstant = .666;
                double smallOffset = .55;
                switch (Position) {
                    case BlueBack:
                    case RedFront:
                    {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d - smallOffset);
                            rightMotors.setPower(1d);
                        }
                    } break;
                    case BlueFront:
                    case RedBack: {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d);
                            rightMotors.setPower(1d - smallOffset);
                        }
                    } break;
                }

                double turnDuration = 3;
                switch (Position) {
                    case RedBack:
                    case RedFront:
                        if (stateOffset > timingConstant && ctime < timingConstant + turnDuration) {
                            rightMotors.setPower(1);
                            leftMotors.setPower(-1);
                        }
                }

                // temporayre code location
                clawServos.setPosition(clawServoMax);

                double finalMoveTime = .7;
                if (stateOffset > timingConstant + turnDuration && stateOffset < timingConstant + turnDuration + finalMoveTime) {
                    leftMotors.setPower(1);
                    rightMotors.setPower(1);
                }

                break;
        }

        int calcPos = (int)Math.round(slidePosition * linSlideUnitMultiplier);
        if (linSlide.getPower() != 1) linSlide.setPower(1);
        if (linSlide.getTargetPosition() != calcPos) linSlide.setTargetPosition(calcPos);

        telemetry.addLine("Blob centers");
        Point redP = redBlobDet.centerOfAll.x == Double.NaN ?
                redDarkBlobDet.centerOfAll : redBlobDet.centerOfAll;
        telemetry.addData("  Center of all reds", redP);
        telemetry.addData("  Center of all blues", blueBlobDet.centerOfAll);

    }

    @Override
    public void stop() {
        cvmanager.stopCV();
    }

}
