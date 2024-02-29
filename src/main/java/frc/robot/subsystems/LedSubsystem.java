// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.RainbowAnimation;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LedConstants;

public class LedSubsystem extends SubsystemBase {

  CANdle m_CaNdle = new CANdle(LedConstants.kCandleID); 
  
  RainbowAnimation m_rainbow = new RainbowAnimation(1, 1, LedConstants.kTotalLedCount,false,8);

  /** Creates a new LedSubsystem. */
  public LedSubsystem() {
 
  }
  
/**
 * 
 * @param Red 0-255
 * @param Green 0-255
 * @param Blue 0-255
 * @param White 0-255
 * @param Start offset from first led (do 8 to ignore CANdle)
 * @param Count number of leds
 */
  public void SetColor(int Red, int Green, int Blue, int White, int Start,int Count){// sets the leds to a specified color 
     
    m_CaNdle.setLEDs(Red, Green, Blue, White, Start, Count);
  }

  /**
   * 
   * @param animation
   * @param AnimationSlot
   */
  public void AnimateLEDs( Animation animation,int AnimationSlot){// animates the LEDs

    m_CaNdle.animate(animation, 0);
    
  }

/** runs a rainbow animation for testing reasons */
  public void TestAnimation(){

    m_CaNdle.animate(m_rainbow);
  }

  public void DisabledAnimation(){
    m_CaNdle.animate(m_rainbow);
  }



/*  _________________
 *  |               |
 *  |   ?      ?    |
 *  |       <       |
*   |               |
 *  |    \_____/    |
 *  |               |
 *  |_______________|
 * 
 */
}
