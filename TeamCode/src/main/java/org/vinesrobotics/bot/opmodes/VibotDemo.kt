/*
 * Copyright (c) 2018 Vines High School Robotics Team
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

package org.vinesrobotics.bot.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import org.vinesrobotics.bot.hardware.Hardware
import org.vinesrobotics.bot.hardware.controllers.Controller
import org.vinesrobotics.bot.hardware.controllers.Controllers
import org.vinesrobotics.bot.hardware.controllers.enums.Joystick
import org.vinesrobotics.bot.hardware.groups.MotorDeviceGroup
import org.vinesrobotics.bot.hardware.groups.ServoDeviceGroup
import org.vinesrobotics.bot.utils.Axis
import org.vinesrobotics.bot.utils.Logging
import java.security.InvalidKeyException

/**
 * Created by ViBots on 4/27/2018.
 */
@TeleOp(name = "Demo Op (Kotlin)", group = "demo")
class VibotDemo : OpMode() {

    private lateinit var controllers: Controllers
    private lateinit var main: Controller
    private lateinit var sub: Controller

    private var robot: Hardware = Hardware()
    private var leftDrive: MotorDeviceGroup = MotorDeviceGroup()
    private var rightDrive: MotorDeviceGroup = MotorDeviceGroup()
    private var flagServo: ServoDeviceGroup = ServoDeviceGroup()

    private val highVal = 1.0
    private val lowVal = 0.0
    private var tgtPos = lowVal

    override fun loop() {
        if (flagServo.position == tgtPos) {
            tgtPos = if (tgtPos == lowVal) highVal else lowVal
            flagServo.position = tgtPos
        }

        var mainState = main.controllerState

        leftDrive.power = mainState.joyVal(Joystick.LEFT, Axis.Y)
        rightDrive.power = mainState.joyVal(Joystick.RIGHT, Axis.Y)
    }

    override fun init() {
        Logging.setTelemetry(telemetry)
        try {
            robot.registerHardwareKeyName("flag")
        } catch (e: InvalidKeyException) {}
        robot.initHardware(hardwareMap)

        var lDriveOpts = robot.getDevicesWithAllKeys<DcMotor>("left","drive")
        try {
            for (opt in lDriveOpts) leftDrive.addDevice(opt.get() as DcMotor)
            leftDrive.direction = DcMotorSimple.Direction.FORWARD
            leftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        } catch (e: Exception) {}

        var rDriveOpts = robot.getDevicesWithAllKeys<DcMotor>("right","drive")
        try {
            for (opt in rDriveOpts) rightDrive.addDevice(opt.get() as DcMotor)
            rightDrive.direction = DcMotorSimple.Direction.REVERSE
            rightDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        } catch (e: Exception) {}

        var flagOpts = robot.getDevicesWithAllKeys<Servo>("servo","flag")
        try {
            for (opt in flagOpts) flagServo.addDevice(opt.get() as Servo)
            flagServo.direction = Servo.Direction.FORWARD
        } catch (e: Exception) {}

        controllers = Controllers.getControllerObjects(this)
        main = controllers.a()
        sub = controllers.b()
    }
}