package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PowerCells extends RobotSubsystem
{
    /* 
    The components relating to ONLY the Intake subsystem 
    should be defined as class variables here 
    */
    public static TalonSRX ShootTalon = new TalonSRX(1);
    
    public double shooterPower = 0;
    PIDController ShooterPID = new PIDController(0.000025, 0, 0);
    public double shooterRPM = 0;

    //Constructors     
    public PowerCells() {
        robotInit();
    }


    /*
    All required "periodic" methods should be implemented below here 
    */
    public  void robotInit()
    {}
    
    public  void teleopInit()
    {
        
    }

    public  void robotPeriodic()
    {} 

    public  void autonomousPeriodic()
    {}

    public  void teleopPeriodic()
    {   
        if (Robot.Driver2.getBButtonPressed() == true){
            shooterPower += 2000;
        }
        if (Robot.Driver2.getAButtonPressed() == true) {
            shooterPower -= 2000;
        }

        shoot(shooterPower);
        displayValues();

    //OLD STUFF - Reformat or delete
        
    /*shooterPower = Robot.Driver2.getTriggerAxis(Hand.kRight)*32000;
    ShootTalon.set(ControlMode.PercentOutput, shooterPower/20);
    Sensors.prevRotations = Sensors.currentRotations;
    Sensors.currentRotations = ShootTalon.getSensorCollection().getQuadraturePosition();
        if (((Sensors.currentRotations-Sensors.prevRotations)*50)/4096 > 0) {
        Sensors.shooterRPM = ((Sensors.currentRotations-Sensors.prevRotations)*50)/4096;
    }
    SmartDashboard.putNumber("Rotations", Sensors.currentRotations/4096);
    SmartDashboard.putNumber("Rotations per min.", Sensors.shooterRPM);
    ShootTalon.set(ControlMode.PercentOutput, ShooterPID.calculate(shooterRPM, shooterPower));
    */
    } 

    public  void testPeriodic()
    {}

    public void autonomousInit()
    {}

    /*
    Any methods (functions) that other robot subsystems may need to 
    use should be below here as public methods
    */
    public boolean isRunning() // This is just an example
    {
        return true; //It can never be stopped.
    }

    public void shoot(Double power) {
        ShootTalon.set(ControlMode.PercentOutput, ShooterPID.calculate(Sensors.shooterRPM, power));
        if (Sensors.shooterRPM < power*0.9*0.36967) {
            SmartDashboard.putBoolean("Shooter at speed", false);
        } else {
            SmartDashboard.putBoolean("Shooter at speed", true);
        }
    }

    private void displayValues() {
        SmartDashboard.putNumber("Shooter Power (Target RPM)", shooterPower);
        SmartDashboard.putNumber("Velocity from Encoder", Sensors.shooterRPM);
        SmartDashboard.putNumber("Calculated Power", ShooterPID.calculate(Sensors.shooterRPM, shooterPower));
        SmartDashboard.updateValues();
    }

    /* 
    Any methods (functions) that ONLY the intake will use 
    should be implemented as private methods below 
    
    private void startIntake() // This is just an example
    {}

    private void stopIntake() // This is just an example
    {}
    */
    
}