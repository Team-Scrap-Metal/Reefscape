package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Elevator extends SubsystemBase {
  private final ElevatorIO io;
  private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
  private final ProfiledPIDController elevatorPID =
      new ProfiledPIDController(
          ElevatorConstants.KP,
          ElevatorConstants.KI,
          ElevatorConstants.KD,
          new Constraints(ElevatorConstants.MAX_VELOCITY, ElevatorConstants.MAX_ACCELERATION));
  private final ElevatorFeedforward elevatorFeedforward =
      new ElevatorFeedforward(
          ElevatorConstants.KS, ElevatorConstants.KG, ElevatorConstants.KV, ElevatorConstants.KA);

  public Elevator(ElevatorIO io) {
    System.out.println("[Init] Creating Elevator");
    this.io = io;
    elevatorPID.setTolerance(ElevatorConstants.PID_TOLERANCE_M);
    elevatorPID.setGoal(0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Elevator", inputs);
    setElevatorVoltage(
        elevatorFeedforward.calculate(elevatorPID.getSetpoint().velocity)
            + elevatorPID.calculate(this.getElevatorPositionMeters()));
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

  public void setSetpointM(double setpoint) {
    elevatorPID.setGoal(setpoint);
  }

  public void setElevatorPercent(double percent) {
    io.setElevatorVoltage(percent * 12);
  }

  public double getElevatorPositionRad() {
    return inputs.elevatorPositionRad;
  }

  public double getElevatorPositionMeters() {
    return inputs.elevatorPositionM;
  }

  public void coastOnDisable(boolean isDisabled) {
    io.setBrakeMode(!isDisabled);
  }
}
