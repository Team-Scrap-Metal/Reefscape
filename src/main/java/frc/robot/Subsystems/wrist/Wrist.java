package frc.robot.Subsystems.wrist;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Wrist extends SubsystemBase {
  private final WristIO io;
  private final WristIOInputsAutoLogged inputs = new WristIOInputsAutoLogged();
  private final ProfiledPIDController wristPID =
      new ProfiledPIDController(
          WristConstants.KP,
          WristConstants.KI,
          WristConstants.KD,
          new Constraints(WristConstants.MAX_VELOCITY, WristConstants.MAX_ACCELERATION));
  private final ArmFeedforward wristFeedforward =
      new ArmFeedforward(WristConstants.KS, WristConstants.KG, WristConstants.KV);

  private double oldSetpoint = 0.0;

  public Wrist(WristIO io) {
    System.out.println("[Init] Creating Wrist");
    this.io = io;
    wristPID.setTolerance(WristConstants.PID_TOLERANCE_RAD);
    wristPID.setGoal(Units.degreesToRadians(90));
    wristPID.enableContinuousInput(-Math.PI, Math.PI);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Wrist", inputs);
    // updateControls();
    setWristVoltage(
        wristFeedforward.calculate(wristPID.getSetpoint().position, wristPID.getSetpoint().velocity)
            + wristPID.calculate(this.getWristPositionRad()));

    SmartDashboard.putNumber("WristSetpoint", wristPID.getSetpoint().position);
    SmartDashboard.putNumber("WristPosition", inputs.wristPositionRad);
    SmartDashboard.putBoolean(
        "safeToLift",
        (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(5)
                && getWristPositionRad() - Units.degreesToRadians(90) > Units.degreesToRadians(-5))
            || (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(-175)
                && getWristPositionRad() - Units.degreesToRadians(90)
                    > Units.degreesToRadians(-185)));
    SmartDashboard.putNumber(
        "WristPosition",
        Units.radiansToDegrees(getWristPositionRad() - Units.degreesToRadians(90)));
  }

  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setSetpointRad(double setpoint) {
    wristPID.setGoal(setpoint);
    SmartDashboard.putNumber("Setpoint", wristPID.getSetpoint().position);
  }

  public void incrementSetpoint(double increment) {
    oldSetpoint = wristPID.getGoal().position;
    oldSetpoint += increment;
    wristPID.setGoal(oldSetpoint);
  }

  public boolean safeToLift() {
    SmartDashboard.putBoolean(
        "safeToLift",
        (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(5)
                && getWristPositionRad() - Units.degreesToRadians(90) > Units.degreesToRadians(-5))
            || (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(-175)
                && getWristPositionRad() - Units.degreesToRadians(90)
                    > Units.degreesToRadians(-185)));

    return (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(5)
            && getWristPositionRad() - Units.degreesToRadians(90) > Units.degreesToRadians(-5))
        || (getWristPositionRad() - Units.degreesToRadians(90) < Units.degreesToRadians(-175)
            && getWristPositionRad() - Units.degreesToRadians(90) > Units.degreesToRadians(-185));
  }

  public void setWristVoltage(double volts) {
    io.setWristVoltage(volts);
  }

  public void setWristPercent(double percent) {
    io.setWristVoltage(percent * 12);
  }

  public double getWristPositionRad() {
    return inputs.wristPositionRad;
  }

  public void coastOnDisable(boolean isDisabled) {
    io.setBrakeMode(!isDisabled);
  }

  public void updateControls() {
    // Step 1: Get new Values
    WristConstants.KP = SmartDashboard.getNumber("WristKP", WristConstants.KP);
    WristConstants.KI = SmartDashboard.getNumber("WristKI", WristConstants.KI);
    WristConstants.KD = SmartDashboard.getNumber("WristKD", WristConstants.KD);
    WristConstants.PID_TOLERANCE_RAD =
        SmartDashboard.getNumber("WristTolerance", WristConstants.PID_TOLERANCE_RAD);
    WristConstants.MAX_VELOCITY =
        SmartDashboard.getNumber("WristMaxVel", WristConstants.MAX_VELOCITY);
    WristConstants.MAX_ACCELERATION =
        SmartDashboard.getNumber("WristMaxAccell", WristConstants.MAX_ACCELERATION);
    WristConstants.KS = SmartDashboard.getNumber("WristKS", WristConstants.KS);
    WristConstants.KG = SmartDashboard.getNumber("WristKG", WristConstants.KG);
    WristConstants.KV = SmartDashboard.getNumber("WristKV", WristConstants.KV);
    // WristConstants.KA = SmartDashboard.getNumber("WristKA", WristConstants.KA);
    // Step 2: Apply new Values
    wristPID.setPID(WristConstants.KP, WristConstants.KI, WristConstants.KD);
    wristPID.setConstraints(
        new Constraints(WristConstants.MAX_VELOCITY, WristConstants.MAX_ACCELERATION));
    wristFeedforward.setKs(WristConstants.KS);
    wristFeedforward.setKg(WristConstants.KG);
    wristFeedforward.setKv(WristConstants.KV);
    // WristFeedforward.setKa(WristConstants.KA);
    // Step 3: Put new Values
    SmartDashboard.putNumber("WristKP", WristConstants.KP);
    SmartDashboard.putNumber("WristKI", WristConstants.KI);
    SmartDashboard.putNumber("WristKD", WristConstants.KD);
    SmartDashboard.putNumber("WristTolerance", WristConstants.PID_TOLERANCE_RAD);
    SmartDashboard.putNumber("WristMaxVel", WristConstants.MAX_VELOCITY);
    SmartDashboard.putNumber("WristMaxAccell", WristConstants.MAX_ACCELERATION);
    SmartDashboard.putNumber("WristKS", WristConstants.KS);
    SmartDashboard.putNumber("WristKG", WristConstants.KG);
    SmartDashboard.putNumber("WristKV", WristConstants.KV);
    // SmartDashboard.putNumber("WristKA", WristConstants.KA);
  }
}
