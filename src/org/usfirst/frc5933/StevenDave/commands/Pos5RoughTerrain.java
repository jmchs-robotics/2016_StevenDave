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

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc5933.StevenDave.subsystems.*;

/**
 *
 */
public class Pos5RoughTerrain extends CommandGroup {


	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
	public Pos5RoughTerrain() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
		addParallel(new LiftingArmDown(1));

		double path5Speed = -0.5;

		double l1 = 200;
		double l2 = 200;
		double l3 = 170;
		double dg1 = 90;
		double dg2 = 135;


		addSequential(new GyroDriveStraight(path5Speed, l1)); // TODO: The inches are not right
		addSequential(new GyroTurnDegrees(path5Speed, dg1)); // TODO: The degrees are not right
		addSequential(new GyroDriveStraight(path5Speed, l2)); // TODO: The inches are not right
		addSequential(new GyroTurnDegrees(path5Speed, dg2)); // TODO: The degrees are not right
		addSequential(new GyroDriveStraight(path5Speed, l3)); // TODO: The inches are not right
		addSequential(new ToggleReverseSpindle(5));

	} 
}
