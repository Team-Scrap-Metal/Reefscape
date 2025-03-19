package frc.robot.Subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.math.util.Units;

public class ElevatorIOVortex implements ElevatorIO {
  private final SparkFlex elevatorLeftMotor;
  private final SparkFlex elevatorRightMotor;
  private final RelativeEncoder elevatorLeftEncoder;
  private final SparkFlexConfig leftMotorConfig = new SparkFlexConfig();
  private final SparkFlexConfig rightMotorConfig = new SparkFlexConfig();

  public ElevatorIOVortex() {
    elevatorLeftMotor = new SparkFlex(ElevatorConstants.LEFT_CANID, MotorType.kBrushless);
    elevatorLeftEncoder = elevatorLeftMotor.getEncoder();
    elevatorRightMotor = new SparkFlex(ElevatorConstants.RIGHT_CANID, MotorType.kBrushless);
    leftMotorConfig
        .smartCurrentLimit(
            ElevatorConstants.STALL_LIMIT_AMPS, ElevatorConstants.FREESPIN_LIMIT_AMPS)
        .inverted(false)
        .idleMode(IdleMode.kBrake);
    elevatorLeftMotor.configure(
        leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightMotorConfig
        .smartCurrentLimit(
            ElevatorConstants.STALL_LIMIT_AMPS, ElevatorConstants.FREESPIN_LIMIT_AMPS)
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .follow(ElevatorConstants.LEFT_CANID);
    elevatorRightMotor.configure(
        rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    elevatorLeftEncoder.setPosition(0);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ElevatorIOInputs inputs) {
    /** This returns the voltage the elevator Motor Recieves */
    inputs.elevatorAppliedVolts =
        elevatorLeftMotor.getAppliedOutput() * elevatorLeftMotor.getBusVoltage()
            + elevatorRightMotor.getAppliedOutput() * elevatorRightMotor.getBusVoltage();
    /** Returns the position of the elevator Motor by how many radians it has rotated */
    inputs.elevatorPositionRad =
        Units.rotationsToRadians(elevatorLeftEncoder.getPosition()) / ElevatorConstants.GEAR_RATIO;
    /** /** Returns the position of the elevator Motor by how many meters it has raised */
    inputs.elevatorPositionM = Units.inchesToMeters(0.5) * inputs.elevatorPositionRad;
    /** Returns the velocity of the elevator Motor by how many radians per second it has rotated */
    inputs.elevatorVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(elevatorLeftEncoder.getVelocity())
            / ElevatorConstants.GEAR_RATIO;
    /**
     * Returns the velocity of the elevator Motor by how many rotations per minute it has rotated
     */
    inputs.elevatorVelocityRotPerMin =
        elevatorLeftEncoder.getVelocity() / ElevatorConstants.GEAR_RATIO;
    /** The Current Drawn from the elevator Motor in Amps */
    inputs.elevatorCurrentAmps =
        new double[] {elevatorLeftMotor.getOutputCurrent(), elevatorRightMotor.getOutputCurrent()};
    /** The tempature of the elevator Motor in Celsius */
    inputs.elevatorTempCelsius =
        new double[] {
          elevatorLeftMotor.getMotorTemperature(), elevatorRightMotor.getMotorTemperature()
        };
  }

  @Override
  /**
   * Sets the voltage for the Elevator
   *
   * @param volts -12 to 12
   */
  public void setElevatorVoltage(double volts) {
    elevatorLeftMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Elevator
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    leftMotorConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    rightMotorConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    elevatorRightMotor.configure(
        leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    elevatorRightMotor.configure(
        rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
