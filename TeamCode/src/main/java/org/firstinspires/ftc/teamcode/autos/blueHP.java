package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous (name="BlueHP", group="Linear OpMode")
public class blueHP extends LinearOpMode {


    private DcMotorEx shooterMotor1, shooterMotor2;
    private DcMotor intakeMotor, transferMotor;
    private Servo gate, hood, turret;

    private Follower follower;


    //these are the different "states" the robot will be in, specific movements and actions
    private enum PathState {
        startPosToShootPos,
        shootPreload,

        pickupSpike3,
        returnShootPos1,
        shootRound1,

        pickupHP1,
        returnShootPos2,
        shootRound2,

        pickupHP2,
        returnShootPos3,
        shootRound3,

        pickupHP3,
        returnShootPos4,
        shootRound4,

        pickupHP4,
        returnShootPos5,
        shootRound5,

        toEndPose
    }


    PathState pathState;


    //all the poses the robot will be in when something happens
    private final Pose startPos = new Pose(19, 124, Math.toRadians(325));
    private final Pose farShot = new Pose(28, 115, Math.toRadians(315));

    private final Pose pickup3 = new Pose(16, 84, Math.toRadians(180));
    private final Pose pickupHP = new Pose(16, 84, Math.toRadians(180));

    private final Pose control1 = new Pose(54, 36, Math.toRadians(180));

    private final Pose endPose = new Pose(60, 120, Math.toRadians(270));


    //these are the paths the robot will follow, one pose to another
    private PathChain
            startToFarShot,
            farShotToP3,
            p3ToFarShot,
            farShotToHP,
            hpToFarShot,
            farShotToEnd;

    public void buildPaths() {
        startToFarShot = follower.pathBuilder()
                .addPath(new BezierLine(startPos, farShot))
                .setLinearHeadingInterpolation(startPos.getHeading(), farShot.getHeading())
                .build();

        farShotToP3 = follower.pathBuilder()
                .addPath(new BezierCurve(farShot, pickup3, control1))
                .setTangentHeadingInterpolation()
                .build();

        p3ToFarShot = follower.pathBuilder()
                .addPath(new BezierCurve(pickup3, farShot, control1))
                .setTangentHeadingInterpolation()
                .build();

        farShotToHP = follower.pathBuilder()
                .addPath(new BezierLine(farShot, pickupHP))
                .setLinearHeadingInterpolation(farShot.getHeading(), pickupHP.getHeading())
                .build();

        hpToFarShot = follower.pathBuilder()
                .addPath(new BezierLine(pickupHP, farShot))
                .setLinearHeadingInterpolation(pickupHP.getHeading(), farShot.getHeading())
                .build();

        farShotToEnd = follower.pathBuilder()
                .addPath(new BezierLine(farShot, endPose))
                .setLinearHeadingInterpolation(farShot.getHeading(), endPose.getHeading())
                .build();
    }


//this is what will activate each path and action in a sequence after it is called


    public void statePathUpdate() {
        switch (pathState) {


            case startPosToShootPos:
                gate.setPosition(0.4);
                hood.setPosition(0);
                //turret.setPosition();
                shooterMotor1.setVelocity(2800);
                shooterMotor2.setVelocity(2800);

                follower.followPath(startToFarShot, true);
                pathState = PathState.shootPreload;

            case shootPreload:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupSpike3;
                }


            case pickupSpike3:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToP3);
                    pathState = PathState.returnShootPos1;
                }

            case returnShootPos1:
                if (!follower.isBusy()) {
                    follower.followPath(p3ToFarShot);
                    pathState = PathState.shootRound1;
                }

            case shootRound1:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupHP1;
                }


            case pickupHP1:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToHP);
                    pathState = PathState.returnShootPos2;
                }

            case returnShootPos2:
                if (!follower.isBusy()) {
                    follower.followPath(hpToFarShot);
                    pathState = PathState.shootRound2;
                }

            case shootRound2:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupHP3;
                }


            case pickupHP2:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToHP);
                    pathState = PathState.returnShootPos3;
                }

            case returnShootPos3:
                if (!follower.isBusy()) {
                    follower.followPath(hpToFarShot);
                    pathState = PathState.shootRound3;
                }

            case shootRound3:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupHP3;
                }


            case pickupHP3:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToHP);
                    pathState = PathState.returnShootPos4;
                }

            case returnShootPos4:
                if (!follower.isBusy()) {
                    follower.followPath(hpToFarShot);
                    pathState = PathState.shootRound4;
                }

            case shootRound4:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupHP4;
                }


            case pickupHP4:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToHP);
                    pathState = PathState.returnShootPos5;
                }

            case returnShootPos5:
                if (!follower.isBusy()) {
                    follower.followPath(hpToFarShot);
                    pathState = PathState.shootRound5;
                }

            case shootRound5:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.toEndPose;
                }


            case toEndPose:
                if (!follower.isBusy()) {
                    follower.followPath(farShotToEnd, true);
                }

        }
    }


        public void Shoot() {
            for (int i = 0; i <= 3; i++) {
                gate.setPosition(0.4);

                intakeMotor.setPower(1);
                sleep(250);
                intakeMotor.setPower(0);

                if (i != 3) {
                    sleep(300);
                }

            }

            gate.setPosition(1);

        }


        @Override
        public void runOpMode() throws InterruptedException {
            pathState = PathState.startPosToShootPos;
            follower = Constants.createFollower(hardwareMap);

            shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shooterMotor1");
            shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooterMotor2");
            hood = hardwareMap.get(Servo.class, "hood");
            gate = hardwareMap.get(Servo.class, "gate");

            turret = hardwareMap.get(Servo.class, "turretServo");
            intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
            transferMotor = hardwareMap.get(DcMotor.class, "transferMotor");

            shooterMotor2.setDirection(DcMotorEx.Direction.REVERSE);
            shooterMotor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            shooterMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            PIDFCoefficients pidfCoefficients = new PIDFCoefficients(-39.61, 0, 0, -11.61);
            shooterMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
            shooterMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);


            buildPaths();
            follower.setPose(startPos);


            waitForStart();
            while (opModeIsActive()) {

                follower.update();
                statePathUpdate();

            }
        }
    }