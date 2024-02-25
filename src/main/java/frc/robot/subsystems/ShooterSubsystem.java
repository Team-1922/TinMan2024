// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  
  CANSparkMax m_Left = new CANSparkMax(ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless);
  CANSparkMax m_Right = new CANSparkMax(ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);
  
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    m_Left.setIdleMode(IdleMode.kCoast);
    m_Right.setIdleMode(IdleMode.kCoast);
  //  m_Left = new CANSparkMax(ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless); 
  // m_Right = new CANSparkMax(ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);
    m_Left.setInverted(true);
    m_Right.setInverted(false);// could be the left one is supposed to be inverted, just putting this here for now
  //  m_Left.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
 //   m_Right.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
  //  m_Left.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
  //  m_Right.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
    
 
  }
public boolean TargetRpmReached(double LeftTargetRPM, double RightTargetRPM){

boolean Left =  m_Left.getEncoder().getVelocity() < LeftTargetRPM;
boolean Right =  m_Right.getEncoder().getVelocity() < RightTargetRPM;

  return Left&&Right;
}



//max rpm = 5676 free 
/**
 * 
 * @param RightTargetRPM target RPM for the right shooter motor
 * @param LeftTargetRPM Target RPM for the left shooter motor
 */
  public void Shoot(double RightTargetRPM,double LeftTargetRPM ){
   
   
    if( m_Left.getEncoder().getVelocity() < LeftTargetRPM){
      m_Left.set(1);
    } else{
      m_Left.set(0);
    }
    if( m_Right.getEncoder().getVelocity() < RightTargetRPM){
      m_Right.set(1);
    } else{
      m_Right.set(0);
    }
  }

  /** Stops the shooter motors */
  public void StopShoot(){

   m_Left.set(0);
    m_Right.set(0);
  }  


}
