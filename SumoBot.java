import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;

/**
 * TODO
 * 
 * <p>
 * </p>
 * 
 * @author Ken Walker
 * @version created: 04/30/2015
 * @version updated: 04/30/2015
 */

public class SumoBot
{
    private static final int INIT_SPEED = 100;
    private static final int INIT_ROTATION_SPEED = 100;
    private static final float WHEEL_DISTANCE = 165F;
    private static final float WHEEL_DIAMETER = 56F;
    
    /**
     * @brief TODO
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
        UltrasonicSensor ultraSensor = new UltrasonicSensor(SensorPort.S1);
        DifferentialPilot pilot = new DifferentialPilot(WHEEL_DIAMETER, WHEEL_DISTANCE,
                                                        Motor.B, Motor.C);
        pilot.setTravelSpeed(INIT_SPEED);
        pilot.setRotateSpeed(INIT_ROTATION_SPEED);
        
        // I added this -rhe
        LightSensor myLightSensor = new LightSensor(SensorPort.S1);
        
        System.out.println("About to start SumoBot.java");
        Button.waitForAnyPress();

        // now calibrate the light sensor

        LCD.clear();
        LCD.drawString("set on DARK surface", 0, 0);
        Button.waitForAnyPress();
        myLightSensor.calibrateLow();
        LCD.drawString("calibd LOW", 0, 1);
        Button.waitForAnyPress();

        LCD.clear();
        LCD.drawString("set on LIGHT surface", 0, 0);
        Button.waitForAnyPress();
        myLightSensor.calibrateHigh();
        LCD.drawString("calibd HI", 0, 1);
        Button.waitForAnyPress();
        
        Behavior[] behaviors = {new BehaviorLightSensor(robot, myLightSensor),
                                new BehaviorSearch(pilot, ultraSensor)};
        
        Arbitrator arbi = new Arbitrator(behaviors);
        arbi.start();
    }
}
