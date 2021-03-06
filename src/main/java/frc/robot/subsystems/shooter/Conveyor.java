/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.PowCon;
public class Conveyor extends Spinable {
  /**
   * Creates a new Conveyor.
   */   
  private Shooter       shooter;
  private WPI_VictorSPX conveyor = new WPI_VictorSPX(PowCon.conveyor);

  public Conveyor(Shooter shooter) {
    this.shooter=shooter;
    conveyor.configFactoryDefault();
    
    conveyor.setNeutralMode(NeutralMode.Coast);
    conveyor.configNeutralDeadband(0.005);
   
    conveyor.configOpenloopRamp(0.5);
    conveyor.configPeakOutputForward(0.8, PowCon.kTimeoutMs);
    conveyor.setInverted(true);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void forward() {
    if(shooter.getflywheelVelocity()>=10000){
      conveyor.set(ControlMode.PercentOutput ,-0.8);
      SmartDashboard.putString("Shooter Status","forward");
    }else if(shooter.getflywheelVelocity()<10000){
      conveyor.set(ControlMode. PercentOutput , 0 );
      SmartDashboard.putString("Shooter Status","slowly");
    }
  }

  @Override
  public void stop() {
    conveyor.set(ControlMode. PercentOutput , 0 );
    SmartDashboard.putString("Shooter Status","stop");

  }

  @Override
  public void reverse() {
    conveyor.set(ControlMode. PercentOutput , 0.8 );
    SmartDashboard.putString("Shooter Status","reverse");
  }

  @Override
  public String getStatus() {
    return null;
  }
}
