// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.limelightlib.LimelightHelpers;

public class Vision extends SubsystemBase {
  /** Creates a new Limelight. */

  private NetworkTable LimelightTable;
  private NetworkTableEntry m_tx;
  private NetworkTableEntry m_ty;
  private NetworkTableEntry m_ta;
  private NetworkTableEntry m_tID;
  private NetworkTableEntry m_Pipeline;
  public double[] limelightData = new double[2];
  
  public Vision() {
    LimelightTable = NetworkTableInstance.getDefault().getTable("limelight-collect");
    m_tx = LimelightTable.getEntry("tx");
    m_ty = LimelightTable.getEntry("ty");
    m_ta = LimelightTable.getEntry("ta");
    m_tID = LimelightTable.getEntry("tid");
    m_Pipeline = LimelightTable.getEntry("PipelineName");
  }

  public void setPipeline(double pipelineID) {
    if((int) pipelineID == 0) {
    LimelightTable.getEntry("PipelineName").setString("Right_Target");
    //m_Pipeline.setString("Right_Target");
    }
    else if ((int) pipelineID == 1) {
      LimelightTable.getEntry("PipelineName").setString("Left_Target");
      //m_Pipeline.setString("Left_Target");
    }
    System.out.println(LimelightHelpers.getCurrentPipelineIndex("crazy"));
    //LimelightHelpers.setPipelineIndex("limelight-shooter", pipelineID);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipelineID);
  }
  
  public double retrieveData(double slot) {
    setPipeline(slot);
    double x = getTx();
    limelightData[(int) slot] = x;
    System.out.println(x);
    return limelightData[(int) slot];
  }

  public double cot(double theta) {
    return (1/Math.tan(theta));
  }

  /**
   * @return Target X
   */
  public double getTx(){
    return m_tx.getDouble(0.0);  
  }

  /**
   * @return Target Y 
   */
  public double getTy(){
    return m_ty.getDouble(0.0);
  }

    /**
   * @return Target angle
   */
  public double getTa(){
    return m_ta.getDouble(0.0);
  }

  public double getID() {
    return m_tID.getDouble(0.0);
  }

  public double getXPos() {
    return 0.0;
  }

  public double getYPos() {
    return 0.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
