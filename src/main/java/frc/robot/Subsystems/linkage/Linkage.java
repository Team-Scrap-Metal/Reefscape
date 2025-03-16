package frc.robot.Subsystems.linkage;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Linkage extends SubsystemBase {
  private final LinkageIO io;
  private final LinkageIOInputsAutoLogged inputs = new LinkageIOInputsAutoLogged();
  // private PIDController LinkagePID = new PIDController(0, 0, 0);

  public Linkage(LinkageIO io) {
    System.out.println("[Init] Creating Linkage");
    this.io = io;

    // LinkagePID = new PIDController(LinkageConstants.kP, LinkageConstants.kI,
    // LinkageConstants.kD);
    // LinkagePID.setTolerance(LinkageConstants.PID_TOLERANCE_RAD);
    // LinkagePID.setSetpoint(0.0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Linkage", inputs);
    // setLinkageVoltage(LinkagePID.calculate(this.getLinkagePositionRad()));
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  // public void setSetpoint(double setpoint) {
  //   LinkagePID.setSetpoint(setpoint);
  // }

  public void setLinkageVoltage(double volts) {
    io.setLinkageVoltage(volts);
  }

  public void setLinkagePercent(double percent) {
    io.setLinkageVoltage(percent * 12);
  }

  // public double getLinkageVoltage() {
  //   return io.getLinkageVoltage();
  // }

  // public double getLinkagePositionRad() {
  //   return inputs.linkagePositionRad();
  // }
}
