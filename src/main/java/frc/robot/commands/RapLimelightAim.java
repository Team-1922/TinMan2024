// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RackAndPinionConstants;
import frc.robot.Constants.LimelightConstants;
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



    m_target =
      (((m_LimelightSubsystem.GetVerticalLimelightAngle()-LimelightConstants.MinVerticalAngle)/(LimelightConstants.MaxVerticalAngle-LimelightConstants.MinVerticalAngle)) // converts the ty into a 0-1 scale 
     *(RackAndPinionConstants.RAPmaxAngle -RackAndPinionConstants.RAPminAngle)) // multiplies by the range the RAP can go
     +RackAndPinionConstants.RAPminAngle; // adds the min angle the rap can be at. (THIS MIGHT HAVE TO BE CHANGED TO BE THE REFERENCE POINT)

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    // only sets the RAP to move if it sees something
  if (m_LimelightSubsystem.HasValidTarget())
    {m_RAP.SetShooterAngle(m_target);}

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_RAP.StopRAPmotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
