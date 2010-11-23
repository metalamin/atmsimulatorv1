package domain.state;

import java.util.Vector;

/**
 * JavaBean que representa un estado.
 */
public class StateBean
{
    private String name;
    private TriggerBean startupTrigger;
    private TriggerBean endTrigger;
    private Vector triggers;
            
    public StateBean()
    {
        triggers = new Vector();
        startupTrigger = new TriggerBean();
        endTrigger = new TriggerBean();
        setTypeAndThrower();
    }
    
    private void setTypeAndThrower()
    {
        startupTrigger.setThrower(StateConstants.STARTUP_THROWER);
        startupTrigger.setType(StateConstants.STARTUP_TYPE);
        endTrigger.setThrower(StateConstants.END_THROWER);
        endTrigger.setType(StateConstants.END_TYPE);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public TriggerBean getStartupTrigger()
    {
        return startupTrigger;
    }

    public void setStartupTrigger(TriggerBean startupTrigger)
    {
        this.startupTrigger = startupTrigger;
        setTypeAndThrower();
    }

    public TriggerBean getEndTrigger()
    {
        return endTrigger;
    }

    public void setEndTrigger(TriggerBean endTrigger)
    {
        this.endTrigger = endTrigger;
        setTypeAndThrower();
    }

    public Vector getTriggers()
    {
        return triggers;
    }

    public void setTriggers(Vector triggers)
    {
        this.triggers = triggers;
    }
    
}
