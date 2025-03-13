package frc.robot.Subsystems.linkage;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Linkage extends SubsystemBase {
  private final LinkageIO io;
  private final LinkageIOInputsAutoLogged inputs = new LinkageIOInputsAutoLogged();

  public Linkage(LinkageIO io) {
    System.out.println("[Init] Creating Linkage");
    this.io = io;
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Linkage", inputs);
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setLinkageVoltage(double volts) {
    io.setLinkageVoltage(volts);
  }

  public void setLinkagePercent(double percent) {
    io.setLinkageVoltage(percent * 12);
  }
}
