// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.GetApriltagData;
import frc.robot.subsystems.Vision;

public class PositionHandler extends SubsystemBase {
  /** Creates a new PositionHandler. */
  private Vision m_Limelight;
  public PositionHandler(Vision camera) {
    m_Limelight = camera;

  }
  
  public ArrayList<Double> returnData() {
    targetApriltag(0); //The left
    targetApriltag(1); //The right
    ArrayList<Double> packagedData = new ArrayList<Double>();
    packagedData.add(m_Limelight.limelightData[0]);
    packagedData.add(m_Limelight.limelightData[1]);
    return packagedData;
  }

  public /*Command*/void targetApriltag(int slot) {
    GetApriltagData getData = new GetApriltagData(m_Limelight, slot);
    //return runOnce(() -> getData.initialize());
    getData.initialize();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
