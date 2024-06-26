// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.RackAndPinionSubsystem;

public class AngleAdjust extends Command {
  RackAndPinionSubsystem m_RackAndPinionSubsystem;
  CommandXboxController m_XboxController;

  /** Creates a new AngleAdjust. 
   * <p>
   * Changes the angle of the shooter based on the triggers of the Xboxcontroller given.
   * <p> <b>Right</b> trigger is positive
   * <p> <b>Left</b> trigger is negitive
  */
  public AngleAdjust(RackAndPinionSubsystem rackAndPinionSubsystem, CommandXboxController operatorcController ) {
    m_RackAndPinionSubsystem = rackAndPinionSubsystem;
    m_XboxController = operatorcController;
    addRequirements(m_RackAndPinionSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  //m_RackAndPinionSubsystem.SetShooterAngle(Constants.RackAndPinionConstants.RAPmaxAngle*
  //(MathUtil.applyDeadband(m_XboxController.getRightTriggerAxis(),0.1)
 // -MathUtil.applyDeadband(m_XboxController.getLeftTriggerAxis(),0.1))
 // );
   m_RackAndPinionSubsystem.SetRAPspeed(Constants.RackAndPinionConstants.RAPMaxSpeed*(
   MathUtil.applyDeadband(m_XboxController.getRightTriggerAxis(),.1)
   -MathUtil.applyDeadband(m_XboxController.getLeftTriggerAxis(),.1))); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
