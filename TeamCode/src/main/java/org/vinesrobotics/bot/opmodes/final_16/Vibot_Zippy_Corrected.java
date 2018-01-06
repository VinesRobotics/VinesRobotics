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

package org.vinesrobotics.bot.opmodes.final_16;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.vinesrobotics.bot.R;
import org.vinesrobotics.bot.hardware.controllers.Controller;
import org.vinesrobotics.bot.hardware.controllers.ControllerState;
import org.vinesrobotics.bot.hardware.controllers.Controllers;
import org.vinesrobotics.bot.hardware.controllers.enums.Button;
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick;
import org.vinesrobotics.bot.utils.Utils;
import org.vinesrobotics.bot.utils.Vec2D;

import java.lang.reflect.InvocationTargetException;


@TeleOp(name="Vibot_Zippy_Corrected", group="Vines")
@Disabled
public class Vibot_Zippy_Corrected extends VibotHardwareBase {

    /* Declare OpMode members. */
    //Hardware robot       = new Hardware();


    boolean died = false;

    Controllers controllers;
    Controller main_ct;
    Controller turret_ct;

    //MotorDeviceGroup leftMotors;
    //MotorDeviceGroup rightMotors;
    //Servo arm_1;
    //double a1_pos = 0;
    //Servo arm_2;
    //double a2_pos = 0;
    //DcMotor itk;
    //Catapult catapult;

    //final double a1_target = 0;
    //final double a2_target = 0;

    //final int catapult_pos = 255;
    //final int catapult_root = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init_m() {
        controllers = Controllers.getControllerObjects(this);
        main_ct = controllers.a();
        turret_ct = controllers.b();
        lastMain = main_ct.getControllerState();
        lastTurret = turret_ct.getControllerState();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop_m() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start_m() {
        if (died) return;
    }

    boolean catapult_debug = false;
    ControllerState lastMain = null;
    ControllerState lastTurret = null;
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop_m() {
        if (died) return;

        ControllerState main = this.main_ct.getControllerState();
        ControllerState turret = this.turret_ct.getControllerState();

        if (main.isPressed(Button.UP) && !lastMain.isPressed(Button.UP)) catapult_debug = !catapult_debug;

        Vec2D<Double> left;
        Vec2D<Double> right;

        left = main.joy(Joystick.RIGHT);
        right = main.joy(Joystick.LEFT);

        leftMotors.setPower(left.y());
        rightMotors.setPower(right.y());

        if (main.isPressed(Button.RS) && main.isPressed(Button.LS)) main = null;

        double itkpw = main.btnVal(Button.RT);// * ( ( main.isPressed(Button.RB) ) ? -1 : 1 );

        if (main.isPressed(Button.RB)) itkpw = -itkpw;

        itk.setPower( itkpw );
        // itk.getController().setMotorPower(itk.getPortNumber(),itkpw);

        if (!catapult.isManual()) {
            if (!main.isPressed(Button.A))
                catapult.ready();
            // Use LT LB

        } else if (catapult.isManualReady() && !main.isPressed(Button.X)){
            double catpw = main.btnVal(Button.LT);/// * ( ( main.isPressed(Button.LB) ) ? -1 : 1 );
            if (main.isPressed(Button.LB)) catpw = -catpw;
            catapult.catapult().setPower(catpw);
        }

        if (main.isPressed(Button.A)) catapult.fire();
        if (main.isPressed(Button.DOWN) && !lastMain.isPressed(Button.DOWN)) catapult.toggleManual();

        telemetry.addLine( "Values in range of -1 to +1" );
        telemetry.addData( "Speed", (-left.y()-right.y())/2 );
        telemetry.addData( "Turning Speed", (-left.y()+right.y())/2 );
        telemetry.addData( "Intake Speed", itkpw );
        telemetry.addData( "Actual intake speed", itk.getPower() );
        updateTelemetry(telemetry);

        lastMain = main_ct.getControllerState().clone();
        lastTurret = turret_ct.getControllerState().clone();
    }



}
// cause evry time I touch i get x felling