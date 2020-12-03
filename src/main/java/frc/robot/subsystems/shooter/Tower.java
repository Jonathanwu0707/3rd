/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.motor_method.*;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.PowCon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Tower extends Spinable {
  /**
   * Creates a new Tower.
   */ 
  private SupplyCurrentLimitConfiguration supplyCurrentLimitConfiguration = new SupplyCurrentLimitConfiguration(true,40, 50, 1);
  private TalonSRX tower = new TalonSRX(PowCon.tower);
  private DigitalInput dot = new DigitalInput(9);
  private static final int forwardL = 5000, reverseL = -5000;
  // private NetworkTableEntry useLimit;
  // private String status = "Stop";

  
  public Tower() {
    tower.configFactoryDefault();
    MotorFactory.setSensor(tower, FeedbackDevice.CTRE_MagEncoder_Absolute);
    // Shuffleboard.getTab("PositionCombine").addString("Tower", this::getStatus);
    Shuffleboard.getTab("PositionCombine").addNumber("TowerPosition", this::getPosition);
      // useLimit = Shuffleboard.getTab("PositionCombine")
      // .add("Tower Limit", 1)
      // .withWidget(BuiltInWidgets.kNumberSlider)
      // .getEntry();

    
    tower.configSupplyCurrentLimit(supplyCurrentLimitConfiguration);
    tower.setNeutralMode(NeutralMode.Coast);
    tower.configNeutralDeadband(0.005);
    tower.configPeakOutputForward(0.2);
    tower.configPeakOutputReverse(-0.2);
    tower.configOpenloopRamp(0.5);
    }
  public double getPosition(){
    return tower.getSelectedSensorPosition(0);
  }
  
  public double getVelocity(){
    return tower.getSelectedSensorVelocity(0);
  }


  @Override
  public void periodic() {
    // aim();
    if (!dot.get()) {
      tower.setSelectedSensorPosition(0, 0, 10);
    }
    SmartDashboard.putBoolean("TowerLimit", dot.get());
    SmartDashboard.putNumber("Tower Position", tower.getSelectedSensorPosition());
  }  

  @Override
  public void forward() {
    if (tower.getSelectedSensorPosition() > forwardL){
      tower.set(ControlMode.PercentOutput, 0);
    }else{
      tower.set(ControlMode.PercentOutput, 0.2);
    }
  }

  @Override
  public void stop() {
    tower.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void reverse() {
    if(tower.getSelectedSensorPosition() < reverseL)
      tower.set(ControlMode.PercentOutput, 0);
    else{
      tower.set(ControlMode.PercentOutput, -0.21);
    }

  }
  public void aim(){
    double error = Limelight.getTx();
    // SmartDashboard.putNumber("error", error);
    if(Math.abs(error) < 0.15 || Limelight.getTa() < 0){
      error = 0;
    }
    if(tower.getSelectedSensorPosition() > reverseL && tower.getSelectedSensorPosition() < forwardL){
      if(error > 10){
        tower.set(ControlMode.PercentOutput, -0.007 * error);
      }else{
        tower.set(ControlMode.PercentOutput, -0.02 * error);
      }
    }
  }

  @Override
  public String getStatus() {
    return null;
  }
}
