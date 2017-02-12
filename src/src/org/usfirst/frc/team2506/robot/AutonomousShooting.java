package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class AutonomousShooting {
	public static final double TARGET_ANGLE = 45;
	public static final double TARGET_DISTANCE_INCHES = 150;
	
	private enum AutonomousStates {
		TurnRight,
		MovingForward,
		Shooting,
		End
	}
	private static AutonomousStates autonomousState = AutonomousStates.TurnRight;
	private static ADXRS450_Gyro gyro;
	private static SwerveDrive driveTrain;
	private static Ultrasonic ultrasonic;
	private static Shooter shooter;
	
	public static void init(ADXRS450_Gyro gyro, SwerveDrive drive, Ultrasonic ultrasonic, Shooter shooter) {
		AutonomousShooting.gyro = gyro;
		driveTrain = drive;
		AutonomousShooting.ultrasonic = ultrasonic;
		AutonomousShooting.shooter = shooter;
	}
	
	public static void run() {
		switch (autonomousState) {
			case TurnRight:
				if (gyro.getAngle() < TARGET_ANGLE) {
					driveTrain.drive(gyro, 0, 0, 0.25);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.MovingForward;
				}
				break;
			case MovingForward:
				if (ultrasonic.getRangeInches() > TARGET_DISTANCE_INCHES) {
					driveTrain.drive(gyro, 0.5, 0, 0);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.Shooting;
				}
				break;
			case Shooting:
				if (shooter.readyToFire())
					shooter.startShooting();
				break;
			default:
				break;
		}
	}
}
