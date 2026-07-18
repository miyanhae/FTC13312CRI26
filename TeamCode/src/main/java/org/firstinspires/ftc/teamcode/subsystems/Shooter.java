package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


public class Shooter {
    public DcMotorEx shooterMotor1, shooterMotor2;
    public Servo gate, hood;

    public void closeGate() {
            gate.setPosition(0.0);
        }
    public void openGate() {
        gate.setPosition(0.5);
    }

}