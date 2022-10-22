package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.CounterBase.EncodingType;


/**
 * Add your docs here.
 */
public class Sensors extends RobotSubsystem {

//Encoder Shooter = new Encoder(0, 1, true, EncodingType.k1X);
    //(#, #, inverted(boolean), encoder type) #X is the number of times it checks per cycle
    //# refers to DIO pins
public static double currentRotations;
public static double prevRotations;
public static double shooterRPM;


public  void teleopPeriodic()
    {} 

    public void robotPeriodic()
    //always checking so that it can be used in auto or test as well as teleop
    {
        // = Shooter.getPeriod();
        //.getPeriod used for number of pulses a second
        //*60 to convert Rotation per Second to per minute
        //System.out.println(ShootSpeedRPS*60);

        shooterRPM = PowerCells.ShootTalon.getSensorCollection().getQuadratureVelocity();

        double LeftSideSpeed = Drive.port_motor_fore.getSpeed();
        double RightSideSpeed = Drive.starboard_motor_fore.getSpeed();
        SmartDashboard.putNumber("Right Side Speed", RightSideSpeed);
        SmartDashboard.putNumber("Left Side Speed", -LeftSideSpeed);
    } 
}