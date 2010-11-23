package config;

import infrastructure.dataaccess.broker.BeanerHandlerFactory;
import util.GeneralException;
import infrastructure.dataaccess.Connectable;

/**
 * Configurador de los beaners.
 * Setea el nombre de la conexion.
 */
public class BeanersConfigurator implements Configurator
{
    private String connectionName;
    
    public BeanersConfigurator()
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
        ((Connectable)BeanerHandlerFactory.getBeanerHandler()).setConnectionName(connectionName);
    }
    
}
