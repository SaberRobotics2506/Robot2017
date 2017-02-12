package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.*;

public class Intake {
	private CANTalon motor;
	
	public Intake(int intake) {
		this.motor = new CANTalon(intake);
	}
	
	public void roll (double speed) {
		motor.set(speed);
	}
}
