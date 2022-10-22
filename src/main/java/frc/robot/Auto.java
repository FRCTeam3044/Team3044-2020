package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto extends RobotSubsystem {

    Timer timer = new Timer();
    int autoSelected;
    public static double NominalDriveSpeed = 0.3; 
    public static final int DRIVE_FORWARD = 0;
    public static final int SHOOT_FROM_LINE = 1;
    public static final int DRIVE_DISTANCE = 2;

    public void autonomousInit() {

        timer.reset();
        timer.start();

        NominalDriveSpeed = Double.parseDouble(Robot.RobotConfiguration.getProperty("NominalDriveSpeed"));
    }


    public void autonomousPeriodic() {
        autoSelected = (int)SmartDashboard.getNumber("Auto Selector", DRIVE_FORWARD);
        switch(autoSelected) {
            case DRIVE_FORWARD:
                if (timer.get() < 2.0) {
                    Drive.drive(NominalDriveSpeed, NominalDriveSpeed);
                } else {
                    Drive.drive(0, 0);  
                }  
                break;
            case SHOOT_FROM_LINE:
                if (timer.get() < 1.0) {
                   // Drive.autoAlign();
                } else if (timer.get() < 5.0) {
                    PowerCells.shootPID(2000); 
                } else if (timer.get() < 2.0) {
                    Drive.drive(NominalDriveSpeed*-1, NominalDriveSpeed*-1);
                } else {
                    Drive.drive(0, 0);
                }
                break;
            case DRIVE_DISTANCE:
                Drive.driveDistance(100, 100);
                break;
            default:
                if (timer.get() < 2.0) {Drive.drive(NominalDriveSpeed, NominalDriveSpeed);}
      }
    }
}