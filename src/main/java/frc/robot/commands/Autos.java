// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.Team364.robot.subsystems.PoseEstimator;
import frc.Team364.robot.subsystems.Swerve;
import frc.robot.RobotContainer;
import com.choreo.lib.Choreo;
import com.choreo.lib.ChoreoTrajectory;

public final class Autos  {
  private static final PoseEstimator m_PoseEstimator = new PoseEstimator();
  private static final Swerve s_Swerve  = new Swerve(m_PoseEstimator);

// this should run a single trajectory the pids will probably have to be tuned
public static Command ChoreoTrajectoryCommand( ChoreoTrajectory traj){
    var thetaController = new PIDController(0, 0, 0);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);    
    s_Swerve.resetOdometry(traj.getInitialPose());
    Command SwerveCommand = Choreo.choreoSwerveCommand(
    traj,
    s_Swerve::getPose,
    new PIDController(0, 0, 0),
    new PIDController(0, 0, 0),
    thetaController,
    (ChassisSpeeds Speeds) -> s_Swerve.drive(
    new Translation2d(Speeds.vxMetersPerSecond, Speeds.vyMetersPerSecond),
    Speeds.omegaRadiansPerSecond,
    true,
    true
    ),
    () -> true,
    s_Swerve
    );
   return SwerveCommand;
  }



  /** Creates a new Autos. */
  public Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
    // Use addRequirements() here to declare subsystem dependencies.
  }


// this should, if it works, make the robot go forwards, then backwards
  public static final SequentialCommandGroup m_TrajectoryTest = 
  new SequentialCommandGroup( 
    ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")), 
    ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")));

  // Returns true when the command should end.

  
}
