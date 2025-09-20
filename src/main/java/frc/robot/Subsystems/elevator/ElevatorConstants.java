package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.util.Units;

public class ElevatorConstants {
  // NAH ID WIN!!!
  public static final int LEFT_CANID = 15;
  public static final int RIGHT_CANID = 16;
  public static final boolean LEFT_IS_INVERTED = false;
  public static final boolean RIGHT_IS_INVERTED = false;
  public static final int STALL_LIMIT_UP_AMPS = 60;
  public static final int FREESPIN_LIMIT_UP_AMPS = 50;
  // public static final int STALL_LIMIT_DOWN_AMPS = 0;
  // public static final int FREESPIN_LIMIT_DOWN_AMPS = 0;
  public static final double GEAR_RATIO = 4.8;
  public static final double LINEAR_CONSTANTS_M = 0.0; // TODO: Update

  public static class ElevatorControls {
    public static double KP = 12.0;
    public static double KI = 0.0;
    public static double KD = 0.0;
    public static double PID_TOLERANCE_M = Units.inchesToMeters(1);
    public static double MAX_VELOCITY = 1.5;
    public static double MAX_ACCELERATION = 0.9;
    public static double KS = 1.0;
    public static double KG = 0.75;
    public static double KV = 0.0;
    public static double KA = 0.0;
  }

  public static final class ElevatorPositions {

    public static final double CARRIAGE_HEIGHT_OFF_FLOOR_M = Units.inchesToMeters(4);
    public static final double L1_START_HEIGHT_M = Units.inchesToMeters(20.0);

    // l2 is 2ft 7 7/8 in tall
    public static final double L2_HEIGHT_M = Units.inchesToMeters(32);

    // l3 is 3ft ll 5/8 inch tall
    public static final double L3_HEIGHT_M = Units.inchesToMeters(47);

    // l4 is 6ft tall
    public static final double L4_START_HEIGHT_M = Units.inchesToMeters(71);
    public static final double L4_END_HEIGHT_M = Units.inchesToMeters(64);

    // Opening from floor is 3ft
    public static final double CORAL_STATION_HEIGHT_M = Units.inchesToMeters(30);
    public static final double GROUND_INTAKE_HEIGHT_M = 0.0;

    public static final double ALGAE_REMOVAL_ONE_HEIGHT_M = Units.inchesToMeters(26);
    public static final double ALGAE_REMOVAL_TWO_HEIGHT_M = Units.inchesToMeters(42);

    public static final double ALGAE_SCORE_HEIGHT_M = Units.inchesToMeters(70);
  }
}
