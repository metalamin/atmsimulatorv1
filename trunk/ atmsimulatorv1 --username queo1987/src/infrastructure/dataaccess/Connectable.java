package infrastructure.dataaccess;

import util.GeneralException;

/**
 * Interfaz para las clases que necesiten conexion.
 */
public interface Connectable
{
    public void setConnectionName(String connection) throws GeneralException;    
}
