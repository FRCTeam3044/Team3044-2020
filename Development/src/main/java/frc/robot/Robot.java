package frc.robot;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.TimedRobot; 


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static XboxController Driver1 = new XboxController(0);
  public static XboxController Driver2 = new XboxController(1);
  
  // This file can be edited in-place on the robot with WinSCP 
  // or, copied over w/ SCP. 
  static final String CONFIG_PATH="/etc/robot.conf";
  public static Properties RobotConfiguration = new Properties(); 
  public static boolean ConfigLoaded = false; 

  
  public static Vision VisionSystem; 
  public static Climb ClimbSystem; 
  public static Drive DriveSystem; 
  public static Sensors SensorSystem; 
  public static PowerCells PowerCellsSystem;  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    loadConfiguration(); 

    initalizeSystems();

  
  }

  public String getRobotName()
  {
    if (RobotConfiguration != null)
    {
        return RobotConfiguration.getProperty("RobotName", "I don't know who I am. Change this by adding a 'RobotName=' line in " + 
        CONFIG_PATH + " on the robot's filesystem :) ");
    }
    else
    {
      return "No configuration loaded from " + CONFIG_PATH;
    }
  }

  // This method should (a) initalize the Subsystems collection and 
  // (b) call robotInit on all Subsystem modules 
  void initalizeSystems()
  {
    Robot.SensorSystem = new Sensors();
    Robot.VisionSystem = new  Vision(); 
    Robot.DriveSystem =  new Drive();
    Robot.ClimbSystem =  new Climb();
    Robot.PowerCellsSystem =  new PowerCells();
  }

  void loadConfiguration() 
  {
    try (InputStream input = new FileInputStream(CONFIG_PATH))
    //when debugging will throw error because the path only exist on the rio 
    {
      RobotConfiguration = new Properties();
      RobotConfiguration.load(input);
      input.close();
      ConfigLoaded=true; 
      System.out.println("Configuration loaded from " + CONFIG_PATH);
      
      if (RobotConfiguration != null) { 
      RobotConfiguration.forEach((k,v) -> System.out.println(k + "=" + v));} 
      //when debugging will throw error because the path only exist on the rio 
    }
    catch (java.io.IOException e) 
    {
      System.out.println("Unable to load robot configuration from " + CONFIG_PATH + ": " + e.getMessage() );
      
      ConfigLoaded = false; 

    }
  }  
  
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Robot.SensorSystem.robotPeriodic();
    Robot.VisionSystem.robotPeriodic();
    Robot.DriveSystem.robotPeriodic();
    Robot.ClimbSystem.robotPeriodic();
    Robot.PowerCellsSystem.robotPeriodic(); 
  }
  /**
   * This function is called once at the start of teleoperated.
   */
  @Override
  public void teleopInit()
  {
    System.out.println(getRobotName() +  " entering teleoperated mode!");
    Robot.SensorSystem.teleopInit();
    Robot.VisionSystem.teleopInit();
    Robot.DriveSystem.teleopInit();
    Robot.ClimbSystem.teleopInit();
    Robot.PowerCellsSystem.teleopInit();
  }
  /**
   * This function is called once at the start of autonomous.
   */
  @Override
  public void autonomousInit() {

    System.out.println(getRobotName() +  " entering autonomous mode!");
    Robot.SensorSystem.autonomousInit();
    Robot.VisionSystem.autonomousInit();
    Robot.DriveSystem.autonomousInit();
    Robot.ClimbSystem.autonomousInit();
    Robot.PowerCellsSystem.autonomousInit();
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Robot.SensorSystem.autonomousPeriodic();
    Robot.VisionSystem.autonomousPeriodic();
    Robot.DriveSystem.autonomousPeriodic();
    Robot.ClimbSystem.autonomousPeriodic();
    Robot.PowerCellsSystem.autonomousPeriodic();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Robot.SensorSystem.teleopPeriodic();
    Robot.VisionSystem.teleopPeriodic();
    Robot.DriveSystem.teleopPeriodic();
    Robot.ClimbSystem.teleopPeriodic();
    Robot.PowerCellsSystem.teleopPeriodic();
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Robot.SensorSystem.testPeriodic();
    Robot.VisionSystem.testPeriodic();
    Robot.DriveSystem.testPeriodic();
    Robot.ClimbSystem.testPeriodic();
    Robot.PowerCellsSystem.testPeriodic();
  }
}