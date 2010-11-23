package config;

import util.GeneralException;
import java.util.Iterator;
import java.util.Map;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Configuracion del sistema.
 */
public final class SystemConfig
{
    private static SystemConfig instance = null;
    
    // Conexiones
    private String connection;
    private String configuratorsConnection;
    
    // Mapping de configuradores...
    private Map configurators;
    
    // Conexion por defecto...
    public static final String DEFAULT_CONNECTION = "cfg/config.xml";
    
    //Configuradores por defecto
    public static final String DEFAULT_CONFIGURATOR_CONNECTION = "cfg/configurators.xml";
    
    private SystemConfig()
    {
        connection = DEFAULT_CONNECTION;
        configuratorsConnection = DEFAULT_CONFIGURATOR_CONNECTION;
        configurators = null;
    }
    
    public static SystemConfig getInstance()
    {
        if (instance == null)
        {
            instance = new SystemConfig();
        }
        return instance;
    }
    
    public void setConnectionName(String name)
    {
        connection = name;
    }
    
    public String getConnection()
    {
        return connection;
    }
    
    public void setConfiguratorsConnectionName(String name)
    {
        configuratorsConnection = name;
    }
    
    public String getConfiguratorsConnection()
    {
        return configuratorsConnection;
    }
    
    /**
     * Agrega un configurador...
     */
    public void addConfigurator(String conf_name, Configurator configurator)
    {
        if (configurators == null)
        {
            configurators = PersistentCollectionFactory.getInstance().getPersistentMap(configuratorsConnection);
        }        
        configurators.put(conf_name, configurator);
    }
    
    /**
     * Configura el sistema, de acuerdo a las
     * propiedades especificadas.
     * Asume que esta todo seteado.
     */
    public void configure() throws GeneralException
    {
        Config cfg = Config.getInstance();
        //Configura las propiedades generales...
        cfg.setConnectionName(connection);
        //Y ahora realiza la configuracion via configuradores...
        if (configurators == null)
        {
            configurators = PersistentCollectionFactory.getInstance().getPersistentMap(configuratorsConnection);
        }
        Iterator itr = configurators.keySet().iterator();
        while (itr.hasNext())
        {
            String cfgName = (String)itr.next();
            Configurator conf = (Configurator)configurators.get(cfgName);
            conf.configure();
        }
    }
}
