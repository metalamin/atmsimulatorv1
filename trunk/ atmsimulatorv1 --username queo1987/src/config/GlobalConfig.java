package config;
import util.GeneralException;
import java.util.HashMap;
import java.util.Map;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Simple clase de configuracion.
 */
public final class GlobalConfig
{
    private static GlobalConfig inst = null;
    
    // Nombre de la conexion.
    private String conn_name;
    
    // El mapping de properties.
    private Map props = null;
    
    private GlobalConfig()
    {
        conn_name = "DEFAULT_CONNECTION_NAME";
    }
    
    public static GlobalConfig getInstance()
    {
        if (inst == null)
        {
            inst = new GlobalConfig();
        }
        return inst;
    }
    
    private Map getMap() throws GeneralException
    {
        if (props == null)
        {
            props = new HashMap();
        }
        return props;
    }
    
    public void setConnection(String name) throws GeneralException
    {
        conn_name = name;
        //props = PersistentCollectionFactory.getInstance().getPersistentMap(conn_name);
        props = new HashMap();
    }
    
    /**
     * Se agrega una propiedad...
     */
    public synchronized void addProperty(String property_name, Object property) throws GeneralException
    {
        Map m = getMap();
        if (m == null)
        {
            m = new HashMap();
        }
        m.put(property_name, property);
    }
    
    /**
     * Devuelve una propiedad, o null si no la encuentra...
     */
    public Object getProperty(String property_name) throws GeneralException
    {
        Object res;
        Map m = getMap();
        if (m == null)
        {
            res = null;
        }
        else
        {
            res = m.get(property_name);
        }
        return res;
    }
}
