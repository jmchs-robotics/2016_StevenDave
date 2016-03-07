// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5933.StevenDave.subsystems;

import org.usfirst.frc5933.StevenDave.RobotMap;
import org.usfirst.frc5933.StevenDave.commands.*;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class BallGrabberSubsystem extends Subsystem {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	private boolean isRunningForward = false;
	private boolean isRunningReverse = false;


	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final SpeedController spindleMotor = RobotMap.ballGrabberSubsystemSpindleMotor;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void runForward() {
		spindleMotor.set(-1.0);
	}

	public void runBackward() {
		spindleMotor.set(1.0);
	}

	public void stop() {
		spindleMotor.set(0);
	}

	public void toggleForward() {
		if (isRunningReverse)
			return;

		if (isRunningForward) {
			spindleMotor.set(0);
			isRunningForward = false;
		} else {
			spindleMotor.set(1);
			isRunningForward = true;
		}
	}

	public void toggleReverse() {
		if (isRunningForward)
			return;

		if (isRunningReverse) {
			spindleMotor.set(0);
			isRunningReverse = false;
		} else {
			spindleMotor.set(-1);
			isRunningReverse = true;
		}
	}
}

