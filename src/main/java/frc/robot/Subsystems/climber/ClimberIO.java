// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
  @AutoLog
  public static class ClimberIOInputs {
    /** This returns the voltage the climber Motor Recieves */
    public double climberAppliedVolts = 0.0;
    /**
     * Returns the position of the absoltute encoder in Radians (Used to make sure climber zero
     * doesnt change on enable
     */
    /** Returns the position of the climber Motor by how many radians it has rotated */
    public double climberPositionRad = 0.0;
    /** Returns the position of the climber Motor by how many degrees it has rotated */
    public double climberPositionDeg = 0.0;
    /** Returns the velocity of the climber Motor by how many radians per second it has rotated */
    public double climberVelocityRadPerSec = 0.0;
    /** The Current Drawn from the climber Motor in Amps */
    public double[] climberCurrentAmps = new double[] {};
    /** The tempature of the climber Motor in Celsius */
    public double[] climberTempCelsius = new double[] {};
  }

  /** Updates inputs for the Climber */
  public default void updateInputs(ClimberIOInputs inputs) {}

  /**
   * Sets the voltage for the Climber
   *
   * @param volts -12 to 12
   */
  public default void setClimberVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Climber
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
