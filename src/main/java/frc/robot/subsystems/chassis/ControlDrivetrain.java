/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.chassis;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.*;
import frc.robot.motor_method.*;
import com.kauailabs.navx.frc.AHRS;

public class ControlDrivetrain extends SubsystemBase {
  protected static WPI_TalonFX leftMas  = new WPI_TalonFX(chassis.leftMaster);
  protected static WPI_TalonFX leftFol  = new WPI_TalonFX(chassis.leftFollewer);
  protected static WPI_TalonFX rightMas = new WPI_TalonFX(chassis.rightMaster);
  protected static WPI_TalonFX rightFol = new WPI_TalonFX(chassis.rightFollower);
  protected static AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private SupplyCurrentLimitConfiguration supplyCurrentLimitConfiguration = new SupplyCurrentLimitConfiguration(true, 40, 50, 1);
  private SlewRateLimiter yFilter = new SlewRateLimiter(0.8);
  private SlewRateLimiter zFilter = new SlewRateLimiter(0.8);
  private double m_quickStopAccumulator = 0, leftout = 0, rightout = 0, lastRotation = 0;
  /**
   * Creates a new ControlDrivetrain.
   */
  public ControlDrivetrain() {
    MotorFactory.setFollower(leftMas, leftFol);
    MotorFactory.setInvert(leftMas, chassis.isLeftMotorInvert);
    MotorFactory.setPosion(leftMas, 0, 0, 10);
    MotorFactory.setSensor(leftMas, FeedbackDevice.IntegratedSensor);
    MotorFactory.setSensorPhase(leftMas, chassis.isLeftPhaseInvert);
    MotorFactory.configLikePrevious(leftFol, chassis.isLeftPhaseInvert, chassis.isLeftMotorInvert);
    leftMas.configSupplyCurrentLimit(supplyCurrentLimitConfiguration);

    MotorFactory.setFollower(rightMas, rightFol);
    rightMas.configSupplyCurrentLimit(supplyCurrentLimitConfiguration);
    MotorFactory.configLikePrevious(rightMas, chassis.isRightPhaseInvert, chassis.isRightMotorInvert);
    MotorFactory.configLikePrevious(rightFol, chassis.isRightPhaseInvert, chassis.isRightMotorInvert);
    MotorFactory.voltageCompSaturation(rightMas, 11);
    MotorFactory.voltageCompSaturation(leftMas,  11);

    leftFol.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 255);
    rightFol.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 255);
    
    
    ahrs.reset();
  }
  public void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
    
    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
    zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
    //簡易死區設定
    if((xSpeed < 0.05) & (xSpeed > -0.05)){
      xSpeed = 0;
    }//簡易死區設定
    if(Math.abs(zRotation) < 0.05){
      zRotation =0;
    }

    xSpeed = yFilter.calculate(xSpeed);
    zRotation = zFilter.calculate(zRotation);
    
    double angularPower;
    boolean overPower;
    double m_quickStopAlpha = 0.1;
    if (isQuickTurn) {
      if (Math.abs(xSpeed) < 0.1) {
        m_quickStopAccumulator = (1 - 0.1) * m_quickStopAccumulator
            + m_quickStopAlpha * MathUtil.clamp(zRotation, -1.0, 1.0) * 2;
      }

      overPower = true;
      angularPower = zRotation;
    } else {
      overPower = false;
      angularPower = Math.abs(xSpeed) * zRotation - m_quickStopAccumulator;

      if (m_quickStopAccumulator > 1) {
        m_quickStopAccumulator -= 1;
      } else if (m_quickStopAccumulator < -1) {
        m_quickStopAccumulator += 1;
      } else {
        m_quickStopAccumulator = 0.0;
      }
    }

    double leftMotorOutput = xSpeed - angularPower;
    double rightMotorOutput = xSpeed + angularPower;

    // If rotation is overpowered, reduce both outputs to within acceptable range
    if (overPower) {
      if (leftMotorOutput > 1.0) {
        rightMotorOutput -= leftMotorOutput - 1.0;
        leftMotorOutput = 1.0;
      } else if (rightMotorOutput > 1.0) {
        leftMotorOutput -= rightMotorOutput - 1.0;
        rightMotorOutput = 1.0;
      } else if (leftMotorOutput < -1.0) {
        rightMotorOutput -= leftMotorOutput + 1.0;
        leftMotorOutput = -1.0;
      } else if (rightMotorOutput < -1.0) {
        leftMotorOutput -= rightMotorOutput + 1.0;
        rightMotorOutput = -1.0;
      }
    }

    // Normalize the wheel speeds
    double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
    if (maxMagnitude > 1.0) {
      leftMotorOutput /= maxMagnitude;
      rightMotorOutput /= maxMagnitude;
    }
    leftout=  leftMotorOutput;
    rightout = rightMotorOutput;

    leftMas.set(-leftout);
    rightMas.set(-rightout);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
