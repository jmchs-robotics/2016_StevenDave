// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5933.StevenDave;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5933.StevenDave.commands.*;
import org.usfirst.frc5933.StevenDave.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    Command arcadeDrive;
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Sensors sensors;
    public static BallGrabberSubsystem ballGrabberSubsystem;
    public static Arm arm;
    public static Winch winch;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        RobotMap.init();
        initDashboardInput();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        sensors = new Sensors();
        ballGrabberSubsystem = new BallGrabberSubsystem();
        arm = new Arm();
        winch = new Winch();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        autonomousCommand = new Pos1LowBar();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        arcadeDrive = new ArcadeDrive();

        readPreferences();
    }

    private void configAutonomousCommand() {
        final String key = "AutonomousCommand";
        Preferences prefs = Preferences.getInstance();
        if (!prefs.containsKey(key)) {
            return;
        }
        String ac = prefs.getString(key, "unknown");
        if (ac.compareTo("Pos2Mote") == 0) {
            autonomousCommand = new Pos2Mote(); 
        } else if (ac.compareTo("Pos2RockWall") == 0) {
            autonomousCommand = new Pos2RockWall(); 
        } else if (ac.compareTo("Pos2Rampart") == 0) {
            autonomousCommand = new Pos2Rampart();
        } else if (ac.compareTo("Pos2RoughTerrain") == 0) {
            autonomousCommand = new Pos2RoughTerrain();
        } else if (ac.compareTo("Pos3Mote") == 0) {
            autonomousCommand = new Pos3Mote(); 
        } else if (ac.compareTo("Pos3RockWall") == 0) {
            autonomousCommand = new Pos3RockWall(); 
        } else if (ac.compareTo("Pos3Rampart") == 0) {
            autonomousCommand = new Pos3Rampart();
        } else if (ac.compareTo("Pos3RoughTerrain") == 0) {
            autonomousCommand = new Pos3RoughTerrain();
        } else if (ac.compareTo("Pos4Mote") == 0) {
            autonomousCommand = new Pos4Mote(); 
        } else if (ac.compareTo("Pos4RockWall") == 0) {
            autonomousCommand = new Pos4RockWall(); 
        } else if (ac.compareTo("Pos4Rampart") == 0) {
            autonomousCommand = new Pos4Rampart();
        } else if (ac.compareTo("Pos4RoughTerrain") == 0) {
            autonomousCommand = new Pos4RoughTerrain();
        } else if (ac.compareTo("Pos5Mote") == 0) {
            autonomousCommand = new Pos5Mote(); 
        } else if (ac.compareTo("Pos5RockWall") == 0) {
            autonomousCommand = new Pos5RockWall(); 
        } else if (ac.compareTo("Pos5Rampart") == 0) {
            autonomousCommand = new Pos5Rampart();
        } else if (ac.compareTo("Pos5RoughTerrain") == 0) {
            autonomousCommand = new Pos5RoughTerrain();
        } else if (ac.compareTo("SpyBox") == 0) {
            autonomousCommand = new SpyBox();
        } else if (ac.compareTo("EncoderDriveStraight") == 0) {
            autonomousCommand = new EncoderDriveStraight();
        } else if (ac.compareTo("EncoderTurnDegrees") == 0) {
            autonomousCommand = new EncoderTurnDegrees();
        } else if (ac.compareTo("SpyBox") == 0) {
            autonomousCommand = new SpyBox();
        }

        System.out.println("Autonomous Command is: " + autonomousCommand.getName());
    }

    // Read the preferences as setup by the smart dashboard preferences view.
    private void readPreferences() {
        configAutonomousCommand();
    }

    /**
     * Initializes the Starting Values on the SmartDashboard, so not to create a Null Pointer.
     */
    public void initDashboardInput() {
        SmartDashboard.putNumber("Degrees for turning", 0);
        SmartDashboard.putNumber("Inches for driving", 0);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
        oi.getXBoxJoystick().setRumble(RumbleType.kLeftRumble, 0);
        oi.getXBoxJoystick().setRumble(RumbleType.kRightRumble, 0);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        RobotMap.enableUltrasonicTrigger(true);
        RobotMap.helmsman.initTracking();
        Robot.driveTrain.configForAutonomous();

        if (autonomousCommand != null) { 
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        RobotMap.helmsman.trackPosition();
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) 
            autonomousCommand.cancel();

        RobotMap.enableUltrasonicTrigger(false);
        driveTrain.configForTeleopMode();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        rumbleInYourPants();

        if (arcadeDrive != null) 
            arcadeDrive.start();
    }

    /**
     * Takes the Z Axis of the RoboRIO accelerometer, and passes it to the Joystick for haptic feedback of the robot.
     */
    public void rumbleInYourPants() {
        double accel_z = RobotMap.helmsman.getAcceleromoterZ();
        Robot.oi.getXBoxJoystick().setRumble(RumbleType.kLeftRumble, (float) Math.abs(accel_z - 1));
        Robot.oi.getXBoxJoystick().setRumble(RumbleType.kRightRumble, (float) Math.abs(accel_z - 1));
    }


    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
