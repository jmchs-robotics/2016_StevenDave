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

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon frontLeftMotor = RobotMap.driveTrainFrontLeftMotor;
    private final CANTalon frontRightMotor = RobotMap.driveTrainFrontRightMotor;
    private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private boolean isReversed = false;
    public static final double RIGHT_DRIVE_MULTIPLIER = 1.0;
    public static final double LEFT_DRIVE_MULTIPLIER = 1.0;


    public static final double TURBO_FAST_MULTIPLIER = 1.0;
    public static final double TURBO_SLOW_MULTIPLIER = 0.5;
    private double turboMultiplier = TURBO_FAST_MULTIPLIER;

    // Maximum change in voltage, in volts / sec. (0 to 24 volts in 1 second)
    // According to Talon SRX manual (1.15) the max value should be 10. However,
    // when I tried a value of 10 the robot response time was very laggy. So I thought
    // I would give 24 a shot, lo and behold it seemed to work just fine. This value seemed
    // to give us the best trade of between lag (response time to commands) and not browning
    // out the robot due to to much current draw.
    private static final int RAMP_RATE_IN_SECONDS = 24; 
    private static final int AUTO_RAMP_RATE_IN_SECONDS = 12; 

    public static final int TURN_MAX_TRIES = 1000;

    private TalonControlMode originalmode;
    private double originalLeftPosition;
    private double targetLeftRotations;
    private double originalRightPosition;
    private double targetRightRotations;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public DriveTrain() {
        super();
        setRampRate(RAMP_RATE_IN_SECONDS);
        frontLeftMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        frontRightMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        frontLeftMotor.configEncoderCodesPerRev(360);
        frontRightMotor.configEncoderCodesPerRev(360);
    }

    public DriveTrain(String name) {
        super(name);
    }

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void arcadeDrive(Joystick stick) {
        double y = stick.getY();
        double x = stick.getX();
        if (isReversed) {
            y *= -1;
            x *= -1;
        }

        y *= turboMultiplier;
        x *= turboMultiplier;
        // TODO: if we want the arcade to drive straight, we will need
        // to scale x by some factor.
        // I think the best way to do this is to temporarily set x to zero,
        // drive the robot using only the y value and slowly change x by some
        // multiplier value until it goes straight. Then let the value of
        // x remain like it should and see how well it works. Make sure to handle
        // the cases where x is: 0, -, +.
        robotDrive.arcadeDrive(y, x);
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    public void driveStraight(double speed, double curve) {
        robotDrive.drive(speed, curve);
    }

    public void set(double speed) {
        frontLeftMotor.set(speed);
        frontRightMotor.set(speed);
    }

    public void toggleTurbo() {
        if (turboMultiplier == TURBO_FAST_MULTIPLIER) {
            turboMultiplier = TURBO_SLOW_MULTIPLIER;
        } else {
            turboMultiplier = TURBO_FAST_MULTIPLIER;
        }
    }

    public void reverseControls() {
        robotDrive.tankDrive(0, 0);
        // delay the reverse by a second or so so we don't blow the drive train.
        Timer.delay(1.0);
        isReversed = !isReversed;
    }

    public void turnDegrees(double speed, double degrees) {
        enableBrakeMode(true);
        RobotMap.helmsman.resetGyro();
        final double startingAngle = RobotMap.helmsman.getCurrentGyroAngle();
        double now = startingAngle;
        final double desired = now + degrees;

        int tries = TURN_MAX_TRIES;

        if (desired > startingAngle) {
            do {
                robotDrive.tankDrive(-speed, speed);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                --tries;
                if (tries == 0) {
                    System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while (now < desired);
        } else {
            do {
                robotDrive.tankDrive(speed, -speed);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                --tries;
                if (tries == 0) {
                    System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while (now > startingAngle + degrees);
        }
        enableBrakeMode(false);

        stop();
    }

    public void enableBrakeMode(boolean enable) {
        frontLeftMotor.enableBrakeMode(enable);
        frontRightMotor.enableBrakeMode(enable);
    }

    private void setRampRate(int rate) {
        frontLeftMotor.setVoltageRampRate(rate);
        frontRightMotor.setVoltageRampRate(rate);
    }

    private void changeControlMode(TalonControlMode mode) {
        frontLeftMotor.changeControlMode(mode);
        frontLeftMotor.enableControl();

        frontRightMotor.changeControlMode(mode);
        frontRightMotor.enableControl();

        if (mode == TalonControlMode.Position) {
        } else {
        }
    }

    public void startPositionMovement(double leftRotations, double rightRotations) {
        originalmode = frontLeftMotor.getControlMode();

        // FIXME: Right now, this algorithm does not handle negative rotations since I don't
        // now what that will do to the position values;
        if ((leftRotations <= 0) || (rightRotations <= 0)) {
            targetLeftRotations = 0;
            targetRightRotations = 0;
            originalLeftPosition = 0;
            originalRightPosition = 0;
            return;
        }

        changeControlMode(TalonControlMode.Position);
        setRampRate(AUTO_RAMP_RATE_IN_SECONDS);

        targetLeftRotations = leftRotations;
        targetRightRotations = rightRotations;

        double frontLeftPosition = frontLeftMotor.getPosition();
        originalLeftPosition = frontLeftPosition;
        frontLeftPosition += leftRotations;

        // The rear motors are supposed to be in follow mode,
        // so we don't have to set the position for these
        // motors.
        // double rearLeftPosition = rearLeftMotor.getPosition();
        // rearLeftPosition += leftRotations;

        double frontRightPosition = frontRightMotor.getPosition();
        originalRightPosition = frontRightPosition;
        frontRightPosition += rightRotations;

        // double rearRightPosition = rearRightMotor.getPosition();
        // rearRightPosition += rightRotations;

        frontLeftMotor.set(frontLeftPosition);
        // rearLeftMotor.set(rearLeftPosition);

        frontRightMotor.set(frontRightPosition);
        // rearRightMotor.set(rearRightPosition);
    }

    public boolean hasFinishedPositionMovement() {
        // FIXME: Right now, this algorithm does not handle negative rotations since I don't
        // now what that will do to the position values;
        if ((targetLeftRotations == 0) && (targetRightRotations == 0)) 
            return true;

        double frontLeftPosition = frontLeftMotor.getPosition();
        boolean leftHasFinished = false;
        if (frontLeftPosition >= originalLeftPosition + targetLeftRotations) {
            leftHasFinished = true;
        }

        double frontRightPosition = frontRightMotor.getPosition();
        boolean rightHasFinished = false;
        if (frontRightPosition >= originalRightPosition + targetRightRotations) {
            rightHasFinished = true;
        }

        return leftHasFinished && rightHasFinished;
    }

    public void endPositionMovement() {
        changeControlMode(originalmode);

        // FIXME: It is unclear if I have to do this ....
        // frontLeftMotor.disableControl();
        // frontRightMotor.disableControl();
        // rearLeftMotor.disableControl();
        // rearRightMotor.disableControl();

        setRampRate(RAMP_RATE_IN_SECONDS);
    }
}
