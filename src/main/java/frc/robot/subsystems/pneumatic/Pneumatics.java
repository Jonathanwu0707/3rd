package frc.robot.subsystems.pneumatic;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics extends SubsystemBase{
    protected Compressor m_Compressor = new Compressor();

    public Pneumatics(){
    }
    public void Phnematics_staute(){
        m_Compressor.setClosedLoopControl(true);
    }

    @Override
    public void periodic(){
    }
}