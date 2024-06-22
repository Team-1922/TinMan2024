package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Constants.CollectorConstants;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

public class Collector extends SubsystemBase {
    double CollectVoltage;
    ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
    private static TalonFX m_CollectorTalon = new TalonFX(CollectorConstants.kCollectorMotorID); 
    private static TalonFX m_CollectorTalon2 = new TalonFX(CollectorConstants.kCollectorSecondMotorID);
      TimeOfFlight m_Tof = new TimeOfFlight(Constants.TofConstants.Tofid);
    CurrentLimitsConfigs m_Configs = new CurrentLimitsConfigs();
     Slot0Configs m_slot0 = new Slot0Configs();
   private LedSubsystem m_LED = new LedSubsystem();
  public boolean m_IsTriggered;


  SingleFadeAnimation m_SingleFade = new SingleFadeAnimation(255, 255, 0, 255, .9, 98, 0);
  
  ColorFlowAnimation m_ColorFlowAnimation = new ColorFlowAnimation(255, 255, 0, 0, .5, 98, Direction.Forward);
  
    /**  Makes a new Collector subsystem */
    public Collector() {

m_Tof.setRangingMode(RangingMode.Short, 24);
//SmartDashboard.putNumber( "TOf sample time",m_Tof.getSampleTime());
        m_slot0.kP = 0;
        m_slot0.kV = .0005;
        m_CollectorTalon.setInverted(false);
        m_CollectorTalon2.setInverted(false); 
        m_Tof.setRangeOfInterest(8, 8, 8, 8); //this is the smallest area it can target 
        m_CollectorTalon.getConfigurator().apply(m_slot0);
        m_CollectorTalon2.getConfigurator().apply(m_slot0);

        //  MOTOR CONFIGS 
        m_Configs.SupplyCurrentLimitEnable = CollectorConstants.kCurrentLimitEnable;
        m_Configs.SupplyCurrentLimit = CollectorConstants.kCurrentSoftLimit;
        m_Configs.SupplyCurrentThreshold = CollectorConstants.kCurrentHardLimit;
        m_Configs.SupplyTimeThreshold = CollectorConstants.kCurrentLimitTime;
        m_CollectorTalon.getConfigurator().apply(m_Configs);
        m_CollectorTalon.getConfigurator().apply(
                new ClosedLoopRampsConfigs()
                    .withVoltageClosedLoopRampPeriod(CollectorConstants.kClosedLoopRamp)
        );
        m_CollectorTalon.getConfigurator().apply(
                new OpenLoopRampsConfigs()
                    .withVoltageOpenLoopRampPeriod(CollectorConstants.kOpenLoopRamp)
        );
        m_CollectorTalon2.getConfigurator().apply(m_Configs);
        m_CollectorTalon2.getConfigurator().apply(
                new ClosedLoopRampsConfigs()
                    .withVoltageClosedLoopRampPeriod(CollectorConstants.kClosedLoopRamp)
        );
        m_CollectorTalon2.getConfigurator().apply(
                new OpenLoopRampsConfigs()
                    .withVoltageOpenLoopRampPeriod(CollectorConstants.kOpenLoopRamp)
        );

    }
/**
 * @param RPM what RPS you want to set the motors to (rotations per second )
 */
    public void ActivateMotor(double RPM) {
            VelocityDutyCycle m_Output = new VelocityDutyCycle(RPM);
  
        m_CollectorTalon2.setControl(m_Output);
        m_CollectorTalon.setControl(m_Output);
    }
/** stops the collector motors */
    public void StopMotor() {
        
        m_CollectorTalon.stopMotor();
        m_CollectorTalon2.stopMotor();
    }
/** Makes the collector spin backwards with the desired RPM */
public void ReverseMotor(double RPM) {
      VelocityDutyCycle m_Output = new VelocityDutyCycle(-RPM);
        m_CollectorTalon.setControl(m_Output);   
        m_CollectorTalon2.setControl(m_Output);
   
    }
  


/** checks if there is something in the Tof target range 
 * @return if the Tof detects something within the target range
*/
    public boolean TofcheckTarget(){
       
        boolean InTarget =
                m_Tof.getRange() < Constants.TofConstants.TofmaxRange 
                && m_Tof.getRange() > Constants.TofConstants.TofminRange;
        SmartDashboard.putBoolean("Has Note?",InTarget);
        if (InTarget) {
            if(m_ShooterSubsystem.TargetRpmReached(Constants.ShooterConstants.kLeftTargetRPS, Constants.ShooterConstants.kRightTargetRPS))
            {
               m_LED.SetColor(0, 255, 0, 0, 0, 96); // green
            }else{
           m_LED.SetColor(255, 255, 255, 255, 0, 96); // white
           }
        } else {
        
         if ( RobotController.isSysActive()){
            if(m_ShooterSubsystem.TargetRpmReached(Constants.ShooterConstants.kLeftTargetRPS, Constants.ShooterConstants.kRightTargetRPS)){
                m_LED.SetColor(255, 165, 0, 0, 0, 98); // orange
            }
            else{
                m_LED.SetColor(255, 0, 0, 0, 0, 98); // red
            }
         } 
          else {
            m_LED.AnimateLEDs(m_ColorFlowAnimation, 0);
          } 
        }
        return InTarget; 
    }

    /**
     * 
     * @return if it has a note
     */
    public boolean HasNote(){
        return  m_Tof.getRange() < Constants.TofConstants.TofmaxRange 
                && m_Tof.getRange() > Constants.TofConstants.TofminRange;
    }


    public double TofcheckDistance(){

        double target = m_Tof.getRange();
        SmartDashboard.putNumber("Tof target distance", target);
        return target;
    }

    @Override
    public void periodic(){
            TofcheckTarget();
    }
    
}
