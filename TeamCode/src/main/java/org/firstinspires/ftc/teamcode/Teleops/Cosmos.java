package org.firstinspires.ftc.teamcode.Teleops;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@TeleOp(name = "cosmos", group = "LinearOpMode")
public class Cosmos extends LinearOpMode
{
    DcMotor leftFront, leftBack, rightFront, rightBack;
    DcMotor intakeMotor, transferMotor;
    DcMotorEx shooterMotor1, shooterMotor2;
    Servo gate, hood, turret;

    Pose goal;

    @Override
    public void runOpMode() throws InterruptedException
    {
        Follower follower = Constants.createFollower(hardwareMap);

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shooterMotor1");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooterMotor2");
        hood = hardwareMap.get(Servo.class, "hood");
        gate = hardwareMap.get(Servo.class, "gate");

        turret = hardwareMap.get(Servo.class, "turretServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        transferMotor = hardwareMap.get(DcMotor.class, "transferMotor");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        shooterMotor2.setDirection(DcMotorEx.Direction.REVERSE);
        shooterMotor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);



        Pose HPBlue = new Pose(8.75, 8.75, 270);
        Pose HPRed = new Pose(183.25, 8.75, 270);

        Pose BlueStandardGoal = new Pose(12, 180, 0);
        Pose BlueSpecialGoal = new Pose(84, 180, 0);
        Pose RedStandardGoal = new Pose(108, 180, 0);
        Pose RedSpecialGoal = new Pose(180, 180, 0);


        waitForStart();
        turret.setPosition(0.5);

        while (opModeIsActive())
        {
            follower.update();

            double lfPower = Range.clip(-1 * gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x, -1, 1);
            double rfPower = Range.clip(-1 * gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x, -1, 1);
            double lbPower = Range.clip(-1 * gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x, -1, 1);
            double rbPower = Range.clip(-1 * gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x, -1, 1);

            leftFront.setPower(lfPower);
            leftBack.setPower(lbPower);
            rightFront.setPower(rfPower);
            rightBack.setPower(rbPower);

            intakeMotor.setPower(gamepad2.right_stick_y);
            transferMotor.setPower(gamepad2.left_stick_y);

            telemetry.addData("flywheel speed", shooterMotor1.getVelocity());
            telemetry.addData("hood angle", (hood.getPosition()*25)+22.57);
            telemetry.addData("Turret heading", (turret.getPosition()*240)+60);
            telemetry.addData("Robot position", follower.getPose());
            telemetry.update();

            //if(gamepad2.right_trigger >= 0.5){
            //shooter.openGate();
            //} else {
            //shooter.closeGate();
            //}


            //Sets up auto aim
            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a){
                follower.setPose(HPBlue);
                goal = BlueStandardGoal;
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.b){
                follower.setPose(HPBlue);
                goal = BlueSpecialGoal;
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.x){
                follower.setPose(HPRed);
                goal = RedStandardGoal;
            }

            if(gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.y){
                follower.setPose(HPRed);
                goal = RedSpecialGoal;
            }



            if(follower.getPose() != null && goal != null) {
                double deltaY = (goal.getY()-follower.getPose().getY());
                double deltaX = (goal.getX()-follower.getPose().getX());
                if (deltaX!=0) {
                    double goalHeadingFromRobot = Math.atan2(deltaY, deltaX);
                    double robotHeading = follower.getHeading();
                    turret.setPosition((180+(goalHeadingFromRobot-robotHeading))/360);

                }
            }

                if (gamepad2.dpad_down) {
                    hood.setPosition(0.25);
                }

                if (gamepad2.dpad_right) {
                   hood.setPosition(0.5);
                }

                if (gamepad2.dpad_up) {
                    hood.setPosition(0.75);
                }

                if (gamepad2.dpad_left) {
                    hood.setPosition(1);
                }



                if (gamepad2.a) {
                    shooterMotor1.setVelocity(0);
                    shooterMotor1.setVelocity(0);

                }

                if (gamepad2.b) {
                    shooterMotor1.setVelocity(2300);
                    shooterMotor1.setVelocity(2300);

                }

                if (gamepad2.x) {
                    shooterMotor1.setVelocity(1800);
                    shooterMotor2.setVelocity(1800);

                }

                if (gamepad2.y) {
                    shooterMotor1.setVelocity(1300);
                    shooterMotor2.setVelocity(1300);
                }
            }


        }

    }