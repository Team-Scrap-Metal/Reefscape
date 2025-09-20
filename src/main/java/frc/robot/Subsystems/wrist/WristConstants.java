package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.util.Units;

public class WristConstants {
  public static final int CAN_ID = 19;
  public static final boolean IS_INVERTED = false;
  public static final int STALL_LIMIT_AMPS = 25;
  public static final int FREE_SPIN_LIMIT_AMPS = 20;
  public static final double GEAR_RATIO = 130;

  //   public static double KP = 24.0;
  //   public static double KI = 0.0;
  //   public static double KD = 0.0;
  //   public static double KS = 0.0;
  //   public static double KG = 3.5;
  //   public static double KV = 0.0;
  public static double KP = 16.0;
  public static double KI = 0.0;
  public static double KD = 0.0;
  public static double KS = 0.0;
  public static double KG = 0.4;
  public static double KV = 0.0;
  public static double PID_TOLERANCE_RAD = Units.degreesToRadians(2);
  public static double MAX_VELOCITY = 7.283;
  public static double MAX_ACCELERATION = 16.566371;

  public static final class WristPositions {
    public static final double L1_ROTATION_RAD =
        Units.degreesToRadians(180) + Units.degreesToRadians(90);

    public static final double LEFT_L2_AND_L3_START_ROTATION_RAD =
        Units.degreesToRadians(90) + Units.degreesToRadians(90);
    public static final double LEFT_L2_AND_L3_END_ROTATION_RAD =
        Units.degreesToRadians(120) + Units.degreesToRadians(90);
    public static final double RIGHT_L2_AND_L3_START_ROTATION_RAD =
        Units.degreesToRadians(270) + Units.degreesToRadians(90);
    public static final double RIGHT_L2_AND_L3_END_ROTATION_RAD =
        Units.degreesToRadians(240) + Units.degreesToRadians(90);

    public static final double LEFT_L4_START_ROTATION_RAD =
        Units.degreesToRadians(90) + Units.degreesToRadians(90);
    public static final double LEFT_L4_END_ROTATION_RAD =
        Units.degreesToRadians(135) + Units.degreesToRadians(90);
    public static final double RIGHT_L4_START_ROTATION_RAD =
        Units.degreesToRadians(270) + Units.degreesToRadians(90);
    public static final double RIGHT_L4_END_ROTATION_RAD =
        Units.degreesToRadians(240) + Units.degreesToRadians(90);

    public static final double ALGAE_REMOVAL_RAD =
        Units.degreesToRadians(90) + Units.degreesToRadians(90);

    public static final double FLOOR_INTAKE_RAD =
        Units.degreesToRadians(180) + Units.degreesToRadians(90);
    // NAH ID WIN!!!
    public static final double STOW_ROTATION_RAD =
        Units.degreesToRadians(0.0) + Units.degreesToRadians(90);
    public static final double ALGAE_REMOVAL_ONE_ROTATION_R =
        Units.degreesToRadians(180) + Units.degreesToRadians(90);
    public static final double ALGAE_REMOVAL_TWO_ROTATION_R =
        Units.degreesToRadians(180) + Units.degreesToRadians(90);
    public static final double ALGAE_SCORE_ROTATION_R =
        Units.degreesToRadians(90) + Units.degreesToRadians(90);
  }
}
