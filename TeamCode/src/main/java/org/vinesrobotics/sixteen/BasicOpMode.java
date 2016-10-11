package org.vinesrobotics.sixteen;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Created by Vines HS Robotics on 9/30/2016.
 */

@Autonomous(name="Basic Forward")
//@Disabled
public class BasicOpMode extends OpMode {
    HardwarePushbot robot       = new HardwarePushbot();
    public void init(){
        telemetry.addLine("Basic Op mode initialized");
        robot.init(hardwareMap);
    }
    public void loop(){

    }
}
