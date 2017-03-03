package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class AutonomousShooting {
	// Tells the robot how far it needs to move back after scoreing the gear to be able to start turning twords the goal.
	public static final double GEAR_HALFWAY_FEET = 3.5;
	// Sets the amount of time in ticks that the robot needs to wait after it gets to the gear area so that the pilot can pull up the gear. 
	public static final double WAIT_TICKS = 150;
	// Tells the robot how far away from the gear area it needs to be in order to stop moving twords it.
	public static final double GEAR_RANGE_INCHES = 2.5;
	// Sets the angle that the robots needs to turn to, this gives it a set point to shoot from every time.
	public static final double TARGET_ANGLE = 107.5;
	//  Sets the variable so that every time it is called the robot will move at half speed to prevent it from moving too fast or slow.
	public static final double ROBOT_SPEED = 0.5;
	
	private enum AutonomousStates {
		MovingForward,
		Wait,
		MovingBackward,
		Turning,
		Shooting,
		End
	}
	// Sets the robots shooting state machine into the first section to start the cycle that it needs to go through.
	private static AutonomousStates autonomousState = AutonomousStates.MovingForward;
	// Sets these variables to values determined in previous sub-systems code.
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
					// Sets the robots drive motors moving forward at half speed
					driveTrain.drive(gyro, ROBOT_SPEED, 0, 0);
				} else {
					/* Sets the robots drive motors to no speed to stop the robot from moving and then moves on 
					to the next section.
					*/
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
