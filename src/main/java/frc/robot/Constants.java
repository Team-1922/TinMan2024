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
  }
  
  public static class ShooterConstants {
    public static final int LeftShooterMotorID = 7;
    public static final int RightShooterMotorID = 8;
// voltage for shooting is set in the shoot command, incase we want to change voltage for shooting at different locations
  }
  public final class MotorConstants {
    public static final int kCollectorMotorID = 6;
    public static final int kCollectorSecondMotorID = 5;

  
    public static final double kRollerVoltage = 8.0;
  }

  public static class LedConstants {
    public static final int TOFid= 1; 
    public static final int TOFminRange = 50; // TODO test this
    public static final int TOFmaxRange = 300; // TODO test this
    public static final int CandleID = 10 ; 
    
    public static final int TotalLedCount = 50; //TODO update number of leds

  }

}
