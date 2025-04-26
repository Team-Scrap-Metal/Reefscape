package frc.robot.Subsystems.endEffector;

import org.littletonrobotics.junction.AutoLog;

public interface EndEffectorIO {
  @AutoLog
  public static class EndEffectorIOInputs {
  }
  /** Updates inputs for the EndEffector */
  public default void updateInputs(EndEffectorIOInputs inputs) {}

  /**
   * Sets the voltage for the EndEffector
   *
   * @param volts -12 to 12
   */
  public default void setEndEffectorVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the EndEffector
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
