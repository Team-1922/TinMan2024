// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Collector;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class CollectNoteAuto extends Command {
 
  Collector m_Collector;
  Timer m_Timer = new Timer();// used to run reverse for .5 seconds  
  Timer m_DelayTimer = new Timer();
  
  boolean m_HasNote; // true if there is a note in the robot
  boolean m_NoteCollected; // true if the note is all the way in the robot 
  boolean m_EndCheck; // true if command should end

  double m_ReverseDuration = Constants.CollectorConstants.kReverseDuration;

 /** Creates a new CollectNote. */
  public CollectNoteAuto(Collector Collector) {

    m_Collector = Collector;
    addRequirements(Collector);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("AutoNoteCheck", false);

    if (!m_Collector.TofcheckTarget()) {// if it doesn't have a note, run reverse 

    m_HasNote = false;
  } else {

    m_HasNote = true;
  }
  m_NoteCollected = false;
  m_EndCheck = false;
  m_Timer.reset();
  m_Timer.start();
  m_DelayTimer.reset();
}
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
 if (!m_HasNote) {
      m_DelayTimer.start();
      if (!m_Collector.TofcheckTarget()) {
      if (m_DelayTimer.hasElapsed(.2)){
        m_Collector.ActivateMotor(Constants.CollectorConstants.kCollectRPM);
        m_DelayTimer.stop();
      }
      } else {
        m_HasNote = true;
        SmartDashboard.putBoolean("AutoNoteCheck", true);
          m_DelayTimer.reset();
      } 
      return;
    }         
  
    if (!m_NoteCollected) { // Sees note, but note is not all the way in the robot 
    
         
      if (m_Collector.TofcheckTarget()) {
        
        m_Collector.ActivateMotor(Constants.CollectorConstants.kSecondCollectRPM);
      } else {
        m_Collector.StopMotor();
        m_NoteCollected = true;
        m_DelayTimer.start();
      }
      return;
    }  
    else { // sees note, note has gone past Tof, reversing collector until it sees note again
      if (!m_Collector.TofcheckTarget()) {
        if (m_DelayTimer.hasElapsed(.2)) {
          
      
        m_Collector.ReverseMotor(Constants.CollectorConstants.kReverseCollectRPM);
        }
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
    m_DelayTimer.stop();
    SmartDashboard.putBoolean("AutoNoteCheck", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return  m_EndCheck;
  }
}
