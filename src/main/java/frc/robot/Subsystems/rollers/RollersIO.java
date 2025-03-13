package frc.robot.Subsystems.rollers;

import org.littletonrobotics.junction.AutoLog;

public interface RollersIO {
  @AutoLog
  public static class RollersIOInputs {}

  /** Updates inputs for the Rollers */
  public default void updateInputs(RollersIOInputs inputs) {}

  /**
   * Sets the voltage for the Rollers
   *
   * @param volts -12 to 12
   */
  public default void setRollersVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Rollers
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
