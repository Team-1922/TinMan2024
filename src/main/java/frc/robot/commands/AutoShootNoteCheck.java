// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class AutoShootNoteCheck extends Command {
  Timer m_Timer = new Timer();
  double m_seconds;
  
  /** Creates a new AutoShootNoteCheck.
   * @param seconds ends command after that amount of time after no longer seeing a note
   *  don't put Seconds to 0 or command will end instantly
   */
  public AutoShootNoteCheck(double seconds) {
    m_seconds = seconds;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize( ) {
    m_Timer.stop();
    m_Timer.reset();
    SmartDashboard.getBoolean("Has Note?", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  if(  !SmartDashboard.getBoolean("Has Note?", true)){
    m_Timer.start();
  }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Timer.hasElapsed(m_seconds);
  }
}
