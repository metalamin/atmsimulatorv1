package domain.implementation;

import domain.state.Action;
import domain.state.Event;

/**
 * Accion que decrementa la cuenta
 * regresiba de un CounTrigger.
 */
public class CountAction implements Action
{
    // El trigger...
    private  CountTrigger ct;
    
    public CountAction(CountTrigger ct)
    {
        this.ct = ct;
    }
    
    public void update(Event ev)
    {
        ct.updateCount();
    }
}
