package frc.Team364.src.main.java.frc.lib;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

/**
 * Thin Falcon wrapper to make setup easier.
 */
public class LazyTalonFX extends TalonFX {

    /**
     * Config using individual parameters.
     * @param deviceNumber
     * @param swerveDriveFXConfig
     * @param neutralMode
     * @param anglemotorinvert
     * @param slowStatusFrame
     */
    public LazyTalonFX(int deviceNumber, TalonFXConfiguration swerveDriveFXConfig, NeutralMode neutralMode, boolean anglemotorinvert, boolean slowStatusFrame){
        super(deviceNumber, "rio");
        super.configFactoryDefault();
       // super.configAllSettings(swerveDriveFXConfig);
        super.setNeutralMode(neutralMode);
        super.setInverted(anglemotorinvert);
        super.setSelectedSensorPosition(0);

        if (slowStatusFrame){
            super.setStatusFramePeriod(StatusFrame.Status_1_General, 255, 30);
            super.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 255, 30);
        }
    }


    

    /**
     * Config using talonFxConstants.
     * @param talonFxConstants
     */
    public LazyTalonFX(TalonFxConstants talonFxConstants){
        super(talonFxConstants.deviceNumber, "rio");
        super.configFactoryDefault();
        super.configAllSettings(talonFxConstants.allConfigs);
        super.setNeutralMode(talonFxConstants.neutralMode);
        super.setInverted(talonFxConstants.invertType);
        super.setSelectedSensorPosition(0);

        if (talonFxConstants.slowStatusFrame){
            super.setStatusFramePeriod(StatusFrame.Status_1_General, 255, 30);
            super.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 255, 30);
        }
    }




 ///   public LazyTalonFX(int driveMotorID, com.ctre.phoenix6.configs.TalonFXConfiguration swerveDriveFXConfig,
    //        NeutralMode driveneutralmode, boolean drivemotorinvert, boolean slowStatusFrame) {
        //TODO Auto-generated constructor stub
   // } */

 /*   public LazyTalonFX(int angleMotorID, TalonFXConfiguration swerveAngleFXConfig, NeutralMode angleneutralmode,
            boolean anglemotorinvert, boolean slowStatusFrame) {
        //TODO Auto-generated constructor stub
    }*/
    
}
