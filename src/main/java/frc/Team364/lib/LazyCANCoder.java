package frc.Team364.lib;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;

/**
 * Thin CANCoder wrapper to make setup easier.
 */
public class LazyCANCoder extends CANcoder {
    /**
     * Thin CANCoder wrapper to make setup easier.
     * @param deviceNumber
     * @param allConfigs CTREConfig file
     */
    public LazyCANCoder(int deviceNumber, CANcoderConfiguration allConfigs){
        super(deviceNumber, "drivebase");
        super.getConfigurator().apply(allConfigs);
    }
}
