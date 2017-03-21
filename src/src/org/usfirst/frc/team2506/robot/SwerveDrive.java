package org.usfirst.frc.team2506.robot;


import java.math.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class SwerveDrive {
	public static final double DEFAULT_SPEED_MULTIPLIER = 0.6;
	public SwerveDrive () {
		
	}
	
	public final double L = 24;
	public final double W = 26;
	
	WheelDrive frontRight;
	WheelDrive backRight;
	WheelDrive frontLeft;
	WheelDrive backLeft;
	
	public SwerveDrive (WheelDrive frontRight, WheelDrive backRight, WheelDrive frontLeft, WheelDrive backLeft) {
		this.frontRight = frontRight;
		this.backRight = backRight;
		this.frontLeft = frontLeft;
		this.backLeft = backLeft;
	}
	public void drive (ADXRS450_Gyro gyro, double y1, double x1, double x2) {
		drive (gyro, y1, x1, x2, DEFAULT_SPEED_MULTIPLIER);
	}
	public void drive (ADXRS450_Gyro gyro, double y1, double x1, double x2, double multiplier) {
		double[] rotatedInputs = rotateInputs(x1, y1, (gyro.getAngle() % 360) - 270 );
		x1 = rotatedInputs[0];
		y1 = rotatedInputs[1];
		_drive(squareAxis(x1) * multiplier, -squareAxis(y1) * multiplier, -squareAxis(x2) * multiplier);
	}
	
	private void _drive (double y1, double x1, double x2) {
		y1 *= -1;
		
		double R = Math.sqrt(L * L + W * W);
		
		double a = x1 - x2 * (L / R);
		double b = x1 + x2 * (L / R);
		double c = y1 - x2 * (W / R);
		double d = y1 + x2 * (W / R);

		// Calculate all the wheel speeds.
		double ws1 = Math.sqrt ((b * b) + (c * c));
		double ws2 = Math.sqrt ((b * b) + (d * d));
		double ws3 = Math.sqrt ((a * a) + (d * d));
		double ws4 = Math.sqrt ((a * a) + (c * c));
		
		// Calculate all the wheel angles.
		double wa1 = Math.atan2 (b, c) / Math.PI;
		double wa2 = Math.atan2 (b, d) / Math.PI;
		double wa3 = Math.atan2 (a, d) / Math.PI;
		double wa4 = Math.atan2 (a, c) / Math.PI;
		
		// Drive the motors.
		frontRight.drive (wa2, ws1);
		frontLeft.drive (wa1, ws2);
		backRight.drive (wa3, ws3);
		backLeft.drive (wa4, -ws4);
	}
	
	private double squareAxis(double axis) {
		return axis * axis * Math.signum(axis);
	}

	private double[] rotateInputs(double x, double y, double gyroAngle) {
		double[] retVal = new double[2];
		double angle = Math.toRadians(gyroAngle);

		// Rotated X input
		retVal[0] = (x * Math.cos(angle)) - (y * Math.sin(angle));
		// Rotated Y input
		retVal[1] = (x * Math.sin(angle)) + (y * Math.cos(angle));

		return retVal;
	}
}
