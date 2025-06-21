package frc.robot.Subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs {
    /** This returns the voltage the elevator Motor Recieves */
    public double elevatorAppliedVolts = 0.0;
    /** Returns the position of the elevator Motor by how many radians it has rotated */
    public double elevatorPositionRad = 0.0;
    /** /** Returns the position of the elevator Motor by how many meters it has raised */
    public double elevatorPositionM = 0.0;
    /** Returns the velocity of the elevator Motor by how many radians per second it has rotated */
    public double elevatorVelocityRadPerSec = 0.0;
    /**
     * Returns the velocity of the elevator Motor by how many rotations per minute it has rotated
     */
    public double elevatorVelocityRotPerMin = 0.0;
    /** The Current Drawn from the elevator Motor in Amps */
    public double[] elevatorCurrentAmps = new double[] {};
    /** The tempature of the elevator Motor in Celsius */
    public double[] elevatorTempCelsius = new double[] {};
  }

  /** Updates inputs for the Elevator */
  public default void updateInputs(ElevatorIOInputs inputs) {}

  /**
   * Sets the voltage for the Elevator
   *
   * @param volts -12 to 12
   */
  public default void setElevatorVoltage(double volts) {}

  /**
   * Set the upward or downward current
   *
   * @param type
   */
  public default void setElevatorCurrentTypeAndVoltage(int type, double volts) {}

  /**
   * Sets the Brake Mode for the Elevator
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
