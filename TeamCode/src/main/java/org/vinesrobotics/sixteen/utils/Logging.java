package org.vinesrobotics.sixteen.utils;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

public class Logging {

    private static Telemetry telemetry = null;

    /**
     * Sets logging telemetry object.
     * @param tel
     */
    public static void setTelemetry(Telemetry tel) {
        telemetry = tel;
    }

    /**
     * Logs single line to telemetry.
     * @param s
     */
    public static void log(String s) {
        telemetry.addLine(s);
        telemetry.update();
    }

    /**
     * Logs object to telemetry.
     * @param s
     * @param o
     */
    public static void logData(String s,Object o) {
        telemetry.addData(s,o);
        telemetry.update();
    }

}
