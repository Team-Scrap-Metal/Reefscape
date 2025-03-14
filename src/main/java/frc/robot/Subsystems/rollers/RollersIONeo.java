package frc.robot.Subsystems.rollers;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.endEffector.EndEffectorConstants;

public class RollersIONeo implements RollersIO {
  private final SparkMax rollerMotor;
  private final RelativeEncoder rollerRelativeEncoder;
  private final SparkMaxConfig rollerConfig = new SparkMaxConfig();
  public RollersIONeo() {
    rollerMotor = new SparkMax(RollersConstants.CAN_ID, MotorType.kBrushless);
    rollerRelativeEncoder = rollerMotor.getEncoder();
    rollerConfig.inverted(RollersConstants.IS_INVERTED).idleMode(IdleMode.kCoast).smartCurrentLimit(RollersConstants.STALL_LIMIT_AMPS, RollersConstants.FREE_SPIN_LIMIT_AMPS);
    rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(RollersIOInputs inputs) {
    inputs.rollerAppliedVolts = rollerMotor.getAppliedOutput() * rollerMotor.getBusVoltage();
    /** Returns the position of the Endeffector Motor by how many radians it has rotated */
    inputs.rollerPositionRad = Units.rotationsToRadians(rollerRelativeEncoder.getPosition())/RollersConstants.GEAR_RATIO;
    /**
     * Returns the velocity of the Endeffector Motor by how many radians per second it has rotated
     */
    inputs.rollerVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(rollerRelativeEncoder.getVelocity())/RollersConstants.GEAR_RATIO;
    /** The Current Drawn from the Endeffector Motor in Amps */
    inputs.rollerCurrentAmps = new double[] {};
    /** The tempature of the Endeffector Motor in Celsius */
    inputs.rollerTempCelsius = new double[] {};
    inputs.rollerVelocityRotPerMin = rollerRelativeEncoder.getVelocity()/RollersConstants.GEAR_RATIO;

  }

  @Override
  /**
   * Sets the voltage for the Rollers
   *
   * @param volts -12 to 12
   */
  public void setRollersVoltage(double volts) {
    rollerMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Rollers
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    rollerConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
