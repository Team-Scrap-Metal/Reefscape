package frc.robot.Subsystems.linkage;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LinkageIONeo implements LinkageIO {
  private final RelativeEncoder linkagRelativeEncoder;
  private final SparkMax linkageMotor;
  private final SparkMaxConfig linkageConfig = new SparkMaxConfig();

  public LinkageIONeo() {
    System.out.println("(INIT) creating LinkageIONeo");
    linkageMotor = new SparkMax(LinkageConstants.CAN_ID, MotorType.kBrushless);
    linkagRelativeEncoder = linkageMotor.getEncoder();

    linkageConfig
        .inverted(LinkageConstants.IS_INVERTED)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(
            LinkageConstants.STALL_LIMIT_AMPS, LinkageConstants.FREE_STALL_LIMIT_AMPS);

    linkageMotor.configure(
        linkageConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(LinkageIOInputs inputs) {

    inputs.linkagePositionRad =
        Units.rotationsToRadians(linkagRelativeEncoder.getPosition()) / LinkageConstants.GEAR_RATIO;
    inputs.linkageAppliedVolts = (linkageMotor.getAppliedOutput() * linkageMotor.getBusVoltage());

    SmartDashboard.putNumber("linkageVolts", inputs.linkageAppliedVolts);
    inputs.linkageTempCelsius = new double[] {linkageMotor.getMotorTemperature()};
    SmartDashboard.putNumber("linkageCelsius", inputs.linkageTempCelsius[0]);
    inputs.linkageCurrentAmps = new double[] {linkageMotor.getOutputCurrent()};
    SmartDashboard.putNumber("linkageAmps", inputs.linkageCurrentAmps[0]);
    inputs.linkageVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(linkagRelativeEncoder.getVelocity())
            / LinkageConstants.GEAR_RATIO;
    inputs.linkagePositionDeg =
        Units.rotationsToDegrees(linkagRelativeEncoder.getPosition()) / LinkageConstants.GEAR_RATIO;
  }

  @Override
  /**
   * Sets the voltage for the Linkage
   *
   * @param volts -12 to 12
   */
  public void setLinkageVoltage(double volts) {
    linkageMotor.setVoltage(volts);
  }

  @Override
  /**
   * Sets the Brake Mode for the Linkage
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    linkageConfig.idleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  public double getLinkageVoltage() {
    return linkagRelativeEncoder.getVelocity();
  }
}
