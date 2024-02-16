package frc.Team364.src.main.java.frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.Team364.src.main.java.frc.lib.math.Conversions;
import frc.Team364.src.main.java.frc.lib.util.CTREModuleState;
import frc.Team364.src.main.java.frc.lib.util.SwerveModuleConstants;
import com.ctre.phoenix6.controls.PositionDutyCycle;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.VoltageConfigs;



public class SwerveMod {
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    private TalonFX mAngleMotor;
    private TalonFX mDriveMotor;
    private CANcoder angleEncoder;
 
    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

    public SwerveMod(int moduleNumber, SwerveModuleConstants moduleConstants){
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;
    
        /* Angle Encoder Config */
    angleEncoder = new CANcoder(
            moduleConstants.cancoderID,"Drivebase"
        );
        CANcoderConfiguration mCanCoderConfigs = new CANcoderConfiguration();
        mCanCoderConfigs.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        mCanCoderConfigs.MagnetSensor.withSensorDirection(SensorDirectionValue.Clockwise_Positive);
        angleEncoder.getConfigurator().apply(mCanCoderConfigs);

        /* Angle Motor Config */
        mAngleMotor = new TalonFX(
            moduleConstants.angleMotorID,"Drivebase"
            
          
        );

        Slot0Configs mAngleConfigs = new Slot0Configs();
            mAngleConfigs.kP = Constants.Swerve.angleKP;
            mAngleConfigs.kI = Constants.Swerve.angleKI;
            mAngleConfigs.kD = Constants.Swerve.angleKD;
            mAngleConfigs.kS = Constants.Swerve.angleKF;
       mAngleMotor.getConfigurator().apply(mAngleConfigs);      

       CurrentLimitsConfigs mAngleCurrentLimitsConfigs = new CurrentLimitsConfigs();
         mAngleCurrentLimitsConfigs.SupplyCurrentLimitEnable = Constants.Swerve.angleEnableCurrentLimit;
         mAngleCurrentLimitsConfigs.SupplyCurrentLimit = Constants.Swerve.angleContinuousCurrentLimit;
       //  mAngleCurrentLimitsConfigs.SupplyCurrentThreshold = Constants.Swerve.anglePeakCurrentLimit;
     //    mAngleCurrentLimitsConfigs.SupplyTimeThreshold = Constants.Swerve.anglePeakCurrentDuration;
       VoltageConfigs mAngleVoltageConfigs = new VoltageConfigs();
         mAngleVoltageConfigs.PeakForwardVoltage = Constants.Swerve.anglePeakVoltage;
         mAngleVoltageConfigs.PeakReverseVoltage = Constants.Swerve.anglePeakVoltage;

     mAngleMotor.getConfigurator().apply(mAngleVoltageConfigs);
       
       mAngleMotor.getConfigurator().apply(mAngleCurrentLimitsConfigs);

        ClosedLoopRampsConfigs mAngleClosedLoopRampsConfigs = new ClosedLoopRampsConfigs();
         mAngleClosedLoopRampsConfigs.TorqueClosedLoopRampPeriod = Constants.Swerve.angleClosedLoopTorqueRamp;
         mAngleClosedLoopRampsConfigs.VoltageClosedLoopRampPeriod = Constants.Swerve.angleClosedLoopVoltageRamp;
        mAngleMotor.getConfigurator().apply(mAngleClosedLoopRampsConfigs);

        MotorOutputConfigs mAngleMotorOutputConfigs = new MotorOutputConfigs();
         mAngleMotorOutputConfigs.PeakForwardDutyCycle = Constants.Swerve.anglePeakDutyCycleOutput;
         mAngleMotorOutputConfigs.PeakReverseDutyCycle = -Constants.Swerve.anglePeakDutyCycleOutput;
        mAngleMotor.getConfigurator().apply(mAngleMotorOutputConfigs);
         
        OpenLoopRampsConfigs mAngleOpenLoopRampsConfigs = new OpenLoopRampsConfigs();
         mAngleOpenLoopRampsConfigs.DutyCycleOpenLoopRampPeriod = Constants.Swerve.angleOpenLoopRamp;
         mAngleMotor.getConfigurator().apply(mAngleOpenLoopRampsConfigs);

  //      mAngleMotor.getConfigurator().apply(Robot.ctreConfigs.swerveAngleFXConfig); // that returns null and makes code crash
       

        /* Drive Motor Config */
        mDriveMotor = new TalonFX(
            moduleConstants.driveMotorID,"Drivebase");

        ClosedLoopRampsConfigs mClosedLoopRampsConfigs = new ClosedLoopRampsConfigs();
         mClosedLoopRampsConfigs.TorqueClosedLoopRampPeriod = Constants.Swerve.closedLoopTorqueRamp;
         mClosedLoopRampsConfigs.VoltageClosedLoopRampPeriod = Constants.Swerve.closedLoopVoltageRamp;
        mDriveMotor.getConfigurator().apply(mClosedLoopRampsConfigs);
        
     VoltageConfigs mDriveVoltageConfigs = new VoltageConfigs();
         mDriveVoltageConfigs.PeakForwardVoltage = Constants.Swerve.drivePeakVoltage;
         mDriveVoltageConfigs.PeakReverseVoltage = -Constants.Swerve.drivePeakVoltage;
         mDriveMotor.getConfigurator().apply(mDriveVoltageConfigs);
        
        MotorOutputConfigs mDriveMotorOutputConfigs = new MotorOutputConfigs();
         mDriveMotorOutputConfigs.PeakForwardDutyCycle = Constants.Swerve.drivePeakMotorOutput;
         mDriveMotorOutputConfigs.PeakReverseDutyCycle = -Constants.Swerve.drivePeakMotorOutput; 
        mDriveMotor.getConfigurator().apply(mDriveMotorOutputConfigs);
        OpenLoopRampsConfigs mOpenLoopRampsConfigs = new OpenLoopRampsConfigs();
         mOpenLoopRampsConfigs.DutyCycleOpenLoopRampPeriod = Constants.Swerve.openLoopRamp;

        CurrentLimitsConfigs mDriveCurrentLimitsConfigs = new CurrentLimitsConfigs();
         mDriveCurrentLimitsConfigs.SupplyCurrentLimitEnable = Constants.Swerve.driveEnableCurrentLimit;
         mDriveCurrentLimitsConfigs.SupplyCurrentLimit = Constants.Swerve.driveContinuousCurrentLimit;
         mDriveCurrentLimitsConfigs.SupplyCurrentThreshold = Constants.Swerve.drivePeakCurrentLimit;
         mDriveCurrentLimitsConfigs.SupplyTimeThreshold = Constants.Swerve.drivePeakCurrentDuration;

        mDriveMotor.getConfigurator().apply(mDriveCurrentLimitsConfigs);
  
        lastAngle = getState().angle;
        mDriveMotor.setNeutralMode(NeutralModeValue.Brake);
        mAngleMotor.setNeutralMode(NeutralModeValue.Coast);
        
    } 

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        /* This is a custom optimize function, since default WPILib optimize assumes continuous controller which CTRE and Rev onboard is not */
        desiredState = CTREModuleState.optimize(desiredState, getState().angle); 
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop){
        if(isOpenLoop){
            double percentOutput = desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
            mDriveMotor.set(percentOutput);
            
            
        }
        else {
            double velocity = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio);
           VelocityVoltage Voutput1 = new VelocityVoltage(velocity);

          VelocityVoltage Voutput2 = Voutput1.withFeedForward(feedforward.calculate(desiredState.speedMetersPerSecond));
          
            mDriveMotor.setControl(Voutput2);
          //  mDriveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward, feedforward.calculate(desiredState.speedMetersPerSecond));
 
        }
    }

    private void setAngle(SwerveModuleState desiredState){
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01)) ? lastAngle : desiredState.angle; //Prevent rotating module if speed is less then 1%. Prevents Jittering.


    PositionDutyCycle Aoutput1 = new PositionDutyCycle(-angle.getRotations()*Constants.Swerve.angleGearRatio);
    Aoutput1.withSlot(0);
    
        mAngleMotor.setControl(Aoutput1);

   /*    if (this.moduleNumber == 0) {
       SmartDashboard.putNumber("angle (deg)", angle.getDegrees());
       SmartDashboard.putNumber("desired angle (rot)", desiredState.angle.getRotations());
       SmartDashboard.putNumber("current angle (rot)", mAngleMotor.getPosition().getValueAsDouble());
    }*/
   // mAngleMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle.getDegrees(), Constants.Swerve.angleGearRatio));
    
     
       lastAngle = angle;
       
    }  

    private Rotation2d getAngle(){
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(mAngleMotor.getPosition().getValueAsDouble(), Constants.Swerve.angleGearRatio)/2048);
    }

    public Rotation2d getCanCoder(){
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValueAsDouble());
    } 

    public void resetToAbsolute(){
        double absolutePosition = (Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset.getDegrees(), Constants.Swerve.angleGearRatio)/2048); // the getcancoder is i
        mAngleMotor.setPosition(absolutePosition);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(
            Conversions.falconToMPS(mDriveMotor.getRotorVelocity().getValueAsDouble(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), 
            getAngle()
        ); 
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(
            Conversions.falconToMeters(mDriveMotor.getPosition().getValueAsDouble(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), 
            getAngle()
        );
    }
}
