// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {
 ShooterSubsystem m_ShootSubsystem;

  /** Creates a new Shoot. */
  public Shoot( ShooterSubsystem ShootSubsystem ) {
    m_ShootSubsystem = ShootSubsystem;

    addRequirements(ShootSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ShootSubsystem.Shoot(8, 8); // might need to be higher, starting low to see if it workds
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ShootSubsystem.StopShoot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
