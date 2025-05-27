package frc.robot.Subsystems.elevator;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.elevator.ElevatorConstants.ElevatorControls;
import org.littletonrobotics.junction.Logger;

public class Elevator extends SubsystemBase {
  private final ElevatorIO io;
  private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
  private final ProfiledPIDController elevatorPID =
      new ProfiledPIDController(
          ElevatorControls.KP,
          ElevatorControls.KI,
          ElevatorControls.KD,
          new Constraints(ElevatorControls.MAX_VELOCITY, ElevatorControls.MAX_ACCELERATION));
  private final ElevatorFeedforward elevatorFeedforward =
      new ElevatorFeedforward(
          ElevatorControls.KS, ElevatorControls.KG, ElevatorControls.KV, ElevatorControls.KA);

  private double oldSetpoint = 0.0;

  public Elevator(ElevatorIO io) {
    System.out.println("[Init] Creating Elevator");
    this.io = io;
    elevatorPID.setTolerance(ElevatorControls.PID_TOLERANCE_M);
    elevatorPID.setGoal(0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Elevator", inputs);
    // updateControls();
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

  public void setElevatorCurrentTypeAndVoltage(int type, double volts) {
    io.setElevatorCurrentTypeAndVoltage(type, volts);
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

  public void incrementSetpoint(double increment) {
    oldSetpoint = elevatorPID.getGoal().position;
    oldSetpoint += increment;
    elevatorPID.setGoal(oldSetpoint);
  }

  public boolean safeToRotate() {
    return getElevatorPositionMeters() > Units.inchesToMeters(10) ? true : false;
  }

  public void updateControls() {
    // Step 1: Get new Values
    ElevatorControls.KP = SmartDashboard.getNumber("ElevatorKP", ElevatorControls.KP);
    ElevatorControls.KI = SmartDashboard.getNumber("ElevatorKI", ElevatorControls.KI);
    ElevatorControls.KD = SmartDashboard.getNumber("ElevatorKD", ElevatorControls.KD);
    ElevatorControls.PID_TOLERANCE_M =
        SmartDashboard.getNumber("ElevatorTolerance", ElevatorControls.PID_TOLERANCE_M);
    ElevatorControls.MAX_VELOCITY =
        SmartDashboard.getNumber("ElevatorMaxVel", ElevatorControls.MAX_VELOCITY);
    ElevatorControls.MAX_ACCELERATION =
        SmartDashboard.getNumber("ElevatorMaxAccell", ElevatorControls.MAX_ACCELERATION);
    ElevatorControls.KS = SmartDashboard.getNumber("ElevatorKS", ElevatorControls.KS);
    ElevatorControls.KG = SmartDashboard.getNumber("ElevatorKG", ElevatorControls.KG);
    ElevatorControls.KV = SmartDashboard.getNumber("ElevatorKV", ElevatorControls.KV);
    ElevatorControls.KA = SmartDashboard.getNumber("ElevatorKA", ElevatorControls.KA);
    // Step 2: Apply new Values
    elevatorPID.setPID(ElevatorControls.KP, ElevatorControls.KI, ElevatorControls.KD);
    elevatorPID.setConstraints(
        new Constraints(ElevatorControls.MAX_VELOCITY, ElevatorControls.MAX_ACCELERATION));
    elevatorFeedforward.setKs(ElevatorControls.KS);
    elevatorFeedforward.setKg(ElevatorControls.KG);
    elevatorFeedforward.setKv(ElevatorControls.KV);
    elevatorFeedforward.setKa(ElevatorControls.KA);
    // Step 3: Put new Values
    SmartDashboard.putNumber("ElevatorKP", ElevatorControls.KP);
    SmartDashboard.putNumber("ElevatorKI", ElevatorControls.KI);
    SmartDashboard.putNumber("ElevatorKD", ElevatorControls.KD);
    SmartDashboard.putNumber("ElevatorTolerance", ElevatorControls.PID_TOLERANCE_M);
    SmartDashboard.putNumber("ElevatorMaxVel", ElevatorControls.MAX_VELOCITY);
    SmartDashboard.putNumber("ElevatorMaxAccell", ElevatorControls.MAX_ACCELERATION);
    SmartDashboard.putNumber("ElevatorKS", ElevatorControls.KS);
    SmartDashboard.putNumber("ElevatorKG", ElevatorControls.KG);
    SmartDashboard.putNumber("ElevatorKV", ElevatorControls.KV);
    SmartDashboard.putNumber("ElevatorKA", ElevatorControls.KA);
  }
}
