package org.usfirst.frc.team2506.robot;

import org.usfirst.frc.team2506.robot.XboxController.JoystickManager;
import org.usfirst.frc.team2506.robot.XboxController.XboxButtons;
import org.usfirst.frc.team2506.robot.XboxController.Events.IButtonPressed;
import org.usfirst.frc.team2506.robot.XboxHandlers.ButtonStartHandler;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private Joystick playerOne = new Joystick(PortConstants.PORT_JOYSTICK_ONE);
	private Joystick playerTwo = new Joystick(PortConstants.PORT_JOYSTICK_TWO);
	private JoystickManager joystickManager = new JoystickManager(playerOne);
	private IButtonPressed startButtonPressedHandler = new ButtonStartHandler(this);

	private WheelDrive backLeft = new WheelDrive(
			WheelConstants.MOTOR_BACK_LEFT_SPEED,
			WheelConstants.MOTOR_BACK_LEFT_ANGLE,
			WheelConstants.MOTOR_BACK_LEFT_ENCODER,
			WheelConstants.MOTOR_BACK_LEFT_OFFSET
			);
	private WheelDrive frontLeft = new WheelDrive(
			WheelConstants.MOTOR_FRONT_LEFT_SPEED,
			WheelConstants.MOTOR_FRONT_LEFT_ANGLE,
			WheelConstants.MOTOR_FRONT_LEFT_ENCODER,
			WheelConstants.MOTOR_FRONT_LEFT_OFFSET
			);
	private WheelDrive backRight = new WheelDrive(
			WheelConstants.MOTOR_BACK_RIGHT_SPEED,
			WheelConstants.MOTOR_BACK_RIGHT_ANGLE,
			WheelConstants.MOTOR_BACK_RIGHT_ENCODER,
			WheelConstants.MOTOR_BACK_RIGHT_OFFSET
			);
	private WheelDrive frontRight = new WheelDrive(
			WheelConstants.MOTOR_FRONT_RIGHT_SPEED,
			WheelConstants.MOTOR_FRONT_RIGHT_ANGLE,
			WheelConstants.MOTOR_FRONT_RIGHT_ENCODER,
			WheelConstants.MOTOR_FRONT_RIGHT_OFFSET
			);

	private SwerveDrive swerveDrive = new SwerveDrive(frontRight, backRight, frontLeft, backLeft);
	//private Winch winch = new Winch(PortConstants.PORT_WINCH);
	private Intake intake = new Intake(PortConstants.PORT_INTAKE);
	//private Shooter shooter = new Shooter(PortConstants.PORT_SHOOTER_SHOOTER, PortConstants.PORT_SHOOTER_HOPPER);
	private BadShooter shooter = new BadShooter(PortConstants.PORT_SHOOTER_SHOOTER, PortConstants.PORT_SHOOTER_HOPPER);
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private GyroUtils gyroUtils = new GyroUtils(gyro, swerveDrive);
	private Ultrasonic ultrasonic = new Ultrasonic(1, 0);
	
	@Override
	public void robotInit() {
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		
    	ultrasonic.setEnabled(true);
    	ultrasonic.setAutomaticMode(true);
	}

	@Override
	public void autonomousInit() {
		gyro.reset();
		//AutonomousGear.init(gyro, swerveDrive, ultrasonic);
	}

	@Override
	public void autonomousPeriodic() {
		//AutonomousGear.run();
	}

	public void teleopInit() {
		swerveDrive.drive(gyro, 0, 0, 0);
		joystickManager.addButtonPressedListener(XboxButtons.BUTTON_A, startButtonPressedHandler);
		joystickManager.start();
	}

	@Override
	public void teleopPeriodic() {
		System.out.println(ultrasonic.getRangeInches());
		//winch.turnOn(playerTwo.getRawAxis(XboxButtons.AXIS_LEFT_TRIGGER));
		intake.roll(playerTwo.getRawAxis(XboxButtons.AXIS_RIGHT_TRIGGER));

		double yAxis = playerOne.getRawAxis(XboxButtons.AXIS_LEFT_Y);
		double xAxis = playerOne.getRawAxis(XboxButtons.AXIS_LEFT_X);
		double rotation = playerOne.getRawAxis(XboxButtons.AXIS_RIGHT_X);
		
		if (yAxis > 0.1 || yAxis < -0.1 || xAxis > 0.1 || xAxis < -0.1 || rotation > 0.1 || rotation < -0.1) {
			// Turbo, bitches! xD
			if (playerOne.getRawButton(XboxButtons.BUTTON_LEFT_STICK))
				swerveDrive.drive(gyro, yAxis, -xAxis, rotation, 0.8);
			else if (playerOne.getRawButton(XboxButtons.BUTTON_RIGHT_STICK))
				swerveDrive.drive(gyro, yAxis, -xAxis, rotation, 0.4);
			else
				swerveDrive.drive(gyro, yAxis, -xAxis, rotation);
		} else {
			swerveDrive.drive(gyro, 0, 0, 0);
		}
		
		if (playerTwo.getRawButton(XboxButtons.BUTTON_A)) {
			shooter.startHopper();
		} else {
			shooter.stopHopper();
		}
		
		if (playerTwo.getRawAxis(XboxButtons.AXIS_LEFT_TRIGGER) > 0.1) {
			shooter.startShooting();
		} else {
			shooter.stopShooting();
		}
	}

	WheelDrive[] wheels = { frontRight, frontLeft, backRight, backLeft };
	WheelDrive testWheel = wheels[0];
	boolean b8 = false;

	@Override
	public void testPeriodic() {
		swerveDrive.drive(gyro, 0, 0, 0);

		for (int b = 1; b <= 4; b++) {
			if (playerOne.getRawButton(b)) {
				testWheel = wheels[b - 1];
				System.out.println("calibrating wheel " + b);
			}
		}

		double stick = playerOne.getRawAxis(1);

		if (stick > .2)
			testWheel.incrementOffset();
		else if (stick < -0.2)
			testWheel.decrementOffset();

		if (playerOne.getRawButton(8)) {
			if (!b8) {
				System.out.println("front right " + frontRight.getOffset());
				System.out.println("front left " + frontLeft.getOffset());
				System.out.println("back right " + backRight.getOffset());
				System.out.println("back left " + backLeft.getOffset());
			}
			b8 = true;
		} else {
			b8 = false;
		}
	}
}
