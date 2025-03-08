package frc.robot.Subsystems.endEffector;

public class EndEffectorIOVortex implements EndEffectorIO {
  public EndEffectorIOVortex() {}

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(EndEffectorIOInputs inputs) {}

  @Override
  /**
   * Sets the voltage for the EndEffector
   *
   * @param volts -12 to 12
   */
  public void setEndEffectorVoltage(double volts) {}

  @Override
  /**
   * Sets the Brake Mode for the EndEffector
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {}
}
