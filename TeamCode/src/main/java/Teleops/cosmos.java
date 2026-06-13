package Teleops;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "cosmos", group = "LinearOpMode")
public class cosmos extends LinearOpMode
{
    //Variables
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private DcMotor shooter;
    private Servo servo1, servo2;



    @Override
    public void runOpMode()
    {

    }

}


