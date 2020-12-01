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
import edu.wpi.first.wpilibj2.command.RunCommand;
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
  private final ControlDrivetrain     controlDrivetrain         = new ControlDrivetrain();
  private final Wing                  m_Wing                    = new Wing();
  private final Pneumatics            m_Pneumatics              = new Pneumatics();
  private final Arm                   m_Arm                     = new Arm();
  private final ControlDrivetrain     m_drivetrain              = new ControlDrivetrain();
  private final Rack                  m_Rack                    = new Rack();
  private final Tower                 m_Tower                   = new Tower();


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
    // new JoystickButton(m_Joystick, Button.flySpin)            .whenHeld(new SpinForward(m_Shooter));
    new JoystickButton(m_Joystick,Button.arm_out)             .whenHeld(new ArmOut(m_Arm));
    new JoystickButton(m_Joystick,Button.arm_in)              .whenHeld(new ArmIn(m_Arm));
    new JoystickButton(m_Joystick,Button.rack_up)             .whenHeld(new SpinForward(m_Rack));
    new JoystickButton(m_Joystick,Button.rack_down)           .whenHeld(new SpinReverse(m_Rack));
    new JoystickButton(m_Joystick, Button.turretleft)         .whenHeld(new SpinForward(m_Tower));
    new JoystickButton(m_Joystick, Button.turretRight)        .whenHeld(new SpinReverse(m_Tower));

  }
  private void driverStationMapping() {
  
  }
  
  private void teleop() {
    m_drivetrain.setDefaultCommand(new RunCommand(
      ()->m_drivetrain.curvatureDrive(m_Joystick.getY(), m_Joystick.getZ(), m_Joystick.getTrigger()), 
      m_drivetrain));
  }

  private void Pneumatic() {
  
  }

  

  public Command getAutonomousCommand() {
    return null;
  }
}
