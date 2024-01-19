package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;

public class Collector extends SubsystemBase {
    private static TalonFX m_CollectorTalon = new TalonFX(Constants.MotorConstants.kCollectorMotorID); 

    public Collector() {
        m_CollectorTalon.setInverted(false);
    }

    public void ActivateMotor(double volts) {
        m_CollectorTalon.setVoltage(volts);
    }

    public void StopMotor() {
        m_CollectorTalon.setVoltage(0);
        m_CollectorTalon.disable();
    }

    @Override
    public void periodic() {

    }
    
}
