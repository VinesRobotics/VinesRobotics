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

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.hardware.matrix.MatrixDcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * This is NOT an OpMode
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot, using Matrix Hardware.
 * See PushbotTeleopTank_Iterative for a usage examples.
 *
 * This is coded as an Extension of HardwarePushbot to illustrate that the only additional
 * action REQUIRED for a MATRIX controller is enabling the Servos.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Matrix Controller has been assigned the name:  "matrix controller"
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 *
 * In addition, the Matrix Controller has been assigned the name:  "matrix controller"
 */
public class HardwarePushbotMatrix extends HardwarePushbot
{
    /* Public OpMode members. */
    private MatrixDcMotorController matrixMotorController = null;
    private ServoController matrixServoController = null;

    /* Constructor */
    public HardwarePushbotMatrix(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {

        // Initialize base Motor and Servo objects
        super.init(ahwMap);

        /*
         * Matrix controllers are special.
         *
         * A Matrix controller is one controller with both motors and servos
         * but software wants to treat it as two distinct controllers, one
         * DcMotorController, and one ServoController.
         *
         * We accomplish this by initializing Motor and Servo controller with the same name
         * given in the configuration.  In the example below the name of the controller is
         * "MatrixController"
         *
         * Normally we don't need to access the controllers themselves, we deal directly with
         * the Motor and Servo objects, but the Matrix interface is different.
         *
         * In order to activate the servos, they need to be enabled on the controller with
         * a call to pwmEnable() and disabled with a call to pwmDisable()
         *
         * Also, the Matrix Motor controller interface provides a call that enables all motors to
         * updated simultaneously (with the same value).
         */

        // Initialize Matrix Motor and Servo objects
        matrixMotorController = (MatrixDcMotorController)ahwMap.dcMotorController.get("matrix controller");
        matrixServoController = ahwMap.servoController.get("matrix controller");

        // Enable Servos
        matrixServoController.pwmEnable();       // Don't forget to enable Matrix Output
    }
}
