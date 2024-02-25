// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.Team364.robot.commands.SwerveCommand;
import frc.Team364.robot.subsystems.PoseEstimator;
import frc.Team364.robot.subsystems.Swerve;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoCommands.AutoCollect;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(ExampleSubsystem subsystem) {
    return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
  }
  private final PoseEstimator s_PoseEstimator = new PoseEstimator();
  //private Swerve s_Swerve = new Swerve(s_PoseEstimator);
  private static Collector m_Collector = new Collector();
 
  private static CollectNote m_Collect = new CollectNote(m_Collector);
  private static AutoCollect m_AutoCollect = new AutoCollect(m_Collector, m_Collect, 0.2);
 // \private static Shoot m_Shoot = new Shoot(m_Shooter, m_Collector, true, 3); // this line causes code to crash



  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }


  private final AutoCollect Collect(){
    AutoCollect collect = new AutoCollect(m_Collector, m_Collect, .2);
  return collect;
  }


 // public static final SequentialCommandGroup Shoot = new SequentialCommandGroup(m_Shoot);
}
