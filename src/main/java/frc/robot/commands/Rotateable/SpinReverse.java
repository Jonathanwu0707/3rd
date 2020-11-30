/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Rotateable;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Spinable;

public class SpinReverse extends CommandBase {
  private Spinable motor;

  public SpinReverse(Spinable motor) {
    this.motor = motor;
    addRequirements(motor);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    motor.reverse();
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

