package frc.robot.Subsystems.wrist;

import org.littletonrobotics.junction.AutoLog;

public interface WristIO {
  @AutoLog
  public static class WristIOInputs {
    /** This returns the voltage the Wrist Motor Recieves */
    public double wristAppliedVolts = 0.0;
    /**
    * Returns the position of the absoltute encoder in Radians (Used to make sure wrist zero doesnt
    * change on enable
    */
    public double wristAbsolutePositionRad = 0.0;
    /**
    * Returns the position of the absoltute encoder in degrees (Used to make sure wrist zero doesnt
    * change on enable
    */
    public double wristAbsolutePositionDeg = 0.0;
    /** Returns the position of the Wrist Motor by how many radians it has rotated */
    public double wristPositionRad = 0.0;
    /** Returns the position of the Wrist Motor by how many degrees it has rotated */
    public double wristPositionDeg = 0.0;
    /** Returns the velocity of the Wrist Motor by how many radians per second it has rotated */
    public double wristVelocityRadPerSec = 0.0;
    /** The Current Drawn from the Wrist Motor in Amps */
    public double[] wristCurrentAmps = new double[] {};
    /** The tempature of the Wrist Motor in Celsius */
    public double[] wristTempCelsius = new double[] {};
  }

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
