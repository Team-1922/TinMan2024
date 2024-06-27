// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PositionHandler;
import frc.robot.subsystems.Vision;
import frc.Team364.robot.subsystems.Swerve;

public class GetFieldPosition extends Command {
  /** Creates a new GetFieldPosition. */
  private Vision m_LL;
  private PositionHandler m_pos;
  private Swerve m_Swerve;
  private double mXLeft = -1.5; // Field position: -1.5         | Relative position: 0
  private double mYLeft = 196.17; // Field position: 196.17  | Relative position: -22.25
  private double mXRight = -1.5; // Field position: -1.5        | Relative position: 0
  private double mYRight = 218.42; // Field position: 218.42      | Relative position: 0
  private double mRobotX;
  private double mRobotY;
  private ArrayList<Double> data;
  private Timer m_Timer;
  private Pose2d currentPose;
  private Rotation2d targetRotation;
  Translation2d coolTranslation;
  
  public GetFieldPosition(PositionHandler pos, Vision limelight, Swerve zoom) {
    m_LL = limelight;
    m_pos = pos;
    m_Swerve = zoom;
    m_Timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);
    addRequirements(pos);
    addRequirements(zoom);

    final Pose2d idealPose = new Pose2d(new Translation2d(-1.5, 218.42), /* null */ new Rotation2d(0));
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Retrieve data, preferably with deferred execution
    //get robot x and y
    m_Timer.start();
    data = m_pos.returnData();
    double offset = m_Swerve.getGyroYaw().getRadians(); //May get more innacurate with time. Robot pose may be used instead
    double alpha = Math.cos(Math.toRadians(data.get(0))-offset);
    double beta = Math.cos(Math.toRadians(data.get(1))-offset);
    mRobotX = (mXRight*beta + mXLeft*alpha - mYRight + mYLeft)/(alpha + beta);
    mRobotY = (mYRight*alpha + mYLeft*beta - mXRight + mXLeft)/(alpha + beta);
    System.out.println(m_pos.getPose());
    coolTranslation = new Translation2d((mRobotX + 1.5), (mRobotY - 218.42)); //Weird translation; looking into this post-event
    targetRotation = new Rotation2d(-m_Swerve.getGyroYaw().getDegrees()+Math.PI/2);
    System.out.println(coolTranslation);
    m_Swerve.drive(coolTranslation.times(0.0254), (-Math.toRadians(data.get(1))), false, true);
  }
    // Teddy suggested having the shooting motors rev up here
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_Timer.hasElapsed(0.5));
  }
}
