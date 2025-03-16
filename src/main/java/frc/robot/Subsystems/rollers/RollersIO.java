package frc.robot.Subsystems.rollers;

import org.littletonrobotics.junction.AutoLog;

public interface RollersIO {
  @AutoLog
  public static class RollersIOInputs {
    /** This returns the voltage the Roller Motor Recieves */
    public double rollerAppliedVolts = 0.0;
    /** Returns the position of the roller Motor by how many radians it has rotated */
    public double rollerPositionRad = 0.0;
    /** Returns the velocity of the roller Motor by how many radians per second it has rotated */
    public double rollerVelocityRadPerSec = 0.0;
    /** Returns the velocity of the roller Motor by how many rotations per minute it has rotated */
    public double rollerVelocityRotPerMin = 0.0;
    /** The Current Drawn from the roller Motor in Amps */
    public double[] rollerCurrentAmps = new double[] {};
    /** The tempature of the roller Motor in Celsius */
    public double[] rollerTempCelsius = new double[] {};
  }

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
