package subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {

    private DcMotor intakeMotor;

    public void powerIntake( double intakePower)
    {
        intakeMotor.setPower(intakePower);
    }

}
