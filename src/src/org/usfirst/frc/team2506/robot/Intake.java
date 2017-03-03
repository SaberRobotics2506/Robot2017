package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.*;

public class Intake {
	private Victor motor;
		public Intake(int intake) {
		this.motor = new Victor(intake);
	}
	// Activates the motor to intake
	public void roll (double speed) {
		motor.set(speed);
	}
}
