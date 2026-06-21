package subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.opencv.core.Mat;

import java.security.PublicKey;

public class AutoAimTurret {
    private Servo turretR1, turretR2;
    private GoBildaPinpointDriver pinpoint;
    public Pose2D goal;



    public void setupTurret(){
        //sets turret to be facing directly away from robot intake, called once
        turretR1.setPosition(0.5);
        turretR2.setPosition(0.5);
    }

    public void setupPinpoint(Pose2D origin){
        //sets up pinpoints, probably only called once. A lot of these values are temporary and need to be changed later
        pinpoint.setOffsets(0,0, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,GoBildaPinpointDriver.EncoderDirection.FORWARD);

        //there will be a few different ways to call the setupPinpoint function, each with a different orientation
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
        double distanceGoalFwdPod = goal.getX(DistanceUnit.INCH) - pinpoint.getPosX(DistanceUnit.INCH);
        double distanceGoalLatPod = goal.getY(DistanceUnit.INCH) - pinpoint.getPosY(DistanceUnit.INCH);

        //gives us the angle of horizontal axis to the line of the robot and goal. we can subtract this from 90 to see how much we need to turn the turret
        return 90-Math.toDegrees(Math.atan2(distanceGoalFwdPod, distanceGoalLatPod));
    }

    public void aimTurret(){
        //one is + and other is _ because they need to spin in opposite directions
        turretR1.setPosition(0.5+ (-1*(getNeededTurretAdjustment())-getRobotHeading())/360);
        turretR2.setPosition(0.5- (-1*(getNeededTurretAdjustment())-getRobotHeading())/360);
    }

}
