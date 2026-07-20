package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.Servo;


public class Turret {
    public Servo turretR1;


    public void aimTurret(double turretAngle){

        double turretAngleTicks = (turretAngle-90)/180;
        turretR1.setPosition(turretAngleTicks);
    }

}
