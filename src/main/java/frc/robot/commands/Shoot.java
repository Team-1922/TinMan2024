// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.RackAndPinionSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {
  ShooterSubsystem m_ShootSubsystem;
  Collector m_Collector;
  RackAndPinionSubsystem m_RAP;
  Timer m_Timer = new Timer();
  Timer m_AutoTimer = new Timer(); // used for auto
  double m_RPMLeft;
  double m_RPMRight;
  double m_Delay;
  double m_CollectVoltage;
  boolean m_IsAuto;
  double m_AutoTime; // used for auto

  /** Creates a new Shoot.
   * @param IsAuto True if the command is being used for Auto
   * @param AutoTime how long the command will run for, only used if {@code IsAuto} is True
   *  <p> DO NOT PUT {@code AutoTime} AT 0 OR THE COMMAND WILL INSTANTLY END
   */ 
  public Shoot( ShooterSubsystem ShootSubsystem, Collector collectorSubsystem, boolean IsAuto, double AutoTime, RackAndPinionSubsystem RAP ) {

    m_IsAuto = IsAuto;
    m_AutoTime = AutoTime;
    m_ShootSubsystem = ShootSubsystem;
    m_Collector = collectorSubsystem;
    m_RAP = RAP;
    addRequirements(collectorSubsystem);
   
    m_RPMLeft = Constants.ShooterConstants.kLeftShooterRPS;
    m_RPMRight = Constants.ShooterConstants.kRightShooterRPS;
    m_CollectVoltage = Constants.CollectorConstants.kCollectRPM;
    
  
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_AutoTimer.reset();
    if(m_IsAuto){
      m_AutoTimer.start();
    }
    m_ShootSubsystem.TargetRpmReached(ShooterConstants.kLeftTargetRPS, ShooterConstants.kRightTargetRPS);

    m_ShootSubsystem.Shoot(m_RPMLeft, m_RPMRight); // might need to be higher, starting low to see if it works

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if (
      SmartDashboard.getBoolean("Up to Speed", false)&& !m_RAP.isRAPmoving() )
  {   
      m_Collector.ActivateMotor(Constants.CollectorConstants.kShootRPM);
    } 
    if(!m_Collector.m_IsTriggered){
      m_AutoTimer.start();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    m_Collector.StopMotor();
    m_ShootSubsystem.StopShoot();
    m_AutoTimer.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return m_AutoTimer.hasElapsed(m_AutoTime); 
  }
}
