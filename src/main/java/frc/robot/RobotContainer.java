// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.Team364.src.main.java.frc.robot.States;
import frc.Team364.src.main.java.frc.robot.commands.SwerveCommand;
import frc.Team364.src.main.java.frc.robot.subsystems.Swerve;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LeftClimb;
import frc.robot.commands.CollectNote;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ExampleSubsystem;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.CollectReverse;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final Shoot m_shoot = new Shoot(m_shooterSubsystem);
  private final Collector m_Collector = new Collector();
  private final CollectNote m_CollectNote = new CollectNote(m_Collector);
  private final CollectReverse m_CollectReverse = new CollectReverse(m_Collector);
  private final ClimberSubsystem m_ClimberSubsystem = new ClimberSubsystem(); 
  private final LeftClimb m_LeftClimbUP = new LeftClimb(m_ClimberSubsystem, false);
  private final LeftClimb m_LeftClimbDOWN = new LeftClimb(m_ClimberSubsystem, true);

    private final LeftClimb m_RightClimbUP = new LeftClimb(m_ClimberSubsystem, false);
  private final LeftClimb m_RightClimbDOWN = new LeftClimb(m_ClimberSubsystem, true);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  //private final XboxController m_operatorController = new XboxController(0);
 

  private final CommandXboxController m_operatorController = new CommandXboxController(0);
  
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();
    private final Swerve s_Swerve = new Swerve();

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

    private final JoystickButton DynamicLock = new JoystickButton(driver, XboxController.Button.kA.value);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

  

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
        

    autoChooser();

  }

 


  public void autoChooser(){

m_autoChooser.setDefaultOption("Placeholder", null);
m_autoChooser.addOption("do nothing", null);


  SmartDashboard.putData("Auto Chooser",m_autoChooser);
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



        
    //Heading lock bindings
    /*   forwardHold.onTrue(
            new InstantCommand(() -> States.driveState = States.DriveStates.forwardHold)).onFalse(
            new InstantCommand(() -> States.driveState = States.DriveStates.standard)
            );
        backwardHold.onTrue(
            new InstantCommand(() -> States.driveState = States.DriveStates.backwardHold)).onFalse(
            new InstantCommand(() -> States.driveState = States.DriveStates.standard)
            ); 
        DynamicLock.onTrue(
            new InstantCommand(() -> States.driveState = States.DriveStates.DynamicLock)).onFalse(
            new InstantCommand(() -> States.driveState = States.DriveStates.standard)
            ); */
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_operatorController.button(1).whileTrue(m_shoot);
    m_operatorController.button(2).whileTrue(m_CollectNote);
    m_operatorController.button(3).whileTrue(m_CollectReverse);

    m_operatorController.button(4).whileTrue(m_RightClimbUP);
    m_operatorController.button(5).whileTrue(m_RightClimbDOWN);
    m_operatorController.button(6).whileTrue(m_LeftClimbUP);
    m_operatorController.button(7).whileTrue(m_LeftClimbDOWN);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
return m_autoChooser.getSelected();
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
  }
}
