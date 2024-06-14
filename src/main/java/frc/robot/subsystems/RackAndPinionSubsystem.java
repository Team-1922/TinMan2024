// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Team364.robot.Constants;
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


  public void GoToReference(){
    
  


  }



/**
 * Gets the shooter angle
 * @return the current shooter angle
 */
  public double GetShooterAngle(){

  double Angle1 =  m_Left.getPosition().getValueAsDouble();

    return 
 Angle1;
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

    if( !(Deg > RackAndPinionConstants.RAPmaxAngle) || !(Deg < RackAndPinionConstants.RAPminAngle) ){
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
