// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
CANSparkMax m_ClimberMotor1 = new CANSparkMax(Constants.ClimberConstants.ClimberMotorID, MotorType.kBrushless);
CANSparkMax m_ClimberMotor2 = new CANSparkMax(Constants.ClimberConstants.ClimberMotorID2,MotorType.kBrushless);
SparkAbsoluteEncoder m_Encoder1 = m_ClimberMotor1.getAbsoluteEncoder();
SparkAbsoluteEncoder m_Encoder2 = m_ClimberMotor2.getAbsoluteEncoder();
Timer m_VelocityTimer = new Timer();
  
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
m_ClimberMotor1.setIdleMode(IdleMode.kBrake);
m_ClimberMotor2.setIdleMode(IdleMode.kBrake);
m_ClimberMotor1.setInverted(false);
m_ClimberMotor2.setInverted(true);
  }

/** makes left climber motor spin
 * 
 * @param DirectionMulitplier 1 for up, -1 for down 
 */
public void LeftClimb(double DirectionMulitplier){

  m_ClimberMotor1.setVoltage(Constants.ClimberConstants.kClimbVoltage*DirectionMulitplier);
}

/** makes right climber motor spin 
 * 
 * @param DirectionMulitplier {@value-1} for down, {@value 1} for up
 */
public void RightClimb(double DirectionMulitplier){


   m_ClimberMotor2.setVoltage(Constants.ClimberConstants.kClimbVoltage*DirectionMulitplier);
}
/** Stops Right Climber motor */
public void StopRightClimber(){

  m_ClimberMotor2.stopMotor();  
}
/** stops Left climber motor  */
public void StopLeftClimber(){

  m_ClimberMotor1.stopMotor();
  
}

public double GetLeftVelocity(){
  double motorVelocity = m_Encoder1.getVelocity();
  return motorVelocity;
}

public double GetRightVelocity(){
    double motorVelocity = m_Encoder2.getVelocity();
  return motorVelocity;
}

public void SetTorque(int max) {
  m_ClimberMotor1.setSmartCurrentLimit(max);
  m_ClimberMotor2.setSmartCurrentLimit(max);
}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
