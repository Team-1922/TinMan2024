// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;

public class shootStart extends Command {
  ShooterSubsystem m_ShooterSubsystem;
  double m_SpeedIncrease;
  /** Creates a new shootStart. 
   * runs shooter motors, does not run collector motors
   * @param SpeedIncrease 
  */
  public shootStart(ShooterSubsystem shooterSubsystem, double SpeedIncrease) {
    m_ShooterSubsystem = shooterSubsystem;
    m_SpeedIncrease = SpeedIncrease;
    addRequirements(shooterSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { 
  //  m_ShooterSubsystem.Shoot(ShooterConstants.kRightShooterRPS*m_SpeedIncrease, ShooterConstants.kLeftShooterRPS*m_SpeedIncrease);
  //  m_ShooterSubsystem.TargetRpmReached(ShooterConstants.kLeftTargetRPS, ShooterConstants.kRightTargetRPS);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
 //   m_ShooterSubsystem.StopShoot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
