package frc.robot.Subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

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
    leftMotorConfig.smartCurrentLimit(40).inverted(false).idleMode(IdleMode.kBrake);
    elevatorLeftMotor.configure(
        leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightMotorConfig
        .smartCurrentLimit(40)
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .follow(ElevatorConstants.LEFT_CANID);
    elevatorRightMotor.configure(
        rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

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
  public void setBrakeMode(boolean enable) {}
}
