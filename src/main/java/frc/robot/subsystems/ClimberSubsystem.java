// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
CANSparkMax m_ClimberMotor1 = new CANSparkMax(9, MotorType.kBrushless);
CANSparkMax m_ClimberMotor2 = new CANSparkMax(10,MotorType.kBrushless);
  
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {

m_ClimberMotor1.setIdleMode(IdleMode.kBrake);
m_ClimberMotor2.setIdleMode(IdleMode.kBrake);

  }

/**
 * @param Voltage how many volts motors will output
 * @param Reversed 
 */
public void Climb(double Voltage, boolean Reversed){
  m_ClimberMotor2.setInverted(Reversed);  
  m_ClimberMotor1.setInverted(Reversed);
    m_ClimberMotor1.setVoltage(Voltage);
    m_ClimberMotor2.setVoltage(Voltage);

}

/** Stops Climber motors */
public void StopClimber(){
  m_ClimberMotor1.stopMotor();
  m_ClimberMotor2.stopMotor();
}




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}