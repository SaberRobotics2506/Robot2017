package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team2506.robot.*;

public class WheelDrive2 extends WheelDrive {
	
	static double ENCODER_MAX = 4.95;
	
	public WheelDrive2(int driveJaguar, int steerJaguar, int encoder, double offset) {
		super(driveJaguar, steerJaguar, encoder, offset);
	}
	
	public void incrementOffset() {
		offset++;
	}
	public void decrementOffset() {
		offset--;
	}
	public double getOffset() {
		return offset;
	}
	
	public void coast () {
		speedMotor.set(0);
	}
	
	public void drive (double angle, double speed) {
		angle *= 180; // convert back to degrees

		double currentAngle = encoder.getVoltage() * 360 / ENCODER_MAX - 180 + offset;
		double currentOpposite = normalize(currentAngle + 180);
		
		double currentDiff = angleDiff(angle, currentAngle);
		double oppositeDiff = angleDiff(angle, currentOpposite);
		
		double commandAngle;
		double commandSpeed;
		if (currentDiff <= oppositeDiff) {
			commandAngle = angle;
			commandSpeed = speed;
		}
		else {
			commandAngle = normalize(angle + 180);
			commandSpeed = -speed;
		}
		
		double adjustedAngle = normalize(commandAngle - offset);
		
		double setpoint = (adjustedAngle / 180) * ENCODER_MAX / 2 + ENCODER_MAX / 2;
		
		pidController.setSetpoint(setpoint);
		speedMotor.set(commandSpeed);
	}
	
	double normalize(double angle) {
		if (angle < -180)
			angle += 360;
		else if (angle > 180)
			angle -= 360;
		return angle;
	}
	
	double angleDiff(double angle1, double angle2)
	{
		double diff;
		
		if (angle1 < -90 && angle2 > 90) {
			diff = (180 + angle1) + (180 - angle2);
		}
		else if (angle2 < -90 && angle1 > 90) {
			diff = (180 + angle2) + (180 - angle1);
		}
		else {
			diff = Math.abs(angle1 - angle2);
		}
		
		return diff;
	}
}
