// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorConstants.ElevatorPositions;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristConstants.WristPositions;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GroundPickup extends SequentialCommandGroup {
  /** Creates a new PositionToPickup. */
  public GroundPickup(Elevator elevator, Wrist wrist, EndEffector endEffector) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new ConditionalCommand(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> wrist.setSetpointRad(WristPositions.FLOOR_INTAKE_RAD), wrist),
                new WaitCommand(1),
                new WaitUntilCommand(() -> wrist.safeToLift()),
                new InstantCommand(
                    () -> elevator.setSetpointM(ElevatorPositions.GROUND_INTAKE_HEIGHT_M),
                    elevator)),
            new SequentialCommandGroup(
                new InstantCommand(() -> elevator.setSetpointM(Units.inchesToMeters(13)), elevator),
                new WaitUntilCommand(() -> elevator.safeToRotate()),
                new InstantCommand(
                    () -> wrist.setSetpointRad(WristPositions.FLOOR_INTAKE_RAD), wrist),
                new WaitCommand(1),
                new WaitUntilCommand(() -> wrist.safeToLift()),
                new InstantCommand(
                    () -> elevator.setSetpointM(ElevatorPositions.GROUND_INTAKE_HEIGHT_M),
                    elevator)),
            () -> elevator.safeToRotate()));
  }
}
