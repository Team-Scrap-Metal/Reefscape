// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

/** Add your docs here. */
public class ElevatorIOSim implements ElevatorIO {
  private final ElevatorSim elevatorSim;

  public ElevatorIOSim() {
    elevatorSim =
        new ElevatorSim(
            DCMotor.getNeoVortex(2), ElevatorConstants.GEAR_RATIO, 0, 0, 0, 0, false, 0, null);
  }
}
