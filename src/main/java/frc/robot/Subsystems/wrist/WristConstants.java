package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.util.Units;

public class WristConstants {
  public static final int CAN_ID = 19;
  public static final boolean IS_INVERTED = false;
  public static final int STALL_LIMIT_AMPS = 40;
  public static final int FREE_SPIN_LIMIT_AMPS = 40;
  public static final double GEAR_RATIO = 40;

  public static final double KP = 34.0;
  public static final double KI = 0.0;
  public static final double KD = 0.0;
  public static final double PID_TOLERANCE_RAD = Units.degreesToRadians(2);
  public static final double MAX_VELOCITY = 6 * Math.PI / 3;
  public static final double MAX_ACCELERATION = MAX_VELOCITY * 2;

  public static final double L1_ROTATION_RAD = Units.degreesToRadians(0.0);

  public static final double L2_AND_L3_START_ROTATION_RAD = Units.degreesToRadians(0.0);
  public static final double L2_AND_L3_END_ROTATION_RAD = Units.degreesToRadians(0.0);

  public static final double L4_START_ROTATION_RAD = Units.degreesToRadians(0.0);
  public static final double L4_END_ROTATION_RAD = Units.degreesToRadians(0.0);

  public static final double ALGAE_REMOVAL_RAD = Units.degreesToRadians(0.0);

  // NAH ID WIN!!!
  public static final double STOW_ROTATION_RAD = Units.degreesToRadians(0.0);
}
