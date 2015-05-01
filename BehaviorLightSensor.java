import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;

/**
  trying a FIRST lejos.robotics.subsumption/
  Behavior-based robotics example:

  this is an implementation of the Behavior interface,
  BehaviorProximity - a getting-near-something behavior

  @author Bagnall - based on course textbook Chapter 14 example,
          p. 278, AND on tutorial at 
     http://www.lejos.org/nxt/nxj/tutorial/Behaviors/BehaviorProgramming.htm
  @author adapted by Sharon Tuttle
  @version 
*/

public class BehaviorLightSensor implements Behavior
{
    // data fields

    private boolean suppressed;
    private DifferentialPilot robot;
	private LightSensor myLightSensor;
	private int lightValue;
	

    /**
        create an instance of this getting-near-something
        behavior

        @param pilot the DifferentialPilot to exhibit this 
                     getting-near-something behavior
        @param anUltraSensor an UltrasonicSensor
    */

    public BehaviorLightSensor(DifferentialPilot pilot,LightSensor myLightSensor)
    {
        this.robot = pilot;
        this.myLightSensor = myLightSensor;
        this.suppressed = false;
    }

    /**
        this getting-near-something behavior wants to take control
        when the distance to a feature is less than SAFE_DISTANCE

        @return if this getting-near-something behavior should 
                take control
    */

    public boolean takeControl()
    {
        lightValue = this.myLightSensor.readValue();
        return (lightValue <= 40);
    }

    /**
        when this getting-near-something-behavior is suppressed,
        the robot should stop its current movement -- we're using
        a boolean that will interact with the action method in a
        way we hope is reasonable...
    */

    public void suppress()
    {
        this.suppressed = true;
    }

    /**
        when the arbitrator calls this for getting-near-something
        behavior, it should go ONE_STEP_BACK and rotate ROTATE_UNIT 
        degrees from where it ends up
    */

    public void action()
    {
        this.suppressed = false;

        if(lightValue < 40)
		{
			robot.rotate(-25);
			System.out.println(lightValue);
		}
		
		

        // but give the arbitrator a chance to "cut in"...

        while ( robot.isMoving() && !suppressed )
		{
            Thread.yield();
        }
        
        // clean up?
     
        robot.stop();
    }
}

