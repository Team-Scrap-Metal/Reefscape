package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.util.Units;

public class WristConstants {
  public static final int CAN_ID = 19;
  public static final boolean IS_INVERTED = false;
  public static final int STALL_LIMIT_AMPS = 40;
  public static final int FREE_SPIN_LIMIT_AMPS = 40;
  public static final double GEAR_RATIO = 40;

  public static final double kP = 0.0;
  public static final double kI = 0.0;
  public static final double kD = 0.0;
  public static final double PID_TOLERANCE_RAD = Units.degreesToRadians(1);
  public static final double MAX_VELOCITY = 0.0;
  public static final double MAX_ACCELERATION = 0.0;
}
