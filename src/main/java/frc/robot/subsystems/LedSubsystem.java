// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.RainbowAnimation;
import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class LedSubsystem extends SubsystemBase {

  CANdle m_CaNdle = new CANdle(Constants.LedConstants.CandleID); 
  TimeOfFlight m_TOF = new TimeOfFlight(Constants.LedConstants.TOFid);
  RainbowAnimation m_rainbow = new RainbowAnimation(0.1, 0.1, Constants.LedConstants.TotalLedCount);
  /** Creates a new LedSubsystem. */
  public LedSubsystem() {
  m_TOF.setRangeOfInterest(0, 0, 0, 0); //TODO update this 
  }
  

  public void SetColor(int Red, int Green, int Blue, int White, int Start,int Count){// sets the leds to a specified color 
     
    m_CaNdle.setLEDs(Red, Green, Blue, White, Start, Count);
  }

  public void AnimateLEDs( Animation animation,int AnimationSlot){// animates the LEDs

    m_CaNdle.animate(animation, 0);
  }


  public void TestAnimation(){

    m_CaNdle.animate(m_rainbow);
  }


  public boolean TOFcheckTarget(){
   return m_TOF.getRange() < Constants.LedConstants.TOFmaxRange && m_TOF.getRange() > Constants.LedConstants.TOFminRange;
  }

  @Override
  public void periodic() {
  if (TOFcheckTarget()){SetColor(0, 255, 0, 0, 0, 8);}
  else{SetColor(255, 0, 0, 0, 0, 8);}
  }

}
