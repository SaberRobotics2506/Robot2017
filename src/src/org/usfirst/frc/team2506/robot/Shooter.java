package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;

public class Shooter {
	//Used to set the speed of the bad shooter
	final double ENCODER_FULL_SPEED = 200;
	final double ENCODER_HALF_SPEED = ENCODER_FULL_SPEED / 2;
	
	final double HOPPER_SPEED = -0.5;
	
	private CANTalon shooter;
	private Victor hopper;
	
	public Shooter(int shooter, int hopper) {
		this.shooter = new CANTalon(shooter);
		this.hopper = new Victor(hopper);
		
		this.shooter.changeControlMode(TalonControlMode.Speed);
		this.shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.shooter.reverseSensor(true);
		this.shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		this.shooter.configPeakOutputVoltage(+12.0f, -12.0f);
		this.shooter.setProfile(0);
		this.shooter.setF(.02);
		this.shooter.setP(-30);
		this.shooter.setI(0);
		this.shooter.setD(0);
		
		
//		setShooterResting();
	}
	
	//For debugging shooter encoder	
	public void printRPM() {
		System.out.println("speed " + shooter.getSpeed());
		System.out.println("Error " + shooter.getClosedLoopError());
		System.out.println("shooter.get: " + shooter.get());
		//System.out.println(shooter.getOutputVoltage() / shooter.getBusVoltage());
	}
	
	//Returns if the shooter is ready to fire
	public boolean readyToFire() {
		setShooterFiring();
		return shooter.getSpeed() >= ENCODER_FULL_SPEED;
	}
	
	public void reset() {
		shooter.reset();
	}
	
	public void startShooting() {
		hopper.set(HOPPER_SPEED);
		setShooterFiring();
	}
	public void stopShooting() {
		hopper.set(0);
		setShooterResting();
	}
	
	//stops shooter from shooting and puts shooter system to rest
	private void setShooterResting() {
		shooter.set(0);
	}
	
	//starts shooting systems at speed of 2000
	private void setShooterFiring() {
//		this.shooter.changeControlMode(TalonControlMode.Speed);
		shooter.set(2000);
	}
}