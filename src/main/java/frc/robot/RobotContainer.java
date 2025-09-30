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

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.TeleopCommands.Coral.AlgaeRemove;
import frc.robot.Commands.TeleopCommands.Coral.AlgaeScore;
import frc.robot.Commands.TeleopCommands.Coral.GroundPickup;
import frc.robot.Commands.TeleopCommands.Coral.PositionToScore;
import frc.robot.Commands.TeleopCommands.Coral.ScoreCoral;
import frc.robot.Commands.TeleopCommands.Coral.ScoreCoralAgain;
import frc.robot.Commands.TeleopCommands.Coral.Stow;
import frc.robot.Constants.DefaultSpeedConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Constants.RobotStateConstants.CoralStateMachine;
import frc.robot.Subsystems.climber.Climber;
import frc.robot.Subsystems.climber.ClimberIO;
import frc.robot.Subsystems.climber.ClimberIONeo;
import frc.robot.Subsystems.drive.Drive;
import frc.robot.Subsystems.drive.ModuleIO;
import frc.robot.Subsystems.drive.ModuleIOKrakenNeo;
import frc.robot.Subsystems.elevator.Elevator;
import frc.robot.Subsystems.elevator.ElevatorConstants;
import frc.robot.Subsystems.elevator.ElevatorIO;
import frc.robot.Subsystems.elevator.ElevatorIOVortex;
import frc.robot.Subsystems.endEffector.EndEffector;
import frc.robot.Subsystems.endEffector.EndEffectorIO;
import frc.robot.Subsystems.endEffector.EndEffectorIONeo;
import frc.robot.Subsystems.gyro.Gyro;
import frc.robot.Subsystems.gyro.GyroIO;
import frc.robot.Subsystems.gyro.GyroIOPigeon;
import frc.robot.Subsystems.linkage.Linkage;
import frc.robot.Subsystems.linkage.LinkageIONeo;
import frc.robot.Subsystems.rollers.Rollers;
import frc.robot.Subsystems.rollers.RollersIO;
import frc.robot.Subsystems.rollers.RollersIONeo;
import frc.robot.Subsystems.wrist.Wrist;
import frc.robot.Subsystems.wrist.WristConstants;
import frc.robot.Subsystems.wrist.WristIO;
import frc.robot.Subsystems.wrist.WristIONeo;
import frc.robot.Utils.PathPlanner;
import frc.robot.Utils.PoseEstimator;

import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // NAH ID WIN!!!
  // Subsystems
  private final Drive m_driveSubsystem;
  private final Gyro m_gyroSubsystem;
  private final Wrist m_wristSubsystem;
  private final Rollers m_rollersSubsystem;
  private final EndEffector m_endEffectorSubsystem;
  private final Elevator m_elevatorSubsystem;
  private final Climber m_climberSubsystem;
  private final PoseEstimator m_poseEstimator;
  private final Linkage m_linkageSubsystem;
  private final PathPlanner m_pathPlanner;
  public boolean toggleSlow = false;
  private double LeftX = DefaultSpeedConstants.LEFT_X;
  private double LeftY = DefaultSpeedConstants.LEFT_Y;
  private double RightX = DefaultSpeedConstants.RIGHT_X;

  private SlewRateLimiter wristRateLimiter;

  private SlewRateLimiter downlinkageSlewRateLimiter;
  private SlewRateLimiter uplinkageSlewRateLimiter;

  // Controller
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DRIVER_PORT);
  private final CommandXboxController auxController =
      new CommandXboxController(OperatorConstants.AUX_PORT);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser =
      new LoggedDashboardChooser<>("Auto Chooser");
  /** The container for the robot. Contains subsystems, OI devices, and commands. 
 * @throws ParseException 
 * @throws IOException */
  public RobotContainer() throws IOException, ParseException {
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
        m_wristSubsystem = new Wrist(new WristIONeo());
        m_rollersSubsystem = new Rollers(new RollersIONeo());
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIONeo());
        m_elevatorSubsystem = new Elevator(new ElevatorIOVortex());
        m_climberSubsystem = new Climber(new ClimberIONeo());
        m_linkageSubsystem = new Linkage(new LinkageIONeo());
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
        m_wristSubsystem = new Wrist(new WristIO() {});
        m_rollersSubsystem = new Rollers(new RollersIO() {});
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIO() {});
        m_elevatorSubsystem = new Elevator(new ElevatorIO() {});
        m_climberSubsystem = new Climber(new ClimberIO() {});
        m_linkageSubsystem = new Linkage(new LinkageIONeo());

        break;

      default:
        // Replayed robot, disable IO implementations
        m_gyroSubsystem = new Gyro(new GyroIO() {});
        m_driveSubsystem =
            new Drive(
                new ModuleIOKrakenNeo(0),
                new ModuleIOKrakenNeo(1),
                new ModuleIOKrakenNeo(2),
                new ModuleIOKrakenNeo(3),
                m_gyroSubsystem);
        m_endEffectorSubsystem = new EndEffector(new EndEffectorIO() {});
        m_wristSubsystem = new Wrist(new WristIO() {});
        m_rollersSubsystem = new Rollers(new RollersIO() {});
        m_elevatorSubsystem = new Elevator(new ElevatorIO() {});
        m_climberSubsystem = new Climber(new ClimberIO() {});
        m_linkageSubsystem = new Linkage(new LinkageIONeo());
        break;
    }
    wristRateLimiter = new SlewRateLimiter(1);
    downlinkageSlewRateLimiter = new SlewRateLimiter(0.001);
    uplinkageSlewRateLimiter = new SlewRateLimiter(0.001);
    m_poseEstimator = new PoseEstimator(m_driveSubsystem, m_gyroSubsystem);
    m_pathPlanner = new PathPlanner(m_driveSubsystem, m_poseEstimator);
    NamedCommands.registerCommand("L1", new PositionToScore(CoralStateMachine.PositionL1, m_elevatorSubsystem, m_wristSubsystem));
        
    
    
    // PathPlannerSetup.configure(m_driveSubsystem, m_poseEstimator);
    // public Command getAutonomousCommand() {
    //     return new com.pathplanner.lib.auto.PathPlannerAuto("")
    // }

    // Configure the button bindings
    // autoChooser.setDefaultOption("None", null);
    // autoChooser.addOption(
    //     "Straight Line", AutoBuilder.followPath(PathPlannerPath.fromPathFile("StraightLine")));
    // autoChooser.addOption(
    //     "Turn Path", AutoBuilder.followPath(PathPlannerPath.fromPathFile("TurnAuto")));
    // SmartDashboard.putData("Auto Chooser", autoChooser);

    configureDriverButtonBindings();
    configureAuxButtonBindings();
  }

  public Command getAutonomousCommand() {
    return AutoBuilder.buildAuto("Test_Auto");
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
                    driverController.getLeftX() * LeftX, // Forward/backward
                    driverController.getLeftY()
                        * LeftY, // Left/Right (multiply by -1 bc controller axis is inverted)
                    driverController.getRightX() * RightX), // Rotate chassis left/right
            m_driveSubsystem));

    // Resets robot heading to be wherever the front of the robot is facing
    driverController
        .a()
        .onTrue(new InstantCommand(() -> m_driveSubsystem.updateHeading(), m_driveSubsystem));
    /**
     * driverController .b() .onTrue( new RunCommand( () -> m_climberSubsystem.setSetpoint(.20)
     *
     * <p>));
     */
    driverController.b().onTrue(new InstantCommand(() -> setDriveModulesPercentages(1, -1, 1)));
    driverController
        .rightTrigger()
        .onTrue(
            new SequentialCommandGroup(
                new AlgaeRemove(m_elevatorSubsystem, m_wristSubsystem, m_endEffectorSubsystem, 1),
                new InstantCommand(() -> setDriveModulesPercentages(0.6, -0.6, 0.6))));

    driverController
        .rightBumper()
        .onTrue(
            new SequentialCommandGroup(
                new AlgaeRemove(m_elevatorSubsystem, m_wristSubsystem, m_endEffectorSubsystem, 0),
                new InstantCommand(() -> setDriveModulesPercentages(0.6, -0.6, 0.6))));
    driverController
        .leftTrigger()
        .onTrue(
            new SequentialCommandGroup(
                new Stow(m_elevatorSubsystem, m_wristSubsystem),
                new WaitCommand(1.5),
                new InstantCommand(() -> setDriveModulesPercentages(0.9, -0.9, 0.9))));
    driverController
        .leftBumper()
        .onTrue(
            new SequentialCommandGroup(
                new InstantCommand(
                    () ->
                        m_elevatorSubsystem.setSetpointM(
                            ElevatorConstants.ElevatorPositions.ALGAE_SCORE_HEIGHT_M)),
                new InstantCommand(() -> setDriveModulesPercentages(0.35, -0.35, 0.4)),
                new InstantCommand(
                    () ->
                        m_wristSubsystem.setSetpointRad(
                            WristConstants.WristPositions.ALGAE_SCORE_ROTATION_R))))
        .onFalse(
            new SequentialCommandGroup(
                new AlgaeScore(m_elevatorSubsystem, m_wristSubsystem, m_endEffectorSubsystem),
                new WaitCommand(1),
                new InstantCommand(() -> m_endEffectorSubsystem.setEndEffectorPercent(0)),
                new InstantCommand(() -> setDriveModulesPercentages(0.4, -0.4, 0.4)),
                new Stow(m_elevatorSubsystem, m_wristSubsystem),
                new WaitCommand(1.5),
                new InstantCommand(() -> setDriveModulesPercentages(0.85, -0.85, 0.85))));
    driverController
        .povLeft()
        .onTrue(new InstantCommand(() -> m_endEffectorSubsystem.setEndEffectorPercent(0)));

    // driverController
    //     .rightTrigger()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_rollersSubsystem.setRollersPercent(0.50), m_rollersSubsystem))
    //     .onFalse(
    //         new InstantCommand(
    //             () -> m_rollersSubsystem.setRollersPercent(0.0), m_rollersSubsystem));

    driverController
        .povUp()
        .onTrue(
            new InstantCommand(() -> m_climberSubsystem.setClimberPercent(1), m_climberSubsystem))
        .onFalse(
            new InstantCommand(
                () -> m_climberSubsystem.setClimberPercent(0.0), m_climberSubsystem));
    driverController
        .povDown()
        .onTrue(
            new InstantCommand(() -> m_climberSubsystem.setClimberPercent(0.5), m_climberSubsystem))
        .onFalse(
            new InstantCommand(
                () -> m_climberSubsystem.setClimberPercent(0.0), m_climberSubsystem));
  }

  private void configureAuxButtonBindings() {
    /** Aux Controls */
    auxController
        .povDown()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> setDriveModulesPercentages(0.85, -0.85, 0.90)),
                new PositionToScore(
                    CoralStateMachine.PositionL1, m_elevatorSubsystem, m_wristSubsystem)));

    auxController
        .povLeft()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> setDriveModulesPercentages(0.75, -0.75, 0.80)),
                new PositionToScore(
                    CoralStateMachine.PositionL2Right, m_elevatorSubsystem, m_wristSubsystem)));
    auxController
        .povRight()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(
                    () -> {
                      LeftX = 0.5;
                      LeftY = -0.5;
                      RightX = .75;
                    }),
                new PositionToScore(
                    CoralStateMachine.PositionL3Right, m_elevatorSubsystem, m_wristSubsystem)));
    auxController
        .povUp()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(
                    () -> {
                      LeftX = 0.25;
                      LeftY = -0.25;
                      RightX = .40;
                    }),
                new PositionToScore(
                    CoralStateMachine.PositionL4Right, m_elevatorSubsystem, m_wristSubsystem)));

    auxController
        .a()
        .onTrue(
            new ParallelDeadlineGroup(
                new PositionToScore(
                    CoralStateMachine.PositionL1, m_elevatorSubsystem, m_wristSubsystem),
                new InstantCommand(() -> setDriveModulesPercentages(0.85, -0.85, 0.9))));

    auxController
        .x()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> setDriveModulesPercentages(0.75, -0.75, 0.80)),
                new PositionToScore(
                    CoralStateMachine.PositionL2Left, m_elevatorSubsystem, m_wristSubsystem)));
    auxController
        .b()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> setDriveModulesPercentages(0.5, -0.5, 0.75)),
                new PositionToScore(
                    CoralStateMachine.PositionL3Left, m_elevatorSubsystem, m_wristSubsystem)));
    auxController
        .y()
        .onTrue(
            new ParallelCommandGroup(
                new InstantCommand(() -> setDriveModulesPercentages(0.25, -0.25, 0.40)),
                new PositionToScore(
                    CoralStateMachine.PositionL4Left, m_elevatorSubsystem, m_wristSubsystem)));

    // auxController
    //     .y()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.setSetpointM(Units.inchesToMeters(20)),
    //             m_elevatorSubsystem));
    // auxController
    //     .a()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.setSetpointM(Units.inchesToMeters(50)),
    //             m_elevatorSubsystem));
    // auxController
    //     .b()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.setSetpointM(Units.inchesToMeters(65)),
    //             m_elevatorSubsystem));
    // auxController
    //     .x()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.setSetpointM(Units.inchesToMeters(0)),
    //             m_elevatorSubsystem));

    // auxController
    //     .leftBumper()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.incrementSetpoint(Units.inchesToMeters(-1)),
    //             m_elevatorSubsystem));

    // auxController
    //     .button(9)
    //     .onTrue(
    //         new ConditionalCommand(
    //             Commands.runOnce(
    //                 () -> {
    //                   m_wristSubsystem.setWristPercent(0.2);
    //                 },
    //                 m_wristSubsystem),
    //             Commands.runOnce(() -> {}, m_wristSubsystem),
    //             () -> m_elevatorSubsystem.safeToRotate() && !m_wristSubsystem.isPIDEnabled()))
    //     .onFalse(
    //         new ConditionalCommand(
    //             Commands.runOnce(
    //                 () -> {
    //                   m_wristSubsystem.setWristPercent(0.0);
    //                 },
    //                 m_wristSubsystem),
    //             Commands.runOnce(() -> {}, m_wristSubsystem),
    //             () -> m_elevatorSubsystem.safeToRotate() && !m_wristSubsystem.isPIDEnabled()));
    // ;

    // auxController
    //     .button(10)
    //     .onTrue(
    //         new ConditionalCommand(
    //             Commands.runOnce(
    //                 () -> {
    //                   m_wristSubsystem.setWristPercent(-0.2);
    //                 },
    //                 m_wristSubsystem),
    //             Commands.runOnce(() -> {}, m_wristSubsystem),
    //             () -> m_elevatorSubsystem.safeToRotate() && !m_wristSubsystem.isPIDEnabled()))
    //     .onFalse(
    //         new ConditionalCommand(
    //             Commands.runOnce(
    //                 () -> {
    //                   m_wristSubsystem.setWristPercent(0.0);
    //                 },
    //                 m_wristSubsystem),
    //             Commands.runOnce(() -> {}, m_wristSubsystem),
    //             () -> m_elevatorSubsystem.safeToRotate() && !m_wristSubsystem.isPIDEnabled()));
    // // () -> m_wristSubsystem.incrementSetpoint(Units.degreesToRadians(1))));

    // auxController
    //     .back()
    //     .onTrue(new InstantCommand(() -> m_wristSubsystem.togglePID(true), m_wristSubsystem));

    // auxController
    //     .start()
    //     .onTrue(new InstantCommand(() -> m_wristSubsystem.togglePID(false), m_wristSubsystem));

    auxController
        .leftTrigger()
        .onTrue(
            new SequentialCommandGroup(
                new GroundPickup(m_elevatorSubsystem, m_wristSubsystem, m_endEffectorSubsystem),
                Commands.runOnce(
                    () -> {
                      m_endEffectorSubsystem.setEndEffectorPercent(0.3);
                    },
                    m_endEffectorSubsystem),
                new WaitCommand(1),
                new InstantCommand(() -> setDriveModulesPercentages(0.9, -0.9, 0.7))))
        .onFalse(
            new InstantCommand(
                () -> m_endEffectorSubsystem.setEndEffectorPercent(0), m_endEffectorSubsystem));
    // auxController
    //     .rightBumper()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_wristSubsystem.incrementSetpoint(Units.inchesToMeters(-1)),
    //             m_elevatorSubsystem));
    auxController
        .rightTrigger()
        .onTrue(new ScoreCoral(m_endEffectorSubsystem, m_wristSubsystem))
        .onFalse(new ScoreCoralAgain(m_endEffectorSubsystem, m_elevatorSubsystem));
    // auxController
    //     .rightTrigger()
    //     .onTrue(
    //         new InstantCommand(
    //             () -> m_elevatorSubsystem.setSetpointM(Units.inchesToMeters(20)),
    //             m_elevatorSubsystem));
    auxController
        .leftBumper()
        .onTrue(
            new SequentialCommandGroup(
                new Stow(m_elevatorSubsystem, m_wristSubsystem, m_endEffectorSubsystem),
                new WaitCommand(1),
                new InstantCommand(() -> setDriveModulesPercentages(0.85, -0.85, 0.85))));
  }

  public void stopEverything() {}

  public void coastOnDisable(boolean isDisabled) {
    m_driveSubsystem.coastOnDisable(isDisabled);
    m_wristSubsystem.coastOnDisable(isDisabled);
    // m_elevatorSubsystem.coastOnDisable(isDisabled);
  }

  public void setDriveModulesPercentages(double Left_X, double Left_Y, double Right_X) {
    LeftX = Left_X;
    LeftY = Left_Y;
    RightX = Right_X;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
//   public Command getAutonomousCommand() {
    // return autoChooser.get();
    // return new RunCommand(() -> m_driveSubsystem.driveWithDeadband(0, -0.5, 0), m_driveSubsystem);
//   }
}
