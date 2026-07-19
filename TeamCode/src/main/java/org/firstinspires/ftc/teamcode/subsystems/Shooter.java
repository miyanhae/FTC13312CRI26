package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


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

        double flywheelVelocityTicks = targetingMath.calculations()[1];
        shooterMotor1.setVelocity(flywheelVelocityTicks);
        shooterMotor2.setVelocity(flywheelVelocityTicks);
    }


    public void aimHood(){

        double hoodAngleTicks = (targetingMath.calculations()[0] -22.57) /25;
        shooterMotor1.setVelocity(hoodAngleTicks);
        shooterMotor2.setVelocity(hoodAngleTicks);
    }


}