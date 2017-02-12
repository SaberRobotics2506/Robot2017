package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.TalonSRX;

public class Winch {

	private CANTalon motor;
	
	public Winch(int motorID) {
		motor = new CANTalon (0);
	}
	
	public void turnOn(double speed) {
		motor.set(speed);
	}
}
