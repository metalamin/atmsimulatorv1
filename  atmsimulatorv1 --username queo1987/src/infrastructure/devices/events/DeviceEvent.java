package infrastructure.devices.events;

import java.util.HashMap;
import java.util.Map;
import domain.state.Event;

/**
 * Implementacion super chota de
 * Event.
 */

public class DeviceEvent implements Event
{
    //Los identificadores

    //El tipo.
    private String tipo;

    //El thrower.
    private String thrwr;
    
    //El contexto
    private Map context;

    public DeviceEvent(String type, String thrower, Map context)
    {
        tipo  = type;
        thrwr = thrower;
        this.context = context;
    }
    
    public DeviceEvent(String type, String thrower)
    {
        tipo  = type;
        thrwr = thrower;
        this.context = new HashMap();
    }

    public DeviceEvent()
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
