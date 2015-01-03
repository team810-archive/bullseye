/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Bullseye extends SimpleRobot {
    
    
    Joystick stick=new Joystick(1);
    Victor left=new Victor(2), right=new Victor(1),winch=new Victor(3);
    RobotDrive chassis=new RobotDrive(left, right);
    double leftJ, rightJ;
    Solenoid arms=new Solenoid(2), flaps=new Solenoid(3), trigger=new Solenoid(1);
    boolean forward, backward, boom, manual, boolArms;
    
    Compressor mainCompressor=new Compressor(1,2);
            
    public void robotInit() {
        
        chassis.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        
    }
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        mainCompressor.start();
        /*chassis.drive(.75, .75);
        Timer.delay(3);
        chassis.drive(0,0);*/
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
        while (isOperatorControl()&&isEnabled()) {
            
            mainCompressor.start();
            leftJ=stick.getRawAxis(4);
            rightJ=stick.getRawAxis(2);
            chassis.tankDrive(leftJ, rightJ);
            
            forward=DriverStation.getInstance().getDigitalIn(5);
            backward=DriverStation.getInstance().getDigitalIn(3);
            boom=DriverStation.getInstance().getDigitalIn(6);
            manual=DriverStation.getInstance().getDigitalIn(1);
            boolArms=arms.get();
            
            //arms.set(stick.getRawButton(7));
            flaps.set(stick.getRawButton(8));
            trigger.set(!boom);
            
            /*if(stick.getRawButton(7)) {
                arms.set(!boolArms);
                
            }*/
            arms.set(stick.getRawButton(7));
                
                
            
            if(!forward)
                winch.set(0.75);
            else if(!backward)
                winch.set(-0.75);
            else
                winch.set(0);
            
            if(!manual) {
                trigger.set(true);
                winch.set(0.75);
                Timer.delay(3.0);
                winch.set(0);
                trigger.set(false);
                winch.set(-0.75);
                Timer.delay(1.45);
                winch.set(0);
            }
                
            
            Timer.delay(0.01);
        }

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
        right.set(1);
        left.set(1);
        Timer.delay(1);
        right.set(0);
        left.set(0);
    }
}
