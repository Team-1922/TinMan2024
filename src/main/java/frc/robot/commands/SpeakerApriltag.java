// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.limelightlib.LimelightHelpers;

public class SpeakerApriltag extends Command {
  Limelight L1;
  Limelight L2;
  Limelight m_LeftLimelight;
  Limelight m_RightLimelight;
  public DoubleSupplier m_txCenter;
  public DoubleSupplier m_txSide;
  /*Adjust these values accordingly */
  double mXCenter = 0;
  double mYCenter = 0;
  double mXSide = 0;
  double mYSide = 0;
  public double mRobotX;
  public double mRobotY;
  /** Creates a new SpeakerApriltag. */
  public SpeakerApriltag(Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    L1 = new Limelight("LimelightOne");
    L2 = new Limelight("LimelightTwo");
    addRequirements(limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_LeftLimelight = new Limelight("Robot");
    mRobotX = 0;
    mRobotY = 0;
    //Note: Figure out how to get values from selected targets.
    m_txCenter = () -> m_LeftLimelight.getTx();
    m_txSide = () -> m_RightLimelight.getTx();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mRobotX = (mXSide * (1/Math.tan(m_txSide.getAsDouble())) + mXCenter * (1/Math.tan(m_txCenter.getAsDouble()) - mYSide + mYCenter))/((1/Math.tan(m_txSide.getAsDouble())) + 1/Math.tan(m_txCenter.getAsDouble()));
    mRobotY = (mYSide * (1/Math.tan(m_txCenter.getAsDouble())) + mYCenter * (1/Math.tan(m_txSide.getAsDouble()) - mXSide + mXCenter))/((1/Math.tan(m_txSide.getAsDouble())) + 1/Math.tan(m_txCenter.getAsDouble()));
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
