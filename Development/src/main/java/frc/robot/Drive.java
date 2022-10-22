package frc.robot;

import com.playingwithfusion.*;
import com.playingwithfusion.CANVenom.ControlMode;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends RobotSubsystem
{
    /* 
    The components relating to ONLY the Drive subsystem 
    should be defined as class variables here 
    */
    public static CANVenom port_motor_fore; 
    public static CANVenom port_motor_aft; 
    public static CANVenom starboard_motor_fore;
    public static CANVenom starboard_motor_aft; 
    double deadband = 0.025;
    double maxX = 650;
    double minX = 630;
    boolean aligned;
    
    

    // Constructors     
    public Drive() {
        robotInit();
    }


    /*
    Required configuration keys: 
    port_motor_fore_canid (int) 
    port_motor_aft_canid (int)
    starboard_motor_fore_canid (int) 
    starboard_motor_aft_canid (int)
    */ 
    public  void robotInit()
    {
        int port_motor_fore_canid = Integer.parseInt( Robot.RobotConfiguration.getProperty("port_motor_fore_canid", "2"));
        int port_motor_aft_canid = Integer.parseInt( Robot.RobotConfiguration.getProperty("port_motor_aft_canid", "4"));
        int starboard_motor_fore_canid = Integer.parseInt( Robot.RobotConfiguration.getProperty("starboard_motor_fore_canid", "1"));
        int starboard_motor_aft_canid = Integer.parseInt( Robot.RobotConfiguration.getProperty("starboard_motor_aft_canid", "3"));

        port_motor_fore = new CANVenom(port_motor_fore_canid); 
        port_motor_aft = new CANVenom(port_motor_aft_canid); 
        starboard_motor_fore = new CANVenom(starboard_motor_fore_canid);
        starboard_motor_aft = new CANVenom(starboard_motor_aft_canid);

    }
    
    public  void teleopInit()
    {}

    public  void robotPeriodic()
    {} 

    public  void autonomousPeriodic()
    {}

    public  void teleopPeriodic()
    {
        
    if (Robot.Driver2.getAButton() == true){
        autoAlign();
    } else {
        aligned = false;
        double Drive1LeftStickYaxis = Robot.Driver1.getY(Hand.kLeft);
        double Drive1RightStickYaxis = Robot.Driver1.getY(Hand.kRight);
        //port_motor_fore.setCommand(ControlMode.Proportional, Drive1RightStickYaxis * Drive1RightStickYaxis * Drive1RightStickYaxis);
        //port_motor_aft.setCommand(ControlMode.Proportional, Drive1RightStickYaxis * Drive1RightStickYaxis * Drive1RightStickYaxis);
        //starboard_motor_fore.setCommand(ControlMode.Proportional, -Drive1LeftStickYaxis * Drive1LeftStickYaxis * Drive1LeftStickYaxis);
        //starboard_motor_aft.setCommand(ControlMode.Proportional, -Drive1LeftStickYaxis * Drive1LeftStickYaxis * Drive1LeftStickYaxis);
        //SmartDashboard.putBoolean("DB", Math.abs(Drive1LeftStickYaxis) > deadband);
        drive(Drive1LeftStickYaxis,Drive1RightStickYaxis);
        SmartDashboard.updateValues();
        }
        
    } 

    public  void testPeriodic()
    {  
        
    }

    public void autonomousInit()
    {}

    public void drive(double left, double right) {
        if (Math.abs(left) > deadband) {
            port_motor_fore.setCommand(ControlMode.SpeedControl, left * left * left);
        } else {
            port_motor_fore.setCommand(ControlMode.VoltageControl, 0);
        }
        if (Math.abs(right) > deadband) {
            starboard_motor_fore.setCommand(ControlMode.SpeedControl, -right * right * right);
        } else {
            starboard_motor_fore.setCommand(ControlMode.VoltageControl, 0);
        }
        port_motor_aft.follow(port_motor_fore);
        starboard_motor_aft.follow(port_motor_fore);
    }

    public void autoAlign() {
        //basic auto align concept 
        /*
        if (maxX > TableEntryListenerTargetCamera.centerX == true && TableEntryListenerTargetCamera.centerX > minX == true);{
            aligned = true;
        } else {
            (maxX <= TableEntryListenerTargetCamera.centerX == false);{
                aligned = false;
                port_motor_fore.setCommand(ControlMode.VoltageControl, .3);
                port_motor_aft.setCommand(ControlMode.VoltageControl, .3);
                System.out.println(TableEntryListenerTargetCamera.centerX);
            }
        } else {
            (minX >= TableEntryListenerTargetCamera.centerX == false);{
                aligned = false;
                starboard_motor_fore.setCommand(ControlMode.VoltageControl, -.3);
                starboard_motor_aft.setCommand(ControlMode.VoltageControl, -.3);
        }
        }*/
    }
     /*
    Any methods (functions) that other robot subsystems may need to 
    use should be below here as public methods
    */


     /* 
    Any methods (functions) that ONLY the Drive will use 
    should be implemented as private methods below 
    */
}