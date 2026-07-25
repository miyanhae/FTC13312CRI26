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

@Autonomous (name="redClose", group="Linear OpMode")
public class redClose extends  LinearOpMode {


    private DcMotorEx shooterMotor1, shooterMotor2;
    private DcMotor intakeMotor, transferMotor;
    private Servo gate, hood, turret;

    private Follower follower;


    //these are the different "states" the robot will be in, specific movements and actions
    private enum PathState {
        startPosToShootPos,
        shootPreload,

        pickupSpike1,
        returnShootPos1,
        shootRound1,

        pickupSpike2,
        returnShootPos2,
        shootRound2,

        pickupAtGate1,
        returnShootPos3,
        shootRound3,

        pickupAtGate2,
        returnShootPos4,
        shootRound4,

        toEndPose
    }


    PathState pathState;


    //all the poses the robot will be in when something happens
    private final Pose startPos = new Pose(192-19.5, 167, Math.toRadians(216));
    private final Pose shortShot = new Pose(192-36, 154, Math.toRadians(225));
    private final Pose mediumShot = new Pose(192-72, 118, Math.toRadians(225));

    private final Pose pickup1 = new Pose(192-12, 106, Math.toRadians(180));
    private final Pose pickup2 = new Pose(192-4, 82, Math.toRadians(180));
    private final Pose classifierGate = new Pose(192-11, 105, Math.toRadians(60));

    private final Pose control1 = new Pose(192-80, 112, Math.toRadians(180));
    private final Pose control2 = new Pose(192-60, 80, Math.toRadians(180));

    private final Pose endPose = new Pose(192-60, 80, Math.toRadians(270));




    //these are the paths the robot will follow, one pose to another
    private PathChain
            startToShortShot,
            shortShotToP1,
            p1ToMediumShot,
            mediumShotToP2,
            p2ToMediumShot,
            mediumShotToGate,
            gateToMediumShot,
            mediumShotToEnd;


    public void buildPaths() {
        startToShortShot = follower.pathBuilder()
                .addPath(new BezierLine(startPos, shortShot))
                .setLinearHeadingInterpolation(startPos.getHeading(), shortShot.getHeading())
                .build();

        shortShotToP1 = follower.pathBuilder()
                .addPath(new BezierCurve(shortShot, pickup1, control1))
                .setTangentHeadingInterpolation()
                .build();


        p1ToMediumShot = follower.pathBuilder()
                .addPath(new BezierLine(pickup1, mediumShot))
                .setLinearHeadingInterpolation(pickup1.getHeading(), mediumShot.getHeading())
                .build();


        mediumShotToP2 = follower.pathBuilder()
                .addPath(new BezierCurve(mediumShot, pickup2, control2))
                .setTangentHeadingInterpolation()
                .build();


        p2ToMediumShot = follower.pathBuilder()
                .addPath(new BezierLine(pickup2, mediumShot))
                .setLinearHeadingInterpolation(pickup2.getHeading(), mediumShot.getHeading())
                .build();

        mediumShotToGate = follower.pathBuilder()
                .addPath(new BezierLine(mediumShot, classifierGate))
                .setLinearHeadingInterpolation(mediumShot.getHeading(), classifierGate.getHeading())
                .build();

        gateToMediumShot = follower.pathBuilder()
                .addPath(new BezierLine(classifierGate, mediumShot))
                .setLinearHeadingInterpolation(classifierGate.getHeading(), mediumShot.getHeading())
                .build();

        mediumShotToEnd = follower.pathBuilder()
                .addPath(new BezierLine(mediumShot, endPose))
                .setTangentHeadingInterpolation()
                .build();
    }






//this is what will activate each path and action in a sequence after it is called


    public void statePathUpdate() {
        switch (pathState) {


            case startPosToShootPos:

                gate.setPosition(0.4);
                hood.setPosition(0);
                turret.setPosition(0.5);

                shooterMotor1.setVelocity(1400);
                shooterMotor2.setVelocity(1400);

                follower.followPath(startToShortShot, true);
                pathState = PathState.shootPreload;

            case shootPreload:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupSpike1;
                }



            case pickupSpike1:
                if (!follower.isBusy()) {
                    follower.followPath(shortShotToP1, true);
                    intakeMotor.setPower(1);
                    pathState = PathState.returnShootPos1;
                }


            case returnShootPos1:
                if (!follower.isBusy()) {
                    follower.followPath(p1ToMediumShot, true);
                    pathState = PathState.shootRound1;
                }


            case shootRound1:
                if (!follower.isBusy()) {
                    //turret.setPosition();
                    shooterMotor1.setVelocity(1800);
                    shooterMotor2.setVelocity(1800);
                    Shoot();
                    pathState = PathState.pickupSpike2;
                }



            case pickupSpike2:
                if (!follower.isBusy()) {
                    follower.followPath(mediumShotToP2, true);
                    intakeMotor.setPower(1);
                    pathState = PathState.returnShootPos2;
                }


            case returnShootPos2:
                if (!follower.isBusy()) {
                    follower.followPath(p2ToMediumShot, true);
                    pathState = PathState.shootRound2;
                }


            case shootRound2:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupAtGate1;
                }



            case pickupAtGate1:
                if (!follower.isBusy()) {
                    follower.followPath(mediumShotToGate, true);
                    intakeMotor.setPower(1);
                    sleep(300);
                    pathState = PathState.returnShootPos3;
                }


            case returnShootPos3:
                if (!follower.isBusy()) {
                    follower.followPath(gateToMediumShot, true);
                    pathState = PathState.shootRound3;
                }


            case shootRound3:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.pickupAtGate1;
                }


            case pickupAtGate2:
                if (!follower.isBusy()) {
                    follower.followPath(mediumShotToGate, true);
                    intakeMotor.setPower(1);
                    sleep(300);
                    pathState = PathState.returnShootPos4;
                }


            case returnShootPos4:
                if (!follower.isBusy()) {
                    follower.followPath(gateToMediumShot, true);
                    pathState = PathState.shootRound4;
                }


            case shootRound4:
                if (!follower.isBusy()) {
                    Shoot();
                    pathState = PathState.toEndPose;
                }


            case toEndPose:
                if (!follower.isBusy()){
                    follower.followPath(mediumShotToEnd, true);
                }

        }
    }

    public void Shoot(){
        for (int i = 0; i <= 3; i++) {
            gate.setPosition(0.4);

            intakeMotor.setPower(1);
            sleep(250);
            intakeMotor.setPower(0);

            if(i != 3) {
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