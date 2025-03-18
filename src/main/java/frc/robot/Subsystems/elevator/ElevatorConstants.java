package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.util.Units;

public class ElevatorConstants {

  public static final int LEFT_CANID = 15;
  public static final int RIGHT_CANID = 16;
  public static final boolean LEFT_IS_INVERTED = false;
  public static final boolean RIGHT_IS_INVERTED = false;
  public static final int STALL_LIMIT_AMPS = 40;
  public static final int FREESPIN_LIMIT_AMPS = 40;
  public static final double GEAR_RATIO = 4;

  public static final double kP = 0.0;
  public static final double kI = 0.0;
  public static final double kD = 0.0;
  public static final double PID_TOLERANCE_RAD = Units.degreesToRadians(1);
}
