package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.*;

public class BadShooter {
	public static final double SHOOTER_MAX_SPEED = 0.9;
	public static final double SHOOTER_RESTING_SPEED = 0;
	public static final double HOPPER_SPEED = -0.6;
	
	private CANTalon shooter;
	private Victor hopper;
	
	public BadShooter(int shooter, int hopper) {
		this.shooter = new CANTalon(shooter);
		this.hopper = new Victor(hopper);
		
		stopShooting();
	}
	
	public void startHopper() {
		this.hopper.set(HOPPER_SPEED);
	}
	public void stopHopper() {
		this.hopper.set(0);
	}
	
	public void startShooting() {
		this.shooter.set(SHOOTER_MAX_SPEED);
	}
	
	public void stopShooting() {
		this.shooter.set(SHOOTER_RESTING_SPEED);
	}
}
