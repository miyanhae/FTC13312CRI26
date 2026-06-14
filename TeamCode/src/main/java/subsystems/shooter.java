package subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class shooter extends LinearOpMode {
    private DcMotor shooter1, shooter2;
    private Servo gate, hood;
    private double strength = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();
        shooter1 = hardwareMap.get(DcMotor.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotor.class, "shooter2");

        gate = hardwareMap.get(Servo.class, "gate");
        hood = hardwareMap.get(Servo.class, "hood");

        shooter2.setDirection(DcMotor.Direction.REVERSE);

        //hood
        if(gamepad2.dpad_down) {
            hood.setPosition(0.25);
        }
        if(gamepad2.dpad_up) {
            hood.setPosition(0.75);
        }


        //gate
        if(gamepad2.right_bumper){
            gate.setPosition(0.5);
        }
        if(gamepad2.left_bumper){
            gate.setPosition(0.0);
        }

        //shooter toggle
        if(gamepad2.y){
            strength = -0.2;
        }

        if(gamepad2.x){
            strength = 0;
        }

        if(gamepad2.b){
            strength = 0.9;
        }

        if(gamepad2.a){
            strength = 0.7;
        }

        shooter1.setPower(strength);
        shooter2.setPower(strength);

    }
}
