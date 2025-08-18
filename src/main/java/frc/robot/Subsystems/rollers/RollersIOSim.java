package frc.robot.Subsystems.rollers;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants.RobotStateConstants;

public class RollersIOSim implements RollersIO {
  DCMotorSim RollerSimMotor;
  double[] standardDeviations = new double[] {0.2, 0.2};

  public RollersIOSim() {
    RollerSimMotor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getNEO(1),
                RollersConstants.MOMENT_OF_INERTIA_JKGM2,
                RollersConstants.GEAR_RATIO),
            DCMotor.getNEO(1),
            standardDeviations);
  }

  @Override
  /** Updates inputs for the Rollers */
  public void updateInputs(RollersIOInputs inputs) {
    RollerSimMotor.update(RobotStateConstants.LOOP_PERIODIC_SEC);

    inputs.rollerAppliedVolts = RollerSimMotor.getInputVoltage();
    /** Returns the position of the Endeffector Motor by how many radians it has rotated */
    inputs.rollerPositionRad = RollerSimMotor.getAngularPositionRad();
    /**
     * Returns the velocity of the Endeffector Motor by how many radians per second it has rotated
     */
    inputs.rollerVelocityRadPerSec = RollerSimMotor.getAngularVelocityRadPerSec();
    /** The Current Drawn from the Endeffector Motor in Amps */
    inputs.rollerCurrentAmps = new double[] {RollerSimMotor.getCurrentDrawAmps()};
    /** The tempature of the Endeffector Motor in Celsius */
    inputs.rollerTempCelsius = new double[] {};
    inputs.rollerVelocityRotPerMin = RollerSimMotor.getAngularVelocityRPM();
  }

  @Override
  /**
   * Sets the voltage for the Rollers
   *
   * @param volts -12 to 12
   */
  public void setRollersVoltage(double volts) {
    RollerSimMotor.setInputVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Rollers
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {}
}
