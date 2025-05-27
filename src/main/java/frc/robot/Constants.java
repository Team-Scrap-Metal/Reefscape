// Copyright 2021-2025 FRC 6328
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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import java.util.Optional;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  /** Defines State of Robot */
  public static class RobotStateConstants {
    public static enum Mode {
      /** Running on a real robot. */
      REAL,

      /** Running a physics simulator. */
      SIM,

      /** Replaying from a log file. */
      REPLAY
    }

    /** Gets Robot Mode (Real, Sim, or Replay) */
    public static final Mode getMode() {
      if (RobotBase.isReal()) {
        return Mode.REAL;
      } else if (RobotBase.isSimulation()) {
        return Mode.SIM;
      } else {
        return Mode.REPLAY;
      }
    }

    public static enum CoralStateMachine {
      PositionL1(true, true),
      PositionL2Left(true, false),
      PositionL2Right(false, false),
      PositionL3Left(true, false),
      PositionL3Right(false, false),
      PositionL4Left(true, false),
      PositionL4Right(false, false);
    

      public final boolean isLeft;
      public final boolean isL1;
    CoralStateMachine(boolean isLeft, boolean isL1) {
      this.isLeft = isLeft;
      this.isL1 = isL1;
    }
  }

        // ScoreL23Left(),
      // ScoreL23Right(),
      // ScoreL4Left(),
      // GroundPickup(),
      // PlayerStationPickup()
      // AlgaeStowed(),
      // AlgaeDeployed(),
      // AlgaeHold(),
      // AlgaeScore(),
      // ClimberIn(),
      // ClimberOut(),
      // Climbed(),
      // Stowed()public final double OFFSET;

    public static enum DriveStateMachine {
      VisionDrive(),
      ManualDrive(),
      SlowDrive(),
      AutoDrive()
    }

    /** Get Alliance (Blue, Red, Null) */
    public static final Optional<Alliance> getAlliance() {
      return DriverStation.getAlliance();
    }

    public static final boolean isAllianceRed() {

      return DriverStation.getAlliance().get() == DriverStation.Alliance.Red ? true : false;
    }

    /** If CAN takes too long, it cancels */
    public static final int CAN_CONFIG_TIMEOUT_SEC = 30;

    /** Command Loop Seconds */
    public static final double LOOP_PERIODIC_SEC = 0.02;

    /** Average Battery Voltage */
    public static final double BATTERY_VOLTAGE = 12;
  }

  /** Defines all Operater Constants */
  public static final class OperatorConstants {
    public static final int DRIVER_PORT = 0;
    public static final int AUX_PORT = 1;
  }

  public final class UnitConversions {
    public static final double MIN_TO_MS = 60000;
  }
}
