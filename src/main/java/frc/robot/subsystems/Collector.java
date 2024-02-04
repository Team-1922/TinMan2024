package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;


public class Collector extends SubsystemBase {
    
    private static TalonFX m_CollectorTalon = new TalonFX(Constants.MotorConstants.kCollectorMotorID); 
    private static TalonFX m_CollectorTalon2 = new TalonFX(Constants.MotorConstants.kCollectorSecondMotorID);

    public Collector() {
        SmartDashboard.putNumber("Collector_VOLTAGE", 8);
    }
/**
 * 
 * @param volts the default value for voltage if the smartdashboard doesn't work 
 */
    public void ActivateMotor(double volts) {
        m_CollectorTalon.setVoltage(SmartDashboard.getNumber("Collector_VOLTAGE", volts));   
        m_CollectorTalon2.setVoltage(SmartDashboard.getNumber("Collector_VOLTAGE", volts));
            m_CollectorTalon.setInverted(false);
            m_CollectorTalon2.setInverted(false);
    }

    public void StopMotor() {
        m_CollectorTalon.setVoltage(0);
        m_CollectorTalon2.setVoltage(0);
        m_CollectorTalon.disable();
        m_CollectorTalon2.disable();
    }

public void ReverseMotor(double volts) {
        m_CollectorTalon.setVoltage(volts);   
     m_CollectorTalon2.setVoltage(volts);
        m_CollectorTalon.setInverted(true);
        m_CollectorTalon2.setInverted(true);
    }

    @Override
    public void periodic() {

    }
    
}
