/* 
  Team 3044 - Robot 
*/

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import java.util.Properties; 
import java.io.InputStream;
import java.io.FileInputStream; 
import java.util.Map;
import java.util.HashMap;  

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  static final String CONFIG_PATH="/etc/robot.conf";

  public Properties RobotConfiguration = new Properties(); 
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    loadConfiguration(); 
  }

  void loadConfiguration() 
  {
    try (InputStream input = new FileInputStream(CONFIG_PATH)) 
    {
      RobotConfiguration = new Properties();

      RobotConfiguration.load(input);

      input.close();
    }
    catch (java.io.IOException e) 
    {
      System.out.println("Unable to load robot configuration: " + e.getMessage() );
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
  }

  @Override
  public void autonomousInit() {

  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
