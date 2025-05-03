package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Wrist extends SubsystemBase {
  private final WristIO io;
  private final WristIOInputsAutoLogged inputs = new WristIOInputsAutoLogged();
  private final ProfiledPIDController wristPID =
      new ProfiledPIDController(
          WristConstants.KP,
          WristConstants.KI,
          WristConstants.KD,
          new Constraints(WristConstants.MAX_VELOCITY, WristConstants.MAX_ACCELERATION));

  public Wrist(WristIO io) {
    System.out.println("[Init] Creating Wrist");
    this.io = io;
    wristPID.setTolerance(WristConstants.PID_TOLERANCE_RAD);
    wristPID.setGoal(0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Wrist", inputs);
    // setWristVoltage(wristPID.calculate(this.getWristPositionRad()));
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setSetpointRad(double setpoint) {
    wristPID.setGoal(setpoint);
    SmartDashboard.putNumber("Setpoint", wristPID.getSetpoint().position);
  }

  public void setWristVoltage(double volts) {
    io.setWristVoltage(volts);
  }

  public void setWristPercent(double percent) {
    io.setWristVoltage(percent * 12);
  }

  public double getWristPositionRad() {
    return inputs.wristPositionRad;
  }

  public void coastOnDisable(boolean isDisabled) {
    io.setBrakeMode(!isDisabled);
  }
}
