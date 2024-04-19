// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PositionHandler;
import frc.robot.subsystems.Vision;
import frc.Team364.robot.subsystems.Swerve;

public class GetFieldPosition extends Command {
  /** Creates a new GetFieldPosition. */
  private Vision m_LL;
  private PositionHandler m_pos;
  private Swerve m_Swerve;
  private double mXLeft = 0; //Field position: -1.5
  private double mYLeft = -22.25; //Field position: 196.17
  private double mXRight = 0; //Field position: -1.5
  private double mYRight = 0; //Field position: 218.42
  private double mRobotX;
  private double mRobotY;
  private ArrayList<Double> data;
  Translation2d coolTranslation;

  public GetFieldPosition(PositionHandler pos, Vision limelight, Swerve zoom) {
    m_LL = limelight;
    m_pos = pos;
    m_Swerve = zoom;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
    addRequirements(pos);
    addRequirements(zoom);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Retrieve data, preferably with deferred execution
    data = m_pos.returnData();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //get robot x and y
    double alpha = m_LL.cot(data.get(0));
    double beta = m_LL.cot(data.get(1));
    mRobotX = (mXRight*beta + mXLeft*alpha - mYRight + mYLeft)/(alpha + beta);
    mRobotY = (mYRight*alpha + mYLeft*beta - mXRight + mXLeft)/(alpha + beta);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println(mRobotX);
    System.out.println(mRobotY);
    //Robot is not getting values from the limelight
    coolTranslation = new Translation2d(-mRobotX + 0.1, -mRobotY + 0.1);
    m_Swerve.drive(coolTranslation, 0 /* May or may not actually be zero */, false, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
