// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.AutoCommands.AutoCollect;
import frc.robot.commands.AutoCommands.AutoShoot;
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
  private static Collector m_Collector = new Collector();
  private static ShooterSubsystem m_Shooter = new ShooterSubsystem();
  private static CollectNote m_Collect = new CollectNote(m_Collector);
  
  

  public Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }


  private final static AutoCollect Collect(){
    AutoCollect collect = new AutoCollect(m_Collector, m_Collect, 1);
  return collect;
  }

  private final static AutoShoot Shoot(){
AutoShoot shoot = new AutoShoot(m_Shooter, 7, 9, 3);
return shoot;
  }



  public  final SequentialCommandGroup TestAuto = new SequentialCommandGroup( Collect(), Shoot()  );

  
}
