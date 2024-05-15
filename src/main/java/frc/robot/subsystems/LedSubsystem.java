// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.LedConstants;

public class LedSubsystem extends SubsystemBase {

  CANdle m_CaNdle = new CANdle(LedConstants.kCandleID); 
  private Collector m_Collector;
  private ShooterSubsystem m_shooter;

    ColorFlowAnimation m_ColorFlowAnimation = new ColorFlowAnimation(255, 255, 0, 0, .5, 98, Direction.Forward);

  
  /** Creates a new LedSubsystem. */
  public LedSubsystem(Collector collector, ShooterSubsystem shooter){
    m_Collector = collector;
    m_shooter = shooter;
  }
  
/**
 * sets the color of the LEDS, also clears the animation in slot 0
 * @param Red [0-255]
 * @param Green [0-255] 
 * @param Blue [0-255]
 * @param White [0-255] only works if the LED strip supports it
 * @param Start offset from first led (do 8 to ignore CANdle)
 * @param Count number of LEDs
 */
  public void SetColor(int Red, int Green, int Blue, int White, int Start,int Count){
    m_CaNdle.clearAnimation(0);
    m_CaNdle.setLEDs(Red, Green, Blue, White, Start, Count);
    
  }



  /**
   * @param animation the animation to run
   * @param AnimationSlot what slot it to put it in (do 0 unless running multiple at once)
   */
  public void AnimateLEDs( Animation animation,int AnimationSlot){// animates the LEDs

    m_CaNdle.animate(animation, AnimationSlot);    
  }

  public void clearAnimation(int AnimationSlot){
    m_CaNdle.clearAnimation(AnimationSlot);
  }

  @Override 
  public void periodic() {
    boolean InTarget = m_Collector.TofcheckTarget();
        SmartDashboard.putBoolean("Has Note?", InTarget);
        if (InTarget) {
            if (m_shooter.TargetRpmReached(Constants.ShooterConstants.kLeftTargetRPS,
                    Constants.ShooterConstants.kRightTargetRPS)) {
                SetColor(0, 255, 0, 0, 0, 96); // green
            } else {
                SetColor(255, 255, 255, 255, 0, 96); // white
            }
        } else {

            if (RobotController.isSysActive()) {
                if (m_shooter.TargetRpmReached(Constants.ShooterConstants.kLeftTargetRPS,
                        Constants.ShooterConstants.kRightTargetRPS)) {
                    SetColor(255, 165, 0, 0, 0, 98); // orange
                } else {
                    SetColor(255, 0, 0, 0, 0, 98); // red
                }
            } else {
                AnimateLEDs(m_ColorFlowAnimation, 0);
            }
        }
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
