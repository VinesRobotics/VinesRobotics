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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.vuforia.Vuforia;
import com.vuforia.VuforiaBase;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.vinesrobotics.bot.R;
import org.vinesrobotics.bot.hardware.Hardware;
import org.vinesrobotics.bot.hardware.HardwareElement;
import org.vinesrobotics.bot.hardware.controllers.Controller;
import org.vinesrobotics.bot.hardware.controllers.ControllerState;
import org.vinesrobotics.bot.hardware.controllers.Controllers;
import org.vinesrobotics.bot.hardware.controllers.enums.Button;
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick;
import org.vinesrobotics.bot.hardware.groups.MotorDeviceGroup;
import org.vinesrobotics.bot.hardware.groups.ServoDeviceGroup;
import org.vinesrobotics.bot.utils.Logging;
import org.vinesrobotics.bot.utils.Utils;
import org.vinesrobotics.bot.utils.Vec2D;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.List;


@TeleOp(name="Controlled (17)", group="Vines")
//@Disabled
public class VibotControlled extends OpMode {

    public MotorDeviceGroup leftMotors;
    public MotorDeviceGroup rightMotors;

    public MotorDeviceGroup linSlide;
    MotorConfigurationType linSlideCfg;
    static double mainLinSlideMax = 2.2 ;
    double linSlideMax = mainLinSlideMax;
    static double mainLinSlideMin = 0;
    double linSlideMin = mainLinSlideMin;
    double linSlideSpeed = 3;
    double linSlideUnitMultiplier;

    static double clawServoMin = 0;
    static double clawServoMax = .5;
    public ServoDeviceGroup clawServos;
    public ServoDeviceGroup jewelArmServos;

    public Hardware robot = new Hardware();

    @Override
    public void init() {
        Logging.setTelemetry(telemetry);
        try {
            robot.registerHardwareKeyName("intake");
            robot.registerHardwareKeyName("bumper");
            robot.registerHardwareKeyName("slide");
            robot.registerHardwareKeyName("claw");
            robot.registerHardwareKeyName("jewel");
            robot.registerHardwareKeyName("arm");
        } catch (InvalidKeyException e) {}
        robot.initHardware(hardwareMap);

        List<HardwareElement> lefts = robot.getDevicesWithAllKeys("left","drive");
        leftMotors = new MotorDeviceGroup();
        try {
            for (HardwareElement he : lefts) {
                leftMotors.addDevice((DcMotor) he.get());
            }
            leftMotors.setDirection(DcMotor.Direction.FORWARD);
            leftMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }catch (Exception e){}

        List<HardwareElement> right = robot.getDevicesWithAllKeys("right","drive");
        rightMotors = new MotorDeviceGroup();
        try {
            for (HardwareElement he : right) {
                rightMotors.addDevice((DcMotor)he.get());
            }
            rightMotors.setDirection(DcMotor.Direction.REVERSE);
            rightMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }catch (Exception e){}

        List<HardwareElement> slides = robot.getDevicesWithAllKeys("motor", "slide");
        linSlide = new MotorDeviceGroup();
        try {
            linSlide.addDevice((DcMotor) slides.get(0).get());
            linSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            linSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linSlide.setContainsOne(); // required to properly get the motor type
            linSlideCfg = linSlide.getMotorType();
            linSlideUnitMultiplier = linSlideCfg.getTicksPerRev();

            /*
            int cpos = linSlide.getCurrentPosition(); // new zero position
            double newz = cpos/linSlideUnitMultiplier;
            linSlideMin = mainLinSlideMin-newz;
            linSlideMax = mainLinSlideMax-newz;

            slidePosition = linSlideMin;

            linSlide.setTargetPosition((int)Math.round(linSlideMin * linSlideUnitMultiplier));
            */
        }catch (Exception e){}

        List<HardwareElement> claw = robot.getDevicesWithAllKeys("claw","servo");
        clawServos = new ServoDeviceGroup();
        try {
            for (HardwareElement he : claw) {
                Servo serv = (Servo)he.get();
                clawServos.addDevice(serv);
                if (robot.hasKey(he, "right")) {
                    serv.setDirection(Servo.Direction.REVERSE);
                }
            }
        }catch (Exception e){}

        List<HardwareElement> jarm = robot.getDevicesWithAllKeys("jewel","arm","servo");
        jewelArmServos = new ServoDeviceGroup();
        try {
            for (HardwareElement he : jarm) {
                Servo serv = (Servo)he.get();
                jewelArmServos.addDevice(serv);
            }
        }catch (Exception e){}

        // old init_m
        controllers = Controllers.getControllerObjects(this);
        main_ct = controllers.a();
        sub_ct = controllers.b();

        //Vuforia.init();
        init_spec();
    }

    public void init_spec() {

    }

    public void init_loop() {
        /*init_loop_m();*/
    }

    @Override
    public void start() {
        /*start_m();*/
        if (died) return;

        Utils.getDeltaTime(this.getRuntime());
    }

    private Exception error = null;
    public double ctime = 0;

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in x loop while this op mode is running
     */
    public void loop() {
        if (error != null) {
            ByteArrayOutputStream sw = new ByteArrayOutputStream();
            error.printStackTrace(new PrintStream(sw));
            telemetry.addLine(String.format("Error after %g seconds:%n%s", this.getRuntime(), sw.toString()));
            updateTelemetry(telemetry);
        } else {
            try {
                if (died) return;
                double delta = Utils.getDeltaTime(this.getRuntime());
                ctime += delta;
                loop_m(delta);
            } catch (Exception e) {
                error = e;
            }
        }
    }

    @Override
    public void stop() {
        //Vuforia.deinit();
    }

    private boolean died = false;

    protected Controllers controllers;
    protected Controller main_ct;
    protected Controller sub_ct;

    protected boolean debugMode = false;
    protected boolean configureMode = false;

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    // loop_m separation preserved to remove error checking dirtiness
    protected double clawPosition = 0;
    protected double slidePosition = linSlideMin;
    private boolean lastToggleDebug = false;
    private boolean lastToggleConfig = false;
    public void loop_m(double deltaTime) {
        ControllerState main = this.main_ct.getControllerState();
        ControllerState sub = this.sub_ct.getControllerState();

        Vec2D<Double> left = main.joy(Joystick.LEFT);
        Vec2D<Double> right = main.joy(Joystick.RIGHT);

        double lPower = left.y(),rPower = right.y();

        leftMotors.setPower(lPower);
        rightMotors.setPower(rPower);

        double slidePower = 1; // power
        if (linSlide.getPower()!=slidePower) linSlide.setPower(slidePower);
        if (sub.isPressed(Button.UP)) slidePosition += linSlideSpeed * deltaTime;
        if (sub.isPressed(Button.DOWN)) slidePosition -= linSlideSpeed * deltaTime;
        if (slidePosition > linSlideMax) slidePosition = linSlideMax;
        if (slidePosition < linSlideMin) slidePosition = linSlideMin;
        int calcPos = (int)Math.round(slidePosition * linSlideUnitMultiplier);
        if (linSlide.getTargetPosition() != calcPos) linSlide.setTargetPosition(calcPos);

        // literal copy of Shields' code; there's a better way to do this
        double servo_speed = .8;
        if (sub.isPressed(Button.A)) clawPosition += servo_speed*deltaTime;
        if (sub.isPressed(Button.X)) clawPosition -= servo_speed*deltaTime;
        if (clawPosition > clawServoMax) clawPosition = clawServoMax;
        if (clawPosition < clawServoMin) clawPosition = clawServoMin;

        clawServos.setPosition(clawPosition);

        telemetry.addLine("Values in range of -1 to +1" );
        telemetry.addData("Speed", (-lPower-rPower)/2 );
        telemetry.addData("Turning Speed", (-lPower+rPower)/2 );

        //boolean lastToggle = main.last().isPressed(Button.LB) && main.last().isPressed(Button.RB);
        boolean toggleDebug = main.isPressed(Button.LB) && main.isPressed(Button.RB);

        if (toggleDebug && !lastToggleDebug) debugMode =! debugMode;
        lastToggleDebug = toggleDebug;
        if (debugMode) {
            telemetry.addLine();
            telemetry.addLine("Debugging");

            telemetry.addData("clawPosition", clawPosition);
            telemetry.addData("servoSpeed", servo_speed);
            telemetry.addData("slidePosition", slidePosition);
            telemetry.addData("slideMin", linSlideMin);
            telemetry.addData("slideMax", linSlideMax);

            boolean toggleConfigure = main.isPressed(Button.X);
            if (toggleConfigure && !lastToggleConfig) configureMode =! configureMode;
            lastToggleConfig = toggleConfigure;
            if (configureMode) {
                telemetry.addLine();
                telemetry.addLine("Configuring");

                // slide pos cfg info
                telemetry.addLine("UP/DN to change realSlideMin");
                telemetry.addLine("L/R to change realSlideMax");
                telemetry.addData("realSlideMin", mainLinSlideMin);
                telemetry.addData("realSlideMax", mainLinSlideMax);

                boolean up = main.isPressed(Button.UP);
                boolean dn = main.isPressed(Button.DOWN);
                boolean l = main.isPressed(Button.LEFT);
                boolean r = main.isPressed(Button.RIGHT);

                if (up && ! lastPressedUp) {
                    mainLinSlideMin += .5;
                    linSlideMin += .5;
                }
                if (dn && ! lastPressedDn) {
                    mainLinSlideMin -= .5;
                    linSlideMin -= .5;
                }
                if (l && ! lastPressedL) {
                    mainLinSlideMax += .5;
                    linSlideMax += .5;
                }
                if (r && ! lastPressedR) {
                    mainLinSlideMax -= .5;
                    linSlideMax -= .5;
                }

                lastPressedUp = up;
                lastPressedDn = dn;
                lastPressedL = l;
                lastPressedR = r;
            }
        }
        updateTelemetry(telemetry);
    }
    boolean lastPressedUp = false;
    boolean lastPressedDn = false;
    boolean lastPressedL = false;
    boolean lastPressedR = false;
}