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

@Autonomous (name="BlueClose", group="Linear OpMode")
public class blueClose extends  LinearOpMode {


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
    private final Pose startPos = new Pose(19, 124, Math.toRadians(325));
    private final Pose shortShot = new Pose(28, 115, Math.toRadians(315));
    private final Pose mediumShot = new Pose(60, 90, Math.toRadians(320));

    private final Pose pickup1 = new Pose(16, 84, Math.toRadians(180));
    private final Pose pickup2 = new Pose(16, 60, Math.toRadians(180));
    private final Pose classifierGate = new Pose(60, 90, Math.toRadians(320));

    private final Pose control1 = new Pose(54, 36, Math.toRadians(180));
    private final Pose control2 = new Pose(54, 36, Math.toRadians(180));
    private final Pose control3 = new Pose(16, 36, Math.toRadians(180));
    private final Pose controlGate = new Pose(16, 36, Math.toRadians(180));

    private final Pose endPose = new Pose(60, 120, Math.toRadians(270));




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
                .addPath(new BezierCurve(pickup1, mediumShot, control2))
                .setTangentHeadingInterpolation()
                .build();


        mediumShotToP2 = follower.pathBuilder()
                .addPath(new BezierCurve(mediumShot, pickup2, control3))
                .setTangentHeadingInterpolation()
                .build();


        p2ToMediumShot = follower.pathBuilder()
                .addPath(new BezierCurve(pickup2, mediumShot, control3))
                .setTangentHeadingInterpolation()
                .build();

        mediumShotToGate = follower.pathBuilder()
                .addPath(new BezierCurve(mediumShot, classifierGate, controlGate))
                .setTangentHeadingInterpolation()
                .build();

        gateToMediumShot = follower.pathBuilder()
                .addPath(new BezierCurve(classifierGate, mediumShot, controlGate))
                .setTangentHeadingInterpolation()
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
                //turret.setPosition();

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