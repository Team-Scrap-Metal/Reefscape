package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Wrist extends SubsystemBase {
  private final WristIO io;
  private final WristIOInputsAutoLogged inputs = new WristIOInputsAutoLogged();
  private PIDController WristPID = new PIDController(0, 0, 0);

  public Wrist(WristIO io) {
    System.out.println("[Init] Creating Wrist");
    this.io = io;
    WristPID = new PIDController(WristConstants.kP, WristConstants.kI, WristConstants.kD);
    WristPID.setTolerance(WristConstants.PID_TOLERANCE_RAD);
    WristPID.setSetpoint(0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Wrist", inputs);
    // setWristVoltage(WristPID.calculate(this.getWristPositionRad()));
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setSetpoint(double setpoint) {
    WristPID.setSetpoint(setpoint);
  }

  public void setWristVoltage(double volts) {
    io.setWristVoltage(volts);
  }

  public void setWristPercent(double percent) {
    io.setWristVoltage(percent * 12);
  }

  // public double getWristPositionRad() {
  //   return inputs.wristPositionRad();
  // }
}
