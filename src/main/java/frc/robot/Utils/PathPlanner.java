// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Utils;

import java.io.IOException;

import org.json.simple.parser.ParseException;

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
public class PathPlanner extends SubsystemBase {
  private Drive drive;
  private PoseEstimator pose;
  public PathPlanner(Drive drive, PoseEstimator pose) throws IOException, ParseException {
    RobotConfig config = RobotConfig.fromGUISettings();
    this.drive = drive;
    this.pose = pose;
    // Configure AutoBuilder last
    AutoBuilder.configure(
            pose::getCurrentPose2d, // Robot pose supplier
            pose::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            drive::getChassisSpeed,
            drive::runVelocity, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
                    new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
            ),
            config,
            () -> {
              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );
  }
}
