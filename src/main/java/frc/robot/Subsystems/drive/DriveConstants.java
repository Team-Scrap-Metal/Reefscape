package frc.robot.Subsystems.drive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class DriveConstants {

  /** Real Constants */
  /** Wheel Radius in Meters */
  public static final double WHEEL_RADIUS_M = Units.inchesToMeters(2);

  /**
   * Chassis Width, distance between the centerline of two adjacent wheels same for length and width
   * because drivetrain is square
   */
  public static final double TRACK_WIDTH_M = Units.inchesToMeters(22);

  /** Gear Ratio for MK4I L3 (Kraken) */
  public static final double GEAR_RATIO = 6.12;

  /** Max Speed the Robot Can Travel in One Linear Direction (m/s) */
  public static final double MAX_LINEAR_SPEED_M_PER_SEC = 5.2; // TODO: Update

  /** Determines how the Motor Acts when On and Disabled */
  public static final boolean IS_BRAKE_MODE = true;

  public static final Translation2d[] getModuleTranslations() {

    double[][] moduleTranslations =
        new double[][] {
          new double[] {
            DriveConstants.TRACK_WIDTH_M / 2.0, -DriveConstants.TRACK_WIDTH_M / 2.0
          }, // Front Right: Module 0
          new double[] {
            DriveConstants.TRACK_WIDTH_M / 2.0, DriveConstants.TRACK_WIDTH_M / 2.0
          }, // Front Left: Module 1
          new double[] {
            -DriveConstants.TRACK_WIDTH_M / 2.0, DriveConstants.TRACK_WIDTH_M / 2.0
          }, // Back Left: Module 2
          new double[] {
            -DriveConstants.TRACK_WIDTH_M / 2.0, -DriveConstants.TRACK_WIDTH_M / 2.0
          } // Back Right: Module 3
        };

    // Translation 2d assumes that the robot front facing is in the positive x
    // direction and the
    // robot left is in the positive y direction
    return new Translation2d[] {
      new Translation2d(
          DriveConstants.TRACK_WIDTH_M / 2.0,
          -DriveConstants.TRACK_WIDTH_M / 2.0), // Front Right: Module 0
      new Translation2d(
          DriveConstants.TRACK_WIDTH_M / 2.0,
          DriveConstants.TRACK_WIDTH_M / 2.0), // Front Left: Module 1
      new Translation2d(
          -DriveConstants.TRACK_WIDTH_M / 2.0,
          DriveConstants.TRACK_WIDTH_M / 2.0), // Back Left: Module 2
      new Translation2d(
          -DriveConstants.TRACK_WIDTH_M / 2.0,
          -DriveConstants.TRACK_WIDTH_M / 2.0) // Back Right: Module 3
    };
  }

  public static enum ABSOLUTE_ENCODER_OFFSET_RAD {
    FRONT_RIGHT(0.245), // Module 0 //TODO: Update
    FRONT_LEFT(-0.318), // Module 1 //TODO: Update
    BACK_LEFT(-0.261), // Module 2 //TODO: Update
    BACK_RIGHT(0.598); // Module 3 //TODO: Update

    public final double OFFSET;

    ABSOLUTE_ENCODER_OFFSET_RAD(double value) {
      OFFSET = value;
    }
  }

  /**
   * Max Speed the Robot Can Rotate (rads/s) Angular Speed is linear speed divided by radius of the
   * "circle" an object moves around (v = wr) The "radius" of the Swerve Drive is equal to half of
   * the distance from one corner to the opposite corner, calculated by sqrt2 * half the track width
   */
  public static final double MAX_ANGULAR_SPEED_RAD_PER_SEC =
      MAX_LINEAR_SPEED_M_PER_SEC / (Math.sqrt(2) * TRACK_WIDTH_M / 2);

  /** Current limiting in amps */
  public static final int CUR_LIM_A = 60;
  /** Enebles the current limit */
  public static final boolean ENABLE_CUR_LIM = true;
  /** Updates encoders every 10 milliseconds */
  public static final int MEASUREMENT_PERIOD_MS = 10;
  /**
   * Within 10% of the desired direction, the joystick is considered to be going in that direction
   */
  public static final double DEADBAND = 0.05;

  /**
   * CAN IDs for motors and encoders /* 1-4: Absolute Encoders, 5-8: Drive Motors, 9-12: Steer
   * Motors, 13: Pigeon
   */
  public static enum ABSOLUTE_ENCODER {
    FRONT_RIGHT(1), // Module 0
    FRONT_LEFT(2), // Module 1
    BACK_LEFT(3), // Module 2
    BACK_RIGHT(4); // Module 3

    public final int ENCODER_ID;

    ABSOLUTE_ENCODER(int ID) {
      ENCODER_ID = ID;
    }
  }

  public enum DRIVE_MOTOR {
    FRONT_RIGHT(5), // Module 0
    FRONT_LEFT(6), // Module 1
    BACK_LEFT(7), // Module 2
    BACK_RIGHT(8); // Module 3

    public final int CAN_ID;

    DRIVE_MOTOR(int value) {
      CAN_ID = value;
    }
  }

  public enum TURN_MOTOR {
    FRONT_RIGHT(9), // Module 0
    FRONT_LEFT(10), // Module 1
    BACK_LEFT(11), // Module 2
    BACK_RIGHT(12); // Module 3

    public final int CAN_ID;

    TURN_MOTOR(int value) {
      CAN_ID = value;
    }
  }

  public static class SimDriveConstants {
    /** Sim Constants */
    /** Moment of inertia of wheel when driving (Also Known As JKG Meters Squared) */
    public static final double DRIVE_MOI_KG_M2 = 0.0003125; // TODO: Update
    /** Moment of inertia of wheel when turning (Also Known As JKG Meters Squared) */
    public static final double STEER_MOI_KG_M2 = 0.0000158025413; // TODO: Update
  }

  public static class NeoCIMModule {
    // PID Constants for Neo Drive
    /**
     * KP represents the constant multiplied by the current error from setpoint (Proportional Error)
     */
    public static final double NEO_KP = 0.0;
    /** KI represents the constant multiplied by the total error from setpoint (Integrated Error) */
    public static final double NEO_KI = 0.0;
    /** KD represents the constant multiplied by the velocity error from setpoint (Derived Error) */
    public static final double NEO_KD = 0.0;

    // Feed Forward Constants for Neo Drive
    /** KS represents the voltage required to overcome static friction */
    public static final double NEO_KS = 0.4;
    /** KV represents the voltage used every second per meter */
    public static final double NEO_KV = 0.4;

    // PID Constants for CIM Steer
    /**
     * KP represents the constant multiplied by the current error from setpoint (Proportional Error)
     */
    public static final double CIM_KP = 5.0; // TODO: Update
    /** KI represents the constant multiplied by the total error from setpoint (Integrated Error) */
    public static final double CIM_KI = 0.0; // TODO: Update
    /** KD represents the constant multiplied by the velocity error from setpoint (Derived Error) */
    public static final double CIM_KD = 0.15; // TODO: Update

    /** Set the inverted for the drive SPARKMAX */
    public static final boolean INVERT_SPARK_MAX = true;
    /** Set the inverted for the turn VictorSPX */
    public static final boolean INVERT_VICTORSPX = false;
  }

  public static class KrakenNEOModule {
    // PID Contstants for Kraken Drive
    public static final double KRAKEN_KP = 0.0;
    /** KI represents the constant multiplied by the total error from setpoint (Integrated Error) */
    public static final double KRAKEN_KI = 0.0;
    /** KD represents the constant multiplied by the velocity error from setpoint (Derived Error) */
    public static final double KRAKEN_KD = 0.0;
    // Feed Forward Constants for Kraken Drive
    // Feed Forward values used in sim: S = 0.4, V = 0.4

    /** KS represents the voltage required to overcome static friction */
    public static final double KRAKEN_KS = 0.4;

    /** KV represents the voltage used every second per meter */
    public static final double KRAKEN_KV = 0.4;

    // PID Constants for Neo Steer
    /**
     * KP represents the constant multiplied by the current error from setpoint (Proportional Error)
     */
    public static final double NEO_KP = 5.0;
    /** KI represents the constant multiplied by the total error from setpoint (Integrated Error) */
    public static final double NEO_KI = 1.2;
    /** KD represents the constant multiplied by the velocity error from setpoint (Derived Error) */
    public static final double NEO_KD = 0.03;

    /** Set the inverted for the drive TalonFX */
    public static final boolean INVERT_TALONFX = false;
    /** Set the inverted for the turn SPARKMAX */
    public static final boolean INVERT_SPARK_MAX = true;
  }
}
