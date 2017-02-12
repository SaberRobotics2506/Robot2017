package org.usfirst.frc.team2506.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team2506.robot.XboxController.JoystickManager;
import org.usfirst.frc.team2506.robot.XboxController.XboxButtons;
import org.usfirst.frc.team2506.robot.XboxController.Events.IButtonPressed;
import org.usfirst.frc.team2506.robot.XboxHandlers.ButtonStartHandler;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter {
	
	Joystick joystick = new Joystick (0);
	
	public static boolean motor_full = false;
	
	private Jaguar motor;
	//private final double MOTOR_ALWAYS_ON_SPEED = ((2800/(12866+(2/3)))/2);
	//private final int SHOOTER_BUTTON = 0x06;
	//private final double MOTOR_SELECTOR_ON = (2800/(12866+(2/3)));
	//private final double MOTOR_SELECTION_STOP = 0;


	
	
	public Shooter (int motorID) {
	
		
		motor = new Jaguar (motorID);

		
		/*motor.set(MOTOR_ALWAYS_ON_SPEED);
	}
	public void driveConroller (Joystick joystick) {
		if (joystick.getRawButton (SHOOTER_BUTTON)) {
			drive (MOTOR_SELECTOR_ON);
		} else {
			drive (MOTOR_SELECTION_STOP);
		}
	}
	
	public void drive (double selectorMOTORSpeed) {
		motor.set (selectorMOTORSpeed);
	*/
	}
	
	public void shooterTest(double speed) {
		motor.set(speed);
	}

}