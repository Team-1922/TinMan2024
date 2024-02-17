package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import com.playingwithfusion.TimeOfFlight;


public class Collector extends SubsystemBase {
    
    private static TalonFX m_CollectorTalon = new TalonFX(Constants.MotorConstants.kCollectorMotorID); 
    private static TalonFX m_CollectorTalon2 = new TalonFX(Constants.MotorConstants.kCollectorSecondMotorID);
      TimeOfFlight m_TOF = new TimeOfFlight(Constants.LedConstants.TOFid);
    private LedSubsystem m_LED = new LedSubsystem();
    public Collector() {
  //      SmartDashboard.putNumber("Collector_VOLTAGE", 8);
        m_TOF.setRangeOfInterest(8, 8, 8, 8); //TODO update this 
    }
/**
 * 
 * @param volts the default value for voltage if the smartdashboard doesn't work 
 */
    public void ActivateMotor(double volts) {
        m_CollectorTalon.setVoltage(volts);   
        m_CollectorTalon2.setVoltage(volts);
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
    public boolean TOFcheckTarget(){
        boolean InTarget = m_TOF.getRange() < Constants.LedConstants.TOFmaxRange && m_TOF.getRange() > Constants.LedConstants.TOFminRange;
           SmartDashboard.putBoolean("Has Note?",InTarget);
        if (InTarget) {
            m_LED.SetColor(0, 255, 0, 0, 0, 8);
        }else{m_LED.SetColor(255, 0, 0, 0, 0, 8);}
        return InTarget; 
    }
    public double TOFcheckDistance(){
        double target = m_TOF.getRange();
        
          SmartDashboard.putNumber("TOF target distance", target);
        return target;
    }

    @Override
    public void periodic() {
   // TOFcheckDistance();
   // TOFcheckTarget();
    }
    
}
