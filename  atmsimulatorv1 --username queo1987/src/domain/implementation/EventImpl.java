package domain.implementation;

import java.util.HashMap;
import java.util.Map;
import domain.state.Event;

/**
 * Implementacion super chota de
 * Event.
 */

public class EventImpl implements Event
{
    //Los identificadores

    //El tipo.
    private String tipo;

    //El thrower.
    private String thrwr;
    
    //El contexto
    private Map context;

    public EventImpl(String type, String thrower, Map context)
    {
        tipo  = type;
        thrwr = thrower;
        this.context = context;
    }
    
    public EventImpl(String type, String thrower)
    {
        tipo  = type;
        thrwr = thrower;
        this.context = new HashMap();
    }

    public EventImpl()
    {
        context = new HashMap();
    }
    
    public String getType() 
    {
        return tipo;
    }

    public String getThrower()
    {
        return thrwr;
    }
    
    public Map getContext()
    {
        return context;
    }
    
    public void setType(String tipo) 
    {
        this.tipo = tipo;
    }

    public void setThrower(String thrwr)
    {
        this.thrwr = thrwr;
    }
    
    public void setContext(Map context)
    {
        this.context = context;
    }
}
