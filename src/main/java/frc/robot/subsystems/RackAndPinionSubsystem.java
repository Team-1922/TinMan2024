// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.RackAndPinionConstants;
public class RackAndPinionSubsystem extends SubsystemBase {

  private static TalonFX m_Left = new TalonFX(RackAndPinionConstants.LeftRAPmotorID); 
  private static TalonFX m_Right = new TalonFX(RackAndPinionConstants.RightRAPmotorID); 
  private double m_LeftStartingAngle;
  private LedSubsystem m_LED = new LedSubsystem();


  // they might be a different type of motor
   TalonFXConfiguration m_RAPConfigs = new TalonFXConfiguration();
  /** Creates a new RackAndPinionSubsystem. */
  public RackAndPinionSubsystem() {

  //TODO: add a limit switch so it can't try and go too far.
  m_Left.getConfigurator().apply(RackAndPinionConstants.RAPVoltageConfigs);
  m_Right.getConfigurator().apply(RackAndPinionConstants.RAPVoltageConfigs);
  m_Right.setControl(new Follower(13, true));
  
  }



  

  /** sets the position that the motors are currently at as the default, used as a reference angle  */
  public void SetCurrentAngleAsDefault(){
     // TODO: this is in rotations, please fix this when we know whatever the conversion ends up being 
   m_LeftStartingAngle =  m_Left.getPosition().getValueAsDouble();


  }

/**
 * goes down at a speed, until it slows down because it hits something, setting it as the default angle
 * @param MinSpeed the speed that will stop the motor if it goes below it
 * @param TargetSpeed the initial speed to go 
 */
  public void GoToReference(double MinSpeed, double TargetSpeed){
    boolean m_StartCheck=false;
    
    if (m_Left.getVelocity().getValueAsDouble() <= TargetSpeed && m_StartCheck ==false){ 
      m_Left.setControl(new VelocityVoltage(TargetSpeed));
      m_LED.SetColor(255, 0, 0, 0, 0, 8);
    }
    if (m_Left.getVelocity().getValueAsDouble()>= TargetSpeed){
      m_StartCheck=true;
      m_LED.SetColor(255, 255, 0, 0, 0, 8);
    }
    if (m_StartCheck==true && m_Left.getVelocity().getValueAsDouble() <=MinSpeed){
      m_Left.setControl(new VelocityVoltage(0)); 
      SetCurrentAngleAsDefault();
      m_LED.SetColor(0, 255, 0, 0, 0, 8);
    }
  }



/**
 * Gets the shooter angle
 * @return the current shooter angle
 */
  public double GetShooterAngle(){

    double Angle1 =  m_Left.getPosition().getValueAsDouble()-m_LeftStartingAngle;

    return Angle1;
  //TODO: update this later, this is a placeholder
  
  }

/**
 * 
 * @param Velocity the speed to set the motor
 */
  public void SetRAPspeed(double Velocity){ // TODO make it go down all the way, then set that as a reference

    m_Left.setControl(new VelocityVoltage(Velocity));
  }
  



  /** sets shooter angle 
 * @param Deg the angle in degrees to set the shooter to, might need a conversion factor later
  */
  public void SetShooterAngle(double Deg){

    if( (Deg < RackAndPinionConstants.RAPmaxAngle) || (Deg > RackAndPinionConstants.RAPminAngle) ){
      m_Left.setControl(new PositionVoltage(Deg)); //TODO: make sure this won't go backwards 
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
