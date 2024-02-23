// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
CANSparkMax m_ClimberMotor1 = new CANSparkMax(Constants.ClimberConstants.ClimberMotorID, MotorType.kBrushless);
CANSparkMax m_ClimberMotor2 = new CANSparkMax(Constants.ClimberConstants.ClimberMotorID2,MotorType.kBrushless);
  
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {

m_ClimberMotor1.setIdleMode(IdleMode.kBrake);
m_ClimberMotor2.setIdleMode(IdleMode.kBrake);
m_ClimberMotor1.setInverted(false);
m_ClimberMotor2.setInverted(false);
  }

/**
 * @param Voltage how many volts motors will output
 * @param SpeedMultiplier negitive for down positive for up 
 * 
 */
public void LeftClimb( double SpeedMultiplier){

  

  m_ClimberMotor1.setVoltage(Constants.ClimberConstants.kClimbVoltage*SpeedMultiplier);
}

/** 
 * @param SpeedMultiplier -1 for down, 1 for up 
 */
public void RightClimb(double SpeedMultiplier){


   m_ClimberMotor2.setVoltage(Constants.ClimberConstants.kClimbVoltage*SpeedMultiplier);
}
/** Stops Right Climber motor */
public void StopRightClimber(){

 m_ClimberMotor2.stopMotor();  
}
/** stops Left climber motor */
public void StopLeftClimber(){

  m_ClimberMotor1.stopMotor();
}



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
