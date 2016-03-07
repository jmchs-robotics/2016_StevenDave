package org.usfirst.frc5933.StevenDave;

import org.usfirst.frc5933.StevenDave.RobotMap;
import org.usfirst.frc5933.StevenDave.mapping.FieldOfPlay;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;

public class Helmsman {

	private AnalogGyro gyro_ = RobotMap.sensorsAnalogGyro;
	private double beginingGyroAngle_ = 0;
	private double beginingGyroCenter_ = 0;
	private double beginingGyroOffset_ = 0;
	private double initialGyroAngle_ = 0;
	private double initialGyroCenter_ = 0;
	private double initialGyroOffset_ = 0;

	private FieldOfPlay field_ = new FieldOfPlay();
	private BuiltInAccelerometer accel_ = new BuiltInAccelerometer();
	private static RiptServer cameraServer_;

	private CANTalon frontLeftMotor_ = RobotMap.driveTrainFrontLeftMotor;
	private double initialFrontLeftMotorPosition_ = 0;
	private double currentFrontLeftMotorPosition_ = 0;

	private CANTalon frontRightMotor_ = RobotMap.driveTrainFrontRightMotor;
	private double initialFrontRightMotorPosition_ = 0;
	private double currentFrontRightMotorPosition_ = 0;

	private static final double FORWARD_V2D = 85.7;     // Voltage to distance (inches) scalar for forward ultrasonic
	private static final double PORT_V2D = 85.7;     // Voltage to distance (inches) scalar for port ultrasonic
	private static final double STARBOARD_V2D = 85.7;     // Voltage to distance (inches) scalar for starboard ultrasonic

	private static AnalogInput forwardUltrasonic_ = RobotMap.sensorsForwardUltrasonic; //bow
	private static AnalogInput portUltrasonic_ = RobotMap.sensorsPortUltrasonic; //left
	private static AnalogInput starboardUltrasonic_ = RobotMap.sensorsStarboardUltrasonic; //right

	Helmsman() {
		gyro_.initGyro();
		beginingGyroAngle_ = gyro_.getAngle();
		beginingGyroCenter_ = gyro_.getCenter();
		beginingGyroOffset_ = gyro_.getOffset();
		cameraServer_ = RiptServer.getInstance();
		cameraServer_.setQuality(100);
		cameraServer_.setSize(0);
		cameraServer_.startAutomaticCapture("cam0");
	}

	public void resetGyro() {
		gyro_.reset();
	}

	public final double getBeginingGyroAngle() {
		return beginingGyroAngle_;
	}

	public final double getBeginingGyroOffset() {
		return beginingGyroOffset_;
	}

	public final double getBeginingGyroCenter() {
		return beginingGyroCenter_;
	}

	public final double getInitialGyroAngle() {
		return initialGyroAngle_;
	}

	public final double getInitialGyroOffset() {
		return initialGyroOffset_;
	}

	public final double getInitialGyroCenter() {
		return initialGyroCenter_;
	}

	public final double getCurrentGyroAngle() {
		return gyro_.getAngle();
	}

	public final double getForwardUltrasonicDistance() {
		return forwardUltrasonic_.getAverageVoltage() * FORWARD_V2D;
	}

	public final double getPortUltrasonicDistance() {
		return portUltrasonic_.getAverageVoltage() * PORT_V2D;
	}

	public final double getStarboardUltrasonicDistance() {
		return starboardUltrasonic_.getAverageVoltage() * STARBOARD_V2D;
	}

	public void initTracking() {
		gyro_.reset();
		initialGyroAngle_ = gyro_.getAngle();
		initialGyroCenter_ = gyro_.getCenter();
		initialGyroOffset_ = gyro_.getOffset();

		initialFrontLeftMotorPosition_ = frontLeftMotor_.get();
		currentFrontLeftMotorPosition_ = initialFrontLeftMotorPosition_;

		initialFrontRightMotorPosition_ = frontRightMotor_.get();
		currentFrontRightMotorPosition_ = initialFrontRightMotorPosition_;
	}

	public void trackPosition() {
		currentFrontLeftMotorPosition_ = frontLeftMotor_.get();
		currentFrontRightMotorPosition_ = frontRightMotor_.get();
	}

	public final double getAcceleromoterZ() {
		return accel_.getZ();
	}

	public final double getCurrentLeftMotorPosition() {
		return currentFrontLeftMotorPosition_;
	}

	public final double getCurrentRightMotorPosition() {
		return currentFrontRightMotorPosition_;
	}
}
