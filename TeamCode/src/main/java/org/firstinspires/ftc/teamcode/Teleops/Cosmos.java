package org.firstinspires.ftc.teamcode.Teleops;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Turret;
import org.firstinspires.ftc.teamcode.subsystems.Drivebase;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.TargetMath;

@TeleOp(name = "cosmos", group = "LinearOpMode")
public class Cosmos extends LinearOpMode
{

    Drivebase drivebase = new Drivebase();
    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Transfer transfer = new Transfer();
    Turret turret = new Turret();
    TargetMath targetingMath = new TargetMath();

    Boolean manualTargeting = false;

    @Override
    public void runOpMode() throws InterruptedException
    {
        targetingMath.follower = Constants.createFollower(hardwareMap);

        drivebase.leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        drivebase.leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        drivebase.rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        drivebase.rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        shooter.shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shooterMotor1");
        shooter.shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooterMotor2");
        shooter.hood = hardwareMap.get(Servo.class, "hood");
        shooter.gate = hardwareMap.get(Servo.class, "gate");

        turret.turretR1 = hardwareMap.get(Servo.class, "turretServo");
        intake.intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        transfer.transferMotor = hardwareMap.get(DcMotor.class, "transferMotor");

        drivebase.leftFront.setDirection(DcMotor.Direction.REVERSE);
        drivebase.leftBack.setDirection(DcMotor.Direction.REVERSE);

        shooter.shooterMotor2.setDirection(DcMotorEx.Direction.REVERSE);
        shooter.shooterMotor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.shooterMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(0, 0, 0, 0);
        //shooter.shooterMotor1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        //shooter.shooterMotor2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);




        Pose HPBlue = new Pose(8.75, 8.75, 0);
        Pose HPRed = new Pose(183.25, 8.75, 0);

        Pose BlueStandardGoal = new Pose(12, 180, 0);
        Pose BlueSpecialGoal = new Pose(84, 180, 0);
        Pose RedStandardGoal = new Pose(108, 180, 0);
        Pose RedSpecialGoal = new Pose(180, 180, 0);


        waitForStart();
        turret.setupTurret();

        while (opModeIsActive())
        {

            targetingMath.follower.update();
            drivebase.drive(-1 * gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x, 1.0);
            intake.powerIntake(gamepad2.right_stick_y);
            transfer.powerTransfer(gamepad2.left_stick_y);

            //if(gamepad2.right_trigger >= 0.5){
            //shooter.openGate();
            //} else {
            //shooter.closeGate();
            //}


            //Sets up auto aim
            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                targetingMath.setKnownPositionForTargeting(HPBlue);
                targetingMath.setGoalLocation(BlueStandardGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.b){
                targetingMath.setKnownPositionForTargeting(HPBlue);
                targetingMath.setGoalLocation(BlueSpecialGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.y){
                targetingMath.setKnownPositionForTargeting(HPRed);
                targetingMath.setGoalLocation(RedStandardGoal);
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.x){
                targetingMath.setKnownPositionForTargeting(HPRed);
                targetingMath.setGoalLocation(RedSpecialGoal);
            }




            if(gamepad2.rightStickButtonWasPressed() && manualTargeting==true){
                manualTargeting = false;
            }
            if(gamepad2.leftStickButtonWasPressed() && manualTargeting==false){
                manualTargeting = true;
            }


            //automatic hood, flywheel, and turret adjust
            if (targetingMath.follower.getPose() != null && targetingMath.goal != null && manualTargeting == false){
                turret.aimTurret();
                shooter.aimHood();
                shooter.setFlywheelVelocity();

            }

            //manual hood and flywheel adjust
            if(manualTargeting == true){

                turret.setupTurret();

                if (gamepad2.dpad_down) {
                    shooter.hood.setPosition(0.25);
                }

                if (gamepad2.dpad_right) {
                    shooter.hood.setPosition(0.5);
                }

                if (gamepad2.dpad_up) {
                    shooter.hood.setPosition(0.75);
                }

                if (gamepad2.dpad_left) {
                    shooter.hood.setPosition(1);
                }



                if (gamepad2.a) {
                    shooter.shooterMotor1.setVelocity(0);
                    shooter.shooterMotor1.setVelocity(0);

                }

                if (gamepad2.b) {
                    shooter.shooterMotor1.setVelocity(2300);
                    shooter.shooterMotor1.setVelocity(2300);

                }

                if (gamepad2.x) {
                    shooter.shooterMotor1.setVelocity(1800);
                    shooter.shooterMotor2.setVelocity(1800);

                }

                if (gamepad2.y) {
                    shooter.shooterMotor1.setVelocity(1300);
                    shooter.shooterMotor2.setVelocity(1300);
                }
            }


        }

    }
}