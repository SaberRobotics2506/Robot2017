package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;

public class WheelDrive {
	private Jaguar jaguar0;
	private Jaguar jaguar1;
	private AnalogInput encoder;
	private PIDController pidController;
	
	private double offset;
	
	public WheelDrive(int jaguar0, int jaguar1, int encoder, double offset) {
		this.jaguar0 = new Jaguar (jaguar0);
		this.jaguar1 = new Jaguar (jaguar1);
		this.encoder = new AnalogInput (encoder);
		pidController = new PIDController (1, 0, 0, this.encoder, this.jaguar1);
		
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
		
		jaguar0.set(y);
		
		double setpoint = x * (4.95 * 0.5) + (4.95 * 0.5) - ((offset / 360) * 5);
		
		if (setpoint < 0) {
			setpoint = 4.95 + setpoint;
		}
		if (setpoint > 4.95) {
			setpoint = setpoint - 4.95;
		}
		pidController.setSetpoint(setpoint);
		
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous();
		pidController.enable();
	}
}
