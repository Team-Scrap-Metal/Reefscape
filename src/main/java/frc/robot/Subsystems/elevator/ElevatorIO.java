package frc.robot.Subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs {}

  /** Updates inputs for the Elevator */
  public default void updateInputs(ElevatorIOInputs inputs) {}

  /**
   * Sets the voltage for the Elevator
   *
   * @param volts -12 to 12
   */
  public default void setElevatorVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Elevator
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
