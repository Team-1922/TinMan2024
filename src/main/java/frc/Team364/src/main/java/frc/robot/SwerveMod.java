package frc.Team364.src.main.java.frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
//import frc.Team364.src.main.java.frc.lib.LazyCANCoder;
import frc.Team364.src.main.java.frc.lib.LazyTalonFX;
import frc.Team364.src.main.java.frc.lib.math.Conversions;
import frc.Team364.src.main.java.frc.lib.util.CTREModuleState;
import frc.Team364.src.main.java.frc.lib.util.SwerveModuleConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;



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
            moduleConstants.cancoderID
        ); 

        /* Angle Motor Config */
       mAngleMotor = new TalonFX(
            moduleConstants.angleMotorID
            
          
        );
        Slot0Configs mAngleConfigs = new Slot0Configs();
            mAngleConfigs.kP = Constants.Swerve.angleKP;
            mAngleConfigs.kI = Constants.Swerve.angleKI;
            mAngleConfigs.kD = Constants.Swerve.angleKD;
            mAngleConfigs.kS = Constants.Swerve.angleKF;

        mAngleMotor.getConfigurator().apply(mAngleConfigs);

        /* Drive Motor Config */
       mDriveMotor = new TalonFX(
            moduleConstants.driveMotorID);

        lastAngle = getState().angle;
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
/* */
    private void setAngle(SwerveModuleState desiredState){
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01)) ? lastAngle : desiredState.angle; //Prevent rotating module if speed is less then 1%. Prevents Jittering.
        PositionVoltage Aoutput1 = new PositionVoltage(Conversions.degreesToFalcon(angle.getDegrees(), Constants.Swerve.angleGearRatio)  );  
        mAngleMotor.setControl(Aoutput1);
    //  mAngleMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle.getDegrees(), Constants.Swerve.angleGearRatio));
     
     
       lastAngle = angle;
       
    }  

    private Rotation2d getAngle(){
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(mAngleMotor.getPosition().getValueAsDouble(), Constants.Swerve.angleGearRatio));
    }

    public Rotation2d getCanCoder(){
        return Rotation2d.fromDegrees(angleEncoder.getAbsolutePosition().getValueAsDouble());
    }

    public void resetToAbsolute(){
        double absolutePosition = Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset.getDegrees(), Constants.Swerve.angleGearRatio);
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
