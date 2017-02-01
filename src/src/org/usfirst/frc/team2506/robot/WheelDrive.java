package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class WheelDrive {
	Jaguar speedMotor;
	Jaguar angleMotor;
	AnalogInput encoder;
	PIDController pidController;
	
	double offset;
	
	final double ENCODER_MAX = 4.95;
	final double ENCODER_180 = ENCODER_MAX / 2;
	final double ENCODER_90 = ENCODER_MAX / 4;
	
	public WheelDrive(int speedMotor, int angleMotor, int encoder, double offset) {
		this.speedMotor = new Jaguar (speedMotor);
		this.angleMotor = new Jaguar (angleMotor);
		this.encoder = new AnalogInput (encoder);
		pidController = new PIDController (1, 0, 0, this.encoder, this.angleMotor);
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous();
		pidController.enable();
		
		this.offset = offset;
	}
	public final double MAX = 0.5;
	
	public void incrementOffset()
	{
		offset++;
	}
	public void decrementOffset()
	{
		offset--;
	}
	public double getOffset()
	{
		return offset;
	}
	
	public void coast () {
		speedMotor.set(0);
	}
	
	public void drive (double angle, double speed) {

		if (angle > MAX) {
			angle -= 1;
			speed *= -1;
		} else if (angle < -MAX) {
			angle += 1;
			speed *= -1;
		}
		
		
//		double setpoint = x * (4.95 * 0.5) + (4.95 * 0.5) - ((offset / 360) * 5);
		double setpoint = angle * (4.95 * 0.5) + (4.95 * 0.5);

		if (offset < -90)
		{
			setpoint = setpoint - offset / 360 * 5 - 4.95 / 2;
			speed *= -1;
			
		}
		else if (offset > 90)
		{
			setpoint = setpoint - offset / 360 * 5 + 4.95 / 2;
			speed *= -1;
		}
		else
			setpoint = setpoint - offset / 360 * 5;
		
		speedMotor.set(speed);
		pidController.setSetpoint(setpoint);
		
	}
}
