// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {
  ShooterSubsystem m_ShootSubsystem;
  Collector m_Collector;
  Timer m_Timer = new Timer();
  Timer m_AutoTimer = new Timer(); // used for auto
  double m_VLeft;
  double m_VRight;
  double m_Delay;
  double m_CollectVoltage;
  boolean m_IsAuto;
  double m_AutoTime; // used for auto
  /** Creates a new Shoot.
   * @param IsAuto True if the command is being used for Auto
   * @param AutoTime how long the command will run for, only used if {@code IsAuto} is True
   *  <p> DO NOT PUT {@code AutoTime} AT 0 OR THE COMMAND WILL INSTANTLY END
   */ 
  public Shoot( ShooterSubsystem ShootSubsystem, Collector collectorSubsystem, boolean IsAuto, double AutoTime ) {

    m_IsAuto = IsAuto;
    m_AutoTime = AutoTime;

    m_ShootSubsystem = ShootSubsystem;
    m_Collector = collectorSubsystem;
    addRequirements(ShootSubsystem, collectorSubsystem);
    
    m_VLeft = Constants.ShooterConstants.kLeftShooterVoltage;
    m_VRight = Constants.ShooterConstants.kRightShooterVoltage;
    m_CollectVoltage = Constants.CollectorConstants.kRollerVoltage;
    m_Delay = Constants.ShooterConstants.kCollectorActivateDelay;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_AutoTimer.reset();
    if(m_IsAuto){
      m_AutoTimer.start();
    }
    m_Timer.reset();
    m_Timer.start();
    m_ShootSubsystem.Shoot(m_VLeft, m_VRight); // might need to be higher, starting low to see if it works
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (m_Timer.hasElapsed(m_Delay)) {
      m_Collector.ActivateMotor(m_CollectVoltage);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_Timer.stop();
    m_Collector.StopMotor();
    m_ShootSubsystem.StopShoot();
    m_AutoTimer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return m_AutoTimer.hasElapsed(m_AutoTime); // Because the timer won't 
  }
}
