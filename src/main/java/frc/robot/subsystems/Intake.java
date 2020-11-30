package frc.robot.subsystems;

import frc.robot.Constants.PowCon;
import frc.robot.subsystems.shooter.Spinable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Intake extends Spinable {
    private final WPI_VictorSPX intake = new WPI_VictorSPX(5);

    public Intake(){
        
        intake.configFactoryDefault();
    
        intake.setNeutralMode(NeutralMode.Coast);
        intake.configNeutralDeadband(0.005);
   
        intake.configOpenloopRamp(0.5);
        intake.configPeakOutputForward(0.9, PowCon.kTimeoutMs);
        intake.setInverted(true);
    }

    @Override
    public void forward() {
        intake.set(ControlMode.PercentOutput,0.9);
        SmartDashboard.putString("Intake", "forward");

    }

    @Override
    public void stop() {
        intake.set(ControlMode.PercentOutput,0);
        SmartDashboard.putString("Intake", "forward");

    }

    @Override
    public void reverse() {
        intake.set(ControlMode.PercentOutput,-0.9);
        SmartDashboard.putString("Intake", "forward");

    }

    @Override
    public String getStatus() {
        return null;
    }

}