package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;

public class AutonomousGear {
	// target distance from goal
	static final double GEAR_RANGE_INCHES = 2.5;
	// robot motor speed
	static final double ROBOT_SPEED = 0.5;
	
	private enum AutonomousStates {
		MovingForward,
		End
	}
	private static AutonomousStates autonomousState = AutonomousStates.MovingForward;
	private static ADXRS450_Gyro gyro;
	private static SwerveDrive driveTrain;
	private static Ultrasonic ultrasonic;
	
	// method that copies the funcionality of a constructor
	public static void init(ADXRS450_Gyro gyro, SwerveDrive drive, Ultrasonic ultrasonic) {
		AutonomousGear.gyro = gyro;
		driveTrain = drive;
		AutonomousGear.ultrasonic = ultrasonic;
	}
	
	public static void run() {
		switch (autonomousState) {
			case MovingForward:
				
				// drives the robot while it is farther than GEAR_RANGE_INCHES away from the goal, then stops
				if (ultrasonic.getRangeInches() > GEAR_RANGE_INCHES) {
					driveTrain.drive(gyro, ROBOT_SPEED, 0, 0);
				} else {
					driveTrain.drive(gyro, 0, 0, 0);
					autonomousState = AutonomousStates.End;
				}
				break;
		}
	}
}
