package config;

import util.GeneralException;
import tools.stateeditor.objects.ObjectCreatorHandlerFactory;
import infrastructure.dataaccess.Connectable;

/**
 * Configurador de los ObjectCreator.
 * Setea el nombre de la conexion.
 */
public class ObjectCreatorConfigurator implements Configurator
{
    private String connectionName;
    
    public ObjectCreatorConfigurator()
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
        ((Connectable)ObjectCreatorHandlerFactory.getObjectCreatorHandler()).setConnectionName(connectionName);
    }
}
