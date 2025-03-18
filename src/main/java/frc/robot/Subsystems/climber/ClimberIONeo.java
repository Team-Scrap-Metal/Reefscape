// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;

public class ClimberIONeo implements ClimberIO {
  private SparkMax climberLeftMotor;
  private SparkMax climberRightMotor;
  private RelativeEncoder climberLeftEncoder;
  private RelativeEncoder climberRightEncoder;
  private SparkMaxConfig climberLeftConfig = new SparkMaxConfig();
  private SparkMaxConfig climberRightConfig = new SparkMaxConfig();

  public ClimberIONeo() {
    climberLeftMotor = new SparkMax(ClimberConstants.LEFT_CANID, MotorType.kBrushless);
    climberRightMotor = new SparkMax(ClimberConstants.RIGHT_CANID, MotorType.kBrushless);
    climberLeftEncoder = climberLeftMotor.getEncoder();
    climberRightEncoder = climberRightMotor.getEncoder();
    climberLeftConfig
        .inverted(ClimberConstants.LEFT_IS_INVERTED)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(ClimberConstants.STALL_LIMIT_AMPS, ClimberConstants.FREESPIN_LIMIT_AMPS);
    climberRightConfig
        .inverted(ClimberConstants.LEFT_IS_INVERTED)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(ClimberConstants.STALL_LIMIT_AMPS, ClimberConstants.FREESPIN_LIMIT_AMPS)
        .follow(ClimberConstants.LEFT_CANID);
    climberLeftMotor.configure(
        climberLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    climberRightMotor.configure(
        climberRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ClimberIOInputs inputs) {
    /** This returns the voltage the climber Motor Recieves */
    inputs.climberAppliedVolts =
        climberLeftMotor.getAppliedOutput() * climberLeftMotor.getBusVoltage()
            + climberRightMotor.getAppliedOutput() * climberRightMotor.getBusVoltage();
    /**
     * Returns the position of the absoltute encoder in Radians (Used to make sure climber zero
     * doesnt change on enable
     */
    /** Returns the position of the climber Motor by how many radians it has rotated */
    inputs.climberPositionRad =
        Units.rotationsToRadians(climberLeftEncoder.getPosition()) / ClimberConstants.GEAR_RATIO;
    /** Returns the position of the climber Motor by how many degrees it has rotated */
    inputs.climberPositionDeg =
        Units.rotationsToDegrees(climberLeftEncoder.getPosition()) / ClimberConstants.GEAR_RATIO;
    /** Returns the velocity of the climber Motor by how many radians per second it has rotated */
    inputs.climberVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(climberLeftEncoder.getVelocity())
            / ClimberConstants.GEAR_RATIO;
    /** The Current Drawn from the climber Motor in Amps */
    inputs.climberCurrentAmps =
        new double[] {climberLeftMotor.getOutputCurrent(), climberRightMotor.getOutputCurrent()};
    /** The tempature of the climber Motor in Celsius */
    inputs.climberTempCelsius =
        new double[] {
          climberLeftMotor.getMotorTemperature(), climberRightMotor.getMotorTemperature()
        };
  }

  @Override
  /**
   * Sets the voltage for the Climber
   *
   * @param volts -12 to 12
   */
  public void setClimberVoltage(double volts) {
    climberLeftMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Climber
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    climberLeftConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    climberRightConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    climberLeftMotor.configure(
        climberLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    climberRightMotor.configure(
        climberRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
