// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.Team364.robot.commands.SwerveCommand;
import frc.Team364.robot.subsystems.PoseEstimator;
import frc.Team364.robot.subsystems.Swerve;
import frc.robot.Constants.OperatorConstants;

import frc.robot.commands.Climb;
import frc.robot.commands.CollectNote;
import frc.robot.commands.CollectNoteAuto;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Rumble;
import frc.robot.commands.Shoot;
import frc.robot.commands.TorqueLimitClimb;
import frc.robot.commands.StopCollector_shooter;
import frc.robot.commands.shootStart;
import frc.robot.commands.AutoCollectCheck;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutoShootNoteCheck;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CollectReverse;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import com.choreo.lib.Choreo;
import com.choreo.lib.ChoreoTrajectory;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();  
  private final Collector m_Collector = new Collector();
  private final CollectNote m_CollectNote = new CollectNote(m_Collector);
  private final CollectNote m_CollectNote2 = new CollectNote(m_Collector);
  private final Shoot m_shoot = new Shoot(m_shooterSubsystem, m_Collector, false,1 );
  private final AutoCollectCheck m_AutoCollectCheck = new AutoCollectCheck();
  private final CollectNoteAuto m_CollectNoteAuto = new CollectNoteAuto(m_Collector);
  private final CollectReverse m_CollectReverse = new CollectReverse(m_Collector);
  private final ClimberSubsystem m_ClimberSubsystem = new ClimberSubsystem(); 
  private final XboxController m_Controller = new XboxController(1);
  private final Rumble m_Rumble = new Rumble();
  private final CommandXboxController m_operatorController = new CommandXboxController(Constants.OperatorConstants.kOperatorControllerPort);
  private final TorqueLimitClimb m_TClimb = new TorqueLimitClimb(m_ClimberSubsystem, m_operatorController, 5, 0.2,6);
  private final Climb m_Climb = new Climb(m_ClimberSubsystem, m_operatorController, 1, 5, .2);
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final AutoShootNoteCheck m_AutoShootNoteCheck = new AutoShootNoteCheck(Constants.ShooterConstants.AutoShootEndDelay);
  private final AutoShoot m_AutoShoot2 = new AutoShoot(m_Collector, true, 5);
  private final PoseEstimator s_PoseEstimator = new PoseEstimator();
  public final Swerve s_Swerve = new Swerve(s_PoseEstimator);
  private final SendableChooser<Command> AutoSelector;
  private final XboxController driver = new XboxController(0);
   /* Driver Controls */
	private final int translationAxis = XboxController.Axis.kLeftY.value;
	private final int strafeAxis = XboxController.Axis.kLeftX.value;
	private final int rotationAxis = XboxController.Axis.kRightX.value;

  private final Trigger forwardHold = new Trigger(() -> (driver.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.7));
  private final Trigger backwardHold = new Trigger(() -> (driver.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.7));

    /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
  private final JoystickButton dampen = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

  private final StopCollector_shooter m_stopCollector_shooter = new StopCollector_shooter(m_Collector, m_shooterSubsystem,m_Controller);
  private final JoystickButton DynamicLock = new JoystickButton(driver, XboxController.Button.kA.value);
  private final shootStart m_ShootStart = new shootStart(m_shooterSubsystem,1);
  private final shootStart m_AutoShootStart = new shootStart(m_shooterSubsystem,1.2);
  private final SequentialCommandGroup m_shootGroup = new SequentialCommandGroup(m_shoot, m_stopCollector_shooter, m_CollectNote);
  private final StopCollector_shooter m_stopCollector_shooter2 = new StopCollector_shooter(m_Collector, m_shooterSubsystem,m_Controller);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    m_ClimberSubsystem.setDefaultCommand(m_Climb);

    // the commands that are used in pathplanner
    NamedCommands.registerCommand("Shoot",m_AutoShoot2); // shoot command   
    NamedCommands.registerCommand("Collect", m_CollectNoteAuto); // collect command   
    NamedCommands.registerCommand("ShootStart", m_AutoShootStart); // starts shooter motors   
    NamedCommands.registerCommand("NoteCheck", m_AutoCollectCheck); // checks for when the tof first sees a note 
    NamedCommands.registerCommand("Shoot End Delay", m_AutoShootNoteCheck); // checks for when the tof no longer sees a note 
    NamedCommands.registerCommand("CollectReverse", m_CollectReverse); // sptits note out collector
    PathPlannerPath testPath = PathPlannerPath.fromChoreoTrajectory("Chor Test Path");

    s_Swerve.setDefaultCommand( 
          new SwerveCommand(
              s_Swerve, 
              () -> -driver.getRawAxis(translationAxis), 
              () -> -driver.getRawAxis(strafeAxis), 
              () -> -driver.getRawAxis(rotationAxis), 
              () -> robotCentric.getAsBoolean(),
              () -> dampen.getAsBoolean(),
              () -> 0 // Dynamic heading placeholder
          )
      ); 
      
        
  AutoSelector = AutoBuilder.buildAutoChooser("JustShoot");
  SmartDashboard.putData("AUTOCHOOSER", AutoSelector);
  }


  public void configShooterSubsystemForAuto(){

    m_shooterSubsystem.configShooterForAuto();
    SmartDashboard.putBoolean("auto_configs",true);
  }

  public void configShooterSubsystemForTeleop(){
    m_shooterSubsystem.configShooterForTeleop();
    SmartDashboard.putBoolean("auto_configs", false);
  }
    
public Command TestAuto( ){
    
  return Commands.sequence(
    
   // .runchor
  );
}
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));


    // DRIVER CONTROLLS
      zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading())); // Y                        | zeros gyro/field-oriented drive
      m_driverController.button(2).whileTrue(m_Rumble); // B                                  | vibrates operator controller
    
    // OPERATOR CONTROLLS    
      m_operatorController.button(1).toggleOnTrue(m_shootGroup); // A                         | shoot + collect
      m_operatorController.button(2).whileTrue(m_CollectNote2); // B                          | collect
      m_operatorController.button(3).whileTrue(m_CollectReverse); // X                        | reverse collect
      m_operatorController.button(5).onTrue(m_ShootStart); // LB                              | starts shooter motors
      m_operatorController.pov(180).whileTrue(m_stopCollector_shooter2); // D-PAD Down         | stops collector and shooter (controller will vibrate when held)

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return AutoSelector.getSelected();
  }
}
