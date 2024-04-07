// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants;

import frc.robot.subsystems.Collector;


public class AutoShoot extends Command {

  Collector m_Collector;
  Timer m_Timer = new Timer();
  Timer m_AutoTimer = new Timer(); // used for auto
  double m_RPMLeft;
  double m_RPMRight;
  double m_Delay;
  double m_CollectVoltage;
  boolean m_IsAuto;
  double m_AutoTime; // used for auto
  Timer m_backuptimer = new Timer();
  boolean m_check;
  Timer m_EndDelay = new Timer();

  /** Creates a new Shoot.
   * @param IsAuto True if the command is being used for Auto
   * @param AutoTime how long the command will run for, only used if {@code IsAuto} is True
   *  <p>  DO NOT PUT {@code AutoTime} AT 0 OR THE COMMAND WILL INSTANTLY END
   */ 
  public AutoShoot( Collector collectorSubsystem, boolean IsAuto, double AutoTime ) {

    m_IsAuto = IsAuto;
    m_AutoTime = AutoTime;

   
    m_Collector = collectorSubsystem;
    addRequirements( collectorSubsystem);
   

  
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_EndDelay.reset();
    m_check = SmartDashboard.getBoolean("Has Note?", false);
    m_AutoTimer.reset();
    if(m_IsAuto){
      m_AutoTimer.start();
    }
    m_backuptimer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   // m_ShootSubsystem.Shoot(m_RPMLeft, m_RPMRight);
    if (
      SmartDashboard.getBoolean("Up to Speed", false)
  
       ) {   
      m_Collector.ActivateMotor(Constants.CollectorConstants.kShootRPM);
    } 

    if(!SmartDashboard.getBoolean("Has Note?", m_IsAuto)){

    m_backuptimer.start();

    }
     if(m_check == true && !SmartDashboard.getBoolean("Has Note?", true)){
      m_EndDelay.start();
     }


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    


    m_Collector.StopMotor();
    m_backuptimer.stop();
    m_AutoTimer.stop();
    m_EndDelay.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return m_AutoTimer.hasElapsed(m_AutoTime) || m_backuptimer.hasElapsed(4) || m_EndDelay.hasElapsed(.25); // Because the timer won't start if it's not auto
  }
}
