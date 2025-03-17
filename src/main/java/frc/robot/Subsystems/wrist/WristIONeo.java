package frc.robot.Subsystems.wrist;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.AbsoluteEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;

public class WristIONeo implements WristIO {
  private final SparkMax wristMotor;
  private final RelativeEncoder wristRelativeEncoder;
  private final SparkAbsoluteEncoder wristAbsoluteEncoder;
  private final SparkMaxConfig wristMotorConfig = new SparkMaxConfig();
  private final AbsoluteEncoderConfig wristAbsoluteEncoderConfig = new AbsoluteEncoderConfig();

  public WristIONeo() {
    wristMotor = new SparkMax(WristConstants.CAN_ID, MotorType.kBrushless);
    wristRelativeEncoder = wristMotor.getEncoder();
    wristAbsoluteEncoder = wristMotor.getAbsoluteEncoder();
    wristMotorConfig
        .inverted(WristConstants.IS_INVERTED)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(WristConstants.STALL_LIMIT_AMPS, WristConstants.FREE_SPIN_LIMIT_AMPS);
    wristMotor.configure(
        wristMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    wristAbsoluteEncoderConfig
        .positionConversionFactor(1)
        .zeroCentered(true); // TODO: Set offset and figured out what endpulseus does
    wristRelativeEncoder.setPosition(
        ((wristAbsoluteEncoder.getPosition() / 2) * WristConstants.GEAR_RATIO));
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from WristIOInputsAutoLogged
   */
  public void updateInputs(WristIOInputs inputs) {
    /** This returns the voltage the Wrist Motor Recieves */
    inputs.wristAppliedVolts = wristMotor.getAppliedOutput() * wristMotor.getBusVoltage();
    /**
     * Returns the position of the absoltute encoder in Radians (Used to make sure wrist zero doesnt
     * change on enable //
     */
    inputs.wristAbsolutePositionRad =
        Units.rotationsToRadians(wristAbsoluteEncoder.getPosition()) / WristConstants.GEAR_RATIO;
    /**
     * Returns the position of the absoltute encoder in Degrees (Used to make sure wrist zero doesnt
     * change on enable
     */
    inputs.wristAbsolutePositionDeg = wristAbsoluteEncoder.getPosition();
    /** Returns the position of the Wrist Motor by how many radians it has rotated */
    inputs.wristPositionRad =
        Units.rotationsToRadians(wristRelativeEncoder.getPosition()) / WristConstants.GEAR_RATIO;
    /** Returns the position of the Wrist Motor by how many degrees it has rotated */
    inputs.wristPositionDeg =
        Units.rotationsToDegrees(wristRelativeEncoder.getPosition()) / WristConstants.GEAR_RATIO;
    /** Returns the velocity of the Wrist Motor by how many radians per second it has rotated */
    inputs.wristVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(wristRelativeEncoder.getVelocity())
            / WristConstants.GEAR_RATIO;
    /** The Current Drawn from the Wrist Motor in Amps */
    inputs.wristCurrentAmps = new double[] {wristMotor.getOutputCurrent()};
    /** The tempature of the Wrist Motor in Celsius */
    inputs.wristTempCelsius = new double[] {wristMotor.getMotorTemperature()};
  }

  @Override
  /**
   * Sets the voltage for the Wrist
   *
   * @param volts -12 to 12
   */
  public void setWristVoltage(double volts) {
    wristMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Wrist
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    wristMotorConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    wristMotor.configure(
        wristMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
