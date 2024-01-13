package frc.Team364.src.main.java.frc.lib;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

/**
 * Thin CANCoder wrapper to make setup easier.
 */
public class LazyCANCoder extends CANCoder {

    public LazyCANCoder(int deviceNumber, CANCoderConfiguration swerveCanCoderConfig){
        super(deviceNumber, "canivore");
        super.configFactoryDefault();
        super.configAllSettings(swerveCanCoderConfig);
    }

        
   
}
