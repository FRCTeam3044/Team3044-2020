package frc.robot;

import java.util.HashMap; 
import java.util.Map; 

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PowerCells extends RobotSubsystem
{
    /* 
    The components relating to ONLY the Intake subsystem 
    should be defined as class variables here 
    */
    public static Solenoid HoodSolenoid;
    public static Solenoid IntakeSolenoid;  // TODO: Confirm ID
    public static TalonSRX DeliveryMotor1;  // D1
    public static TalonSRX DeliveryMotor2;  // D2 
    public static TalonSRX ShootTalon1; 
    public static TalonSRX ShootTalon2; 
    public static TalonSRX IntakeBar;       // TODO configurize 
    public static double D1Speed=.75;       // TODO fix 
    public static double D2Speed=.8;        // TODO configurize 

    static double ShooterKp;
    static double ShooterKi;
    static double ShooterKd; 
    static PIDController ShooterPID =   null; 

    public static final int SHOOTER_MODE_PID_CTL=0; 
    public static final int SHOOTER_MODE_POWER_CTL=1; 
    
    public static Map<String, Double> PowerSettings = new HashMap<String,Double>();
    public static Map<String, Integer> PIDSetpoints = new HashMap<String,Integer>(); 

    public static int ShooterControlMode = SHOOTER_MODE_POWER_CTL; 
    public static boolean HoodExtended = false; 
    public static String ShooterPowerSetting="Trench"; 
    

    // d1 .5 loading .75 shooting 
    // d2 .8 


    // Constructors
    public PowerCells() {
        robotInit();
    }

    public  void robotInit()
    {
        // Load configuration for subsystem 
        
        int shooter1_talon_canid = Integer.parseInt(Robot.RobotConfiguration.getProperty("shooter_1_talon_canid","15")); 
        int shooter2_talon_canid = Integer.parseInt(Robot.RobotConfiguration.getProperty("shooter_2_talon_canid","16"));
        int hood_solenoid_id =Integer.parseInt(Robot.RobotConfiguration.getProperty("hood_solenoid_id","0")); 
        int intake_solenoid_id = Integer.parseInt(Robot.RobotConfiguration.getProperty("intake_solenoid_id","1"));
        int shooter_mover_1_canid = Integer.parseInt(Robot.RobotConfiguration.getProperty("shooter_mover_1_canid","13"));
        int shooter_mover_2_canid = Integer.parseInt(Robot.RobotConfiguration.getProperty("shooter_mover_2_canid","14"));;
        int intake_talon_canid = Integer.parseInt(Robot.RobotConfiguration.getProperty("intake_talon_canid","11"));

        ShootTalon1 = new TalonSRX(shooter1_talon_canid); 
        ShootTalon2 = new TalonSRX(shooter2_talon_canid); 
        DeliveryMotor1 = new TalonSRX(shooter_mover_1_canid); 
        DeliveryMotor2 = new TalonSRX(shooter_mover_2_canid);
        HoodSolenoid = new Solenoid(hood_solenoid_id); 
        IntakeSolenoid = new Solenoid(intake_solenoid_id); 
        IntakeBar = new TalonSRX(intake_talon_canid);               

        ShooterKp=Double.parseDouble(Robot.RobotConfiguration.getProperty("shooter_Kp", "0.000025"));
        ShooterKi=Double.parseDouble(Robot.RobotConfiguration.getProperty("shooter_Ki", "0"));
        ShooterKd=Double.parseDouble(Robot.RobotConfiguration.getProperty("shooter_Kd", "0"));     
        ShooterPID= new PIDController(ShooterKp, ShooterKi, ShooterKd);

        PowerSettings.put("AutoLine", .55);         // TODO: configurize , percent 
        PowerSettings.put("Triangle",.45);          // TODO: configurize , percent 
        PowerSettings.put("Trench",.45);            // TODO: configurize , percent 
        //// 
        PIDSetpoints.put("AutoLine",300);           // TODO: configurize , RPM
        PIDSetpoints.put("Triangle", 290);          // TODO: configurize , RPM
        PIDSetpoints.put("Trench", 366);            // TODO: configurize , RPM

        Robot.mode = "PowerCells";
    }
    
    public  void teleopInit()
    {}

    public  void robotPeriodic()
    {} 


    public void autonomousPeriodic() {
        
    }

    public void teleopPeriodic() 
    {
        if (Robot.mode=="PowerCells")
        {
            // Intake arm  

            int pov2 = Robot.Driver2.getPOV(); 
            switch (pov2)
            {
                case 0:
                    // Intake Solenoid 
                    IntakeSolenoid.set(true); 
                    break;
                case 180:
                    // Intake Solenoid 
                    IntakeSolenoid.set(false); 
                    break;  
                default:
                    IntakeSolenoid.set(false);
                    break; 
                
            }

            // Powercells eject/intake 
            if (Robot.Driver2.getY(Hand.kRight) > .1 ) 
            {
                IntakeBar.set(ControlMode.PercentOutput, Robot.Driver2.getY(Hand.kRight)); 
                DeliveryMotor1.set(ControlMode.PercentOutput, D1Speed /*Robot.Driver2.getY(Hand.kRight) *3*/ ); 
            }
            else
            {
                IntakeBar.set(ControlMode.PercentOutput, 0); 
                DeliveryMotor1.set(ControlMode.PercentOutput, 0); 
            }

            // Shoot         
            // Get preset 
            if (Robot.Driver2.getAButtonPressed() && !Robot.Driver2.getYButtonPressed()) 
            { 
                ShooterPowerSetting="Triangle"; 
                Robot.VisionSystem.SetHUDVerticalMode(Vision.HUD_MODE_VERTICAL_LOW);
                // TODO: Extend hood 
                // HoodSolenoid.set(true);
            }
            if (Robot.Driver2.getYButton() && !Robot.Driver2.getAButton()) 
            {
                ShooterPowerSetting="Trench";
                Robot.VisionSystem.SetHUDVerticalMode(Vision.HUD_MODE_VERTICAL_HIGH);
                // TODO: retract hood
                //HoodSolenoid.set(false);
            }

            if (ShooterControlMode == SHOOTER_MODE_PID_CTL)
            {
                if (Robot.Driver2.getTriggerAxis(Hand.kRight) >= .25) 
                {
                    shootPID(PIDSetpoints.get(ShooterPowerSetting));
                }
                else
                {
                    shootPID(0);
                }
            }
            else
            {
                if (Robot.Driver2.getTriggerAxis(Hand.kRight) >= .25) 
                {
                    shootPower(PowerSettings.get(ShooterPowerSetting));
                }  
                else
                {
                    shootPower(0.0); 
                }    
            }   
            
            if (Robot.Driver2.getBackButtonPressed()) {
                Robot.mode = "Climb";
            }
        }  
        else 
        {
            // Shut it all down, not in PowerCells mode! 
            shootPower(0.0); 
            DeliveryMotor1.set(ControlMode.PercentOutput,0.0);
            DeliveryMotor2.set(ControlMode.PercentOutput,0.0);
        }
    }

    public void testPeriodic() {
    }

    public void autonomousInit() {
    }


    public static void shootPID(Integer setPoint)
    {
        SmartDashboard.putNumber("Shooter setpoint (Target RPM)", setPoint);
        SmartDashboard.putNumber("Velocity from Encoder", Sensors.shooterRPM);
        SmartDashboard.putNumber("Calculated Power", ShooterPID.calculate(Sensors.shooterRPM, setPoint));
        SmartDashboard.updateValues();        

        ShootTalon1.set(ControlMode.PercentOutput, ShooterPID.calculate(Sensors.shooterRPM, setPoint));
        ShootTalon2.set(ControlMode.PercentOutput, ShooterPID.calculate(Sensors.shooterRPM, -setPoint));
        if (Sensors.shooterRPM < setPoint *0.9*0.36967) 
        {
            SmartDashboard.putBoolean("Shooter at speed", false);
            DeliveryMotor2.set(ControlMode.PercentOutput,0);
        } 
        else 
        {
            SmartDashboard.putBoolean("Shooter at speed", true);
            DeliveryMotor2.set(ControlMode.PercentOutput, D2Speed);
        }
    }

    public static void shootPower(Double power) {
        
        ShootTalon1.set(ControlMode.PercentOutput, power);
        ShootTalon2.set(ControlMode.PercentOutput, power);
        if (Sensors.shooterRPM < PIDSetpoints.get(ShooterPowerSetting)) 
        {
            SmartDashboard.putBoolean("Shooter at speed", false);
            DeliveryMotor2.set(ControlMode.PercentOutput,0);
        } 
        else 
        {
            SmartDashboard.putBoolean("Shooter at speed", true);
            DeliveryMotor2.set(ControlMode.PercentOutput,D2Speed);
        }
    }

    


}