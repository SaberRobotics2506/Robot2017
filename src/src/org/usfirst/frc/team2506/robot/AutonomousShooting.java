package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class AutonomousShooting {
	public static final double GEAR_HALFWAY_FEET = 3.5;
	public static final double WAIT_TICKS = 150;
	public static final double GEAR_RANGE_INCHES = 2.5;
	public static final double TARGET_ANGLE = 107.5;
	public static final double ROBOT_SPEED = 0.5;
	
	private enum AutonomousStates {
		MovingForward,
		Wait,
		MovingBackward,
		Turning,
		Shooting,
		End
	}
	private static AutonomousStates autonomousState = AutonomousStates.MovingForward;
	private static ADXRS450_Gyro gyro;
	private static SwerveDrive driveTrain;
	private static Ultrasonic ultrasonic;
	private static BadShooter shooter;
	
	public static void init(ADXRS450_Gyro gyro, SwerveDrive drive, Ultrasonic ultrasonic, BadShooter shooter) {
		AutonomousShooting.gyro = gyro;
		driveTrain = drive;
		AutonomousShooting.ultrasonic = ultrasonic;
		AutonomousShooting.shooter = shooter;
	}
	
	private static int clock = 0;
	public static void run() {
		switch (autonomousState) {
			case MovingForward:
				if (ultrasonic.getRangeInches() > GEAR_RANGE_INCHES) {
					driveTrain.drive(gyro, ROBOT_SPEED, 0, 0);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.Wait;
				}
				break;
			case Wait:
				if (clock++ > 150) {
					autonomousState = AutonomousStates.MovingBackward;
				}
				break;
			case MovingBackward:
				if (ultrasonic.getRangeInches() / 12 < GEAR_HALFWAY_FEET) {
					driveTrain.drive(gyro, -ROBOT_SPEED, 0, 0);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.Turning;
				}
				break;
			case Turning:
				if (((gyro.getAngle() % 360) - 90) < TARGET_ANGLE) {
					driveTrain.drive(gyro, 0, 0, ROBOT_SPEED);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.Shooting;
				}
				break;
			case Shooting:
				shooter.startShooting();
				break;
		}
	}
}
