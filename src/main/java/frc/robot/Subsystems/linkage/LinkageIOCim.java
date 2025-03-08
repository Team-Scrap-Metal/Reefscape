package frc.robot.Subsystems.linkage;

public class LinkageIOCim implements LinkageIO {
  public LinkageIOCim() {}

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(LinkageIOInputs inputs) {}

  @Override
  /**
   * Sets the voltage for the Linkage
   *
   * @param volts -12 to 12
   */
  public void setLinkageVoltage(double volts) {}

  @Override
  /**
   * Sets the Brake Mode for the Linkage
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {}
}
