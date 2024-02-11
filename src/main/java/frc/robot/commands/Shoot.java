// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {
 ShooterSubsystem m_ShootSubsystem;
 Collector m_Collector;
  /** Creates a new Shoot. */
  public Shoot( ShooterSubsystem ShootSubsystem, Collector collectorSubsystem ) {
    m_ShootSubsystem = ShootSubsystem;
    m_Collector = collectorSubsystem;
    addRequirements(ShootSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Collector.ActivateMotor(Constants.MotorConstants.kRollerVoltage);
    m_ShootSubsystem.Shoot(9, 7); // might need to be higher, starting low to see if it workds
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Collector.StopMotor();
    m_ShootSubsystem.StopShoot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
