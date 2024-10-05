// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.Team364.robot.subsystems.PoseEstimator;
import frc.Team364.robot.subsystems.Swerve;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ShooterSubsystem;

import com.choreo.lib.Choreo;
import com.choreo.lib.ChoreoTrajectory;

public final class Autos  {
  private static final PoseEstimator m_PoseEstimator = new PoseEstimator();
  private static final Swerve s_Swerve  = new Swerve(m_PoseEstimator);
  private static final Collector m_collector = new Collector();
  private static final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();



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

  public static shootStart AutoShootStart(){
   shootStart m_ShootStart = new shootStart(m_shooterSubsystem, 1.2);
    return m_ShootStart;
  } 

  public static AutoShoot m_autoShoot(){
    AutoShoot m_shoot = new AutoShoot(m_collector, true, 5);
    return m_shoot;
  }

  public static CollectNoteAuto Collect(){
    CollectNoteAuto m_Collect = new CollectNoteAuto(m_collector);
    return m_Collect;
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

  public static final SequentialCommandGroup m_ShootTest = new SequentialCommandGroup(AutoShootStart(),new WaitCommand(1.25), m_autoShoot());
 
  public static final ParallelCommandGroup m_2Piece = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(new WaitCommand(1.25),m_autoShoot(),
      new ParallelCommandGroup(Collect(),
        new SequentialCommandGroup(ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")), ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),m_autoShoot()));


/* for the auto names
 * C = center note
 * A = amp note
 * S = stage note
 * Fc = a note in the center of the field
 */



  public static final ParallelCommandGroup m_4PieceCAS = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot()  
        ));      

  public static final ParallelCommandGroup m_4PieceCSA = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot()  
        ));        

  public static final ParallelCommandGroup m_4PieceACS = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot()  
        ));    

  public static final ParallelCommandGroup m_4PieceSCA = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot()  
        ));

  public static final ParallelCommandGroup m_3PieceCA = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot()
      ));

  public static final ParallelCommandGroup m_3PieceCS = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot()
        )); 

  public static final ParallelCommandGroup m_5PieceCASFc = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot(),  
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center field note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center field note")))),
      m_autoShoot()  
        ));  
              
 public static final ParallelCommandGroup m_4PieceCAFc = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from amp note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center field note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center field note")))),
      m_autoShoot()  
        ));

 public static final ParallelCommandGroup m_4PieceCSFc = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to stage note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from stage note")))),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center to center field note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("center from center field note")))),
      m_autoShoot()  
        ));                       
 
 public static final ParallelCommandGroup m_Amp2Piece = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("Amp side to amp note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("Amp side from amp note")))),
      m_autoShoot()
        ));
      
 public static final ParallelCommandGroup m_Stage2Piece = 
  new ParallelCommandGroup(m_autoShoot(),
    new SequentialCommandGroup(
      new WaitCommand(1.25),
      m_autoShoot(),
      new ParallelCommandGroup(
        Collect(),
        new SequentialCommandGroup(
          ChoreoTrajectoryCommand(Choreo.getTrajectory("stage side to center field note")),
          ChoreoTrajectoryCommand(Choreo.getTrajectory("stage side from center field note")))),
      m_autoShoot()
        ));
        
}
