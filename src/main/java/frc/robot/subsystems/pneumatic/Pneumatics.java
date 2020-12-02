package frc.robot.subsystems.pneumatic;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics extends SubsystemBase{
    protected Compressor c = new Compressor();

    public Pneumatics(){
    }
    public void Phnematics_staute(){
        c.setClosedLoopControl(true);
    }

    @Override
    public void periodic(){
    }
}