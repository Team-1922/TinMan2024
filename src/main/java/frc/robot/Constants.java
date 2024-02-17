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

    public static final double kCollectorActivateDelay = 0.1;
    public static final int kLeftShooterVoltage = 9;
    public static final int kRightShooterVoltage = 7;
    public static final int kLeftShooterMotorID = 7;
    public static final int kRightShooterMotorID = 8;
// voltage for shooting is set in the shoot command, incase we want to change voltage for shooting at different locations
  }
  public final class CollectorConstants {

    public static final int kCollectorMotorID = 6;
    public static final int kCollectorSecondMotorID = 5;  
    public static final double kRollerVoltage = 8.0;
    public static final double kReverseRollerVoltage = 2; 
    public static final double kReverseDuration = 0.5;


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
