// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RackAndPinionConstants;
import frc.robot.subsystems.RackAndPinionSubsystem;

public class resetRAPangle extends Command {
  RackAndPinionSubsystem m_RackAndPinionSubsystem; 

  /** Creates a new resetRAPangle. */
  public resetRAPangle(RackAndPinionSubsystem rackAndPinionSubsystem) {
    m_RackAndPinionSubsystem = rackAndPinionSubsystem;
    addRequirements(m_RackAndPinionSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_RackAndPinionSubsystem.SetCurrentAngleAsDefault();

        
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    
    

  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
