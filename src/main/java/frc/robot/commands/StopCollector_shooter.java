// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ShooterSubsystem;

public class StopCollector_shooter extends Command {
  ShooterSubsystem m_ShooterSubsystem;
  Collector m_Collector;
  XboxController m_XboxController;
  /** Creates a new StopCollector_shooter. 
   * 
   * Stops both the collector and shooter. Will vibrate the '<b>operatorController</b>' when held.
   * 
  */
  public StopCollector_shooter(Collector collector, ShooterSubsystem shooterSubsystem, XboxController operatorController) {
    m_Collector = collector;
    m_ShooterSubsystem = shooterSubsystem;
    m_XboxController = operatorController;
    addRequirements( collector, shooterSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Collector.StopMotor();
    m_ShooterSubsystem.StopShoot();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_XboxController.setRumble(RumbleType.kBothRumble, 1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_XboxController.setRumble(RumbleType.kBothRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
