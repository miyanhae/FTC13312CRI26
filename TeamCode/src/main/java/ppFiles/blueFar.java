package ppFiles.Autos;

import static java.lang.Thread.sleep;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;



public class blueFar {

    private Follower follower;
    PathState pathState;


    private enum PathState {

        startPosToShootPos,
        shootPreload,
        pickup1,
        returnShootPos1,
        shootRound1,
        pickup2,
        returnShootPos2,
        shootRound2,
        pickup3,
        returnShootPos3,
        shootRound3,
        toEndPose
    }

    //all the poses the robot will be in when something happens
    private final Pose startPos = new Pose(84, 9, Math.toRadians(0));
    private final Pose shootPos = new Pose(84, 36, Math.toRadians(294));

    private final Pose pickup1 = new Pose(12, 60, Math.toRadians(180));
    private final Pose pickup2 = new Pose(12, 84, Math.toRadians(180));
    private final Pose pickup3 = new Pose(12, 108, Math.toRadians(180));

    private final Pose endPose = new Pose(60, 36, Math.toRadians(90));

    private final Pose control1 = new Pose(84, 60, Math.toRadians(180));
    private final Pose control2 = new Pose(84, 84, Math.toRadians(180));
    private final Pose control3 = new Pose(84, 108, Math.toRadians(180));



    //these are the paths the robot will follow, one pose to another
    private PathChain
            startPosToShootPos,
            shootPosToP1,
            returnShootPos1,
            shootPosToP2,
            returnShootPos2,
            shootPosToP3,
            returnShootPos3,
            toEndPos;


    public void buildPaths() {
        startPosToShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPos, shootPos))
                .setLinearHeadingInterpolation(startPos.getHeading(), shootPos.getHeading())
                .build();

        shootPosToP1 = follower.pathBuilder()
                .addPath(new BezierCurve(shootPos, pickup1, control1))
                .setTangentHeadingInterpolation()
                .build();

        returnShootPos1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1, shootPos))
                .setLinearHeadingInterpolation(pickup1.getHeading(), shootPos.getHeading())
                .build();


        shootPosToP2 = follower.pathBuilder()
                .addPath(new BezierCurve(shootPos, pickup2, control2))
                .setTangentHeadingInterpolation()
                .build();

        returnShootPos2 = follower.pathBuilder()
                .addPath(new BezierLine(pickup2, shootPos))
                .setLinearHeadingInterpolation(pickup2.getHeading(), shootPos.getHeading())
                .build();


        shootPosToP3 = follower.pathBuilder()
                .addPath(new BezierCurve(shootPos, pickup3, control3))
                .setTangentHeadingInterpolation()
                .build();

        returnShootPos3 = follower.pathBuilder()
                .addPath(new BezierLine(pickup3, shootPos))
                .setLinearHeadingInterpolation(pickup3.getHeading(), shootPos.getHeading())
                .build();


        toEndPos = follower.pathBuilder()
                .addPath(new BezierLine(shootPos, endPose))
                .setLinearHeadingInterpolation(shootPos.getHeading(), endPose.getHeading())
                .build();
    }


//this is what will activate each path and action in a sequence after it is called

    public void statePathUpdate() {
        switch (pathState) {

            case startPosToShootPos:
                if (!follower.isBusy()) {
                    follower.followPath(startPosToShootPos, true);
                    pathState = PathState.shootPreload;
                }

            case shootPreload:
                if (!follower.isBusy()) {
                    sleep(1000);
                    shoot();
                    pathState = PathState.pickup1;
                }



            case pickup1:
                if (!follower.isBusy()) {
                    follower.followPath(shootPosToP1, true);
                    pathState = PathState.returnShootPos1;
                }

            case returnShootPos1:
                if (!follower.isBusy()) {
                    follower.followPath(returnShootPos1, true);
                    pathState = PathState.shootRound1;
                }


            case shootRound1:
                if (!follower.isBusy()) {
                    sleep(1000);
                    shoot();
                    pathState = PathState.pickup2;
                }



            case pickup2:
                if (!follower.isBusy()) {
                    follower.followPath(shootPosToP2, true);
                    pathState = PathState.returnShootPos2;
                }

            case returnShootPos2:
                if (!follower.isBusy()) {
                    follower.followPath(returnShootPos2, true);
                    pathState = PathState.shootRound2;
                }

            case shootRound2:
                if (!follower.isBusy()) {
                    sleep(1000);
                    shoot();
                    pathState = PathState.pickup3;
                }



            case pickup3:
                if (!follower.isBusy()) {
                    follower.followPath(shootPosToP3, true);
                    pathState = PathState.returnShootPos3;
                }

            case returnShootPos3:
                if (!follower.isBusy()) {
                    follower.followPath(returnShootPos3, true);
                    pathState = PathState.shootRound3;
                }

            case shootRound3:
                if (!follower.isBusy()) {
                    sleep(1000);
                    shoot();
                    pathState = PathState.toEndPose;
                }



            case toEndPose:
                if (!follower.isBusy()) {
                    follower.followPath(toEndPos, true);
                }

        }

        public void shoot() {

            for (int i = 0; i <= 3; i++) {
                shootBlock.setPosition(0);

                intakeMotor.setPower(1);
                sleep(300);
                intakeMotor.setPower(0);

                if (i != 3) {
                    sleep(500);
                }

                shootBlock.setPosition(0.5);

            }
        }


    }
}

