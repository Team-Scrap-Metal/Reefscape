package frc.robot.Subsystems.endEffector;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class EndEffector extends SubsystemBase {
  private final EndEffectorIO io;
  private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

  public EndEffector(EndEffectorIO io) {
    System.out.println("[Init] Creating EndEffector");
    this.io = io;
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("EndEffector", inputs);
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setEndEffectorVoltage(double volts) {
    io.setEndEffectorVoltage(volts);
  }

  public void setEndEffectorPercent(double percent) {
    io.setEndEffectorVoltage(percent * 12);
  }
}
