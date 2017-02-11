package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;

public class TankDrive implements DriveTrain {
	private CANTalon backLeft;
	private CANTalon backRight;
	private CANTalon frontLeft;
	private CANTalon frontRight;
	
	public TankDrive (WheelDrive backLeft, WheelDrive backRight, WheelDrive frontLeft, WheelDrive frontRight) {
		this.backLeft = backLeft.getSpeedMotor();
		this.backRight = backRight.getSpeedMotor();
		this.frontLeft = frontLeft.getSpeedMotor();
		this.frontRight = frontRight.getSpeedMotor();
	}
	
	@Override
	public void drive(double... axis) {
		backLeft.set (axis [0]);
		frontLeft.set (axis [0]);
		
		backRight.set (axis[1]);
		frontRight.set (axis[1]);
	}
	
}
