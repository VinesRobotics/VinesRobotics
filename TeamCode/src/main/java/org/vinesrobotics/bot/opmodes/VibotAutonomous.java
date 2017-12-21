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

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.vinesrobotics.bot.utils.Range;
import org.vinesrobotics.bot.utils.vuforia.VuforiaManager;

/**
 * Created by ViBots on 11/30/2017.
 */

public class VibotAutonomous extends VibotControlled {

    public enum AutoPosition {
        RedBack, BlueBack, RedFront, BlueFront, None
    }

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
        /*
       VuforiaManager.init();

       telemetry.addLine("Vu inited");
       telemetry.update();*/
    }

    private enum AutoState {
        AUTO_START(0,.001),
        ADJUST_SLIDE(.001, .2),
        MOVE_JEWEL(.2,3),
        CRYPTO_SAFEZONE(3,Double.POSITIVE_INFINITY),;

        public Range timeRange;

        AutoState(double minTime, double maxTime) {
            timeRange = new Range(minTime, maxTime);
        }
    }

    private AutoState currentState = AutoState.AUTO_START;
    private double stateOffset = 0;

    private static double timingConstant = 1.;
    private static double turnDuration = 1;
    private static double finalMoveTime =.2;
    private static double smallOffset = .5;

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
                jewelArmServos.setPosition(0);
                clawServos.setPosition(clawServoMin);
                break;
            case ADJUST_SLIDE:
                slidePosition = .3;
                break;
            case MOVE_JEWEL:
                // figure out which way to turn and turn
                break;
            case CRYPTO_SAFEZONE:
                jewelArmServos.setPosition(0);

                leftMotors.setPower(0);
                rightMotors.setPower(0);

                switch (Position) {
                    case BlueBack: {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d);
                            rightMotors.setPower(1d - smallOffset);
                        }
                    } break;
                    case BlueFront: {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d-smallOffset);
                            rightMotors.setPower(1d);
                        }
                    } break;

                    case RedBack: {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d-smallOffset);
                            rightMotors.setPower(1d);
                        }
                    } break;
                    case RedFront: {
                        if (stateOffset < timingConstant) {
                            leftMotors.setPower(1d);
                            rightMotors.setPower(1d-smallOffset);
                        }
                    } break;
                }

                switch (Position) {
                    case RedBack:
                    case RedFront:
                        if (stateOffset > timingConstant && ctime < timingConstant+turnDuration) {
                            rightMotors.setPower(1);
                            leftMotors.setPower(-1);
                        }
                }

                // temporayre code location
                clawServos.setPosition(clawServoMax);

                if (stateOffset > timingConstant+turnDuration && stateOffset < timingConstant+turnDuration+finalMoveTime) {
                    leftMotors.setPower(1);
                    rightMotors.setPower(1);
                }

                break;
        }

        int calcPos = (int)Math.round(slidePosition * linSlideUnitMultiplier);
        if (linSlide.getTargetPosition() != calcPos) linSlide.setTargetPosition(calcPos);

    }

    @Override
    public void stop() {

    }

}
