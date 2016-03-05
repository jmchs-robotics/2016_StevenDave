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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5933.StevenDave.subsystems.*;
import org.usfirst.frc5933.StevenDave.RobotMap;
import org.usfirst.frc5933.StevenDave.commands.DriveStraight;
import org.usfirst.frc5933.StevenDave.commands.ToggleReverseSpindle;
import org.usfirst.frc5933.StevenDave.commands.TurnDegrees;

/**
 *
 */
public class DeliverBallToLowerWindow extends CommandGroup {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public DeliverBallToLowerWindow() {

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
    }

    public void readDumbDashboard() {

        int startingPosition = (int) SmartDashboard.getNumber("Autonomous Starting Position");
        int barrierNumber = (int) SmartDashboard.getNumber("Autonomous Barrier Number");
        int lowerWindowPosition = (int) SmartDashboard.getNumber("Autonomous Lower Window Position");

        switch (startingPosition) {
        case 1: {
            doPath1(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        case 2: {
            doPath2(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        case 3: {
            doPath3(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        case 4: {
            doPath4(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        case 5: {
            doPath5(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        case 6: {
            doPath6(startingPosition, barrierNumber, lowerWindowPosition);
        } break;
        default: {
            doDefaultPath();
        } break;
        } 
    }

    private void doDefaultPath() {
        RobotMap.helmsman.resetGyro();
        addSequential(new DriveStraight(1.0, 65));
        addSequential(new TurnDegrees(0.6, 173));
        RobotMap.helmsman.resetGyro();
        addSequential(new DriveStraight(1.0, 65));
        addSequential(new TurnDegrees(0.6, 174));
        RobotMap.helmsman.resetGyro();
        addSequential(new DriveStraight(1.0, 63));
    }

    private void doPath1(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path1Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Low bar, path to Lower Window 1

            addSequential(new DriveStraight(path1Speed, 132)); // TODO: The inches are not right
            addSequential(new DriveStraight(path1Speed, -132)); // TODO: The inches are not right
        }
    }

    private void doPath2(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path2Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Barrier 2 to Lower Window 1

            double l1 = 26 * 12; //Inches
            double dg = 132; //Degrees
            double l2 = 10.375 * 12; //Inches

            if (barrierNumber == 2) {
                l1 +=10;
            }

            addSequential(new DriveStraight(path2Speed, l1)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path2Speed, dg)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path2Speed, l2)); // TODO: The inches are not right
            addSequential(new ToggleReverseSpindle());
        }
    }

    private void doPath3(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path3Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Barrier 3 to Lower Window 1

            double l1 = 20.5 * 12; //Inches
            double dg1 = 50; //Degrees
            double l2 = 7.25 * 12; //Inches
            double dg2 = 109; //Degrees
            double l3 = 10.5 * 12; //Inches

            if (barrierNumber == 2) {
                l1 +=10;
            }

            addSequential(new DriveStraight(path3Speed, l1)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path3Speed, dg1)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path3Speed, l2)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path3Speed, dg2)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path3Speed, l3)); // TODO: The inches are not right
            addSequential(new ToggleReverseSpindle());
        }
    }

    private void doPath4(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path4Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Barrier 4 to Lower Window 1

            double l1 = 18.125 * 12; //Inches
            double dg1 = 55; //Degrees
            double l2 = 11.75 * 12; //Inches
            double dg2 = 109; //Degrees
            double l3 = 10.5 * 12; //Inches

            if (barrierNumber == 2) {
                l1 += 10;
            }

            addSequential(new DriveStraight(path4Speed, l1)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path4Speed, dg1)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path4Speed, l2)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path4Speed, dg2)); // TODO: The degress are not right
            addSequential(new DriveStraight(path4Speed, l3)); // TODO: The inches are not right
            addSequential(new ToggleReverseSpindle());
        }
    }

    private void doPath5(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path5Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Barrier 5 to Lower Window 1

            double l1 = 200;
            double l2 = 200;
            double l3 = 170;
            double dg1 = 90;
            double dg2 = 135;

            if (barrierNumber == 2) {
                l1 += 10;
            }

            addSequential(new DriveStraight(path5Speed, l1)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path5Speed, dg1)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path5Speed, l2)); // TODO: The inches are not right
            addSequential(new TurnDegrees(path5Speed, dg2)); // TODO: The degrees are not right
            addSequential(new DriveStraight(path5Speed, l3)); // TODO: The inches are not right
            addSequential(new ToggleReverseSpindle());
        }
    }

    private void doPath6(int startingPosition, int barrierNumber, int lowerWindowPosition) {
        double path6Speed = -0.5;
        if (lowerWindowPosition == 1) {
            //Spy Box to Lower Window 1

            //TODO: Position unknown, will have to change lengths and degrees

            double l1 = 60;
            double l2 = 110;
            double dg1 = 45;
            double dg2 = 90;

            if (barrierNumber == 2) {
                l1 += 10;
            }

            addSequential(new TurnDegrees(path6Speed, dg1)); //May have to initially turn 
            addSequential(new DriveStraight(path6Speed, l1)); 
            addSequential(new TurnDegrees(path6Speed, dg2));
            addSequential(new DriveStraight(path6Speed, l2)); 
            addSequential(new ToggleReverseSpindle());
        }
    }
}