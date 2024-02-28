// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;

public class SpeakerApriltag extends Command {
  Limelight mLimelight;
  DoubleSupplier m_txCenter;
  DoubleSupplier m_txSide;
  /*Adjust these values accordingly */
  double mXCenter = 0;
  double mYCenter = 0;
  double mXSide = 0;
  double mYSide = 0;
  /** Creates a new SpeakerApriltag. */
  public SpeakerApriltag(Limelight limelight) {
    mLimelight = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double mRobotX;
    double mRobotY;
    //Note: Figure out how to get values from selected targets.
    m_txCenter = () -> mLimelight.getTx();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
