package org.firstinspires.ftc.teamcode.pedroPathing;


import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(13.61)
            .forwardZeroPowerAcceleration(-54)
            .lateralZeroPowerAcceleration(-85.3)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.07, 0, 0.001, 0.03))
            .headingPIDFCoefficients(new PIDFCoefficients(0.4, 0, 0, 0.05))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.08, 0, 0, 0.6, 0.01))
            .centripetalScaling(0.000375);
    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            100,
            1,
            1
    );



    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(0)
            .strafePodX(-6)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotor.Direction.REVERSE)
            .leftRearMotorDirection(DcMotor.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotor.Direction.FORWARD)
            .rightRearMotorDirection(DcMotor.Direction.FORWARD)
            .xVelocity(72.9346198134545)
            .yVelocity(57.79485315037525);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .pinpointLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}