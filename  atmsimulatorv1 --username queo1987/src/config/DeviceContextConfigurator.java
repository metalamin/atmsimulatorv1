package config;

import util.GeneralException;
import infrastructure.devices.DeviceConfig;
import infrastructure.dataaccess.Connectable;


/**
 * Configurador de los DeviceContext.
 * Setea el nombre de la conexion.
 */
public class DeviceContextConfigurator implements Configurator
{
    private String connectionName;
    
    public DeviceContextConfigurator()
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
        ((Connectable)DeviceConfig.getInstance()).setConnectionName(connectionName);
    }
}
