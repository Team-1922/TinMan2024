// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  private NetworkTable LimelightTable;
  private NetworkTableEntry m_tx;
  private NetworkTableEntry m_ty;
  private NetworkTableEntry m_ta;

  public Limelight(String LimelightName) {
    LimelightTable = NetworkTableInstance.getDefault().getTable(LimelightName);
    m_tx = LimelightTable.getEntry("tx");
    m_ty = LimelightTable.getEntry("ty");
    m_ta = LimelightTable.getEntry("ta");
  }
  
  /**
   * @return Target X
   */
  public NetworkTableEntry GetTx(){
  return m_tx;  
  }

  /**
   * @return Target Y 
   */
  public NetworkTableEntry GetTy(){
return m_ty;
  }

    /**
   * @return Target angle
   */
  public NetworkTableEntry GetTa(){
return m_ta;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
