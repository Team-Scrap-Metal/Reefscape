package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.wrist.Wrist;

public class Processor extends SequentialCommandGroup {
  public Processor(Elevator elevator, Wrist wrist, EndEffector endEffector) {
    addCommands(
        new SequentialCommandGroup(
            new InstantCommand(() -> elevator.setSetpointM(0.0)),
            new WaitUntilCommand(() -> elevator.getElevatorPositionMeters() < 0.02),
            new InstantCommand(() -> endEffector.setEndEffectorPercent(0.5))));
  }
}
