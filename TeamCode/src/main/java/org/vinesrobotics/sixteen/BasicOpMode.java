package org.vinesrobotics.sixteen;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.vinesrobotics.sixteen.hardware.controllers.Controller;
import org.vinesrobotics.sixteen.hardware.controllers.Controllers;
import org.vinesrobotics.sixteen.utils.Logging;

/**
 * Created by Vines HS Robotics on 9/30/2016.
 */

@TeleOp(name="Basic Forward")
//@Disabled
public class BasicOpMode extends OpMode {
    HardwarePushbot robot       = new HardwarePushbot();
    Controllers ctrls;

    public void init(){
        telemetry.addLine("Basic Op mode initialized");
        robot.init(hardwareMap);
        telemetry.update();

        Logging.setTelemetry(telemetry);

        ctrls = Controllers.getControllerObjects(this);
        ctrls.calibrate(Controller.CalibrationType.SIMPLE);
    }
    public void loop(){
        telemetry.addData("Right one",ctrls.a().getJoystick(Controller.Joystick.RIGHT));
        telemetry.addData("Left one",ctrls.a().getJoystick(Controller.Joystick.LEFT));
        telemetry.addData("Right two",ctrls.b().getJoystick(Controller.Joystick.RIGHT));
        telemetry.addData("Left two",ctrls.b().getJoystick(Controller.Joystick.LEFT));
    }
}
