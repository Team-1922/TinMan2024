// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.limelightlib.LimelightHelpers;
import frc.robot.commands.GetApriltagData;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  private GetApriltagData getData;

  private NetworkTable LimelightTable;
  private NetworkTableEntry m_tx;
  private NetworkTableEntry m_ty;
  private NetworkTableEntry m_ta;
  private NetworkTableEntry m_tID;
  private NetworkTableEntry m_Pipieline;
  private double[] limelightData = new double[2];
  
  public Limelight() {
    LimelightTable = NetworkTableInstance.getDefault().getTable("goofball");
    m_tx = LimelightTable.getEntry("tx");
    m_ty = LimelightTable.getEntry("ty");
    m_ta = LimelightTable.getEntry("ta");
    m_tID = LimelightTable.getEntry("tid");
    m_Pipieline = LimelightTable.getEntry("pipeline");
  }

  Limelight reference = new Limelight();

  public void setPipeline(int pipelineID) {
    m_Pipieline.setNumber(pipelineID);
  }

  public void retrieveData(int slot, double x) {
    limelightData[slot] = x;
  }

  public double[] returnData() {
    targetApriltag(0);
    targetApriltag(1);
    return limelightData;
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

  public Command targetApriltag(int slot) {
    getData = new GetApriltagData(reference, slot);
    return runOnce(() -> getData.initialize());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
