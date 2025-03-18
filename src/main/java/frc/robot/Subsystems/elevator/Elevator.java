package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Elevator extends SubsystemBase {
  private final ElevatorIO io;
  private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
  private PIDController ElevatorPID = new PIDController(0, 0, 0);

  public Elevator(ElevatorIO io) {
    System.out.println("[Init] Creating Elevator");
    this.io = io;
    ElevatorPID =
        new PIDController(ElevatorConstants.kP, ElevatorConstants.kI, ElevatorConstants.kD);
    ElevatorPID.setTolerance(ElevatorConstants.PID_TOLERANCE_RAD);
    ElevatorPID.setSetpoint(0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Elevator", inputs);
    setElevatorVoltage(ElevatorPID.calculate(this.getElevatorPositionMeters()));
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setElevatorVoltage(double volts) {
    io.setElevatorVoltage(volts);
  }

  public void setSetpoint(double setpoint) {
    ElevatorPID.setSetpoint(setpoint);
  }

  public void setElevatorPercent(double percent) {
    io.setElevatorVoltage(percent * 12);
  }

  // public double getElevatorPositionRad() {
  //   return inputs.elevatorPositionRad();
  // }

  public double getElevatorPositionMeters() {
    return inputs.elevatorPositionM;
  }
}
