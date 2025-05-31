// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorConstants.ElevatorPositions;
import frc.robot.Subsystems.endEffector.EndEffector;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreCoralAgain extends SequentialCommandGroup {
  /** Creates a new ScoreCoralAgain. */
  public ScoreCoralAgain(EndEffector endEffector, Elevator elevator) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new ConditionalCommand(
            new SequentialCommandGroup(
                new InstantCommand(() -> endEffector.setEndEffectorPercent(-0.2), endEffector),
                new WaitCommand(1),
                new InstantCommand(() -> endEffector.setEndEffectorPercent(0), endEffector)),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L4_END_HEIGHT_M), endEffector),
            () -> !RobotStateConstants.currentState.isL4));
  }
}
