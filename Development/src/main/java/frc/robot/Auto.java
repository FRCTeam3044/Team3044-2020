package frc.robot;

import com.playingwithfusion.CANVenom.ControlMode;
import edu.wpi.first.wpilibj.Timer;

public class Auto extends RobotSubsystem {

    Timer timer = new Timer();
    String autoSelected = "driveFoward";

    public void autonomousInit() {
        timer.reset();
        timer.start();
    }

    public void autonomousPeriodic() {
        switch(autoSelected) {
            case "driveFoward":
                if (timer.get() < 2.0) {
                    drive(0.5);
                } else {
                    drive(0.0);  
                }  
                break;
            case "shootFromLine":
                //We'll do stuff here when the shooty code gets finished
                break;
            default:
            
        }
        
        
        
        
        //if (timer.get() < 2.0) {

        //} else {

        //}
    }

    private void drive(Double speed) {
        Drive.port_motor_fore.setCommand(ControlMode.Proportional, speed);
        Drive.port_motor_aft.setCommand(ControlMode.Proportional, speed);
        Drive.starboard_motor_fore.setCommand(ControlMode.Proportional, -speed);
        Drive.starboard_motor_aft.setCommand(ControlMode.Proportional, -speed);  
    }
}