package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class GyroUtils {
	private ADXRS450_Gyro gyro;
	private SwerveDrive driveTrain;
	
	public GyroUtils(ADXRS450_Gyro gyro, SwerveDrive driveTrain) {
		this.gyro = gyro;
		this.driveTrain = driveTrain;
	}
	
	public void drive(double heading, double speed) {
	}
	public void drive(double heading, double speed, double range) {
	}
	
	public void rotateFieldRelative (double heading, double speed, double range) {
		double angle = gyro.getAngle() % 360;
		if (angle > range + heading) {
			driveTrain.drive(gyro, 0, 0, -speed);
		}
		else if (angle < heading - range) {
			driveTrain.drive(gyro, 0, 0, speed);
		} else {
			driveTrain.drive(gyro, 0, 0, 0);
		}
	}
}
