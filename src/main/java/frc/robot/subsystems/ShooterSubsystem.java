// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  //  StrobeAnimation m_StrobeAnimation = new StrobeAnimation(0, 255, 255, 255, 0,Constants.LedConstants.kTotalLedCount,0) ;
  CANSparkMax m_Left = new CANSparkMax(ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless);
  CANSparkMax m_Right = new CANSparkMax(ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);
  RelativeEncoder m_LeftEncoder = m_Left.getEncoder();
  RelativeEncoder m_RightEncoder = m_Right.getEncoder();
  LedSubsystem m_LedSubsystem = new LedSubsystem();
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    
    m_Left.setIdleMode(IdleMode.kCoast);
    m_Right.setIdleMode(IdleMode.kCoast);
    m_Left.setSmartCurrentLimit(80);
    m_Right.setSmartCurrentLimit(80);
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

boolean Left =  m_LeftEncoder.getVelocity() >= (LeftTargetRPM*0.85);
boolean Right =  m_RightEncoder.getVelocity() >= (RightTargetRPM*0.85);
if(Left&&Right){
  SmartDashboard.putBoolean("Up to Speed", true);
  m_LedSubsystem.SetColor(0, 255, 0, 255, 9, 24);
}else{
  SmartDashboard.putBoolean("Up to Speed", false);
  m_LedSubsystem.SetColor(0, 0, 0, 0, 9, 24);
  
}

  return Left && Right;
}

public void AmpShoot(double RightTargetRPM, double LeftTargetRPM){
m_Left.set(LeftTargetRPM/5676);
m_Right.set(RightTargetRPM/5676);

}

//max rpm = 5676 free 
/**
 * 
 * @param RightTargetRPM target RPM for the right shooter motor
 * @param LeftTargetRPM Target RPM for the left shooter motor
 */
  public void Shoot(double RightTargetRPM,double LeftTargetRPM ){
   
   
    if( m_LeftEncoder.getVelocity() < LeftTargetRPM){
      m_Left.set(1);
    } else{
     m_Left.set(.6);
    }

    if( m_RightEncoder.getVelocity() < RightTargetRPM){
      m_Right.set(.33);
    } else{
      m_Right.set(.26);
    }
  }

  /** Stops the shooter motors */
  public void StopShoot(){

   m_Left.set(0);
    m_Right.set(0);


  }  

 @Override
 public void periodic(){
  
 SmartDashboard.putNumber("left rpm", m_LeftEncoder.getVelocity());
SmartDashboard.putNumber( "Right rpm", m_RightEncoder.getVelocity());
  SmartDashboard.putNumber("left shooter motor temperature", m_Left.getMotorTemperature());
  SmartDashboard.putNumber(" right shooter motor temperature", m_Right.getMotorTemperature());
 }

}