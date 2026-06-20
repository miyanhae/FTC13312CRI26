package subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


public class Shooter {
    private DcMotorEx shooterMotor1, shooterMotor2;
    private Servo gate, hood;


    public void shotRangeShort()
    {
        shooterMotor1.setVelocity(1000);
        shooterMotor2.setVelocity(1000);
    }
    public void shotRangeMedium()
    {
        shooterMotor1.setVelocity(1500);
        shooterMotor2.setVelocity(1500);

    }
    public void shotRangeFar()
    {
        shooterMotor1.setVelocity(2000);
        shooterMotor2.setVelocity(2000);
    }
    public void stopShooter()
    {
        shooterMotor1.setVelocity(0);
        shooterMotor2.setVelocity(0);
    }



    public void closeGate() {
            gate.setPosition(0.0);
        }

    public void openGate() {
        gate.setPosition(0.5);
    }







    public void launchAngle1(){
        hood.setPosition(0.25);
    }

    public void launchAngle2(){
        hood.setPosition(0.5);
    }

    public void launchAngle3(){
        hood.setPosition(0.75);
    }

    public void launchAngle4(){
        hood.setPosition(1);
    }

}