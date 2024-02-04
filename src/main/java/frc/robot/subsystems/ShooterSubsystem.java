// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
public class ShooterSubsystem extends SubsystemBase {

  CANSparkMax m_Left = new CANSparkMax(Constants.ShooterConstants.LeftShooterMotorID, MotorType.kBrushless);
  CANSparkMax m_Right = new CANSparkMax(Constants.ShooterConstants.RightShooterMotorID, MotorType.kBrushless);
 // TalonFX m_Left = new TalonFX(Constants.ShooterConstants.LeftShooterMotorID);
//  TalonFX m_Right = new TalonFX(Constants.ShooterConstants.RightShooterMotorID);
 
  
  /** Creates a new SshooterSsubsystem. */
  public ShooterSubsystem() {
    m_Left.setInverted(false);
    m_Right.setInverted(true);// could be the left one is supposed to be inverted, just putting this here for now
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public void Shoot(double leftVoltage, double rightVoltage ){
    m_Left.setVoltage(leftVoltage); // voltage
    m_Right.setVoltage(rightVoltage);
    

  }

  public void StopShoot(){// just in case they dont stop

    m_Left.setVoltage(0);
    m_Right.setVoltage(0);
  }  


}
