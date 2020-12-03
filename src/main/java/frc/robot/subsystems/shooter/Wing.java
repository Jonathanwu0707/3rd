/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.PowCon;


public class Wing extends Spinable {
  /**
   * Creates a new Wing.
   */
  private final WPI_VictorSPX wing = new WPI_VictorSPX(PowCon.wingRight);
  private final WPI_VictorSPX middleWing = new WPI_VictorSPX(PowCon.wingMiddle);
  
  public Wing() {
    wing.configFactoryDefault();
    middleWing.configFactoryDefault();
    
    wing.setNeutralMode(NeutralMode.Coast);
    wing.configNeutralDeadband(0.005);
    middleWing.setNeutralMode(NeutralMode.Coast);
    middleWing.configNeutralDeadband(0.005);
   
    wing.configOpenloopRamp(0.5);
    wing.configPeakOutputForward(0.6, PowCon.kTimeoutMs);
    wing.setInverted(true);
    middleWing.configOpenloopRamp(0.5);
    middleWing.configPeakOutputForward(0.8, PowCon.kTimeoutMs);
    middleWing.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void forward() {
    wing.set(ControlMode.PercentOutput , 0.6);
    middleWing.set(ControlMode.PercentOutput , 0.8);
    SmartDashboard.putString("Wing", "forward");
  }

  @Override
  public void stop() {
    wing.set(ControlMode.PercentOutput , 0);
    middleWing.set(ControlMode.PercentOutput , 0);
    SmartDashboard.putString("Wing", "stop");
  }

  @Override
  public void reverse() {
    wing.set(ControlMode.PercentOutput , -0.6);
    middleWing.set(ControlMode.PercentOutput , -0.8);
    SmartDashboard.putString("Wing", "reverse");

  }

  @Override
  public String getStatus() {
    return null;
  }
}
