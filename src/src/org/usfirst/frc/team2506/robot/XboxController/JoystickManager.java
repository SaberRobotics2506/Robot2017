package org.usfirst.frc.team2506.robot.XboxController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.usfirst.frc.team2506.robot.XboxController.Events.*;

import edu.wpi.first.wpilibj.*;

public class JoystickManager {
	private Joystick joystick;
	
	private Hashtable<Integer, IButtonHeld> buttonHeldListeners = new Hashtable<Integer, IButtonHeld> ();
	private Hashtable<Integer, IButtonPressed> buttonPressedListeners = new Hashtable<Integer, IButtonPressed> ();
	
	private Hashtable<Integer, Boolean> buttonClears = new Hashtable<Integer, Boolean> ();
	
	public JoystickManager (Joystick joystick) {
		this.joystick = joystick;
	}
	
	public void addButtonHeldHandler (int button, IButtonHeld handler) {
		buttonHeldListeners.put (button, handler);
	}
	public void addButtonPressedListener(int button, IButtonPressed handler) {
		buttonPressedListeners.put (button, handler);
	}
	
	public void start () {
		new Thread(new Runnable() { 
			public void run () { threadRun (); 
			}
		}).start();
	}
	
	private void threadRun () {
		while (true) {
			handleButton (XboxButtons.BUTTON_A);
			handleButton (XboxButtons.BUTTON_B);
			handleButton (XboxButtons.BUTTON_X);
			handleButton (XboxButtons.BUTTON_Y);
			handleButton (XboxButtons.BUTTON_LB);
			handleButton (XboxButtons.BUTTON_RB);
			
			handleButton (XboxButtons.BUTTON_SELECT);
			handleButton (XboxButtons.BUTTON_START);
			
			handleButton (XboxButtons.BUTTON_LEFT_STICK);
			handleButton (XboxButtons.BUTTON_RIGHT_STICK);
		}
	}
	
	private void handleButton (int button) {
		if (!buttonClears.containsKey(button)) {
			buttonClears.put(button, true);
		}
		
		if (joystick.getRawButton (button)) {
			fireButtonHeldListeners (button);
			if (buttonClears.get (button)) {
				System.out.println("Fired the buton pressed listener");
				fireButtonPressedListeners (button);
				buttonClears.put(button, false);
			}
		}
		else {
			buttonClears.put (button, true);
		}
	}
	
	private void fireButtonHeldListeners (int button) {
		if (buttonHeldListeners.containsKey (button))
			buttonHeldListeners.get (button).buttonHeld ();
	}
	
	private void fireButtonPressedListeners (int button) {
		if (buttonPressedListeners.containsKey (button))
			buttonPressedListeners.get (button).buttonPressed ();
	}
}
