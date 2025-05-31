// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Constants.RobotStateConstants.CoralStateMachine;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorConstants.ElevatorPositions;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristConstants.WristPositions;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PositionToScore extends SequentialCommandGroup {
  /** Creates a new PositionToScore. */
  public PositionToScore(CoralStateMachine coral, Elevator elevator, Wrist wrist) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    switch (coral) {
      case PositionL1:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL1;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L1_START_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(() -> wrist.setSetpointRad(WristPositions.L1_ROTATION_RAD), wrist));
        break;
      case PositionL2Left:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL2Left;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L2_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.LEFT_L2_AND_L3_START_ROTATION_RAD),
                wrist));
        break;
      case PositionL2Right:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL2Right;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L2_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.RIGHT_L2_AND_L3_START_ROTATION_RAD),
                wrist));
        break;
      case PositionL3Left:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL3Left;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L3_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.LEFT_L2_AND_L3_START_ROTATION_RAD),
                wrist));
        break;
      case PositionL3Right:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL3Right;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L3_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.RIGHT_L2_AND_L3_START_ROTATION_RAD),
                wrist));
        break;
      case PositionL4Left:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL4Left;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L4_START_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.LEFT_L4_START_ROTATION_RAD), wrist));
        break;
      case PositionL4Right:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.PositionL4Right;
                },
                wrist),
            new InstantCommand(
                () -> elevator.setSetpointM(ElevatorPositions.L4_START_HEIGHT_M), elevator),
            new WaitUntilCommand(() -> elevator.safeToRotate()),
            new InstantCommand(
                () -> wrist.setSetpointRad(WristPositions.RIGHT_L4_START_ROTATION_RAD), wrist));
        break;
      case Stowed:
        addCommands(
            Commands.runOnce(
                () -> {
                  RobotStateConstants.currentState = CoralStateMachine.Stowed;
                },
                wrist),
            new Stow(elevator, wrist));
        break;
    }
  }
}
