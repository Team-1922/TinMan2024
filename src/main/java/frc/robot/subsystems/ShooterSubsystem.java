// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  // kp of motors is ~.05
  private static TalonFX m_Left = new TalonFX(ShooterConstants.kLeftShooterMotorID);
  private static TalonFX m_Right = new TalonFX(ShooterConstants.kRightShooterMotorID);

  LedSubsystem m_LedSubsystem = new LedSubsystem();
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
  m_Left.setNeutralMode(NeutralModeValue.Brake);
  m_Right.setNeutralMode(NeutralModeValue.Brake);
  
  VoltageConfigs m_VoltageConfigs = new VoltageConfigs();
    m_VoltageConfigs.PeakForwardVoltage = ShooterConstants.kShooterForwardVoltageLimit;
    m_VoltageConfigs.PeakReverseVoltage = ShooterConstants.kShooterReverseVoltageLimit;
  CurrentLimitsConfigs m_CurrentLimitsConfigs = new CurrentLimitsConfigs();
    m_CurrentLimitsConfigs.SupplyCurrentLimitEnable = true;
    m_CurrentLimitsConfigs.SupplyCurrentLimit = ShooterConstants.kCurrentLimit;
    m_CurrentLimitsConfigs.StatorCurrentLimitEnable = true;
    m_CurrentLimitsConfigs.StatorCurrentLimit = 60; 


  m_Left.getConfigurator().apply(m_CurrentLimitsConfigs);
  m_Right.getConfigurator().apply(m_CurrentLimitsConfigs);  
  m_Left.getConfigurator().apply(m_VoltageConfigs);
  m_Right.getConfigurator().apply(m_VoltageConfigs);
  m_Left.setInverted(true);
  m_Right.setInverted(false);



  Slot0Configs m_Slot0Configs = new Slot0Configs();
    m_Slot0Configs.kP = .5;    
 
m_Left.getConfigurator().apply(m_Slot0Configs);
m_Right.getConfigurator().apply(m_Slot0Configs);


  }
  /**
   * 
   * @param LeftTargetRPS
   * @param RightTargetRPS
   * 
   */
public boolean TargetRpmReached(double LeftTargetRPS, double RightTargetRPS){

boolean Left = (m_Left.getVelocity().getValueAsDouble()) >= (LeftTargetRPS);
boolean Right = (m_Right.getVelocity().getValueAsDouble()) >= (RightTargetRPS);
if(Left&&Right){
  SmartDashboard.putBoolean("Up to Speed", true);

}else{
  SmartDashboard.putBoolean("Up to Speed", false);

  
}

  return Left && Right;
}


/**
 * 
 * @param RightTargetRPM target RPM for the right shooter motor
 * @param LeftTargetRPM Target RPM for the left shooter motor
 */
  public void Shoot(double RightTargetRPM,double LeftTargetRPM ){

    m_Left.setControl(new VelocityDutyCycle(LeftTargetRPM));
    m_Right.setControl(new VelocityDutyCycle(LeftTargetRPM));  
  }

  /** Stops the shooter motors */
  public void StopShoot(){
    
  m_Left.set(0);
  m_Right.set(0);
  }  

 @Override
 public void periodic(){
  SmartDashboard.putNumber("left temp (C)", m_Left.getDeviceTemp().getValueAsDouble());
  SmartDashboard.putNumber("right temp (C)",m_Right.getDeviceTemp().getValueAsDouble());
  SmartDashboard.putNumber("left rps", m_Left.getVelocity().getValueAsDouble());
  SmartDashboard.putNumber( "Right rps", m_Right.getVelocity().getValueAsDouble());

 }

}