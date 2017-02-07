package org.usfirst.frc.team2506.robot.XboxHandlers;

import org.usfirst.frc.team2506.robot.Robot;
import org.usfirst.frc.team2506.robot.XboxController.Events.*;

public class ButtonStartHandler implements IButtonPressed {

	private Robot robot;
	
	public ButtonStartHandler (Robot robot) {
		this.robot = robot;
	}
	@Override
	public void buttonPressed() {
		robot.isSwerveDrive = robot.isSwerveDrive ? false : true;
	}
}
