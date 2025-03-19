package frc.robot.Subsystems.endEffector;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;

public class EndEffectorIONeo implements EndEffectorIO {
  private final SparkMax endEffectorMotor;
  private final RelativeEncoder endEffectorRelativeEncoder;
  private final SparkMaxConfig endEffectorConfig = new SparkMaxConfig();

  public EndEffectorIONeo() {
    endEffectorMotor = new SparkMax(EndEffectorConstants.CAN_ID, MotorType.kBrushless);
    endEffectorRelativeEncoder = endEffectorMotor.getEncoder();
    endEffectorConfig
        .inverted(EndEffectorConstants.IS_INVERTED)
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(
            EndEffectorConstants.STALL_LIMIT_AMPS, EndEffectorConstants.FREE_SPIN_LIMIT_AMPS);
    endEffectorMotor.configure(
        endEffectorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(EndEffectorIOInputs inputs) {
    /** This returns the voltage the Endeffector Motor Recieves */
    inputs.endEffectorAppliedVolts =
        endEffectorMotor.getAppliedOutput() * endEffectorMotor.getBusVoltage();
    /** Returns the position of the Endeffector Motor by how many radians it has rotated */
    inputs.endEffectorPositionRad =
        Units.rotationsToRadians(endEffectorRelativeEncoder.getPosition())
            / EndEffectorConstants.GEAR_RATIO;
    // NAH ID WIN!!!
    /**
     * Returns the velocity of the Endeffector Motor by how many radians per second it has rotated
     */
    inputs.endEffectorVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(endEffectorRelativeEncoder.getVelocity())
            / EndEffectorConstants.GEAR_RATIO;
    /** The Current Drawn from the Endeffector Motor in Amps */
    inputs.endEffectorCurrentAmps = new double[] {};
    /** The tempature of the Endeffector Motor in Celsius */
    inputs.endEffectorTempCelsius = new double[] {};
    inputs.endEffectorVelocityRotPerMin =
        endEffectorRelativeEncoder.getVelocity() / EndEffectorConstants.GEAR_RATIO;
  }

  @Override
  /**
   * Sets the voltage for the EndEffector
   *
   * @param volts -12 to 12
   */
  public void setEndEffectorVoltage(double volts) {
    endEffectorMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the EndEffector
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    endEffectorConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    endEffectorMotor.configure(
        endEffectorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
