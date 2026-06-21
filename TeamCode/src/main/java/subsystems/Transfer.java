package subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Transfer {

    public DcMotor transferMotor;

    public void powerTransfer(double transferPower)
    {
        transferMotor.setPower(transferMotor);
    }

}
