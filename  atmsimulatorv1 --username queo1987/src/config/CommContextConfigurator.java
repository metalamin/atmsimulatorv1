package config;

import infrastructure.communication.CommunicationConfig;
import util.GeneralException;
import infrastructure.dataaccess.Connectable;

/**
 * Configurador de los CommContext.
 * Setea el nombre de la conexion.
 */
public class CommContextConfigurator implements Configurator
{
    private String connectionName;
    
    public CommContextConfigurator()
    {
    }

    public String getConnectionName()
    {
        return connectionName;
    }

    public void setConnectionName(String connectionName)
    {
        this.connectionName = connectionName;
    }
    
    public void configure() throws GeneralException
    {
        ((Connectable)CommunicationConfig.getInstance()).setConnectionName(connectionName);
    }
}
