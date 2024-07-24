// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Team364.robot.Constants;
import frc.robot.Constants.RackAndPinionConstants;
public class RackAndPinionSubsystem extends SubsystemBase {

  private static TalonFX m_Left = new TalonFX(RackAndPinionConstants.LeftRAPmotorID); 
  private static TalonFX m_Right = new TalonFX(RackAndPinionConstants.RightRAPmotorID); 
  private double m_LeftStartingAngle;
  private LedSubsystem m_LED = new LedSubsystem();
  public double m_Position;
  public double[] m_StartingVector = new double[2];
  public double[] m_TargetVector = new double[2];
  public double [] m_MidVector = new double[2];
  public double m_CalculatedVoltage;

  public double m_RAPtarget;

  // they might be a different type of motor
   TalonFXConfiguration m_RAPConfigs = new TalonFXConfiguration();
  /** Creates a new RackAndPinionSubsystem. */
  public RackAndPinionSubsystem() {

  //TODO: add a limit switch so it can't try and go too far.
  m_Left.getConfigurator().apply(RackAndPinionConstants.RAPVoltageConfigs);
  m_Right.getConfigurator().apply(RackAndPinionConstants.RAPVoltageConfigs);
  m_Right.setControl(new Follower(13, true));
  m_Left.getConfigurator().apply(RackAndPinionConstants.RAPmotionMagicConfigs);
  m_Left.getConfigurator().apply(RackAndPinionConstants.RAPslot0Configs);
  m_Right.getConfigurator().apply(RackAndPinionConstants.RAPslot0Configs);
  m_Left.getConfigurator().apply(RackAndPinionConstants.RAPTalonFXConfiguration);

  }





  /** sets the position that the motors are currently at as the default, used as a reference angle
    */
  public void SetCurrentAngleAsDefault(){
     // TODO: this is in rotations, please fix this when we know whatever the conversion ends up being 
   m_LeftStartingAngle =  m_Left.getPosition().getValueAsDouble();

    SmartDashboard.putNumber("reference in rotations", m_LeftStartingAngle);
  }

/**
 * goes down at a speed, until it slows down because it hits something, setting it as the default angle
 * @param MinSpeed the speed that will stop the motor if it goes below it
 * @param TargetSpeed the initial speed to go 
 */
  public void GoToReference(double MinSpeed, double TargetSpeed){
    boolean m_StartCheck=false;
    
    if ( m_StartCheck ==false && m_Left.getVelocity().getValueAsDouble()< MinSpeed){ 
      m_Left.setControl(new VelocityDutyCycle(TargetSpeed));
      SmartDashboard.putBoolean("STARTCHECK", false);
    }
    if (m_Left.getVelocity().getValueAsDouble()>= MinSpeed && m_StartCheck==false){
      m_StartCheck=true;
      SmartDashboard.putBoolean("STARTCHECK", true);
    }
    if (m_StartCheck==true && m_Left.getVelocity().getValueAsDouble() <=MinSpeed){
      m_Left.setControl(new VelocityDutyCycle(0)); 
      SetCurrentAngleAsDefault();
      SmartDashboard.putNumber("testnumber", m_Left.getPosition().getValueAsDouble());
    }
  }

/**
 * 
 * @return the speed of the RAP
 */
public double GetRAPspeed(){
  return m_Left.getVelocity().getValueAsDouble();
}

/**
 * Gets the shooter angle
 * @return the current shooter angle
 */
  public double GetShooterAngle(){
    return (m_Left.getPosition().getValueAsDouble()-m_LeftStartingAngle);
  }

/**
 * 
 * @param Velocity the speed to set the motor
 */
  public void SetRAPspeed(double Velocity){ // TODO make it go down all the way, then set that as a reference
    m_Left.setControl(
      new VelocityDutyCycle(Velocity));
  }

  /**
   *  it goes to a position, but without motion magic
   * @param Pos rotations to go to. 
   */
  public void GoToPositonWithoutMotionMagic( double Pos){
    m_Left.setControl(new PositionDutyCycle(Pos));
  }

 public double getRAPreference(){
  return m_LeftStartingAngle;
 }

  /** sets shooter angle 
 * @param Rot position in rotations to set motor to
  */
  public void SetShooterAngle(double Rot){
      m_Left.setControl(new MotionMagicDutyCycle(Rot)); 
    SmartDashboard.putNumber("current target", Rot);
    m_RAPtarget = Rot;
  }
  
  public double[][] calculateVoltage(double finalAngle, double voltageDialation, double[][] combinedVectors) {
    m_Position = m_Left.getPosition().getValueAsDouble();
    m_StartingVector[0] = m_Position;
    m_StartingVector[1] = 0.5;
    m_MidVector[0] = (finalAngle-m_Position)/2;
    m_MidVector[1] = (12*voltageDialation);
    m_TargetVector[0] = finalAngle;
    m_TargetVector[1] = 0.5;
    combinedVectors[0][0] = m_StartingVector[0];
    combinedVectors[0][1] = m_StartingVector[1];
    combinedVectors[1][0] = m_MidVector[0];
    combinedVectors[1][1] = m_MidVector[1];
    combinedVectors[2][0] = m_TargetVector[0];
    combinedVectors[2][1] = m_TargetVector[1];
    //May consider removing the matrix in the future if it saves resources.
    
    return combinedVectors;
  }

  public void setVoltage(double Voltage) {
    m_Left.setVoltage(Voltage);
  }

  /**
   * stops the Rack and pinion motors
   */
  public void StopRAPmotors(){
    m_Left.stopMotor();
    m_Right.stopMotor();
  }


/**
 * checks if the RAP is moving, used in the shoot command so we don't shoot while its moving.
 * @return if the RAP is moving
 */
  public boolean isRAPmoving(){
  
  return (Math.abs(GetRAPspeed()) >RackAndPinionConstants.RAPMinSpeed);
  }




  @Override
  public void periodic() {
    SmartDashboard.putNumber("current position in rotations", m_Left.getPosition().getValueAsDouble());
    // This method will be called once per scheduler run
  }
}
