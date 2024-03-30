// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.limelightlib.LimelightHelpers;

public class GetApriltagData extends Command {
  private Limelight m_Limelight;
  private int m_ID;
  /** Creates a new SpeakerApriltag. */
  public GetApriltagData(Limelight firstLimelight, int pipelineID) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Limelight = firstLimelight;
    m_ID = pipelineID;
    addRequirements(firstLimelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Limelight.setPipeline(m_ID);
    m_Limelight.retrieveData(m_ID, m_Limelight.getTx());
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
