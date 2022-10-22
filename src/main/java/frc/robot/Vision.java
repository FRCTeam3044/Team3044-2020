package frc.robot; 

//import org.opencv.core.Rect;

//import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

//Opencv import may not be needed as all actual image processing will be offloaded

public class Vision extends RobotSubsystem
{
    public static final int HUD_MODE_VERTICAL_HIGH = 0; 
    public static final int HUD_MODE_VERTICAL_LOW = 1; 
    public static final int HUD_MODE_VERTICAL_CENTER =-1; 

    private int hudVerticalMode = Vision.HUD_MODE_VERTICAL_CENTER; 

    int highYOffset=0; 
    int lowYOffset = 0; 


    public static NetworkTable HUDNetworkTable ;    

    public void SetHUDVerticalMode(final int hudMode)
    {
        hudVerticalMode=hudMode; 
    }

    public Vision() 
    {
        robotInit();
    }

    public void teleopPeriodic()
    {
        if (Robot.Driver2.getBButtonPressed())
        {
            hudVerticalMode = HUD_MODE_VERTICAL_HIGH;
        }

        if (Robot.Driver2.getXButtonPressed())
        {
            hudVerticalMode = HUD_MODE_VERTICAL_LOW; 
        }


    }

    public void testPeriodic()
    {
        teleopPeriodic();
    }

    public void robotInit() {
        
        CameraServer.getInstance().startAutomaticCapture();
        initHUD();
    
    }

    void initHUD()
    {

        NetworkTableInstance.getDefault().setServer( Robot.RobotConfiguration.getProperty("RobotIP","10.30.44.2"));
        NetworkTableInstance.getDefault().setServerTeam(Robot.TEAM_NUMBER);
        HUDNetworkTable = NetworkTableInstance.getDefault().getTable("HUDNetworkTable");
        HUDNetworkTable.getEntry("XOffset").setDefaultDouble(0); 
        HUDNetworkTable.getEntry("YOffset").setDefaultDouble(0); 
        highYOffset= Integer.parseInt( Robot.RobotConfiguration.getProperty("hud_high_y_offset", "0"));
        lowYOffset = Integer.parseInt( Robot.RobotConfiguration.getProperty("hud_low_y_offset", "0"));
    }

    public void robotPeriodic()
    {   
        updateHUD(); 
    }

    void updateHUD()
    {
        switch (hudVerticalMode)
        {
            case Vision.HUD_MODE_VERTICAL_HIGH:
                HUDNetworkTable.getEntry("YOffset").setDouble(this.highYOffset); 
                break; 
            case Vision.HUD_MODE_VERTICAL_LOW:
                HUDNetworkTable.getEntry("YOffset").setDouble(this.lowYOffset); 
                break; 
            case Vision.HUD_MODE_VERTICAL_CENTER: 
                HUDNetworkTable.getEntry("YOffset").setDouble(0); 
                break; 
            default: 
                System.err.println("Invalid HUD mode switch");
                break; 
        }
        HUDNetworkTable.getEntry("XOffset").setDouble(0); 
    }

    
     
}