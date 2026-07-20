package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class Shooter {
    TargetMath targetingMath = new TargetMath();

    public DcMotorEx shooterMotor1, shooterMotor2;
    public Servo gate, hood;

    public void closeGate() {
            gate.setPosition(0.0);
        }
    public void openGate() {
        gate.setPosition(0.5);
    }

    public void setFlywheelVelocity(){

        double flywheelVelocityRadsPerSec = targetingMath.calculations()[1] / 1.41731;
        //linear speed divided by radius of flywheel = angular speed

        shooterMotor1.setVelocity(flywheelVelocityRadsPerSec, AngleUnit.RADIANS);
        shooterMotor2.setVelocity(flywheelVelocityRadsPerSec, AngleUnit.RADIANS);
    }


    public void aimHood(){

        double hoodAngleTicks = (targetingMath.calculations()[0] -22.57) /25;
        hood.setPosition(hoodAngleTicks);
    }


}