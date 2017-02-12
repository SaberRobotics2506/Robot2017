package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;

public class Shooter {
	final double ENCODER_FULL_SPEED = 1200;
	final double ENCODER_HALF_SPEED = ENCODER_FULL_SPEED / 2;
	
	final double HOPPER_SPEED = 0.9;
	
	private CANTalon shooter;
	private Victor hopper;
	
	public Shooter(int shooter, int hopper, int encoder) {
		this.shooter = new CANTalon(shooter);
		this.hopper = new Victor(hopper);
		
		this.shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.shooter.reverseSensor(false);
		this.shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		this.shooter.configPeakOutputVoltage(+12.0f, -12.0f);
		this.shooter.setProfile(0);
		this.shooter.setP(1);
		this.shooter.setI(0.2);
		this.shooter.setD(0);
		
		this.shooter.changeControlMode(TalonControlMode.Speed);
		
		setShooterResting();
	}
	
	public boolean readyToFire() {
		setShooterFiring();
		return shooter.getSpeed() >= ENCODER_FULL_SPEED - 200;
	}
	
	public void startShooting() {
		hopper.set(HOPPER_SPEED);
	}
	public void stopShooting() {
		hopper.set(0);
		setShooterResting();
	}
	
	private void setShooterResting() {
		shooter.set(ENCODER_HALF_SPEED);
	}
	private void setShooterFiring() {
		shooter.set(ENCODER_FULL_SPEED);
	}
}