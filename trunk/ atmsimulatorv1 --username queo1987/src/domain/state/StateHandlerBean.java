package domain.state;

import java.util.Collection;
import java.util.Vector;

/**
 * Bean para el StateHandler
 */
public class StateHandlerBean
{
    private Collection states;
    
    public StateHandlerBean()
    {
        states = new Vector();
    }

    public Collection getStates()
    {
        return states;
    }

    public void setStates(Collection states)
    {
        this.states = states;
    }
}
