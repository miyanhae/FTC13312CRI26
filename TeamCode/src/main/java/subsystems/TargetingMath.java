package subsystems;

import com.pedropathing.math.MathFunctions;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class TargetingMath {

    public GoBildaPinpointDriver pinpoint;

    //constants
    public Pose2D goal;
    double g = 32.2;
    double yDistanceRobotToControl = 24;
    double angleControlToGoal = -45;
    double distanceControlToGoal = 2;



    public void setupPinpoint(Pose2D origin){
        //sets up pinpoints, probably only called once. A lot of these values are temporary and need to be changed later
        pinpoint.setOffsets(0,0, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,GoBildaPinpointDriver.EncoderDirection.FORWARD);

        //there will be a few different ways to call the setupPinpoint function, each with a different starting position
        pinpoint.setPosition(origin);
    }



    public void setGoalLocation(Pose2D goalPose) {
        //sets goal for other functions to use
        this.goal = goalPose;

    }

    public double getRobotHeading(){
        return pinpoint.getHeading(AngleUnit.DEGREES);
    }


    public double getNeededTurretAdjustment(){
        double xDistanceGoalFwdPod = goal.getX(DistanceUnit.INCH) - pinpoint.getPosX(DistanceUnit.INCH);
        double zDistanceGoalLatPod = goal.getY(DistanceUnit.INCH) - pinpoint.getPosY(DistanceUnit.INCH);

        //gives us the angle of horizontal axis to the line of the robot and goal. we can subtract this from 90 to see how much we need to turn the turret
        return 90-Math.toDegrees(Math.atan2(xDistanceGoalFwdPod, zDistanceGoalLatPod));
    }

    public double getRobotToGoalDistance(){
        double xDistanceGoalFwdPod = goal.getX(DistanceUnit.INCH) - pinpoint.getPosX(DistanceUnit.INCH);
        double zDistanceGoalLatPod = goal.getY(DistanceUnit.INCH) - pinpoint.getPosY(DistanceUnit.INCH);

        //gives us the angle of horizontal axis to the line of the robot and goal. we can subtract this from 90 to see how much we need to turn the turret
        return Math.sqrt((xDistanceGoalFwdPod * xDistanceGoalFwdPod) + (zDistanceGoalLatPod * zDistanceGoalLatPod));
    }

    public double getNeededHoodAdjustment(){
        return MathFunctions.clamp(Math.atan(yDistanceRobotToControl/getRobotToGoalDistance()), 0, 90)/180;



    }



}
