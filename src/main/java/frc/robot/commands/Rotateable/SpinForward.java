/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Rotateable;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.Spinable;

public class SpinForward extends CommandBase {
  /**
   * Creates a new SpinForward.
   */
  private Spinable motor;

  public SpinForward(Spinable motor) {
    this.motor = motor;
    addRequirements(motor);
  }

  public SpinForward(Shooter m_Shooter) {
}

@Override
  public void initialize() {
  }

  @Override
  public void execute() {
    motor.forward();
  }

  @Override
  public void end(boolean interrupted) {
    motor.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
