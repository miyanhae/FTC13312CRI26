package org.firstinspires.ftc.teamcode.Teleops;

import com.pedropathing.math.MathFunctions;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;


@TeleOp(name = "PRACTICE MATCH ONLY", group = "LinearOpMode")
public class halfField extends LinearOpMode {
    DcMotor leftFront, leftBack, rightFront, rightBack;
    DcMotor intakeMotor, transferMotor;
    DcMotorEx shooterMotor1, shooterMotor2;
    Servo hood, gate, turret;

    Boolean manualTargeting = false;
    Pose2D goal;
    String HPLabel, goalLabel;



    @Override
    public void runOpMode() throws InterruptedException {
        GoBildaPinpointDriver pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

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

        shooterMotor1.setDirection(DcMotorEx.Direction.REVERSE);
        shooterMotor1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        Pose2D HPBlue = new Pose2D(DistanceUnit.INCH, 87.25, 8.75, AngleUnit.DEGREES, 270);
        Pose2D HPRed = new Pose2D(DistanceUnit.INCH, 8.75, 8.75, AngleUnit.DEGREES, 270);

        Pose2D BlueStandardGoal = new Pose2D(DistanceUnit.INCH, 12, 196, AngleUnit.DEGREES, 0);
        Pose2D RedStandardGoal = new Pose2D(DistanceUnit.INCH, 84, 196, AngleUnit.DEGREES, 0);


        waitForStart();
        while (opModeIsActive()) {
            pinpoint.update();

            double lfPower = Range.clip(-1 * gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x, -1, 1);
            double rfPower = Range.clip(-1 * gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x, -1, 1);
            double lbPower = Range.clip(-1 * gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x, -1, 1);
            double rbPower = Range.clip(-1 * gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x, -1, 1);

            leftFront.setPower(lfPower);
            leftBack.setPower(lbPower);
            rightFront.setPower(rfPower);
            rightBack.setPower(rbPower);

            intakeMotor.setPower(gamepad2.right_stick_y);
            transferMotor.setPower(-1 * gamepad2.left_stick_y);

            telemetry.addData("flywheel speed", shooterMotor1.getVelocity());
            telemetry.addData("hood angle", hood.getPosition());
            telemetry.addData("Turret heading", turret.getPosition());
            telemetry.addData("Gate position", gate.getPosition());
            telemetry.addData("Robot X", pinpoint.getPosX(DistanceUnit.INCH));
            telemetry.addData("Robot Y", pinpoint.getPosY(DistanceUnit.INCH));
            telemetry.addData("Robot Theta", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.addData("-----","-----");
            telemetry.addData("goal", goalLabel);
            telemetry.addData("start zone", HPLabel);
            telemetry.addData("Manual Targeting", manualTargeting);


            telemetry.update();




            if (gamepad2.right_trigger != 0) {
                gate.setPosition(0.4);
            } else {
                gate.setPosition(1);
            }

            //Sets up auto aim
            if (gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.a) {
                pinpoint.setPosition(HPBlue);
                goal = BlueStandardGoal;
                goalLabel = "Blue";
                HPLabel = "Blue";
            }

            if (gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.b) {
                pinpoint.setPosition(HPBlue);
                goal = BlueStandardGoal;
                goalLabel = "Blue";
                HPLabel = "Blue";
            }

            if (gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.x) {
                pinpoint.setPosition(HPRed);
                goal = RedStandardGoal;
                goalLabel = "Red";
                HPLabel = "Red";
            }

            if (gamepad2.right_bumper && gamepad2.left_bumper && gamepad2.y) {
                pinpoint.setPosition(HPRed);
                goal = RedStandardGoal;
                goalLabel = "Red";
                HPLabel = "Red";
            }


            if (gamepad2.rightStickButtonWasPressed() && manualTargeting == false) {
                manualTargeting = true;
            }

            if (gamepad2.leftStickButtonWasPressed() && manualTargeting == true) {
                manualTargeting = false;
            }


            if (manualTargeting == true) {
                turret.setPosition(0.5);



                if (gamepad2.dpad_down) {
                    hood.setPosition(0);
                }

                if (gamepad2.dpad_up) {
                    hood.setPosition(1);
                }



                if (gamepad2.a) {
                    shooterMotor1.setVelocity(2400);
                    shooterMotor2.setVelocity(2400);
                }

                if (gamepad2.b) {
                    shooterMotor1.setVelocity(1500);
                    shooterMotor2.setVelocity(1500);
                }

                if (gamepad2.x) {
                    shooterMotor1.setVelocity(0);
                    shooterMotor2.setVelocity(0);
                }

            }


            if (manualTargeting == false) {
                if (goal != null) {
                    double deltaY = (goal.getY(DistanceUnit.INCH) - pinpoint.getPosY(DistanceUnit.INCH));
                    double deltaX = (goal.getX(DistanceUnit.INCH) - pinpoint.getPosX(DistanceUnit.INCH));

                    if (deltaX != 0) {
                        double goalHeadingFromXAxis = Math.toDegrees(Math.atan2(deltaY, deltaX));
                        double robotHeading = pinpoint.getHeading(AngleUnit.DEGREES);
                        double turretRelativeToRobotHeading = AngleUnit.normalizeDegrees(goalHeadingFromXAxis - robotHeading - 180);
                        turret.setPosition(0.5 + (turretRelativeToRobotHeading / 360));
                    }

                    double controlY = 31;
                    double controlRadial = 4.5;
                    double passThruPoint = Math.toRadians(-45);
                    double g = 386.09;
                    double distanceToControl = Math.sqrt(deltaX * deltaX + deltaY * deltaY) - controlRadial;
                    double hoodAngle = MathFunctions.clamp(Math.atan((2 * controlY)/(distanceToControl - Math.tan(passThruPoint))), 29.5, 56.5);
                    double flywheelLinearSpeed = Math.sqrt((g * distanceToControl * distanceToControl) / 2 * Math.pow(Math.cos(hoodAngle), 2) * (distanceToControl * Math.tan(hoodAngle) - controlY));


                    hood.setPosition((hoodAngle -56.5)/27);
                    shooterMotor1.setVelocity(flywheelLinearSpeed / 1.26, AngleUnit.RADIANS);
                    shooterMotor2.setVelocity(flywheelLinearSpeed / 1.26, AngleUnit.RADIANS);

                }
            }
        }
    }
}