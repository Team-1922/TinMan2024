// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.CoastOut;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  
 // CANSparkMax m_Left = new CANSparkMax(ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless);
 // CANSparkMax m_Right = new CANSparkMax(ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);

  private static TalonFX m_Left = new TalonFX(ShooterConstants.kLeftShooterMotorID);
  private static TalonFX m_Right = new TalonFX(ShooterConstants.kRightShooterMotorID);

  LedSubsystem m_LedSubsystem = new LedSubsystem();
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
  
  VoltageConfigs m_VoltageConfigs = new VoltageConfigs();
  m_VoltageConfigs.PeakForwardVoltage = ShooterConstants.kShooterForwardVoltageLimit;
  m_VoltageConfigs.PeakReverseVoltage = ShooterConstants.kShooterReverseVoltageLimit;

  m_Left.getConfigurator().apply(m_VoltageConfigs);

    m_Left.setInverted(true);
    m_Right.setInverted(false);// could be the left one is supposed to be inverted, just putting this here for now
  //  m_Left.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
 //   m_Right.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
  //  m_Left.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
  //  m_Right.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
    
 
  }
  /**
   * 
   * @param LeftTargetRPM 
   * @param RightTargetRPM
   * @return
   */
public boolean TargetRpmReached(double LeftTargetRPM, double RightTargetRPM){

boolean Left = (m_Left.getVelocity().getValueAsDouble()) >= (LeftTargetRPM/60);// divided by 60 to convert units
boolean Right = (m_Right.getVelocity().getValueAsDouble()) >= (RightTargetRPM/60);
if(Left&&Right){
  SmartDashboard.putBoolean("Up to Speed", true);
  m_LedSubsystem.SetColor(0, 255, 0, 255, 9, 24);
}else{
  SmartDashboard.putBoolean("Up to Speed", false);
  m_LedSubsystem.SetColor(0, 0, 0, 0, 9, 24);
  
}

  return Left && Right;
}

/** this has not been tested */
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
  

    m_Left.setControl(new VelocityDutyCycle(LeftTargetRPM/60));
    m_Right.setControl(new VelocityDutyCycle(LeftTargetRPM/60));
   /* 
    if( m_Left.getVelocity().getValueAsDouble() < LeftTargetRPM/60){
      m_Left.set(1);
      m_Left.setControl(new VelocityDutyCycle(LeftTargetRPM/60));
    } else{
     m_Left.set(.6);
    }

    if( m_Right.getVelocity().getValueAsDouble() < (RightTargetRPM/60)){
      m_Right.set(.5);
    } else{
      m_Right.set(.33);
    }*/
  }

  /** Stops the shooter motors */
  public void StopShoot(){

    m_Left.setControl(new CoastOut());
    m_Right.setControl(new CoastOut());
 


  }  

 @Override
 public void periodic(){
  
 SmartDashboard.putNumber("left rpm", m_Left.getVelocity().getValueAsDouble()*60);
SmartDashboard.putNumber( "Right rpm", m_Right.getVelocity().getValueAsDouble()*60);
//  SmartDashboard.putNumber("left shooter motor temperature", m_Left.getMotorTemperature());
 // SmartDashboard.putNumber(" right shooter motor temperature", m_Right.getMotorTemperature());
 }

}