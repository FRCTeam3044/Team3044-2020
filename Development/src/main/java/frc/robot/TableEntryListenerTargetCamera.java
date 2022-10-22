/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
//import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TableEntryListenerTargetCamera extends Sensors{

   double[] centerXs;
   double[] defaultValue = new double[0];
   NetworkTable table;
   public static double centerX;

   
   @Override
   public void robotInit() {
     table = NetworkTableInstance.getDefault().getTable("GRIP/mycontoursReport");
   }
   
   @Override
   public void teleopPeriodic() {
       double[] centerX = table.getEntry("centerX").getDoubleArray(defaultValue);
       SmartDashboard.getNumberArray("CenterX", defaultValue);
       }
   }