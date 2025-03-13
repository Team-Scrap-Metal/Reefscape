package frc.robot.Subsystems.wrist;

public class WristIONeo implements WristIO {
  public WristIONeo() {}

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(WristIOInputs inputs) {}

  @Override
  /**
   * Sets the voltage for the Wrist
   *
   * @param volts -12 to 12
   */
  public void setWristVoltage(double volts) {}

  @Override
  /**
   * Sets the Brake Mode for the Wrist
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {}
}
