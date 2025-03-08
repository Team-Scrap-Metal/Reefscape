package frc.robot.Subsystems.elevator;

public class ElevatorIOVortex implements ElevatorIO {
  public ElevatorIOVortex() {}

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ElevatorIOInputs inputs) {}

  @Override
  /**
   * Sets the voltage for the Elevator
   *
   * @param volts -12 to 12
   */
  public void setElevatorVoltage(double volts) {}

  @Override
  /**
   * Sets the Brake Mode for the Elevator
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {}
}
