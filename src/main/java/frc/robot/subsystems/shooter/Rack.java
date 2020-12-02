/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.PowCon;
import frc.robot.motor.MotorFactory;
import com.ctre.phoenix.motorcontrol.*;

public class Rack extends Spinable {
  /**
   * Creates a new Rack.
   */
  private SupplyCurrentLimitConfiguration supplyCurrentLimitConfiguration = new SupplyCurrentLimitConfiguration(true, 8, 10, 1);
  private WPI_TalonSRX rack = new WPI_TalonSRX(PowCon.rack);
  private int lastPosition = 0;


  public Rack() {
    rack.configFactoryDefault();
    
    lastPosition = rack.getSelectedSensorPosition();
    MotorFactory.setInvert(rack, false);
    MotorFactory.setSensor(rack, FeedbackDevice.CTRE_MagEncoder_Absolute);
    rack.setSelectedSensorPosition(lastPosition);
    MotorFactory.setSensorPhase(rack, false);
    MotorFactory.configPF(rack, 0.01, 0.1, 0);
    rack.configMotionAcceleration(1600, 10);
    rack.configMotionCruiseVelocity(1500, 10);

    rack.configPeakOutputForward(0.2, PowCon.kTimeoutMs);
    rack.configPeakOutputReverse(0.2, PowCon.kTimeoutMs);
    rack.configSupplyCurrentLimit(supplyCurrentLimitConfiguration);
    
    rack.setNeutralMode(NeutralMode.Coast);
    rack.configNeutralDeadband(0.005, PowCon.kTimeoutMs);
    
   
  }
  public void initial() {
    rack.overrideLimitSwitchesEnable(false);
    double[] history = new double[10];
    int count = 0;
    while (true) {
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        rack.set(ControlMode.PercentOutput, -0.18);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        history[count] = rack.getSelectedSensorPosition();
        count++;
        // 超出十個就從最舊的開始覆蓋
        if (count >= 10) {
            count = 0;
        }
        // 找出最大最小
        for (int i = 0; i < history.length; i++) {
            if (history[i] > max)
                max = history[i];
            if (history[i] < min)
                min = history[i];
        }
        SmartDashboard.putNumber("temp max", max);
        SmartDashboard.putNumber("temp min", min);
        // 判斷是否有改變
        if ((max - min) < 50) {
            break;
        }
    }
    rack.set(ControlMode.PercentOutput, 0);
    rack.overrideLimitSwitchesEnable(true);
    // 會出來代表已經到最底，並且限位被按下
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    if(rack.isRevLimitSwitchClosed() == 1){
        SmartDashboard.putString("limitType", "NO");
        rack.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    }else if(rack.isRevLimitSwitchClosed() == 0){
        SmartDashboard.putString("limitType", "NC");
        rack.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    }
}
  @Override
  public void periodic() {
  // position = rack.getSelectedSensorPosition();
        // SmartDashboard.putNumber("unit", Rack.aim(Limelight.getDistance()));
        //不論常開/常閉設置如何，關閉的傳感器在所有情況下均返回true，而打開的傳感器在所有情況下均返回false。這樣可以確保函數名稱沒有歧義。
        SmartDashboard.putBoolean("limit", rack.getSensorCollection().isRevLimitSwitchClosed());
        SmartDashboard.putNumber("Rack Position", rack.getSelectedSensorPosition());
        // aim();  }
  }

  @Override
  public void forward() {
    rack.set(ControlMode.PercentOutput, 0.2);
    SmartDashboard.putString("rack", "forward(up)");
  }

  @Override
  public void stop() {
    
    rack.set(ControlMode.PercentOutput, 0);
    SmartDashboard.putString("rack", "stop");

  }

  @Override
  public void reverse() {

    rack.set(ControlMode.PercentOutput,- 0.2);
    SmartDashboard.putString("rack", "reverse(down)");
  }

  @Override
  public String getStatus() {
    return null;
  }
}
