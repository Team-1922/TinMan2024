// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.RackAndPinionSubsystem;

public class RapLimelightAim extends Command {
  LimelightSubsystem m_LimelightSubsystem;
  RackAndPinionSubsystem m_RAP;
  double m_target;

  double m_tyRange;
  double m_RAPrange;
  /** Creates a new RapLimelightAim. */
  public RapLimelightAim(RackAndPinionSubsystem RAP, LimelightSubsystem limelightSubsystem) {
    m_RAP = RAP;
    m_LimelightSubsystem = limelightSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

// how far up/down the target is in the targeted area,  multiplied by the RAP range, then added to the min angle we can target the RAP 
    m_target =
      (((m_LimelightSubsystem.GetTy()-Constants.LimelightConstants.MinTy)/(Constants.LimelightConstants.MaxTy-Constants.LimelightConstants.MinTy))
     *(Constants.RackAndPinionConstants.RAPmaxAngle -Constants.RackAndPinionConstants.RAPminAngle))
     +Constants.RackAndPinionConstants.RAPminAngle;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
m_RAP.SetShooterAngle(m_target);
  

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
