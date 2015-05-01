import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;

/**
 * @brief TODO
 * 
 * <p>
 * </p>
 * 
 * @author Ken Walker
 * @version created: 04/30/2015
 * @version updated: 04/30/2015
 */
public class BehaviorEngage implements Behavior
{
    private DifferentialPilot m_pilot;
    private boolean m_suppressed;
    
    /**
     * @brief TODO
     * 
     * @param pilot 
     */
    public BehaviorEngage(DifferentialPilot pilot)
    {
        this.m_pilot = pilot;
        this.m_suppressed = false;
    }
    
    /**
     * @brief TODO
     * 
     * @return 
     */
    public boolean takeControl()
    {
        return true;
    }
    
    /**
     * TODO
     */
    public void suppress()
    {
        this.m_suppressed = true;
    }
    
    /**
     * TODO
     */
    public void action()
    {
        m_suppressed = false;
        
        if(!m_pilot.isMoving())
        {
            m_pilot.forward();
        }
        
        while(!m_suppressed)
        {
            Thread.yield();;
        }
        
        m_pilot.stop();
    }
}
