package org.vinesrobotics.sixteen.utils;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.vinesrobotics.sixteen.hardware.LocalControl;

/**
 * Created by Vines HS Robotics on 10/14/2016.
 */

public class Logging {

    protected static Telemetry telemetry = null;
    public static LocalControl<Telemetry> hardwareAccess = null;

    /**
     * Sets logging telemetry object.
     * @param tel
     */
    public static void setTelemetry(Telemetry tel) {
        telemetry = tel;
        hardwareAccess = new LocalControl<>(tel);
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
