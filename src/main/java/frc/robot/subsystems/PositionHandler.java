// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Vision;

public class PositionHandler extends SubsystemBase {
  /** Creates a new PositionHandler. */
  private Vision m_Limelight;
  public PositionHandler(Vision camera) {
    m_Limelight = camera;

  }
  
  public ArrayList<Double> returnData() {
    Double right = Double.valueOf(m_Limelight.retrieveData(1)); //The left
    Double left = Double.valueOf(m_Limelight.retrieveData(0)); //The right
    ArrayList<Double> packagedData = new ArrayList<Double>();
    packagedData.add(left);
    packagedData.add(right);
    return packagedData;
  }

  /*
  public Command targetApriltag(int slot) {
    return runOnce(() -> m_Limelight.retrieveData(slot)); //Not running this. Why?
    //getData.initialize();
  }
  */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}