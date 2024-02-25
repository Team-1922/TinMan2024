// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfigurator;

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
    public static final int kOperatorControllerPort = 0;

    public static final double AngleSlewRate = 1.5;
    public static final double DriveSlewRate = 2;
    public static final double StraifeSlewRate = 2;
  }
  
  
  public static class ShooterConstants {

    public static final double kCollectorActivateDelay = 0.25;
    public static final int kLeftShooterRPM = 2000;
    public static final int kRightShooterRPM = 5000; // 5676 is max
    public static final int kLeftShooterMotorID = 8;
    public static final int kRightShooterMotorID = 7;


    // CONFIGS 
    public static final double kLeftCurrentLimit = 40; // amps (not used)
    public static final double kRightCurrentLimit = 40; // amps (not used)
    public static final double kOpenLoopRamp = 0.2; // seconds
    public static final double kClosedLoopRamp = 0.2; // seconds
    public static final double kCurrentDisableThreshold = 100; // amps (not used)
    
    
  }

  public final class CollectorConstants {

    public static final int kCollectorMotorID = 6;
    public static final int kCollectorSecondMotorID = 5;
    public static final double kRollerVoltage = 8.0;
    public static final double kReverseRollerVoltage = 2.5; 
    public static final double kReverseDuration = 0.5;
    public static final double kRollerSecondVoltage = 6; // only used in collect command so note doesn't go out of the robot
    public static final double kCollectRPM = (1000/600)*2048;
    public static final double kSecondCollectRPM = (1000/600)*2048;
    public static final double kReverseCollectRPM = (1000/600)*2048;
    // CONFIGS
    public static final double kOpenLoopRamp = 0.2; // seconds
    public static final double kClosedLoopRamp = 0.2; // seconds
    public static final double kCurrentSoftLimit = 40; // amps
    public static final double kCurrentHardLimit = 45; // amps
    public static final boolean kCurrentLimitEnable = true; 
    public static final double kCurrentLimitTime = 0.1;
  }
  
  public final class ClimberConstants {
    public static final int ClimberMotorID = 9;
    public static final int ClimberMotorID2 = 10;
    public static final double kClimbVoltage = 0.1; //TODO test this

  }

  public static class TofConstants{

    public static final int TofRangeOfIntrest = 8;
    public static final int Tofid = 1; 
    public static final int TofminRange = 50; // TODO test this
    public static final int TofmaxRange = 300; // TODO test this

  }

  public static class LedConstants {
    public static final int kCandleID = 10 ; 
    public static final int kTotalLedCount = 8; //TODO update number of leds
  }

}
