// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.RackAndPinionSubsystem;

public class RAPgoToAngle extends Command {
  RackAndPinionSubsystem m_RackAndPinionSubsystem;
  double m_Rot;
  /** Creates a new RAPgoToAngle.
   * @param Rot how many rotations away from the reference you want to go 
   */
  public RAPgoToAngle(double Rot, RackAndPinionSubsystem rackAndPinionSubsystem) {
    m_RackAndPinionSubsystem = rackAndPinionSubsystem;
    m_Rot = Rot;
    addRequirements(m_RackAndPinionSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_RackAndPinionSubsystem.SetShooterAngle(m_Rot);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_RackAndPinionSubsystem.StopRAPmotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(m_Rot - m_RackAndPinionSubsystem.GetShooterAngle())) <.25;
  }
}
