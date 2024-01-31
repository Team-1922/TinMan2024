package frc.Team364.src.main.java.frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest.SwerveControlRequestParameters;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;


public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public static CANcoderConfiguration swerveCanCoderConfig;

    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANcoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Swerve.angleEnableCurrentLimit, 
            Constants.Swerve.angleContinuousCurrentLimit, 
            Constants.Swerve.anglePeakCurrentLimit, 
            Constants.Swerve.anglePeakCurrentDuration);

        swerveAngleFXConfig.Slot0.kP = Constants.Swerve.angleKP;
        swerveAngleFXConfig.Slot0.kI = Constants.Swerve.angleKI;
        swerveAngleFXConfig.Slot0.kD = Constants.Swerve.angleKD;
        swerveAngleFXConfig.Slot0.kV = Constants.Swerve.angleKF;
        //swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Swerve.driveEnableCurrentLimit, 
            Constants.Swerve.driveContinuousCurrentLimit, 
            Constants.Swerve.drivePeakCurrentLimit, 
            Constants.Swerve.drivePeakCurrentDuration);

        swerveDriveFXConfig.Slot0.kP = Constants.Swerve.driveKP;
        swerveDriveFXConfig.Slot0.kI = Constants.Swerve.driveKI;
        swerveDriveFXConfig.Slot0.kD = Constants.Swerve.driveKD;
        swerveDriveFXConfig.Slot0.kV = Constants.Swerve.driveKF; 
             
       // swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        //swerveDriveFXConfig.openloopRamp = Constants.Swerve.openLoopRamp;
        //swerveDriveFXConfig.closedloopRamp = Constants.Swerve.closedLoopRamp;
        
        /* Swerve CANCoder Configuration */
       swerveCanCoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
       swerveCanCoderConfig.MagnetSensor.withSensorDirection(SensorDirectionValue.Clockwise_Positive);
      
       
    }
}