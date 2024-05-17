// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RackAndPinionConstants;
public class RackAndPinionSubsystem extends SubsystemBase {

  private static TalonFX m_Left = new TalonFX(RackAndPinionConstants.LeftRAPmotorID); 
  private static TalonFX m_Right = new TalonFX(RackAndPinionConstants.RightRAPmotorID); 
  private double m_LeftStartingAngle;
  private double m_RightStartingAngle; 
  // they might be a different type of motor
   TalonFXConfiguration m_RAPConfigs = new TalonFXConfiguration();
  /** Creates a new RackAndPinionSubsystem. */
  public RackAndPinionSubsystem() {

  m_RAPConfigs.Voltage.PeakForwardVoltage = RackAndPinionConstants.kVoltageLimit;
  m_RAPConfigs.Voltage.PeakReverseVoltage = RackAndPinionConstants.kVoltageLimit;
  //TODO: add a limit switch so it can't try and go too far. 
  m_Left.getConfigurator().apply(m_RAPConfigs);
  m_Right.getConfigurator().apply(m_RAPConfigs);

  
  }

  /** sets the position that the motors are currently at as the default, used as a reference angle  */
  public void SetAngleAsDefault(){
     // TODO: this is in rotations, please fix this when we know whatever the conversion ends up being 
   m_LeftStartingAngle =  m_Left.getPosition().getValueAsDouble();
   m_RightStartingAngle = m_Right.getPosition().getValueAsDouble(); 

  }

  public double GetShooterAngle(){

    m_Left.getPosition();
    m_Right.getPosition();
    return 0.0; // update this later
  
  }



  /** sets shooter angle 
 * @param Deg the angle in degrees to set the shooter to
  */
  public void SetShooterAngle(double Deg){

    if( Deg > RackAndPinionConstants.RAPmaxAngle || Deg < RackAndPinionConstants.RAPminAngle ){
      throw new Error("The angle you are attempting to go to is outside the possible range!");
    }
    else {
      // put stuff here
    }
    
  } 



  public void StopRAPmotors(){
    m_Left.stopMotor();
    m_Right.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
