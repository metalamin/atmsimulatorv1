package domain.implementation;

import util.GeneralException;
import domain.state.StateHandlerFactory;

import domain.state.Action;
import domain.state.Event;

/**
 * Esta clase representa un cambio de estado.
 */
public class StateChange implements Action
{
    private String next_state;
    
    public StateChange()
    {
    }
    
    public StateChange(String stat)
    {
        next_state = stat;
    }
    
    public void setState(String state)
    {
        this.next_state = state;
    }
    
    public String getState()
    {
        return next_state;
    }
    
    public void update(Event event) throws GeneralException
    {
        StateHandlerFactory.getStateHandler().setState(next_state);
    }
    
    public String toString()
    {
        return "State change (" + getState() + ")";
    }
    
}