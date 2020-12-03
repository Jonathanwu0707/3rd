package frc.robot.subsystems;

import frc.robot.Constants.PowCon;
import frc.robot.subsystems.shooter.Spinable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Intake extends Spinable {
    private final VictorSPX intake = new VictorSPX(PowCon.intake);

    public Intake(){
        
        intake.configFactoryDefault();
        // intake.setInverted(true);
        intake.setNeutralMode(NeutralMode.Coast);
        intake.configNeutralDeadband(0.005);
   
        // intake.configOpenloopRamp(0.5);
        // intake.configPeakOutputForward(1, PowCon.kTimeoutMs);
    }

    @Override
    public void forward() {
        
        intake.set(ControlMode.PercentOutput,-0.9);
        SmartDashboard.putString("Intake", "forward");

    }

    @Override
    public void stop() {
        intake.set(ControlMode.PercentOutput,0);
        SmartDashboard.putString("Intake", "stop");

    }

    @Override
    public void reverse() {
        intake.set(ControlMode.PercentOutput,0.9);
        SmartDashboard.putString("Intake", "reverse");

    }

    @Override
    public String getStatus() {
        return null;
    }

}