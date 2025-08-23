package frc.robot.Utils;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.drive.Drive;
import frc.robot.Subsystems.drive.DriveConstants;
import frc.robot.Subsystems.gyro.Gyro;

/** This class handels the odometry and locates the robots current position */
public class PoseEstimator extends SubsystemBase {
  /**
   * Increase the numbers to trust the model's state estimate less it is a matrix in form of [x, y,
   * theta] or meters, meters, radians
   */
  public static Vector<N3> stateStandardDevs = VecBuilder.fill(0.1, 0.1, 0.1);

  public static Vector<N3> visionStandardDevs = VecBuilder.fill(0.1, 0.1, 9999999);

  private SwerveDrivePoseEstimator poseEstimator;
  private Drive drive;
  private Gyro gyro;
  private Field2d field2d;
  LimelightHelpers.PoseEstimate limelightMeasurement;
  LimelightHelpers.PoseEstimate mt2 =
      LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");

  public PoseEstimator(Drive drive, Gyro gyro) {

    field2d = new Field2d();
    SmartDashboard.putData(field2d);
    this.drive = drive;
    this.gyro = gyro;

    poseEstimator =
        new SwerveDrivePoseEstimator(
            new SwerveDriveKinematics(DriveConstants.getModuleTranslations()),
            gyro.getYaw(),
            drive.getSwerveModulePositions(),
            new Pose2d(new Translation2d(2.15, 4.18), new Rotation2d()),
            stateStandardDevs,
            visionStandardDevs);

    int[] validIDs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    LimelightHelpers.SetFiducialIDFiltersOverride("limelight", validIDs);
    LimelightHelpers.setCameraPose_RobotSpace(
        "",
        // 0.33655, // Forward offset (meters)
        // 0.14, // Side offset (meters)

        0.14,
        -0.33655,
        0.18415, // Height offset (meters)
        // 0,
        // 0,
        // 0,
        90, // Roll (degrees)
        0, // Pitch (degrees)
        90 // Yaw (degrees)
        );

    // if our axngular velocity is greater than 360 degrees per second, ignore vision updates

    // limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");
    // mt1 = LimelightHelpers.getB/otPoseEstimate_wpiBlue("limelight");
    // mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");

    // SmartDashboard.putString(
    //     "Limelight",
    // LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight").toString());
  }

  @Override
  public void periodic() {
    // When ran on the real robot it would overload the command scheduler, causing input delay from
    // joystick to driving
    field2d.setRobotPose(getCurrentPose2d());
    // field2d.setRobotPose(getCurrentPose2d().getX(), getCurrentPose2d().getY(), getRotation());
    SmartDashboard.putNumber("mt2", mt2.tagCount);
    SmartDashboard.putString("running", "running");

    LimelightHelpers.SetRobotOrientation(
        "limelight", gyro.getYaw().getDegrees() - 180, 0, 0, 0, 0, 0);
    boolean doRejectUpdate = false;
    if (Math.abs(gyro.getRate()) > 360) {
      doRejectUpdate = true;
    }
    if (mt2.tagCount == 0) {
      doRejectUpdate = true;
    }
    if (!doRejectUpdate) {
      poseEstimator.setVisionMeasurementStdDevs(VecBuilder.fill(0.1, 0.1, 999999));
      poseEstimator.addVisionMeasurement(mt2.pose, mt2.timestampSeconds);
      System.out.println(getCurrentPose2d());
      // field2d.setRobotPose(mt2.pose);
      // System.out.println(mt2.tagCount);
      //   System.out.println(
      //
      // NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(0));
    }
    System.out.println(gyro.getYaw().getDegrees());
    // System.out.println(this.getRotation().getDegrees());

    poseEstimator.updateWithTime(
        Timer.getFPGATimestamp(), drive.getRotation(), drive.getSwerveModulePositions());
  }

  /**
   * @return the current pose in a Pose2d
   */
  public Pose2d getCurrentPose2d() {
    return poseEstimator.getEstimatedPosition();
  }
  /**
   * Resets the pose
   *
   * @param currentPose2d
   */
  public void resetPose(Pose2d currentPose2d) {
    poseEstimator.resetPosition(gyro.getYaw(), drive.getSwerveModulePositions(), currentPose2d);
  }
  /**
   * @return the rotation in a Rotation2d in degrees
   */
  public Rotation2d getRotation() {
    return poseEstimator.getEstimatedPosition().getRotation();
  }
}
