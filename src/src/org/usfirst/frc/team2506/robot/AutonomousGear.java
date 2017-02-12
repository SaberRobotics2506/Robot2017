package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;

public class AutonomousGear {
	static final double GEAR_RANGE_INCHES = 4;
	private enum AutonomousStates {
		MovingForward,
		End
	}
	private static AutonomousStates autonomousState = AutonomousStates.MovingForward;
	private static ADXRS450_Gyro gyro;
	private static SwerveDrive driveTrain;
	private static Ultrasonic ultrasonic;
	private static Shooter shooter;
	
	public static void init(ADXRS450_Gyro gyro, SwerveDrive drive, Ultrasonic ultrasonic, Shooter shooter) {
		AutonomousGear.gyro = gyro;
		driveTrain = drive;
		AutonomousGear.ultrasonic = ultrasonic;
		AutonomousGear.shooter = shooter;
	}
	
	public static void run() {
		switch (autonomousState) {
			case MovingForward:
				if (ultrasonic.getRangeInches() > GEAR_RANGE_INCHES) {
					driveTrain.drive(gyro, 0.5, 0, 0);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.End;
				}
				break;
		}
	}
}
