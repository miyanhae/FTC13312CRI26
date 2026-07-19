package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.Servo;

public class Turret {
    public Servo turretR1;
    TargetMath targetingMath = new TargetMath();


    public void setupTurret(){
        //sets turret to be facing directly away from robot intake, called once
        turretR1.setPosition(0.5);
    }



    public void aimTurret(){

        double turretAngleTicks =(targetingMath.calculations()[2]-45)/270;
        turretR1.setPosition(turretAngleTicks);
    }

}
