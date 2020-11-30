package frc.robot.subsystems.pneumatic;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Pne;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm extends SubsystemBase{
    protected DoubleSolenoid m_DoubleSolenoid = new DoubleSolenoid(Pne.ArmDS_1,Pne.ArmDS_2);

    public Arm(){
    }
    
    public void Arm_on(){
        m_DoubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void Arm_off(){
        m_DoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}