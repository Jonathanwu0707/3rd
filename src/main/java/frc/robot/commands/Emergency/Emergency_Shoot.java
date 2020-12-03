package frc.robot.commands.Emergency;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.*;

public class Emergency_Shoot extends CommandBase{
  public Emergency_Shooter m_energency_shooter;
    
  public  Emergency_Shoot(Emergency_Shooter energency_shooter) {
        m_energency_shooter = energency_shooter ;
        addRequirements(m_energency_shooter);
  }

    @Override
  public void initialize() {
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_energency_shooter.emergency();
   }
   
   // Called once the command ends or is interrupted.
   @Override
   public void end(boolean interrupted) {
   }
   
   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
   return false;
   }
}