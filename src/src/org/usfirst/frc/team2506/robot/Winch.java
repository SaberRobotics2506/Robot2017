package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.Jaguar;

public class Winch {

	private Jaguar motor;
	
	public Winch(int motorID) {
		motor = new Jaguar (motorID);
	}
	
	public void turnOn(double speed) {
		motor.set(speed);
	}
}
