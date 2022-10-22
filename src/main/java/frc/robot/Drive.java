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
    static double deadband = 0.025;

    // change these based on cam data output
    static double maxX = 650;
    static double minX = 630;

    // used to check if ready for shooter to fire
    static boolean aligned;

    // Constructors
    public Drive() {
        robotInit();
    }

    public void robotInit() {

        int port_motor_fore_canid = Integer.parseInt(
            Robot.RobotConfiguration.getProperty("port_motor_fore_canid", "2"));
        int port_motor_aft_canid = Integer.parseInt(
            Robot.RobotConfiguration.getProperty("port_motor_aft_canid", "4"));
        int starboard_motor_fore_canid = Integer.parseInt(
            Robot.RobotConfiguration.getProperty("starboard_motor_fore_canid", "1"));
        int starboard_motor_aft_canid = Integer.parseInt(
            Robot.RobotConfiguration.getProperty("starboard_motor_aft_canid", "3"));

        port_motor_fore = new CANVenom(port_motor_fore_canid);
        port_motor_aft = new CANVenom(port_motor_aft_canid);
        starboard_motor_fore = new CANVenom(starboard_motor_fore_canid);
        starboard_motor_aft = new CANVenom(starboard_motor_aft_canid);

    }

    public void teleopInit() {
    }

    public void robotPeriodic() {
    }

    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() 
    {
        if (Robot.Driver1.getTriggerAxis(Hand.kRight) > .25 )
        {
            driveDistance(3, 3);
        }
        
        //autoAlign();
        aligned = false;
        double Drive1LeftStickYaxis = Robot.Driver1.getY(Hand.kLeft);
        double Drive1RightStickYaxis = Robot.Driver1.getY(Hand.kRight);
        drive(Drive1LeftStickYaxis, Drive1RightStickYaxis);
        SmartDashboard.updateValues();
    
    }

    public void testPeriodic() 
    {

    }

    public void autonomousInit() 
    {
    }

    

    public static void drive(double left, double right) 
    {
        if (Math.abs(left) > deadband) {
            port_motor_fore.setCommand(ControlMode.SpeedControl,
                    (Math.pow(left, 3))); /* for an exponential power curve */
        } else {
            port_motor_fore.setCommand(ControlMode.VoltageControl, 0);
        }
        if (Math.abs(right) > deadband) {
            starboard_motor_fore.setCommand(ControlMode.SpeedControl,
                    (Math.pow(-right, 3))); /* for an exponential power curve */
        } else {
            starboard_motor_fore.setCommand(ControlMode.VoltageControl, 0);
        }
        port_motor_aft.follow(port_motor_fore);
        starboard_motor_aft.follow(port_motor_fore);
    }

    public static void driveDistance(double leftDistance, double rightDistance) {
        port_motor_fore.setCommand(ControlMode.PositionControl, leftDistance);
        starboard_motor_fore.setCommand(ControlMode.PositionControl, rightDistance);
        port_motor_aft.follow(port_motor_fore);
        starboard_motor_aft.follow(port_motor_fore);
    }

    /*
    public static void autoAlign() {
        // basic auto align concept
        boolean minXgood = (minX >= TableEntryListener.centerX);
        boolean maxXgood = (maxX <= TableEntryListener.centerX);
        if (maxXgood == true && minXgood == true) {
            aligned = true;
        } else if (maxXgood == true) {
                aligned = false;
                port_motor_fore.setCommand(ControlMode.VoltageControl, .3);
                port_motor_aft.setCommand(ControlMode.VoltageControl, .3);
                aligned = false;
        } else if (minXgood == true) {
                aligned = false;
                starboard_motor_fore.setCommand(ControlMode.VoltageControl, -.3);
                starboard_motor_aft.setCommand(ControlMode.VoltageControl, -.3);
                aligned = false;
        }*/ 
    
     /*
    Any methods (functions) that other robot subsystems may need to 
    use should be below here as public methods
    */


     /* 
    Any methods (functions) that ONLY the Drive will use 
    should be implemented as private methods below 
    */
}