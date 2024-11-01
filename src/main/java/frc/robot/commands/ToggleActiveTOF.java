// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class ToggleActiveTOF extends Command {
  Collector m_Collector;
  /** Creates a new ToggleActiveTOF. */
  public ToggleActiveTOF(Collector Collector) {
    m_Collector = Collector;
    addRequirements(m_Collector);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_Collector.ToggleTOF();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
