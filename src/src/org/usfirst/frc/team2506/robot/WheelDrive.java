package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class WheelDrive {
	Jaguar jaguar0;
	Jaguar jaguar1;
	AnalogInput encoder;
	PIDController pidController;
	
	double offset;
	
	final double ENCODER_MAX = 4.95;
	final double ENCODER_180 = ENCODER_MAX / 2;
	final double ENCODER_90 = ENCODER_MAX / 4;
	
	public WheelDrive(int jaguar0, int jaguar1, int encoder, double offset) {
		this.jaguar0 = new Jaguar (jaguar0);
		this.jaguar1 = new Jaguar (jaguar1);
		this.encoder = new AnalogInput (encoder);
		pidController = new PIDController (1, 0, 0, this.encoder, this.jaguar1);
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
		jaguar0.set(0);
	}
	
	public void drive (double x, double y) {

		if (x > MAX) {
			x -= 1;
			y *= -1;
		} else if (x < -MAX) {
			x += 1;
			y *= -1;
		}
		
		
//		double setpoint = x * (4.95 * 0.5) + (4.95 * 0.5) - ((offset / 360) * 5);
		double setpoint = x * (4.95 * 0.5) + (4.95 * 0.5);

		if (offset < -90)
		{
			setpoint = setpoint - offset / 360 * 5 - 4.95 / 2;
			y = -y;
			
		}
		else if (offset > 90)
		{
			setpoint = setpoint - offset / 360 * 5 + 4.95 / 2;
			y = -y;
		}
		else
			setpoint = setpoint - offset / 360 * 5;
		
		jaguar0.set(y);
		pidController.setSetpoint(setpoint);
		
	}
}
