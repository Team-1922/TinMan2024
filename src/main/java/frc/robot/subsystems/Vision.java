// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
    LimelightTable = NetworkTableInstance.getDefault().getTable("limelight-shooter");
    m_tx = LimelightTable.getEntry("tx");
    m_ty = LimelightTable.getEntry("ty");
    m_ta = LimelightTable.getEntry("ta");
    m_tID = LimelightTable.getEntry("tid");
    m_Pipeline = LimelightTable.getEntry("pipeline");
  }

  public void wait(double time) {
    WaitCommand wait = new WaitCommand(time);
  }

  public void setPipeline(double pipelineID) {
    if((int) pipelineID == 0) {
      NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("pipeline").setNumber(0.0);
    }
    else /*if ((int) pipelineID == 1)*/ {
      NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("pipeline").setNumber(1.0);
    }
    //LimelightHelpers.setPipelineIndex("limelight-shooter", pipelineID);
    
    //System.out.println("pipeline Index " + LimelightHelpers.getCurrentPipelineIndex("limelight"));
    //System.out.println("Pipeline index: " + NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("pipeline").getDouble(0.0));
  }
  
  public double retrieveData(double slot) {
    setPipeline(slot);
    RunCommand run = new RunCommand(() -> wait(0.25));
    double x = getTx();
    limelightData[(int) slot] = x;
    //System.out.println(x + " Value");
    return limelightData[(int) slot];
  }

  public double cot(double theta) {
    return (1/Math.tan(theta));
  }

  /**
   * @return Target X
   */
  public double getTx(){
    return NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("tx").getDouble(0);
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
