package frc.robot.Subsystems.linkage;

import org.littletonrobotics.junction.AutoLog;

public interface LinkageIO {
  @AutoLog
  public static class LinkageIOInputs {
    public double linkageAppliedVolts = 0.0;
    /** Returns the position of the Propulsion Motor by how many radians it has rotated */
    public double linkagePositionRad = 0.0;
    /** Returns the position of the Propulsion Motor by how many degrees it has rotated */
    public double linkagePositionDeg = 0.0;
    /**
     * Returns the velocity of the Propulsion Motor by how many radians per second it has rotated
     */
    public double linkageVelocityRadPerSec = 0.0;
    /** The Current Drawn from the Propulsion Motor in Amps */
    public double[] linkageCurrentAmps = new double[] {};
    /** The tempature of the Propulsion Motor in Celsius */
    public double[] linkageTempCelsius = new double[] {};
  }

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
