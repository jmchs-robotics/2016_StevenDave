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

import org.usfirst.frc5933.StevenDave.PreferenceConstants;
import org.usfirst.frc5933.StevenDave.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
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

    // Allow us to flip the direction of the arcade controls during a match 
    private boolean reverseTeleopControls_ = false;

    // Allow us to dampen the speed of the robot during arcade movement so
    // it is easier to control 
    private static final double TURBO_FAST_MULTIPLIER = 1.0;
    private static final double TURBO_SLOW_MULTIPLIER = 0.5;
    private double turboMultiplier = TURBO_FAST_MULTIPLIER;

    // Maximum change in voltage, in volts / sec. (0 to 24 volts in 1 second)
    // According to Talon SRX manual (1.15) the max value should be 10. However,
    // when I tried a value of 10 the robot response time was very laggy. So I thought
    // I would give 24 a shot, lo and behold it seemed to work just fine. This value seemed
    // to give us the best trade of between lag (response time to commands) and not browning
    // out the robot due to to much current draw.
    private static final int RAMP_RATE_IN_SECONDS = 24; 
    private static final int AUTO_RAMP_RATE_IN_SECONDS = 12; 
    private static final int TURN_MAX_TRIES = 1000;

    // These values are used when we are using position mode to drive to a certain point.
    private double targetLeftPosition_ = 0;
    private double targetRightPosition_ = 0;

    // These values are used to track the last position through the execute position
    // method. This allows us to see when the Talon controllers have finished the position
    // movement
    private double lastLeftPosition_ = 0;
    private double lastRightPosition_ = 0;
    private double sameLeftCount_ = 0;
    private double sameRightCount_ = 0;
    private final static double FINISHED_POSITION_MOVEMENT_POSITION_COUNT = 50;

    // Use a string builder private data member since it is a ton more efficient
    // in building debug output. Sting concatenation is notoriously in-efficient.
    private StringBuilder encoderDebugString_ = new StringBuilder();

    // Debounce the debug output a bit so we don't use up all of the CPU.
    private int encoderDebugOutputCount_ = 0;
    private static final double ENCODER_DEBUG_COUNT = 10;

    // To try and keep the robot driving straight we will flip the sequence of
    // who gets their position (Left and Right motors) set first.
    private boolean setLeftFirst_ = true;

    // Are we close to the position end ?
    private boolean isCloseDebounce_ = false;
    private static final double POSITION_CLOSE = 200;

    private boolean debug_ = false;

    private double closedLoopFeedForward_ = 0;
    private double closedLoopPorportional_ = 0.115;
    private double closedLoopIntegration_ = 0;
    private double closedLoopDerivative_ = 0;


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public DriveTrain() {
        super();

        // use the preferences to determine if we should debug this subsystem
        if (Preferences.getInstance().containsKey(PreferenceConstants.DEBUG_SUBSYSTEM_DRIVE_TRAIN_KEY)) {
            debug_ = Preferences.getInstance().getBoolean(PreferenceConstants.DEBUG_SUBSYSTEM_DRIVE_TRAIN_KEY, false);
        }

        // Make sure that the encoders and position data are counting in the right direction
        frontRightMotor.reverseOutput(true);
        frontRightMotor.reverseSensor(false);
        frontLeftMotor.reverseOutput(false);
        frontLeftMotor.reverseSensor(true);
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

        // reverse the arcade controls during a match
        if (reverseTeleopControls_) {
            y *= -1;
            x *= -1;
        }

        // Slow down / or speed up arcade mode dynamically.
        y *= turboMultiplier;
        x *= turboMultiplier;


        // If we want the arcade to drive straight, we will need
        // to scale x by some factor.
        // I think the best way to do this is to temporarily set x to zero,
        // drive the robot using only the y value and slowly change x by some
        // multiplier value until it goes straight. Then let the value of
        // x remain like it should and see how well it works. Make sure to handle
        // the cases where x is: 0, -, +.

        if (debug_)
            printEncoderDebugging(true);

        robotDrive.arcadeDrive(-y, x);
    }

    // Stop it .... Just Stop it ......
    public void stop() {
        robotDrive.stopMotor();
    }

    // Don't drink and drive ....
    public void driveStraight(double speed, double curve) {
        robotDrive.drive(speed, curve);
    }

    // the method that is called by some other class to change the arcade motor speed
    public void toggleTurbo() {
        if (turboMultiplier == TURBO_FAST_MULTIPLIER) {
            turboMultiplier = TURBO_SLOW_MULTIPLIER;
        } else {
            turboMultiplier = TURBO_FAST_MULTIPLIER;
        }
    }

    // the method that is called to reverse the controls 
    public void reverseControls() {
        robotDrive.tankDrive(0, 0);
        // delay the reverse by a second or so so we don't blow the drive train.
        Timer.delay(1.0);
        reverseTeleopControls_ = !reverseTeleopControls_;
    }

    // This method does not work very well since the speed
    // of the operation can affect its accuracy. To make this better, an overshoot
    // must be reversed.
    // This type of control loop can work, but to be anywhere close to accurate
    // it has to be run very slowly.
    public void gyroTurnDegrees(double degrees) {
        RobotMap.helmsman.resetGyro();
        final double startingAngle = RobotMap.helmsman.getCurrentGyroAngle();
        double now = startingAngle;
        //final double desired = now + degrees;
        final double desired = 57;

        int tries = TURN_MAX_TRIES;

        if (desired > startingAngle) {
            do {
                robotDrive.tankDrive(-0.8,0.8); // -speed, speed);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                System.out.println("Angle top group: " + now);
                --tries;
                if (tries == 0) {
                    if (debug_)
                        System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while(now < desired-60);

            do {
                robotDrive.tankDrive(-0.5,0.5); // -speed, speed);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                System.out.println("Angle top group: " + now);
                --tries;
                if (tries == 0) {
                    if (debug_)
                        System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while(now < desired-2);
        } else {
            do {
                robotDrive.tankDrive(0.8, -0.8);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                System.out.println("Angle bottom group: " + now);
                --tries;
                if (tries == 0) {
                    if (debug_)
                        System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while(now > startingAngle + degrees);

            do {
                robotDrive.tankDrive(-0.5,0.5); // -speed, speed);
                now = RobotMap.helmsman.getCurrentGyroAngle();
                System.out.println("Angle top group: " + now);
                --tries;
                if (tries == 0) {
                    if (debug_)
                        System.err.println("Failed to turn specified degrees.");
                    break;
                }
            } while(now < desired-2);
        }
        stop();
    }

    // In autonomous mode we don't want to coast since it makes it harder to
    // to compensate for errors.
    public void enableBrakeMode(boolean enable) {
        frontLeftMotor.enableBrakeMode(enable);
        frontRightMotor.enableBrakeMode(enable);
    }

    // Let switch the ramp rates for teleop and autonomous for differing reasons.
    // For teleop we don't want to brown out.
    // For autonmous we want to keep as much accuracy as possible.
    private void setRampRate(int rate) {
        frontLeftMotor.setVoltageRampRate(rate);
        frontRightMotor.setVoltageRampRate(rate);
    }

    // In teleop we will use percent vbus
    // In autonomous we will use Position (At least that was the idea at when this comment was written)
    private void changeControlMode(TalonControlMode mode) {
        frontLeftMotor.changeControlMode(mode);
        frontRightMotor.changeControlMode(mode);
    }

    // Set up the position movement variables and configuration.
    public void startPositionMovement(double leftRotations, double rightRotations) {
        configForAutonomous();

        lastLeftPosition_ = 0;
        sameLeftCount_ = 0;
        lastRightPosition_ = 0;
        sameRightCount_ = 0;

        isCloseDebounce_ = false;

        targetLeftPosition_ = frontLeftMotor.getPosition() + leftRotations;
        targetRightPosition_ = frontRightMotor.getPosition() + rightRotations;
        // The rear motors are supposed to be in follow mode,
        // so we don't have to set the position for these
        // motors.
        if (debug_) {
            System.out.println("Start Position Movement");
            printEncoderDebugging(false);
        }

        if (setLeftFirst_) {
            frontLeftMotor.set(targetLeftPosition_);
            frontRightMotor.set(targetRightPosition_);
        } else {
            frontRightMotor.set(targetRightPosition_);
            frontLeftMotor.set(targetLeftPosition_);
        }
        setLeftFirst_ = !setLeftFirst_;
    }

    // print every ten loops, printing too much too fast is generally bad for performance,
    // and you get this stinky message saying that something(I don't remember the exact output)
    //  was not updated enough
    private void printEncoderDebugging(boolean useCount) {
        if (useCount) {
            if (encoderDebugOutputCount_ < ENCODER_DEBUG_COUNT) {
                ++encoderDebugOutputCount_;
                return;
            }
        }

        encoderDebugOutputCount_ = 0;
        encoderDebugString_.append("L: ");
        encoderDebugString_.append("\tpos:");
        encoderDebugString_.append(frontLeftMotor.getPosition() );
        encoderDebugString_.append("\terr:");
        encoderDebugString_.append(frontLeftMotor.getClosedLoopError());
        encoderDebugString_.append("\ttrg:");
        encoderDebugString_.append(targetLeftPosition_);

        encoderDebugString_.append("\tL: ");
        encoderDebugString_.append("\tpos:");
        encoderDebugString_.append(frontRightMotor.getPosition() );
        encoderDebugString_.append("\terr:");
        encoderDebugString_.append(frontRightMotor.getClosedLoopError());
        encoderDebugString_.append("\ttrg:");
        encoderDebugString_.append(targetRightPosition_);
        System.out.println(encoderDebugString_.toString());

        encoderDebugString_.setLength(0);
    }

    public boolean hasFinishedPositionMovement() {
        double frontLeftPosition = frontLeftMotor.getPosition();
        // May want to see if the last position is close enough to the current position 
        if (lastLeftPosition_ == frontLeftPosition) {
            ++sameLeftCount_;
        } else {
            sameLeftCount_ = 0;
        }

        // May want to see if the last position is close enough to the current position 
        double frontRightPosition = frontRightMotor.getPosition();
        if (lastRightPosition_ == frontRightPosition) {
            ++sameRightCount_;
        } else {
            sameRightCount_ = 0;
        }

        boolean leftHasFinished = sameLeftCount_ >= FINISHED_POSITION_MOVEMENT_POSITION_COUNT;
        boolean rightHasFinished = sameRightCount_ >= FINISHED_POSITION_MOVEMENT_POSITION_COUNT;

        return leftHasFinished && rightHasFinished;
    }

    // Cleanup any position movement configuration.
    public void endPositionMovement() {
        if (debug_) {
            System.out.println("End Position Movement");
            printEncoderDebugging(false);
        }
        configForTeleopMode();
    }

    // I think the method names says it all.
    public void configForTeleopMode() {
        changeControlMode(TalonControlMode.PercentVbus);
        setRampRate(RAMP_RATE_IN_SECONDS);
    }

    // Internal convenience method
    private void setTalonAbsolutePosition(CANTalon talon) {
        /* lets grab the 360 degree position of the MagEncoder's absolute position */
        /* mask out the bottom12 bits, we don't care about the wrap arounds */
        int absolutePosition = talon.getPulseWidthPosition() & 0xFFF;

        /* use the low level API to set the quad encoder signal */
        talon.setEncPosition(absolutePosition);
    }

    // Internal convenience method
    private void setTalonFeedbackDevice(CANTalon talon) {
        talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    }

    // Internal convenience method
    private void configVoltages(CANTalon talon, float nominal, float peak) {
        talon.configNominalOutputVoltage(nominal, -nominal);
        talon.configPeakOutputVoltage(peak, -peak);
    }

    // Internal convenience method
    private void resetClosedLoopErrors(CANTalon talon) {
        talon.setAllowableClosedLoopErr(0); /* always servo */
    }

    // Internal convenience method
    private void setClosedLoopGains(CANTalon talon) {

        closedLoopFeedForward_ = Preferences.getInstance().getDouble(PreferenceConstants.CLOSED_LOOP_FEEDFORWARD_KEY, closedLoopFeedForward_);
        closedLoopPorportional_ = Preferences.getInstance().getDouble(PreferenceConstants.CLOSED_LOOP_PORPORTIONAL_KEY, closedLoopPorportional_);
        closedLoopIntegration_ = Preferences.getInstance().getDouble(PreferenceConstants.CLOSED_LOOP_INTEGRATION_KEY, closedLoopIntegration_);
        closedLoopDerivative_ = Preferences.getInstance().getDouble(PreferenceConstants.CLOSED_LOOP_DERIVATIVE_KEY, closedLoopDerivative_);

        talon.setProfile(0);
        talon.setF(closedLoopFeedForward_);
        talon.setP(closedLoopPorportional_);
        talon.setI(closedLoopIntegration_); 
        talon.setD(closedLoopDerivative_);    
    }

    // I think the method names says it all.
    public void configForAutonomous() {
        // A lot of this information / procedure was divined by reading
        // the Talon software manual and looking at source code from Cross 
        // The Road Electronics. I found CTRE source code from git hub:
        // https://github.com/CrossTheRoadElec

        changeControlMode(TalonControlMode.Position);
        // I think this interfering with the error correction
        // setRampRate(AUTO_RAMP_RATE_IN_SECONDS);

        setTalonAbsolutePosition(frontLeftMotor);
        setTalonAbsolutePosition(frontRightMotor);

        setTalonFeedbackDevice(frontLeftMotor);
        setTalonFeedbackDevice(frontRightMotor);

        configVoltages(frontLeftMotor, 0, 6);
        configVoltages(frontRightMotor, 0, 6);

        /* set the allowable closed-loop error,
         * Closed-Loop output will be neutral within this range.
         * See Table in Section 17.2.1 for native units per rotation. 
         */
        resetClosedLoopErrors(frontLeftMotor);
        resetClosedLoopErrors(frontRightMotor);

        /* set closed loop gains in slot0 */
        setClosedLoopGains(frontLeftMotor);
        setClosedLoopGains(frontRightMotor);
    }

    private boolean closeToEndPosition() {
        double leftPosition = Math.abs(lastLeftPosition_ * 1000);
        double rightPosition = Math.abs(lastRightPosition_ * 1000);

        boolean leftIsClose = false;
        if (Math.abs(targetLeftPosition_ * 1000) - leftPosition < POSITION_CLOSE) {
            leftIsClose = true;
        }
        boolean rightIsClose = false;
        if (Math.abs(targetRightPosition_ * 1000) - rightPosition < POSITION_CLOSE) {
            rightIsClose = true;
        }

        return leftIsClose && rightIsClose;
    }

    // Called by a command to run the position movement.
    public void executePositionMove() {
        // Switch between left and right to hopefully drive a bit straighter.
        if (setLeftFirst_) {
            frontLeftMotor.set(targetLeftPosition_);
            frontRightMotor.set(targetRightPosition_);
        } else {
            frontRightMotor.set(targetRightPosition_);
            frontLeftMotor.set(targetLeftPosition_);
        }
        setLeftFirst_ = !setLeftFirst_;

        // Make sure to set the last position so the executePosition method can tell if
        // we re done.
        lastLeftPosition_ = frontLeftMotor.getPosition();
        lastRightPosition_ = frontRightMotor.getPosition();

        // Slow down the position movement so we don't have to do as much correction
        if (closeToEndPosition()) {
            if (!isCloseDebounce_) {
                if (debug_)
                    System.out.println("Position movement is getting close");
                isCloseDebounce_ = true;
                // configVoltages(frontLeftMotor, 0, 6);
                // configVoltages(frontRightMotor, 0, 6);
            }
        }
        if (debug_)
            printEncoderDebugging(true);
    }
}
