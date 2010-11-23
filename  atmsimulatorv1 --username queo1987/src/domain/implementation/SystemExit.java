package domain.implementation;

import domain.state.Action;
import domain.state.Event;

/**
 * Salida limpia del sistema
 */
public class SystemExit implements Action
{
    public void update(Event ev)
    {
        System.exit(0);
    }
    
    public String toString()
    {
        return "Exit";
    }    
}
