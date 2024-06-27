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
    boolean m_StartCheck=false;
    double TargetSpeed = 0.35;
    double MinSpeed = RackAndPinionConstants.RAPMinSpeed;
  /** Creates a new resetRAPangle. */
  public resetRAPangle(RackAndPinionSubsystem rackAndPinionSubsystem) {
    m_RackAndPinionSubsystem = rackAndPinionSubsystem;
    addRequirements(m_RackAndPinionSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_StartCheck =false;
    //TODO: figure out a good speed to go at
        
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    
    
    if ( m_StartCheck ==false && m_RackAndPinionSubsystem.GetRAPspeed()< MinSpeed){ 
      m_RackAndPinionSubsystem.SetRAPspeed(TargetSpeed);
      SmartDashboard.putBoolean("STARTCHECK", false);
    }
    if (m_RackAndPinionSubsystem.GetRAPspeed()>= MinSpeed && m_StartCheck==false){
      m_StartCheck=true;
      SmartDashboard.putBoolean("STARTCHECK", true);
   
    }
    if (m_StartCheck==true && m_RackAndPinionSubsystem.GetRAPspeed() <=MinSpeed){
      m_RackAndPinionSubsystem.SetRAPspeed(0);
      m_RackAndPinionSubsystem.SetCurrentAngleAsDefault();
      SmartDashboard.putNumber("testnumber", m_RackAndPinionSubsystem.GetRAPspeed());
   
    }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
