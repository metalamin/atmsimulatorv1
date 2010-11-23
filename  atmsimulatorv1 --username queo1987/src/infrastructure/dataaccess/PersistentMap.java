package infrastructure.dataaccess;

import infrastructure.dataaccess.broker.BrokerFactory;
import util.GeneralException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

/**
 * Mapping persistente.
 * Implementa la interfaz "Map",
 * manejando PersistenceException
 * para cuando no puede guardar las cosas.
 */
class PersistentMap implements Map
{
    //El Map backupeado.
    private Map map;
    
    //El nombre de la coleccion.
    private String name;
    
    public PersistentMap(String name)
    {
        super();
        this.name = name;
        try
        {
           Map aux_map = (Map)BrokerFactory.getBroker().load(name);
           map = aux_map;
        }
        catch (DataAccessException daex)
        {
            /**
             * Esta exception se tira
             * si no pudo leer.
             */
            map = new LinkedHashMap();
        }
        catch (GeneralException gex)
        {
            throw new PersistenceException("Error inesperado.", gex);
        }
    }
    
    public int size()
    {
        return map.size();        
    }
    
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }
    
    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }
    
    public Object get(Object key)
    {
        return map.get(key);
    }
    
    private void saveMap()
    {
        try
        {
            BrokerFactory.getBroker().save(name, map);
        }
        catch (DataAccessException dae)
        {
            throw new PersistenceException("Imposible guardar el Mapping en la conexión " + name, dae);
        }
    }
    
    public Object put(Object key, Object value)
    {
        Object ret = map.put(key, value);
        try
        {
            saveMap();
            return ret;
        }
        catch (PersistenceException iax)
        {
            map.remove(key);
            throw iax;
        }
    }
    
    public Object remove(Object key)
    {
        Object value = map.remove(key);
        try
        {
            saveMap();
            return value;
        }
        catch (PersistenceException iax)
        {
            map.put(key, value);
            throw iax;
        }
    }
    
    public void putAll(Map t)
    {
        Map mp = new LinkedHashMap(map);
        map.putAll(t);
        try
        {
            saveMap();
        }
        catch (PersistenceException iax)
        {
            map = mp;
            throw iax;
        }
    }
    
    public void clear()
    {
        Map mp = new LinkedHashMap(map);
        map.clear();
        try
        {
            saveMap();
        }
        catch (PersistenceException iax)
        {
            map = mp;
            throw iax;
        }
    }
    
    public Set keySet()
    {
        return map.keySet();
    }
    
    public Collection values()
    {
        return map.values();
    }
    
    public Set entrySet()
    {
        return map.entrySet();
    }
}
