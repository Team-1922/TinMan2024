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
 

  boolean ReverseCheck; // false if it needs to reverse to see if there is a note in the collector currently 
  boolean HasNote; // true if there is a note in the robot
  boolean NoteCollected; // true if the note is all the way in the robot 
  boolean EndCheck; // true if command should end

 /** Creates a new CollectNote. */
  public CollectNote(Collector Collector) {
    m_Collector = Collector;
    
    addRequirements(Collector);
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(!m_Collector.TOFcheckTarget()){// if it doesn't have a note, run reverse 
      m_Collector.ReverseMotor(Constants.MotorConstants.kRollerVoltage);
      ReverseCheck= false;
      HasNote= false;
    }
    else{
      ReverseCheck= true; 
      HasNote = true;
    }
    NoteCollected = false;
    EndCheck = false;
    m_Timer.reset();
    m_Timer.start();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if (!ReverseCheck ) {// if it didn't have a note, it stops the reverse after .5 seconds, or if it sees a note 
      if (m_Collector.TOFcheckTarget()|| m_Timer.hasElapsed(.5)) {
        m_Collector.StopMotor();
        ReverseCheck = true;
      }
    }
    if(ReverseCheck&& !HasNote){// if it didn't have a note to start, and it has run the reverse, run the collector until it does 
       if (!m_Collector.TOFcheckTarget()) {
        m_Collector.ActivateMotor(Constants.MotorConstants.kRollerVoltage);
       }else{
        HasNote= true;
       } 
        }
    if(ReverseCheck && HasNote && !NoteCollected){ // if it did have a note, run collect until it doesn't see it anymore 
     if (m_Collector.TOFcheckTarget()) {
      m_Collector.ActivateMotor(Constants.MotorConstants.kRollerVoltage);
      }else{
        m_Collector.StopMotor();
        NoteCollected = true;
       }
    }
      
    if(NoteCollected){
      if(!m_Collector.TOFcheckTarget()){
      m_Collector.ReverseMotor(Constants.MotorConstants.kRollerVoltage);
      }else{
        m_Collector.StopMotor();
        EndCheck= true;
      }
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

    return EndCheck;
  }
}
