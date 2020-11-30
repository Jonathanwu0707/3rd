/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Button;
import frc.robot.commands.Arm.*;
import frc.robot.commands.Arm.ArmOut;
import frc.robot.commands.Rotateable.SpinForward;
import frc.robot.commands.Rotateable.SpinReverse;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.chassis.ControlDrivetrain;
import frc.robot.subsystems.shooter.*;
import frc.robot.subsystems.pneumatic.*;

public class RobotContainer {
 private final Shooter               m_Shooter                 = new Shooter();
 private final Conveyor              m_Conveyor                = new Conveyor(m_Shooter);
 private final Intake                m_Intake                  = new Intake();
 private final Joystick              m_Joystick                = new Joystick(0);
 private final Joystick              m_driverStation           = new Joystick(1);
 private final ControlDrivetrain     controlDrivetrain         = new ControlDrivetrain();
 private final Wing                  m_Wing                    = new Wing();
 private final Pneumatics            m_Pneumatics              = new Pneumatics();
 private final Arm                   m_arm                     = new Arm();



  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    joystickMapping();
    driverStationMapping();
    teleop();
    Pneumatic();
  }
  private void joystickMapping() {
    new JoystickButton(m_Joystick, Button.intake_opp)         .whenHeld(new SpinReverse(m_Intake) )
                                                              .whenHeld(new SpinReverse(m_Conveyor))
                                                              .whenHeld(new SpinReverse(m_Wing));
    new JoystickButton(m_Joystick, Button.flySpin)            .whenHeld(new SpinForward(m_Shooter));
    new JoystickButton(m_Joystick,Button.armOut)              .whenHeld(new ArmOut(m_arm));
    new JoystickButton(m_Joystick,Button.armIn)               .whenHeld(new ArmIn(m_arm));


  }
  private void driverStationMapping() {
  
  }
  
  private void teleop() {
  
  }

  private void Pneumatic() {
  
  }

  

  public Command getAutonomousCommand() {
    return null;
  }
}
