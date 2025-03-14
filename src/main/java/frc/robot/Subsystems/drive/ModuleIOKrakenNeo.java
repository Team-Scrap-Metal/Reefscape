package frc.robot.Subsystems.drive;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.RobotStateConstants;

/** Runs an Individual Real Module with all Motors as Neos */
public class ModuleIOKrakenNeo implements ModuleIO {
  private final TalonFX driveTalonFX;
  private final SparkMax turnSparkMax;

  private final RelativeEncoder turnRelativeEncoder;
  private final CANcoder turnAbsoluteEncoder;

  private final double absoluteEncoderOffset;
  private final int swerveModuleNumber;
  private final SparkMaxConfig neoConfig = new SparkMaxConfig();
  private final MotorOutputConfigs krakenConfig;
  ;

  public ModuleIOKrakenNeo(int index) {
    this.swerveModuleNumber = index;
    System.out.println("[Init] Creating ModuleIOKrakenNEO" + swerveModuleNumber);

    // sets drive & turn spark maxes, turn encoder, and absolute encoder offset
    switch (index) {
      case 0:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.FRONT_LEFT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.FRONT_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_LEFT.OFFSET;
        break;
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.FRONT_LEFT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.FRONT_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_LEFT.OFFSET;
        break;
      case 1:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.BACK_LEFT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.BACK_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_LEFT.OFFSET;
        break;
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.BACK_LEFT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.BACK_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_LEFT.OFFSET;
        break;
      case 2:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.BACK_RIGHT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.BACK_RIGHT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_RIGHT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_RIGHT.OFFSET;
        break;
      case 3:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.FRONT_RIGHT.CAN_ID);
        turnSparkMax =
            new SparkMax(DriveConstants.TURN_MOTOR.FRONT_RIGHT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_RIGHT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_RIGHT.OFFSET;
        break;
      default:
        throw new RuntimeException("Invalid module index for ModuleIOSparkMax");
    }

    // Set CAN Timeout
    driveTalonFX.setExpiration(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    turnSparkMax.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);

    // Connects Turn Motor and Turn Encoder
    turnRelativeEncoder = turnSparkMax.getEncoder();

    // Sets configs for motors
    krakenConfig =
        new MotorOutputConfigs()
            .withInverted(DriveConstants.KrakenNEOModule.INVERT_TALONFX)
            .withNeutralMode(NeutralModeValue.Brake);
    neoConfig
        .inverted(DriveConstants.KrakenNEOModule.INVERT_SPARK_MAX)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(40, 40);

    /** For each drive motor, update values */
    for (int i = 0; i < DriveConstants.DRIVE_MOTOR.values().length; i++) {
      // turnSparkMax.setPeriodicFramePeriod(
      //     PeriodicFrame.kStatus2, DriveConstants.MEASUREMENT_PERIOD_MS); TODO: Update to current
      // code

      // Sets Motor Configs
      driveTalonFX.getConfigurator().apply(krakenConfig);
      turnSparkMax.configure(
          neoConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      CurrentLimitsConfigs currentLimitsConfig =
          new CurrentLimitsConfigs().withSupplyCurrentLimit(DriveConstants.DRIVE_SUPPLY_LIMIT_AMP);
      currentLimitsConfig.withSupplyCurrentLimitEnable(DriveConstants.ENABLE_CUR_LIM);
      currentLimitsConfig.withStatorCurrentLimit(DriveConstants.DRIVE_STATOR_LIMIT_AMP);
      currentLimitsConfig.withStatorCurrentLimitEnable(DriveConstants.ENABLE_CUR_LIM);
      driveTalonFX.getConfigurator().apply(currentLimitsConfig);

      driveTalonFX.setPosition(0.0); // resets position
    }

    // Initializes motors in brake mode
    driveTalonFX.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionRad =
        Units.rotationsToRadians(
            driveTalonFX.getPosition().getValueAsDouble() / DriveConstants.GEAR_RATIO);
    inputs.driveVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(
                driveTalonFX.getVelocity().getValueAsDouble() * 60)
            / DriveConstants.GEAR_RATIO;

    // unit conversions: Kraken getVelocity returns rotations per sec, multiply by 60 to get RPM
    inputs.driveVelocityRadPerSecAbs =
        Math.abs(
            Units.rotationsPerMinuteToRadiansPerSecond(
                    driveTalonFX.getVelocity().getValueAsDouble() * 60)
                / DriveConstants.GEAR_RATIO);

    inputs.driveAppliedVolts =
        driveTalonFX.getMotorVoltage().getValueAsDouble()
            * driveTalonFX.getSupplyVoltage().getValueAsDouble();

    inputs.driveCurrentAmps = new double[] {driveTalonFX.getStatorCurrent().getValueAsDouble()};
    inputs.driveTempCelsius = new double[] {driveTalonFX.getDeviceTemp().getValueAsDouble()};

    // getPosition returns rotations of motor, not the turn angle
    inputs.turnAbsolutePositionRad =
        MathUtil.angleModulus(
            new Rotation2d(
                    Units.rotationsToRadians(
                            turnAbsoluteEncoder.getAbsolutePosition().getValueAsDouble())
                        + absoluteEncoderOffset)
                .getRadians());

    inputs.turnAppliedVolts = turnSparkMax.getAppliedOutput() * turnSparkMax.getBusVoltage();
    inputs.turnCurrentAmps = new double[] {turnSparkMax.getOutputCurrent()};
    inputs.turnTempCelsius = new double[] {turnSparkMax.getMotorTemperature()};
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveTalonFX.setVoltage(volts);
  }

  @Override
  public void setTurnVoltage(double volts) {
    turnSparkMax.setVoltage(volts);
  }

  @Override
  public void setDriveBrakeMode(boolean enable) {
    driveTalonFX.setNeutralMode(enable ? NeutralModeValue.Brake : NeutralModeValue.Coast);
  }

  @Override
  public void setTurnBrakeMode(boolean enable) {
    neoConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    turnSparkMax.configure(
        neoConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
