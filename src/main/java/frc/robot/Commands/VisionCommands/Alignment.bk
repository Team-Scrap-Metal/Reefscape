// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.VisionCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.drive.Drive;
import frc.robot.Utils.LimelightHelpers;
// import frc.robot.Utils.LimelightHelpers.PoseEstimate;
import frc.robot.Utils.LimelightHelpers.RawFiducial;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Alignment extends Command {
  private Drive drive;
  // private int[] validIDS = {7,8,9,10,11,17,18,19,20,21,22};
  // private LimelightHelpers limelightHelpers;
  /** Creates a new Alignment. */
  public Alignment(Drive drive) {
    this.drive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // drive.setRawRobotRel(0, 0, 0);
    // Stop the robot for auto align purposes.
    drive.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RawFiducial[] fiducials = LimelightHelpers.getRawFiducials("");
    for (RawFiducial fiducial : fiducials) {
      int id = fiducial.id; // Tag ID
      double txnc = fiducial.txnc; // X offset (no crosshair)
      // double tync = fiducial.tync;             // Y offset (no crosshair)
      // double ta = fiducial.ta;                 // Target area
      // double distToCamera = fiducial.distToCamera;  // Distance to camera
      // double distToRobot = fiducial.distToRobot;    // Distance to robot
      // double ambiguity = fiducial.ambiguity;   // Tag pose ambiguity

      if (id % 2 == 0) {
        // Case for even ids

      } else {
        // Case for odd ids

      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drive.setRawRobotRel(0, 0, 0);
    // Once the robot is done executing its tasks,
    // it will stop
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
