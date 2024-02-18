// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  
  CANSparkMax m_Left = new CANSparkMax(ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless);
  CANSparkMax m_Right = new CANSparkMax(ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);

  

  
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    
    m_Left.setInverted(true);
    m_Right.setInverted(false);// could be the left one is supposed to be inverted, just putting this here for now
    m_Left.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
    m_Right.setOpenLoopRampRate(ShooterConstants.kOpenLoopRamp);
    m_Left.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
    m_Right.setClosedLoopRampRate(ShooterConstants.kClosedLoopRamp);
    
 
  }



/**
 * 
 * @param leftVoltage Voltage to set left shooter motor to.
 * @param rightVoltage Voltage to set the right shooter motor to.
 */
  public void Shoot(double leftVoltage, double rightVoltage ){
   
    m_Left.setVoltage(leftVoltage);
    m_Right.setVoltage(rightVoltage);
  }

  /** Stops the shooter motors */
  public void StopShoot(){

   m_Left.setVoltage(0);
    m_Right.setVoltage(0);
  }  


}
