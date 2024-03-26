// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClimberSubsystem;

public class TorqueLimitClimb extends Command {
  
  ClimberSubsystem m_ClimberSubsystem; 
  CommandXboxController m_Controller;
  int m_RightAxis;
  double m_Deadband;
  int m_OverrideButton;
  //Motor-Torque constant for the NEO550s is 0.097 keep this in mind for calculations
  /** Creates a new Climb.
   * 
   * @param LeftAxis the axis used for controlling the left climber motor
   * @param RightAxis the axis used for controlling the right climber motor
   * @param Deadband the deadband to apply to the controller
   * @param OverrideButton button used to use independent control
   */
  public TorqueLimitClimb(ClimberSubsystem climberSubsystem, CommandXboxController controller, int RightAxis, double Deadband, int OverrideButton) {
    m_Controller = controller;
    m_ClimberSubsystem = climberSubsystem;
    m_RightAxis = RightAxis;
    m_Deadband = Deadband;
    m_OverrideButton = OverrideButton;
    addRequirements(climberSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Limit amps to lower torque
    m_ClimberSubsystem.SetTorque(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    

if(m_Controller.button(m_OverrideButton).getAsBoolean()
){
  m_ClimberSubsystem.LeftClimb(
    MathUtil.applyDeadband(-m_Controller.getRawAxis(m_RightAxis), m_Deadband));
     m_ClimberSubsystem.RightClimb(
    MathUtil.applyDeadband(m_Controller.getRawAxis(1), m_Deadband));
}else{
    m_ClimberSubsystem.LeftClimb(
      MathUtil.applyDeadband(-m_Controller.getRawAxis(m_RightAxis), m_Deadband));
    m_ClimberSubsystem.RightClimb(
        MathUtil.applyDeadband(m_Controller.getRawAxis(m_RightAxis), m_Deadband));
      if(m_ClimberSubsystem.GetRightVelocity() <= 10 && m_ClimberSubsystem.GetLeftVelocity() <= 10) {
        if(m_ClimberSubsystem.GetRightVelocity() >= 0 && m_ClimberSubsystem.GetLeftVelocity() >= 0) {
            m_ClimberSubsystem.SetTorque(30);
          }
        }
    //Check velocity: Once it reaches a threshold (stalling against the chain), unlimit the torque and pull it up
  }}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    m_ClimberSubsystem.StopLeftClimber();
    m_ClimberSubsystem.StopRightClimber();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
