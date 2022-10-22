package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//  Review or Remove 14/2/2020
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Sensors extends RobotSubsystem 
{
    public static double currentRotations;
    public static double prevRotations;
    public static double shooterRPM;
    public static double telescopeHeight;

    public  void teleopPeriodic()
    {
        
    } 

    public void robotPeriodic()
    //always checking so that it can be used in auto or test as well as teleop
    {
        shooterRPM = PowerCells.ShootTalon1.getSensorCollection().getQuadratureVelocity();
        telescopeHeight = Climb.Telescope.getSensorCollection().getQuadratureVelocity();

        double LeftSideSpeed = Drive.port_motor_fore.getSpeed();
        double RightSideSpeed = Drive.starboard_motor_fore.getSpeed();

        double l_position = Drive.port_motor_fore.getPosition(); 
        double r_position = Drive.starboard_motor_fore.getPosition();

        SmartDashboard.putNumber("Right Side Speed", RightSideSpeed);
        SmartDashboard.putNumber("Left Side Speed", -LeftSideSpeed);

        SmartDashboard.putNumber("Left Position", l_position ); 
        SmartDashboard.putNumber("Right Position", r_position);

        SmartDashboard.putString("Mode", Robot.mode);
        SmartDashboard.putNumber("Telescope height", telescopeHeight);

    } 
}