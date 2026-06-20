package Teleops;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import subsystems.Drivebase;
import subsystems.Intake;
import subsystems.Shooter;
import subsystems.Transfer;

@TeleOp(name = "cosmos", group = "LinearOpMode")
public class cosmos extends LinearOpMode
{
    //Variables
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private DcMotorEx shooterMotor1, shooterMotor2;
    private Servo turret1, turret2;
    private Servo hood;
    private Servo blocker;
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {

        waitForStart();

        //hardware maps
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        hood = hardwareMap.get(Servo.class, "hood");
        blocker = hardwareMap.get(Servo.class, "blocker");

        turret1 = hardwareMap.get(Servo.class, "turret1");
        turret2 = hardwareMap.get(Servo.class, "turret2");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); //0 is blue 1 is red


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        shooterMotor2.setDirection(DcMotorEx.Direction.REVERSE);
        shooterMotor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(0, 0, 0, 0);
        shooterMotor1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        shooterMotor2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);


        Drivebase drivebase = new Drivebase();
        Intake intake = new Intake();
        Shooter shooter = new Shooter();
        Transfer transfer = new Transfer();

        while (opModeIsActive())
        {

            drivebase.drive(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x, 1.0);

            intake.powerIntake(gamepad2.right_stick_y);
            transfer.powerTransfer(gamepad2.left_stick_y);



            //shooting motor controls below

            if(gamepad2.a){
                shooter.stopShooter();
            }
            if (gamepad2.b) {
                shooter.shotRangeFar();
            }
            if (gamepad2.y) {
                shooter.shotRangeMedium();
            }
            if (gamepad2.x) {
                shooter.shotRangeShort();
            }

            //Hood controls below

            if(gamepad2.dpad_left){
                shooter.launchAngle1();
            }
            if(gamepad2.dpad_up){
                shooter.launchAngle2();
            }
            if(gamepad2.dpad_right){
                shooter.launchAngle3();
            }
            if(gamepad2.dpad_down){
                shooter.launchAngle4();
            }


            if(gamepad2.right_trigger >= 0.5){
                shooter.openGate();
            } else {
                shooter.closeGate();
            }


        }

    }
}


