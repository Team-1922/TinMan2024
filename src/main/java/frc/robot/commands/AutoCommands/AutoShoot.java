// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShoot extends Command {
 ShooterSubsystem m_ShootSubsystem;
double m_LeftVoltage;
double m_RightVoltage;
double m_duration;
Timer m_Timer = new Timer();
  /** Creates a new AutoShoot.
   * @param duration how long the command will run for (seconds)
   * 
   */
  public AutoShoot( ShooterSubsystem ShootSubsystem,double LeftVoltage,double RightVoltage, double duration ) {
    m_ShootSubsystem = ShootSubsystem;
    m_LeftVoltage = LeftVoltage;
    m_RightVoltage = RightVoltage;
    m_duration = duration;
    addRequirements(ShootSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
m_Timer.start();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ShootSubsystem.Shoot(m_LeftVoltage, m_RightVoltage); // might need to be higher, starting low to see if it workds
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Timer.reset();
    m_ShootSubsystem.StopShoot();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Timer.hasElapsed(m_duration);
  }
}
