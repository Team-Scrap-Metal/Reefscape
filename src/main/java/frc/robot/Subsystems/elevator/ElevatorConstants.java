package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.util.Units;

public class ElevatorConstants {
  // NAH ID WIN!!!
  public static final int LEFT_CANID = 15;
  public static final int RIGHT_CANID = 16;
  public static final boolean LEFT_IS_INVERTED = false;
  public static final boolean RIGHT_IS_INVERTED = false;
  public static final int STALL_LIMIT_AMPS = 40;
  public static final int FREESPIN_LIMIT_AMPS = 40;
  public static final double GEAR_RATIO = 4;
  public static final double LINEAR_CONSTANTS_M = 0.0; // TODO: Update

  public static class ElevatorControls{
  public static double KP = 2.0;
  public static double KI = 0.0;
  public static double KD = 0.0;
  public static double PID_TOLERANCE_M = Units.inchesToMeters(5);
  public static double MAX_VELOCITY = 0.25;
  public static double MAX_ACCELERATION = MAX_VELOCITY * 2;
  public static double KS = 0.0;
  public static double KG = 0.25;
  public static double KV = 0.0;
  public static double KA = 0.0;
  }

  public static final class ElevatorPositions{

  public static final double CARRIAGE_HEIGHT_OFF_FLOOR_M = Units.inchesToMeters(5);
  public static final double L1_START_HEIGHT_M = 0.0;
  public static final double L1_END_HEIGHT_M = 0.0;

  // l2 is 2ft 7 7/8 in tall
  public static final double L2_START_HEIGHT_M = Units.feetToMeters(2) + Units.inchesToMeters(10);
  public static final double L2_END_HEIGHT_M = Units.feetToMeters(2) + Units.inchesToMeters(8);

  // l3 is 3ft ll 5/8 inch tall
  public static final double L3_START_HEIGHT_M = Units.feetToMeters(4) + Units.inchesToMeters(2);
  public static final double L3_END_HEIGHT_M = Units.feetToMeters(3) + Units.inchesToMeters(11);

  // l4 is 6ft tall
  public static final double L4_START_HEIGHT_M = Units.feetToMeters(6) + Units.inchesToMeters(2);
  public static final double L4_END_HEIGHT_M = Units.feetToMeters(6) + Units.inchesToMeters(0);

  // Opening from floor is 3ft
  public static final double CORAL_STATION_HEIGHT_M = Units.feetToMeters(3);
  public static final double GROUND_INTAKE_HEIGHT_M = 0.0;
}
}
