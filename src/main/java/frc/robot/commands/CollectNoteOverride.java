// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Collector;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class CollectNoteOverride extends Command {
 
  Collector m_Collector;



  double m_ReverseDuration = Constants.CollectorConstants.kReverseDuration;

 /** Creates a new CollectNote. */
  public CollectNoteOverride(Collector Collector) {

    m_Collector = Collector;
    addRequirements(Collector);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

}
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
 m_Collector.ActivateMotor(Constants.CollectorConstants.kCollectRPM);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

   m_Collector.StopMotor();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return  false;
  }
}
