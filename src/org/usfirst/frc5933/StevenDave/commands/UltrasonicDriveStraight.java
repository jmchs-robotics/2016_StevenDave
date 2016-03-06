// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5933.StevenDave.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5933.StevenDave.Robot;
import org.usfirst.frc5933.StevenDave.RobotMap;

/**
 *
 */
public class UltrasonicDriveStraight extends Command {
	private double inches_ = 0;
	private double speed_ = 0;
	private int tickCount_ = 5;
	public final static double someDegreeOfIntoxication = 0.001;
	private double lastWallDistance = 12;
	private boolean useDumbDashboard_ = true;
	
	public UltrasonicDriveStraight(double speed, double inches) {
		speed_ = speed;
		inches_ = inches;
		useDumbDashboard_ = false;
	}

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public UltrasonicDriveStraight() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (useDumbDashboard_) {
			speed_ = .5;
			inches_ = SmartDashboard.getNumber("Inches for driving");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	int absoluteCount_ = 0;
		while (tickCount_ > 0) {

			double wallDistance = RobotMap.helmsman.getPortUltrasonicDistance();
			
			double curve = (wallDistance - lastWallDistance) * someDegreeOfIntoxication; //
			Robot.driveTrain.driveStraight(-speed_, -curve); //DO NOT change this

			System.out.println("Ultrasonic Front: " + RobotMap.helmsman.getForwardUltrasonicDistance() + " - " + "Ultrasonic Left: " + RobotMap.helmsman.getPortUltrasonicDistance() + " - " + "Ultrasonic Right: " + RobotMap.helmsman.getStarboardUltrasonicDistance());

			if (RobotMap.helmsman.getForwardUltrasonicDistance() < inches_){ //drive until x' away from wall
				++absoluteCount_;
				if (absoluteCount_ > 4){
					tickCount_ = 0;
					absoluteCount_ = 0;
				}
				
			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tickCount_ == 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}