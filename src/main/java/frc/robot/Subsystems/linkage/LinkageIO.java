package frc.robot.Subsystems.linkage;

import org.littletonrobotics.junction.AutoLog;

public interface LinkageIO {
  @AutoLog
  public static class LinkageIOInputs {}

  /** Updates inputs for the Linkage */
  public default void updateInputs(LinkageIOInputs inputs) {}

  /**
   * Sets the voltage for the Linkage
   *
   * @param volts -12 to 12
   */
  public default void setLinkageVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Linkage
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
