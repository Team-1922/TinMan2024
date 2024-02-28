// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CollectNote;
import frc.robot.subsystems.Collector;

public class AutoCollect extends Command {
  Collector m_Collector = new Collector();
  CollectNote m_CollectNote = new CollectNote(m_Collector);
  Timer m_Timer = new Timer();
  double m_delay = 0 ;
  /** Creates a new AutoCollect. */
  public AutoCollect( Collector collector, CollectNote collectNote, double EndDelay) {
  m_CollectNote = collectNote;
  m_Collector = collector;
  m_delay = EndDelay;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_CollectNote.execute();
    if (m_Collector.TofcheckTarget()){
 m_Timer.start();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_CollectNote.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Timer.hasElapsed(m_delay);
  }
}
