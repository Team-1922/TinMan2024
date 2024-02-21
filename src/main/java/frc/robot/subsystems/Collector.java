package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import com.playingwithfusion.TimeOfFlight;


public class Collector extends SubsystemBase {
    double CollectVoltage;
   public boolean m_TofIsTriggered;
    private static TalonFX m_CollectorTalon = new TalonFX(Constants.CollectorConstants.kCollectorMotorID); 
    private static TalonFX m_CollectorTalon2 = new TalonFX(Constants.CollectorConstants.kCollectorSecondMotorID);
      TimeOfFlight m_Tof = new TimeOfFlight(Constants.TofConstants.Tofid);
   // private LedSubsystem m_LED = new LedSubsystem();
    /**  Makes a new Collector subsystem */
    public Collector() {
       
        m_Tof.setRangeOfInterest(8, 8, 8, 8); //this is the smallest area it can target 
    }
/**
 * @param volts what voltage you want to set the motors to
 */
    public void ActivateMotor(double volts) {
        m_CollectorTalon.setVoltage(volts);   
        m_CollectorTalon2.setVoltage(volts);
        m_CollectorTalon.setInverted(false);
        m_CollectorTalon2.setInverted(false);
    }
/** stops the collector motors */
    public void StopMotor() {
        
        m_CollectorTalon.stopMotor();
        m_CollectorTalon2.stopMotor();
    }

public void ReverseMotor(double volts) {

        m_CollectorTalon.setVoltage(volts);   
        m_CollectorTalon2.setVoltage(volts);
        m_CollectorTalon.setInverted(true);
        m_CollectorTalon2.setInverted(true);
    }
  


/** checks if there is something in the Tof target range 
 * @return if the Tof detects something within the target range
*/
    public boolean TofcheckTarget(){
        
        boolean InTarget =
                m_Tof.getRange() < Constants.TofConstants.TofmaxRange 
                && m_Tof.getRange() > Constants.TofConstants.TofminRange;
        SmartDashboard.putBoolean("Has Note?",InTarget);
   /*      if (InTarget) {
            m_LED.SetColor(0, 255, 0, 0, 0, Constants.LedConstants.kTotalLedCount);
        } else {
            m_LED.SetColor(255, 0, 0, 0, 0, Constants.LedConstants.kTotalLedCount);
        }*/
    
        return InTarget; // the LEDs are just there to help with testing, can be removed later. 
    }


    public double TofcheckDistance(){

        double target = m_Tof.getRange();
        SmartDashboard.putNumber("Tof target distance", target);
        return target;
    }

    @Override
    public void periodic() {
   
   // TofcheckDistance();
    TofcheckTarget();
    }
    
}
