package org.firstinspires.ftc.teamcode.subsystems;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import static com.pedropathing.math.MathFunctions.clamp;



public class TargetMath {

    public Pose goal;


    public void setGoalLocation(Pose goalPose) {
        //sets goal for other functions to use
        this.goal = goalPose;
    }

    //constants
    double g = 386.1;
    double controlHeight = 31;
    double controlAngle = Math.toRadians(-45);
    double controlRadial = 4.5;




    public double[] calculations(Pose currentPose, Vector robotVelocity, Double robotHeading){

        //this vector is on the top view plane
        double xDistance = goal.getX() - currentPose.getX();
        double yDistance = goal.getY() - currentPose.getY();
        double robotToGoalVectorHeading = Math.atan2(xDistance, yDistance);
        Vector robotToGoalVector = new Vector(Math.sqrt(xDistance * xDistance + yDistance * yDistance), robotToGoalVectorHeading);
        double distanceRobotToControl = robotToGoalVector.getMagnitude() - controlRadial;


        //Uncompensated for robot velocity, also the hood angle is on the side view plane
        double hoodAngle = clamp(Math.atan(2 * controlHeight / distanceRobotToControl - Math.tan(controlAngle)), 22.57, 47.57);
        double flywheelSpeed = Math.sqrt(g * distanceRobotToControl * distanceRobotToControl / (2 * Math.pow(Math.cos(hoodAngle), 2) * (distanceRobotToControl * Math.tan(hoodAngle - controlHeight))));

        //top view plane
        double coordinateTheta = robotVelocity.getTheta() - robotToGoalVector.getTheta();
        double radialVelRobot = -Math.cos(coordinateTheta) * robotVelocity.getMagnitude();
        double tangentialVelRobot = Math.sin(coordinateTheta) * robotVelocity.getMagnitude();

        //basic idea is we keep our airtime the same as before velocity compensating
        //we find a new velocity to get to the new "distance" in the same amount of time
        //new "distance" is based off the compensated velocity * airtime and then is used as an input for flywheel speed

        double yVelBall = flywheelSpeed * Math.sin(hoodAngle);
        double airtime = distanceRobotToControl / (flywheelSpeed * Math.cos(hoodAngle));
        double adjustedXVelBall = distanceRobotToControl/airtime + radialVelRobot;
        double adjustedVelBall = Math.sqrt(adjustedXVelBall * adjustedXVelBall + tangentialVelRobot * tangentialVelRobot);
        double compensatedDistanceRobotToGoal = adjustedVelBall * airtime;
        double turretLead = Math.atan(tangentialVelRobot/adjustedXVelBall);

        double compensatedHoodAngle= MathFunctions.clamp(Math.atan(yVelBall /adjustedVelBall),22.57,47.57);
        double compensatedFlywheelSpeed = Math.sqrt(g * compensatedDistanceRobotToGoal * compensatedDistanceRobotToGoal / (2 * Math.pow(Math.cos(compensatedHoodAngle), 2) * (compensatedDistanceRobotToGoal * Math.tan(compensatedHoodAngle) - controlHeight)));
        double turretAngle = MathFunctions.clamp(Math.toDegrees(robotHeading - robotToGoalVector.getTheta() + turretLead), 90, 270);

        double calculatedTargetingValues[] = {compensatedHoodAngle, compensatedFlywheelSpeed, turretAngle};
        return calculatedTargetingValues;
    }



}