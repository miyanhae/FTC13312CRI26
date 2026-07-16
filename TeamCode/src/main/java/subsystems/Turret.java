package subsystems;

import com.pedropathing.math.MathFunctions;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Turret {
    public Servo turretR1, turretR2;
    TargetingMath targetingMath = new TargetingMath();


    public void setupTurret(){
        //sets turret to be facing directly away from robot intake, called once
        turretR1.setPosition(0.5);
        turretR2.setPosition(0.5);
    }



    public void aimTurret(){
        //one is + and other is _ because they need to spin in opposite directions
        turretR1.setPosition(MathFunctions.clamp(0.5+ (targetingMath.getNeededTurretAdjustment()-targetingMath.getRobotHeading()), 2.5, 357.5)/360);
        turretR2.setPosition(MathFunctions.clamp(0.5- (targetingMath.getNeededTurretAdjustment()-targetingMath.getRobotHeading()), 2.5, 357.5)/360);
    }

}
