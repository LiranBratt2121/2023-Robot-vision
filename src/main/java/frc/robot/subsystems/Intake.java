// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveTrainConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.SensorConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

public class Intake extends SubsystemBase {
  private WPI_TalonFX m_leftIntakeMotor;
  private WPI_TalonFX m_rightIntakeMotor;
  private WPI_TalonFX m_liftMotor;

  private DigitalInput m_topLimitSwitch;
  private DigitalInput m_bottomLimitSwitch;

  private final ColorSensorV3 m_colorSensor;

  public Intake() {
    m_leftIntakeMotor = new WPI_TalonFX(IntakeConstants.kLeftIntakeMotorID);
    m_rightIntakeMotor = new WPI_TalonFX(IntakeConstants.kRightIntakeMotorID);
    m_liftMotor = new WPI_TalonFX(IntakeConstants.kLiftMotorID);

    m_topLimitSwitch = new DigitalInput(SensorConstants.kTopLimitSwitchPort);
    m_bottomLimitSwitch = new DigitalInput(SensorConstants.kBottomLimitSwitchPort);

    m_colorSensor = new ColorSensorV3(SensorConstants.kI2cPort);
  }

  public void grab(double speed) {
    m_leftIntakeMotor.set(speed);
    m_rightIntakeMotor.set(speed);
  }

  public void moveIntakeMechanism(double speed) {
    m_liftMotor.set(speed);
  }

  public Color getColor() {
    return m_colorSensor.getColor();
  }

  public int getProximity() {
    return m_colorSensor.getProximity();
  }

  public boolean isTopSwitchPressed(){
    return m_topLimitSwitch.get();
  }

  public boolean isBottomSwitchPressed(){
    return m_bottomLimitSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
