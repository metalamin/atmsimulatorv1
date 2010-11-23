package config;
import util.GeneralException;
import java.util.HashMap;
import java.util.Map;
import infrastructure.dataaccess.Connectable;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Simple clase de configuracion.
 */
public final class Config implements Connectable
{
    private static Config inst = null;
    
    // Nombre de la conexion.
    private String conn_name;
    
    // El mapping de properties.
    private Map props = null;
    
    private Config()
    {
        conn_name = "DEFAULT_CONNECTION_NAME";
    }
    
    public static Config getInstance()
    {
        if (inst == null)
        {
            inst = new Config();
        }
        return inst;
    }
    
    private Map getMap() throws GeneralException
    {
        if (props == null)
        {
            props = PersistentCollectionFactory.getInstance().getPersistentMap(conn_name);
        }
        return props;
    }
    
    public void setConnectionName(String name) throws GeneralException
    {
        conn_name = name;
        props = PersistentCollectionFactory.getInstance().getPersistentMap(conn_name);
    }
    
    /**
     * Se agrega una propiedad...
     */
    public void addProperty(String property_name, String property) throws GeneralException
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
    public String getProperty(String property_name) throws GeneralException
    {
        String res;
        Map m = getMap();
        if (m == null)
        {
            res = null;
        }
        else
        {
            res = (String)m.get(property_name);
        }
        return res;
    }
}
