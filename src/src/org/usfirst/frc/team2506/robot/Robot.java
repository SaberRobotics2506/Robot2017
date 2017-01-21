package org.usfirst.frc.team2506.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Joystick joystick = new Joystick (0);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {

	}

	public void teleopInit()
	{
	}
	
	WheelDrive backLeft = new WheelDrive (0, 1, 0, -51);
	WheelDrive frontLeft = new WheelDrive (3, 2, 1, 17);
	WheelDrive backRight = new WheelDrive (4, 5, 2, 82);
	WheelDrive frontRight = new WheelDrive (7, 6, 3, -31);
	WheelDrive[] wheels = { frontRight, frontLeft, backRight, backLeft };
	WheelDrive testWheel = wheels[0];
	
	private SwerveDrive swerveDrive = new SwerveDrive (frontRight, backRight, frontLeft, backLeft);
	
	@Override
	public void teleopPeriodic() {
		double zero = joystick.getRawAxis(0);
		double one = joystick.getRawAxis(1);
		double four = joystick.getRawAxis(4);
		if (zero > 0.1 || zero < -0.1 || one > 0.1 || one < -0.1 || four > 0.1 || four < -0.1) {
			swerveDrive.drive (one * 0.5, -zero * 0.5, -four);
		} else {
			backLeft.coast ();
			backRight.coast ();
			frontLeft.coast ();
			frontRight.coast ();
		}
	}
	
	boolean b8 = false;
	@Override
	public void testPeriodic()
	{
		swerveDrive.drive(0, 0, 0);
		
		for (int b = 1; b <= 4; b++)
		{
			if (joystick.getRawButton(b))
			{
				testWheel = wheels[b-1];
				System.out.println("calibrating wheel " + b);
			}
		}
		
		double stick = joystick.getRawAxis(1);
		
		if (stick > .2) testWheel.incrementOffset();
		else if (stick < -0.2) testWheel.decrementOffset();
		
		if (joystick.getRawButton(8))
		{
			if (!b8)
			{
				System.out.println("front right " + frontRight.getOffset());
				System.out.println("front left " + frontLeft.getOffset());
				System.out.println("back right " + backRight.getOffset());
				System.out.println("back left " + backLeft.getOffset());
			}
			b8 = true;
		}
		else
		{
			b8 = false;
		}
	}
}

