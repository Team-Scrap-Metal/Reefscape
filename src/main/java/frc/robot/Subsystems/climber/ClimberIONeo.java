// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.climber;

public class ClimberIONeo implements ClimberIO {
    public ClimberIONeo() {}
  
    @Override
    /**
     * updates the inputs to be actual values
     *
     * @param inputs from ModuleIOInputsAutoLogged
     */
    public void updateInputs(ClimberIOInputs inputs) {}
  
    @Override
    /**
     * Sets the voltage for the Climber
     *
     * @param volts -12 to 12
     */
    public void setClimberVoltage(double volts) {}
  
    @Override
    /**
     * Sets the Brake Mode for the Climber
     *
     * <p>Brake means motor holds position, Coast means easy to move
     *
     * @param enable if enable, it sets brake mode, else it sets coast mode
     */
    public void setBrakeMode(boolean enable) {}
  }
  