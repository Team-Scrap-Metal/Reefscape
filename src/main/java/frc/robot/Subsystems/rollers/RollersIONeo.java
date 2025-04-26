package frc.robot.Subsystems.rollers;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;

public class RollersIONeo implements RollersIO {

  public RollersIONeo() {
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(RollersIOInputs inputs) {
  }

  @Override
  /**
   * Sets the voltage for the Rollers
   *
   * @param volts -12 to 12
   */
  public void setRollersVoltage(double volts) {
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
  }
}
