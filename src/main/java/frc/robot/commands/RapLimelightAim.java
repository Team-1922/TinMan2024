// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RackAndPinionConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.RackAndPinionSubsystem;

public class RapLimelightAim extends Command {
  LimelightSubsystem m_LimelightSubsystem;
  RackAndPinionSubsystem m_RAP;
  double m_target;

  double m_tyRange;
  double m_RAPrange;
  Rumble m_Rumble;

  /** Creates a new RapLimelightAim. */
  public RapLimelightAim(RackAndPinionSubsystem RAP, LimelightSubsystem limelightSubsystem, Rumble rumble) {
    m_RAP = RAP;
    m_LimelightSubsystem = limelightSubsystem;
    m_Rumble = rumble;
    addRequirements(m_RAP);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {



   
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
   if (m_LimelightSubsystem.HasValidTarget()){
    m_target =
     
    (((m_LimelightSubsystem.GetVerticalLimelightAngle()-LimelightConstants.MinVerticalAngle)
      /(LimelightConstants.MaxVerticalAngle-LimelightConstants.MinVerticalAngle)) // converts the ty into a 0-1 scale 
      *(RackAndPinionConstants.RAPmaxAngle -RackAndPinionConstants.RAPminAngle)) // multiplies by the range the RAP can go
      +RackAndPinionConstants.RAPminAngle; // adds the min angle the rap can be at. (THIS MIGHT HAVE TO BE CHANGED TO BE THE REFERENCE POINT)
    
      SmartDashboard.putNumber("aim target", m_target+m_RAP.getRAPreference());
    m_RAP.GoToPositonWithoutMotionMagic(m_target);
      
   }
   else {
    m_Rumble.initialize(); // rumble the controller if there is no valid target
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
