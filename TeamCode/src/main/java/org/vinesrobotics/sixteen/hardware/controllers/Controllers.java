package org.vinesrobotics.sixteen.hardware.controllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

public class Controllers {

    public static Controllers getControllerObjects(OpMode omode) {
        return new Controllers(omode.gamepad1, omode.gamepad2);
    }

    private Controller gpa = null;
    private Controller gpb = null;

    private Controllers(Gamepad one, Gamepad two) {
        if (one != null)
            gpa = new Controller(one,"1");
        if (two != null)
            gpb = new Controller(two,"2");
    }

    public Controller a(){
        return gpa;
    }

    public Controller b(){
        return gpb;
    }

}
