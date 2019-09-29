package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

/**
 * Aim:
 * 1. Find the relationship between the X and Y outputs for the joystick
 * 2. Find how the IMU measures angles
 * 3. Find max value inputs for motor powers
 *
 * Results:
 * 1. When the joystick is pushed all the way up, y = 1.0, x = 0.0. As the joystick rotates
 * clockwise, y remains at 1.0 while x increases, up until a certain point where y begins to
 * decrease and x continues to increase to 1.0 and vice versa in the anticlockwise direction
 *
 * 2. The IMU measure from 0 degrees to 180 degrees and then measures from -179 back to 0
 *
 * 3. Max inputs for motor power was at 2.57. Min inputs (when the joystick isn't moved) for motor
 *    power was -0.11. This is considered negligible as it is not enough to power the motor
 */

@TeleOp

public class Experimental extends OpMode{

    //IMU
    BNO055IMU imu;
    Orientation angles;

    @Override
    public void init(){

        //DEVICE MAPPING

        //IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class,"imu");
        imu.initialize(parameters);
    }

    @Override
    public void loop(){

        // TELEMETRY
        double lefty = -gamepad1.left_stick_y;
        double leftx = gamepad1.left_stick_x;
        double rightx = gamepad1.right_stick_x;

        //Joystick Power Max Values
        fLMax = lefty + leftx + rightx;
        bLMax = lefty - leftx + rightx;
        fRMax = lefty - leftx - rightx;
        bRMax = lefty + leftx - rightx;

        // Joystick
        telemetry.addData("Left Stick Y: ", lefty);
        telemetry.addData("Left Stick X: ", leftx);
        telemetry.addData("Right Stick X: ", rightx);

        telemetry.addData("");

        telemetry.addData("fLMax: ", fLMax);
        telemetry.addData("bLMax: ", bLMax);
        telemetry.addData("fRMax: ", fRMax);
        telemetry.addData("bRMax: ", bRMax);

        telemetry.addData("");

        // IMU
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("Pitch: ", angles.firstAngle);
        telemetry.addData("Yaw: ", angles.secondAngle);
        telemetry.addData("Roll: ", angles.thirdAngle);
        telemetry.update();
    }
}