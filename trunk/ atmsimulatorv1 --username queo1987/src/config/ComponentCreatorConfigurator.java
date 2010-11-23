package config;

import util.GeneralException;
import tools.stateeditor.components.ComponentCreatorHandlerFactory;
import infrastructure.dataaccess.Connectable;

/**
 * Configurador de los ComponentCreator.
 * Setea el nombre de la conexion.
 */
public class ComponentCreatorConfigurator implements Configurator
{
    private String connectionName;
    
    public ComponentCreatorConfigurator()
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
        ((Connectable)ComponentCreatorHandlerFactory.getComponentCreatorHandler()).setConnectionName(connectionName);
    }
    
}
