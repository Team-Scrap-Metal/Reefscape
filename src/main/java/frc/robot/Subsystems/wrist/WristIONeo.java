package frc.robot.Subsystems.wrist;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WristIONeo implements WristIO {
  private final SparkMax wristMotor;
  private final RelativeEncoder wristRelativeEncoder;
  // private final SparkAbsoluteEncoder wristAbsoluteEncoder;
  private final SparkMaxConfig wristMotorConfig = new SparkMaxConfig();
  // private final AbsoluteEncoderConfig wristAbsoluteEncoderConfig = new AbsoluteEncoderConfig();
  private final SparkLimitSwitch wristLimitSwitch;
  // private final SparkAnalogSensor wristAnalogSensor;
  private final LimitSwitchConfig limitSwitchConfig;

  public WristIONeo() {
    wristMotor = new SparkMax(WristConstants.CAN_ID, MotorType.kBrushless);
    wristRelativeEncoder = wristMotor.getEncoder();
    // wristAnalogSensor = wristMotor.getAnalog();
    // AnalogSensorConfig analogSensorConfig = new AnalogSensorConfig();
    // SmartDashboard.putNumber("wristAnalogSensor", wristAnalogSensor.getVoltage());

    // wristAbsoluteEncoder = wristMotor.getAbsoluteEncoder();
    // wristAbsoluteEncoderConfig.positionConversionFactor(1).zeroCentered(true).zeroOffset(0.621);
    wristLimitSwitch = wristMotor.getReverseLimitSwitch();
    limitSwitchConfig =
        new LimitSwitchConfig()
            .reverseLimitSwitchEnabled(true)
            .reverseLimitSwitchType(Type.kNormallyOpen);
    wristMotorConfig
        .inverted(WristConstants.IS_INVERTED)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(WristConstants.STALL_LIMIT_AMPS, WristConstants.FREE_SPIN_LIMIT_AMPS)
        .apply(limitSwitchConfig);
    wristMotor.configure(
        wristMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // limitSwitchConfig.

    // wristRelativeEncoder.setPosition(
    //     ((wristAbsoluteEncoder.getPosition() / 2) * WristConstants.GEAR_RATIO));
    // wristRelativeEncoder.setPosition(wristAbsoluteEncoder.getPosition() / 2);
    // wristRelativeEncoder.setPosition(Units.degreesToRotations(90));
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from WristIOInputsAutoLogged
   */
  public void updateInputs(WristIOInputs inputs) {
    SmartDashboard.putBoolean("LimitSwitchPressed", wristLimitSwitch.isPressed());
    /** This returns the voltage the Wrist Motor Recieves */
    inputs.wristAppliedVolts = wristMotor.getAppliedOutput() * wristMotor.getBusVoltage();
    SmartDashboard.putNumber("WristVolts", inputs.wristAppliedVolts);
    /**
     * Returns the position of the absoltute encoder in Radians (Used to make sure wrist zero doesnt
     * change on enable //
     */
    // inputs.wristAbsolutePositionRad = wristAbsoluteEncoder.getPosition() * Math.PI;
    // /**
    //  * Returns the position of the absoltute encoder in Degrees (Used to make sure wrist zero
    // doesnt
    //  * change on enable
    //  */
    // inputs.wristAbsolutePositionDeg = wristAbsoluteEncoder.getPosition() * 180;
    /** Returns the position of the Wrist Motor by how many radians it has rotated */
    if (!wristLimitSwitch.isPressed()) {
      inputs.wristPositionRad =
          MathUtil.angleModulus(
              Units.rotationsToRadians(wristRelativeEncoder.getPosition())
                      / WristConstants.GEAR_RATIO
                  + Units.degreesToRadians(90));
    } else if (wristLimitSwitch.isPressed()) {
      inputs.wristPositionRad =
          MathUtil.angleModulus(Units.degreesToRadians(-180) + Units.degreesToRadians(90));
      wristRelativeEncoder.setPosition(180);
    }
    /** Returns the position of the Wrist Motor by how many degrees it has rotated */
    inputs.wristPositionDeg = Units.radiansToDegrees(inputs.wristPositionRad);
    /** Returns the velocity of the Wrist Motor by how many radians per second it has rotated */
    inputs.wristVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(wristRelativeEncoder.getVelocity())
            / WristConstants.GEAR_RATIO;
    /** The Current Drawn from the Wrist Motor in Amps */
    inputs.wristCurrentAmps = new double[] {wristMotor.getOutputCurrent()};
    SmartDashboard.putNumber("WristAmps", inputs.wristCurrentAmps[0]);
    /** The tempature of the Wrist Motor in Celsius */
    inputs.wristTempCelsius = new double[] {wristMotor.getMotorTemperature()};
    SmartDashboard.putNumber("WristCelsius", inputs.wristTempCelsius[0]);
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
