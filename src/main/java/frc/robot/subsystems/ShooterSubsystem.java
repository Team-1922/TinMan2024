// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

  CANSparkMax m_Left = new CANSparkMax(Constants.ShooterConstants.kLeftShooterMotorID, MotorType.kBrushless);
  CANSparkMax m_Right = new CANSparkMax(Constants.ShooterConstants.kRightShooterMotorID, MotorType.kBrushless);

 
 
  
  /** Creates a new SshooterSsubsystem. */
  public ShooterSubsystem() {
    m_Left.setInverted(true);
    m_Right.setInverted(false);// could be the left one is supposed to be inverted, just putting this here for now
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
