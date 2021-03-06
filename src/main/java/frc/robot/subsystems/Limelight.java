/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;
import frc.robot.Constants.Vision;

/**
 * Add your docs here.
 */
public class Limelight {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable(Constants.Vision.limelightName);
    
    public static double temp_tx;
    public static double temp_ty;
    static {
        Shuffleboard.getTab("Limelight").addNumber("LimelightDistance", Limelight::getDistance);
        Shuffleboard.getTab("Limelight").addNumber("LimelightX", Limelight::getTx);
        Shuffleboard.getTab("Limelight").addNumber("LimelightY", Limelight::getTy);
        Shuffleboard.getTab("Limelight").addNumber("LimelightArea", Limelight::getTa);
        Shuffleboard.getTab("PositionCombine").addNumber("LimelightX", Limelight::getTx);
    }
    public static double getTx() {
        if(getTa() > 0){
            temp_tx = table.getEntry("tx").getDouble(0.0);
            return temp_tx; 
        }
        else
            return temp_tx;
    }

    public static double getTy() {
        if(getTa() > 0){
            temp_ty = table.getEntry("ty").getDouble(0.0);
            return temp_ty;
        }
        else
            return temp_ty;
    }

    public static double getTa() {
        return table.getEntry("ta").getDouble(0.0);
    }

    public static double getDistance(){
        return (Vision.targetHigh - Vision.limelightHigh)
                            / Math.tan(Math.toRadians(Vision.limelightAngle+getTy()));
    }

}
