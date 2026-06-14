package subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


public class Shooter {
    private DcMotorEx shooter1, shooter2;
    private Servo gate, hood;
    boolean openGateCheck = true;


    public void shotRangeShort()
    {
        shooter1.setVelocity(1000);
        shooter2.setVelocity(1000);
    }
    public void shotRangeMedium()
    {
        shooter1.setVelocity(1500);
        shooter2.setVelocity(1500);

    }
    public void shotRangeFar()
    {
        shooter1.setVelocity(2000);
        shooter2.setVelocity(2000);
    }
    public void stopShooter()
    {
        shooter1.setVelocity(0);
        shooter2.setVelocity(0);
    }



    public void toggleGate() {
        if (openGateCheck == true) {
            gate.setPosition(0.0);
            openGateCheck = false;
        } else if (openGateCheck == false) {
            gate.setPosition(0.5);
            openGateCheck = true;
        }
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