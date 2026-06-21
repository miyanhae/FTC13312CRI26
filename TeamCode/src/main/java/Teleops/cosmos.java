package Teleops;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import subsystems.AutoAimTurret;
import subsystems.Drivebase;
import subsystems.Intake;
import subsystems.Shooter;
import subsystems.Transfer;

@TeleOp(name = "cosmos", group = "LinearOpMode")
public class cosmos extends LinearOpMode
{

    Drivebase drivebase = new Drivebase();
    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Transfer transfer = new Transfer();
    AutoAimTurret turret = new AutoAimTurret();

    @Override
    public void runOpMode() throws InterruptedException
    {

        Pose2D HPBlue = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
        Pose2D HPRed = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);

        Pose2D BlueStandardGoal = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
        Pose2D BlueSpecialGoal = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
        Pose2D RedStandardGoal = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
        Pose2D RedSpecialGoal = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);


        waitForStart();
        turret.setupTurret();

        while (opModeIsActive())
        {

            drivebase.drive(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x, 1.0);
            intake.powerIntake(gamepad2.right_stick_y);
            transfer.powerTransfer(gamepad2.left_stick_y);



            //Sets up auto aim
            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                turret.setupPinpoint(HPBlue);
                turret.setGoalLocation(BlueStandardGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                turret.setupPinpoint(HPBlue);
                turret.setGoalLocation(BlueSpecialGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                turret.setupPinpoint(HPRed);
                turret.setGoalLocation(RedStandardGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                turret.setupPinpoint(HPRed);
                turret.setGoalLocation(RedSpecialGoal);
            }

            if(turret.goal != null) {
            turret.aimTurret();
            }



            if(gamepad2.right_trigger >= 0.5){
                shooter.openGate();
            } else {
                shooter.closeGate();
            }


        }

    }
}


