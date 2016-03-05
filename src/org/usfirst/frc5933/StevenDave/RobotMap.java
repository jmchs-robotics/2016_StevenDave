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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon driveTrainFrontLeftMotor;
    public static CANTalon driveTrainFrontRightMotor;
    public static RobotDrive driveTrainRobotDrive;
    public static AnalogGyro sensorsAnalogGyro;
    public static AnalogInput sensorsForwardUltrasonic;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainFrontLeftMotor = new CANTalon(0);
        LiveWindow.addActuator("DriveTrain", "FrontLeftMotor", driveTrainFrontLeftMotor);
        
        driveTrainFrontRightMotor = new CANTalon(1);
        LiveWindow.addActuator("DriveTrain", "FrontRightMotor", driveTrainFrontRightMotor);
        
        driveTrainRobotDrive = new RobotDrive(driveTrainFrontRightMotor, driveTrainFrontLeftMotor);
        
        driveTrainRobotDrive.setSafetyEnabled(true);
        driveTrainRobotDrive.setExpiration(0.1);
        driveTrainRobotDrive.setSensitivity(0.5);
        driveTrainRobotDrive.setMaxOutput(1.0);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        sensorsAnalogGyro = new AnalogGyro(0);
        LiveWindow.addSensor("Sensors", "AnalogGyro", sensorsAnalogGyro);
        sensorsAnalogGyro.setSensitivity(0.007);
        sensorsForwardUltrasonic = new AnalogInput(1);
        LiveWindow.addSensor("Sensors", "ForwardUltrasonic", sensorsForwardUltrasonic);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
