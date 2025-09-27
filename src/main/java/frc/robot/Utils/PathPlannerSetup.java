// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Utils;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Subsystems.drive.Drive;

/** Add your docs here. */
public final class PathPlannerSetup {

  private PathPlannerSetup() {}
  public static void configure(Drive drive, PoseEstimator pose) {
    RobotConfig cfg;
    try { cfg = RobotConfig.fromGUISettings(); } 
    catch (Exception e) { throw new RuntimeException("PathPlanner RobotConfig"); }


    // Load the RobotConfig from the GUI settings. You should probably
    // store this in your Constants file

    SmartDashboard.putString("Running PathPlanner", "running");
    

    AutoBuilder.configure(
        pose::getCurrentPose2d,
        pose::resetPose,
        drive::getChassisSpeed,
        drive::runVelocity,
        new PPHolonomicDriveController(
            new PIDConstants( // Translation PID constants
                0.0, // TODO: Update Values && put in constants
                0, // TODO: Update Values && put in constants
                0), // TODO: Update Values && put in constants
            new PIDConstants( // Rotation PID constants
                4, // TODO: Update Values && put in constants
                0, // TODO: UpdateD Values && put in constants
                0)), // TODO: Update Values && put in constants
        // DriveConstants.MAX_LINEAR_SPEED_M_PER_SEC, // Max module speed, in m/s
        // DriveConstants.TRACK_WIDTH_M, // Drive base radius in meters. Distance from robot center
        // to
        // furthest module.
        cfg,
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          if (RobotStateConstants.getAlliance().isPresent()) {
            return RobotStateConstants.getAlliance().get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        drive);
  }
}
