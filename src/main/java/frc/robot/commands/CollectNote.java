// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Collector;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class CollectNote extends Command {
 
  Collector m_Collector;
  Timer m_Timer = new Timer();// used to run reverse for .5 seconds  

  boolean m_ReverseCheck; // false if it needs to reverse to see if there is a note in the collector currently 
  boolean m_HasNote; // true if there is a note in the robot
  boolean m_NoteCollected; // true if the note is all the way in the robot 
  boolean m_EndCheck; // true if command should end

  double m_ReverseDuration = Constants.CollectorConstants.kReverseDuration;

 /** Creates a new CollectNote. */
  public CollectNote(Collector Collector) {

    m_Collector = Collector;
    addRequirements(Collector);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (!m_Collector.TofcheckTarget()) {// if it doesn't have a note, run reverse 
      m_Collector.ReverseMotor(Constants.CollectorConstants.kRollerVoltage);
      m_ReverseCheck = false;
      m_HasNote = false;
    } else {
      m_ReverseCheck = true; 
      m_HasNote = true;
    }
    m_NoteCollected = false;
    m_EndCheck = false;
    m_Timer.reset();
    m_Timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if (!m_HasNote) {
      if (!m_ReverseCheck) {// Reverses collector to check if there is a note already in the robot
        if (m_Collector.TofcheckTarget() || m_Timer.hasElapsed(m_ReverseDuration)) {
          m_Collector.StopMotor();
          m_ReverseCheck = true;
        } 
        return;
      }
   
      if (!m_Collector.TofcheckTarget()) {
        m_Collector.ActivateMotor(Constants.CollectorConstants.kRollerVoltage);
      } else {
        m_HasNote = true;
      } 
      return;
    }         

    if (!m_NoteCollected) { // Sees note, but note is not all the way in the robot 
      if (m_Collector.TofcheckTarget()) {
        m_Collector.ActivateMotor(Constants.CollectorConstants.kRollerVoltage);
      } else {
        m_Collector.StopMotor();
        m_NoteCollected = true;
      }
      return;
    } 
      
    if (m_NoteCollected) { // sees note, note has gone past Tof, reversing collector until it sees note again
      if (!m_Collector.TofcheckTarget()) {
        m_Collector.ReverseMotor(Constants.CollectorConstants.kRollerVoltage);
      } else {
        m_Collector.StopMotor();
        m_EndCheck = true;
      }
      return;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_Collector.StopMotor();
    m_Timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return m_EndCheck;
  }
}
