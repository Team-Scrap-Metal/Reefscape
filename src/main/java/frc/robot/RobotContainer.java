// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Subsystems.drive.Drive;
import frc.robot.Subsystems.drive.ModuleIO;
import frc.robot.Subsystems.drive.ModuleIOKrakenNeo;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorIO;
import frc.robot.Subsystems.elevator.ElevatorIOVortex;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.endEffector.EndEffectorIO;
import frc.robot.Subsystems.endEffector.EndEffectorIONeo;
import frc.robot.Subsystems.gyro.Gyro;
import frc.robot.Subsystems.gyro.GyroIO;
import frc.robot.Subsystems.gyro.GyroIOPigeon;
import frc.robot.Subsystems.linkage.Linkage;
import frc.robot.Subsystems.linkage.LinkageIO;
import frc.robot.Subsystems.linkage.LinkageIONeo;
import frc.robot.Subsystems.rollers.Rollers;
import frc.robot.Subsystems.rollers.RollersIO;
import frc.robot.Subsystems.rollers.RollersIONeo;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristIO;
import frc.robot.Subsystems.wrist.WristIONeo;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive m_driveSubsystem;
  private final Gyro m_gyroSubsystem;
  private final Linkage m_linkageSubsystem;
  private final Wrist m_wristSubsystem;
  private final Rollers m_rollersSubsystem;
  private final EndEffector m_endEffectorSubsystem;
  private final Elevator m_elevatorSubsystem;

  // private final PoseEstimator m_poseEstimator; TODO: Update PoseEstimator Stuff

  // Controller
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DRIVER_PORT);
  private final CommandXboxController auxController =
      new CommandXboxController(OperatorConstants.AUX_PORT);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser =
      new LoggedDashboardChooser<>("Auto Chooser");

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (RobotStateConstants.getMode()) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        m_gyroSubsystem = new Gyro(new GyroIOPigeon());
        m_driveSubsystem =
            new Drive(
                new ModuleIOKrakenNeo(0),
                new ModuleIOKrakenNeo(1),
                new ModuleIOKrakenNeo(2),
                new ModuleIOKrakenNeo(3),
                m_gyroSubsystem);
        m_linkageSubsystem = new Linkage(new LinkageIONeo());
        m_wristSubsystem = new Wrist(new WristIONeo());
        m_rollersSubsystem = new Rollers(new RollersIONeo());
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIONeo());
        m_elevatorSubsystem = new Elevator(new ElevatorIOVortex());

        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        m_gyroSubsystem = new Gyro(new GyroIO() {});
        m_driveSubsystem =
            new Drive(
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                m_gyroSubsystem);
        m_linkageSubsystem = new Linkage(new LinkageIO() {});
        m_wristSubsystem = new Wrist(new WristIO() {});
        m_rollersSubsystem = new Rollers(new RollersIO() {});
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIO() {});
        m_elevatorSubsystem = new Elevator(new ElevatorIO() {});
        break;

      default:
        // Replayed robot, disable IO implementations
        m_gyroSubsystem = new Gyro(new GyroIO() {});
        m_driveSubsystem =
            new Drive(
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                m_gyroSubsystem);
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIO() {});
        m_linkageSubsystem = new Linkage(new LinkageIO() {});
        m_wristSubsystem = new Wrist(new WristIO() {});
        m_rollersSubsystem = new Rollers(new RollersIO() {});
        m_elevatorSubsystem = new Elevator(new ElevatorIO() {});
        break;
    }

    // m_poseEstimator = new PoseEstimator(m_driveSubsystem, m_gyroSubsystem);
    // Configure the button bindings
    autoChooser.addDefaultOption("null", null);
    configureDriverButtonBindings();
    configureAuxButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureDriverButtonBindings() {
    /** Driver Controls */

    // Driving the robot
    m_driveSubsystem.setDefaultCommand(
        new RunCommand(
            () ->
                m_driveSubsystem.driveWithDeadband(
                    driverController.getLeftX() * 1, // Forward/backward
                    driverController.getLeftY()
                        * -1, // Left/Right (multiply by -1 bc controller axis is inverted)
                    driverController.getRightX() * (1)), // Rotate chassis left/right
            m_driveSubsystem));

    // Resets robot heading to be wherever the front of the robot is facing
    driverController
        .a()
        .onTrue(new InstantCommand(() -> m_driveSubsystem.updateHeading(), m_driveSubsystem));
    /**
     * driverController .b() .onTrue( new RunCommand( () -> m_linkageSubsystem.setSetpoint(.20)
     *
     * <p>));
     */
    driverController
        .rightBumper()
        .onTrue(
            new InstantCommand(() -> m_linkageSubsystem.setLinkagePercent(0.1), m_linkageSubsystem))
        .onFalse(
            new InstantCommand(() -> m_linkageSubsystem.setLinkagePercent(0), m_linkageSubsystem));
    driverController
        .leftBumper()
        .onTrue(
            new InstantCommand(
                () -> m_linkageSubsystem.setLinkagePercent(-0.1), m_linkageSubsystem))
        .onFalse(
            new InstantCommand(() -> m_linkageSubsystem.setLinkagePercent(0), m_linkageSubsystem));
    driverController
        .rightTrigger()
        .onTrue(
            new InstantCommand(() -> m_rollersSubsystem.setRollersPercent(1.0), m_linkageSubsystem))
        .onFalse(
            new InstantCommand(() -> m_rollersSubsystem.setRollersPercent(0), m_linkageSubsystem));
    driverController
        .leftTrigger()
        .onTrue(
            new InstantCommand(
                () -> m_rollersSubsystem.setRollersPercent(-1.0), m_rollersSubsystem))
        .onFalse(
            new InstantCommand(() -> m_rollersSubsystem.setRollersPercent(0), m_rollersSubsystem));
  }

  private void configureAuxButtonBindings() {
    /** Aux Controls */
    auxController
        .leftBumper()
        .onTrue(
            new InstantCommand(
                () -> m_endEffectorSubsystem.setEndEffectorPercent(0.2), m_endEffectorSubsystem))
        .onFalse(
            new InstantCommand(
                () -> m_endEffectorSubsystem.setEndEffectorPercent(0), m_endEffectorSubsystem));

    auxController
        .rightBumper()
        .onTrue(
            new InstantCommand(
                () -> m_endEffectorSubsystem.setEndEffectorPercent(-0.2), m_endEffectorSubsystem))
        .onFalse(new InstantCommand(() -> m_endEffectorSubsystem.setEndEffectorPercent(0)));

    auxController
        .leftTrigger()
        .onTrue(new InstantCommand(() -> m_wristSubsystem.setWristPercent(0.2), m_wristSubsystem))
        .onFalse(new InstantCommand(() -> m_wristSubsystem.setWristPercent(0), m_wristSubsystem));

    auxController
        .rightTrigger()
        .onTrue(new InstantCommand(() -> m_wristSubsystem.setWristPercent(-0.2), m_wristSubsystem))
        .onFalse(new InstantCommand(() -> m_wristSubsystem.setWristPercent(0), m_wristSubsystem));

    auxController
        .a()
        .onTrue(
            new InstantCommand(
                () -> m_elevatorSubsystem.setElevatorPercent(-0.21), m_elevatorSubsystem))
        .onFalse(
            new InstantCommand(
                () -> m_elevatorSubsystem.setElevatorPercent(0), m_elevatorSubsystem));
    auxController
        .b()
        .onTrue(
            new InstantCommand(
                () -> m_elevatorSubsystem.setElevatorPercent(0.21), m_elevatorSubsystem))
        .onFalse(
            new InstantCommand(
                () -> m_elevatorSubsystem.setElevatorPercent(0), m_elevatorSubsystem));
  }

  public void stopEverything() {}

  public void coastOnDisable(boolean isDisabled) {
    m_driveSubsystem.coastOnDisable(isDisabled);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
