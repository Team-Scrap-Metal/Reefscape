package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorConstants.ElevatorPositions;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristConstants.WristPositions;

public class AlgaeRemove extends SequentialCommandGroup {
  private double setPoint = 0;

  public AlgaeRemove(Elevator elevator, Wrist wrist, EndEffector endEffector, Integer position) {
    if (position == 0) {
      setPoint = ElevatorPositions.ALGAE_REMOVAL_ONE_HEIGHT_M;
    } else {
      setPoint = ElevatorPositions.ALGAE_REMOVAL_TWO_HEIGHT_M;
    }

    addCommands(
        new SequentialCommandGroup(
            new InstantCommand(() -> elevator.setSetpointM(setPoint)),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.ALGAE_REMOVAL_ONE_ROTATION_R))),
        new InstantCommand(() -> endEffector.setEndEffectorPercent(-0.15)));
  }
}
