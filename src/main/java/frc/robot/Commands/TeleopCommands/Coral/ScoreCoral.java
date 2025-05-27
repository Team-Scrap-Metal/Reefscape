// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.TeleopCommands.Coral;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Constants.RobotStateConstants.CoralStateMachine;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristConstants.WristPositions;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreCoral extends SequentialCommandGroup {
  /** Creates a new ScoreCoral. */
  public ScoreCoral(EndEffector nefector, Wrist wrist) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new ConditionalCommand(
            new ConditionalCommand(
                Commands.runOnce(
                    () -> {
                      nefector.setEndEffectorPercent(-0.2);
                    },
                    nefector),
                new ConditionalCommand(
                    Commands.runOnce(
                        () -> {
                          wrist.setSetpointRad(WristPositions.LEFT_L2_AND_L3_END_ROTATION_RAD);
                          nefector.setEndEffectorPercent(-0.2);
                        },
                        nefector),
                    Commands.runOnce(
                        () -> {
                          wrist.setSetpointRad(WristPositions.RIGHT_L2_AND_L3_END_ROTATION_RAD);
                          nefector.setEndEffectorPercent(-0.2);
                        },
                        nefector,
                        wrist),
                    () -> RobotStateConstants.currentState.isLeft),
                () -> RobotStateConstants.currentState.isL1),
            Commands.runOnce(() -> {}, wrist),
            () -> RobotStateConstants.currentState != CoralStateMachine.Stowed));
  }
}
