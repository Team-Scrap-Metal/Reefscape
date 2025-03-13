package frc.robot.Subsystems.wrist;

import org.littletonrobotics.junction.AutoLog;

public interface WristIO {
  @AutoLog
  public static class WristIOInputs {}

  /** Updates inputs for the Wrist */
  public default void updateInputs(WristIOInputs inputs) {}

  /**
   * Sets the voltage for the Wrist
   *
   * @param volts -12 to 12
   */
  public default void setWristVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Wrist
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
