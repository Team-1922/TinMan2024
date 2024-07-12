// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;

public class LimelightSubsystem extends SubsystemBase {

  NetworkTable m_Limelight = NetworkTableInstance.getDefault().getTable("limelight"); //TODO: make sure that table name is correct
  NetworkTableEntry ty = m_Limelight.getEntry("ty");
  NetworkTableEntry tv = m_Limelight.getEntry("tv"); // 0 if it doesn't have a target, 1 if it does
  /** Creates a new LimelightSubsystem. */
  public LimelightSubsystem() {

  }




    /**
   * gets the ty value from the limelight
   */
  public double GetTy(){
    
   return ty.getDouble(0.0);
 
  }

  /**
   * 
   * @return true/false if the limelight sees a valid target
   */
 public boolean HasValidTarget(){
  if(tv.getDouble(0)==0){
    return false;
  }
    else{
      return true;}
  
 }
  

  @Override
  public void periodic() {

  
    // This method will be called once per scheduler run
  }
}
