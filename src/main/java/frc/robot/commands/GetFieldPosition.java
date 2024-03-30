// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;

public class GetFieldPosition extends Command {
  /** Creates a new GetFieldPosition. */
  private Limelight m_LL;
  private double mXLeft = 0; //Field position: -1.5
  private double mYLeft = -22.25; //Field position: 196.17
  private double mXRight = 0; //Field position: -1.5
  private double mYRight = 0; //Field position: 218.42
  private double slot0 = 0;
  private double slot1 = 1;

  public GetFieldPosition(Limelight limelight) {
    m_LL = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Retrieve data, preferably with deferred execution
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //get robot x and y
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //generate a pose maybe? This may depend on how easy it'll be to access the swerve subsystem from here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
