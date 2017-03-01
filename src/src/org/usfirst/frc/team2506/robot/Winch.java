package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.TalonSRX;

public class Winch {

	private Jaguar motor;
	
	public Winch(int motorID) {
		motor = new Jaguar (motorID);
	}
	
	public void turnOn(double speed) {
		motor.set(speed);
	}
}
