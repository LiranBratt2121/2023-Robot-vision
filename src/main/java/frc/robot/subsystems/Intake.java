// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ColorSensorConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.SensorConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.TogglePipeline;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

public class Intake extends SubsystemBase {
  private WPI_TalonFX m_intakeMotor;
  private WPI_TalonFX m_liftMotor;

  private DigitalInput m_topLimitSwitch;
  private DigitalInput m_bottomLimitSwitch;

  private final ColorSensorV3 m_colorSensor;

  private PhotonVision m_PhotonVision;

  public Intake() {
    m_intakeMotor = new WPI_TalonFX(IntakeConstants.kLeftIntakeMotorID);
    m_liftMotor = new WPI_TalonFX(IntakeConstants.kLiftMotorID);

    m_topLimitSwitch = new DigitalInput(SensorConstants.kTopLimitSwitchPort);
    m_bottomLimitSwitch = new DigitalInput(SensorConstants.kBottomLimitSwitchPort);

    m_colorSensor = new ColorSensorV3(SensorConstants.kI2cPort);
  }

  public void grab(double speed) {
    m_intakeMotor.set(speed);
  }

  public void lift(double speed) {
    if (m_topLimitSwitch.get() && speed > 0)
      m_liftMotor.set(0);
    else if (m_bottomLimitSwitch.get() && speed < 0)
      m_liftMotor.set(0);
    else
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
    if(m_colorSensor.getRed() >= ColorSensorConstants.kConemin[0] && m_colorSensor.getRed() <= ColorSensorConstants.kConemax[0] && m_colorSensor.getGreen() >= ColorSensorConstants.kConemin[1] && m_colorSensor.getGreen() <= ColorSensorConstants.kConemax[1] && m_colorSensor.getBlue() >= ColorSensorConstants.kConemin[2] && m_colorSensor.getBlue() <= ColorSensorConstants.kConemax[2]){
      new TogglePipeline(m_PhotonVision,VisionConstants.kRetroPipline);
    }
    if(m_colorSensor.getRed() >= ColorSensorConstants.kCubemin[0] && m_colorSensor.getRed() <= ColorSensorConstants.kCubemax[0] && m_colorSensor.getGreen() >= ColorSensorConstants.kCubemin[1] && m_colorSensor.getGreen() <= ColorSensorConstants.kCubemax[1] && m_colorSensor.getBlue() >= ColorSensorConstants.kCubemin[2] && m_colorSensor.getBlue() <= ColorSensorConstants.kCubemax[2]){
      new TogglePipeline(m_PhotonVision,VisionConstants.kAprilPipline);
    }
    // This method will be called once per scheduler run
  }
}
