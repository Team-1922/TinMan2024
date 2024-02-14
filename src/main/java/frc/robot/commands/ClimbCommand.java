// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimbCommand extends Command {
  boolean m_Reverse;
  ClimberSubsystem m_ClimberSubsystem;
  /** Creates a new ClimbCommand.
   * @param Reversed false for up, true for down
   */
  public ClimbCommand(ClimberSubsystem climberSubsystem,Boolean Reversed) {
    m_Reverse = Reversed;
    m_ClimberSubsystem = climberSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
m_ClimberSubsystem.Climb(m_Reverse); 

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
m_ClimberSubsystem.StopClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
