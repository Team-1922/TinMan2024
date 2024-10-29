// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {

    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;


  }
  
  
  public static class ShooterConstants {

    public static final double kCollectorActivateDelay = 0.25; // seconds
    public static final int kLeftShooterRPS = 5;
    public static final int kRightShooterRPS = 15; 
    public static final int kLeftShooterMotorID = 8;
    public static final int kRightShooterMotorID = 7;
    public static final double kRightTargetRPS = 3.4; // gets to ~8
    public static final double kLeftTargetRPS =1.2; // gets up to ~2
    public static final double AutoShootEndDelay = .3; // seconds 


    // CONFIGS 
    public static final double kShooterForwardTeleopVoltageLimit = 3; // volts
    public static final double kShooterReverseTeleopVoltageLimit = -3; // volts
    public static final double kShooterForwardAutoVoltageLimit = 5; // volts
    public static final double kShooterReverseAutoVoltageLimit = -5; // volts
    public static final double kCurrentLimit = 35; // amps 
    public static final double kOpenLoopRamp = 0.2; // seconds
    public static final double kClosedLoopRamp = 0.2; // seconds
    public static final double kCurrentDisableThreshold = 100; // amps (not used)
    
 
  }

  public final class CollectorConstants {

    public static final int kCollectorMotorID = 6;
    public static final int kCollectorSecondMotorID = 5;
    public static final double kRollerVoltage = 8.0; // unused volts
    public static final double kReverseRollerVoltage = 2.5;  // unused volts
    public static final double kReverseDuration = 0.5; // seconds
    public static final double kRollerSecondVoltage = 6; // only used in collect command so note doesn't go out of the robot
    public static final double kCollectRPM = 950; //(700/600)*2048;
    public static final double kSecondCollectRPM = 450; //(600/600)*2048;
    public static final double kReverseCollectRPM = 525;//(600/600)*2048; // x/600*2048
    public static final double kShootRPM = 1000;
    
    // CONFIGS
    public static final double kOpenLoopRamp = 0.2; // seconds
    public static final double kClosedLoopRamp = 0.2; // seconds
    public static final double kCurrentSoftLimit = 40; // amps
    public static final double kCurrentHardLimit = 45; // amps
    public static final boolean kCurrentLimitEnable = true; 
    public static final double kCurrentLimitTime = 0.1; // seconds
    
  }
  
  public final class ClimberConstants {

    public static final int ClimberMotorID = 9;
    public static final int ClimberMotorID2 = 10;
    public static final double kClimbVoltage = 10; 

  }

  public static class TofConstants{

    public static final int TofRangeOfIntrest = 8; // don't change this 
    public static final int Tofid = 0; 
    public static final int TofminRange = 0; // mm
    public static final int TofmaxRange = 425; // mm

  }

  public static class LedConstants {

    public static final int kCandleID = 10; 
    public static final int kTotalLedCount = 98; //8 on candle + ~90 on strips
  }

}
