package infrastructure.services.comm.channel.rpc;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import infrastructure.services.comm.message.Message;

/**
 * Implementacion de Message para rpc
 */
public class RPCMessage implements Message, Serializable
{
    private Map elems;
    
    public RPCMessage()
    {
        elems = new LinkedHashMap();
    }
    
    /**
     * Devuelve el elemento del mensaje
     * asociado a la clave "key".
     */
    public Object getElement(Object key)
    {
        return elems.get(key);
    }
    
    /**
     * Asocia un elemento a una clave.
     */    
    public void setElement(Object key, Object elemt)
    {
        elems.put(key, elemt);
    }
}
