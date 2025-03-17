// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
  @AutoLog
  public static class ClimberIOInputs {}

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
