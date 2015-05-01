import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;

/**
 * Basic search for enemy robot with Ultrasonic Sensor.
 * 
 * <p>TODO: Detailed description.
 * </p>
 * 
 * @author Ken Walker
 * @version created: 04/28/2015
 * @version update: 04/30/2015
 */

public class BehaviorSearch implements Behavior
{
    //--------------
    // Member Variables
    private boolean m_suppressed;
    private DifferentialPilot m_pilot;
    private UltrasonicSensor m_ultraSensor;
    
    private static final int DELAY_WHEN_FOUND = 10;
    private static final int ROTATE_DEGREE_UNIT = 10;
    private static final int IGNORE_DISTANCE = 50; // distance in which to ingore
                                                   //  sensor input as to not
                                                   //  a wall or other feature
    
    /**
     * @brief Default constructor for UltrasonicSearch.
     * 
     * <p> TODO: detailed description.
     * </p>
     * 
     * @param pilot The robots differential pilot.
     * @param ultraSensor The Ultrasonic Sensor for the robot.
     */
    public BehaviorSearch(DifferentialPilot pilot,
                            UltrasonicSensor ultraSensor)
    {
        this.m_pilot = pilot;
        this.m_ultraSensor = ultraSensor;
        this.m_suppressed = false;
    }
    
    /**
     * @brief Overridden method of Behavior to determine if the behavior should
     * take over.
     * 
     * <p> TODO: detail.
     * </p>
     * 
     * @return Returns true if the behavior should take over.
     */
    public boolean takeControl()
    {
        int distance = this.m_ultraSensor.getDistance();
        if(distance == 255)
            return true;
        
        return false;
    }
    
    /**
     * @brief Overridden method of Behavior to suppress.
     * 
     * <p>
     * </p>
     */
    public void suppress()
    {
        this.m_suppressed = true;
    }
    
    /**
     * @brief Overridden method of Behavior to perform behavior specific action.
     * 
     * <p>
     * </p>
     */
    public void action()
    {
        boolean rightPhase = false;
        int counter = 1;
        int distance = this.m_ultraSensor.getDistance();
        this.m_suppressed = false;
        
        while(distance == 255)
        {
            if(rightPhase)
            {
                m_pilot.rotate(ROTATE_DEGREE_UNIT * counter, true);
                rightPhase = false;
                counter++;
            }
            else
            {
                m_pilot.rotate(ROTATE_DEGREE_UNIT * counter, true);
                rightPhase = true;
            }
            
            while(m_pilot.isMoving())
            {
                distance = this.m_ultraSensor.getDistance();
                LCD.clear();
                System.out.println(distance);
                if(distance != 255)
                {
                    Delay.msDelay(DELAY_WHEN_FOUND);
                    m_pilot.stop();
                    return;
                }
                
                while(!m_suppressed)
                {
                    Thread.yield();
                }
            }
        }
    }
}
