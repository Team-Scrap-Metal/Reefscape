package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.wrist.Wrist;

public class AlgaeScore extends SequentialCommandGroup {
  public AlgaeScore(Elevator elevator, Wrist wrist, EndEffector endEffector) {
    addCommands(new InstantCommand(() -> endEffector.setEndEffectorPercent(1)));
  }
}
