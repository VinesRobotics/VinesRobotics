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

package org.firstinspires.ftc.robotcontroller.internal;

import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.eventloop.opmode.AnnotatedOpModeRegistrar;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptNullOp;

/**
 * {@link FtcOpModeRegister} is responsible for registering opmodes for use in an FTC game.
 * @see #register(OpModeManager)
 */
public class FtcOpModeRegister implements OpModeRegister {

    /**
     * {@link #register(OpModeManager)} is called by the SDK game in order to register
     * OpMode classes or instances that will participate in an FTC game.
     *
     * There are two mechanisms by which an OpMode may be registered.
     *
     *  1) The preferred method is by means of class annotations in the OpMode itself.
     *  See, for example the class annotations in {@link ConceptNullOp}.
     *
     *  2) The other, retired,  method is to modify this {@link #register(OpModeManager)}
     *  method to include explicit calls to OpModeManager.register().
     *  This method of modifying this file directly is discouraged, as it
     *  makes updates to the SDK harder to integrate into your code.
     *
     * @param manager the object which contains methods for carrying out OpMode registrations
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.TeleOp
     * @see com.qualcomm.robotcore.eventloop.opmode.Autonomous
     */
    public void register(OpModeManager manager) {

        /**
         * Register OpModes implemented in the Blocks visual programming language.
         */
        BlocksOpMode.registerAll(manager);

        /**
         * Register OpModes that use the annotation-based registration mechanism.
         */
        AnnotatedOpModeRegistrar.register(manager);

        /**
         * Any manual OpMode class registrations should go here.
         */
    }
}
