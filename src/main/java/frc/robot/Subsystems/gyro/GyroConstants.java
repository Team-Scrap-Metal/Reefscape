// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.gyro;

public class GyroConstants {
  /** Sets the offset of the heading so that the Intake side is the front of the robot */
  public static final double HEADING_OFFSET_DEGREES = -90;

  /**
   * CAN IDs for motors and encoders /* 1-4: Absolute Encoders, 5-8: Drive Motors, 9-12: Steer
   * Motors, 13: Pigeon
   */
  public static final int CAN_ID = 13;
}
