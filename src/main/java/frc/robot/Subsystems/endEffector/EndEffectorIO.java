package frc.robot.Subsystems.endEffector;

import org.littletonrobotics.junction.AutoLog;

public interface EndEffectorIO {
  @AutoLog
  public static class EndEffectorIOInputs {
    /** This returns the voltage the Endeffector Motor Recieves */
    public double endEffectorAppliedVolts = 0.0;
    /** Returns the position of the Endeffector Motor by how many radians it has rotated */
    public double endEffectorPositionRad = 0.0;
    /**
     * Returns the velocity of the Endeffector Motor by how many radians per second it has rotated
     */
    public double endEffectorVelocityRadPerSec = 0.0;
    /**
     * Returns the velocity of the Endeffector Motor by how many rotations per minute it has rotated
     */
    public double endEffectorVelocityRotPerMin = 0.0;
    /** The Current Drawn from the Endeffector Motor in Amps */
    public double[] endEffectorCurrentAmps = new double[] {};
    /** The tempature of the Endeffector Motor in Celsius */
    public double[] endEffectorTempCelsius = new double[] {};
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
