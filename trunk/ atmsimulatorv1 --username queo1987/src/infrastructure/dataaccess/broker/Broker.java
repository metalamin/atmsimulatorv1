package infrastructure.dataaccess.broker;

import infrastructure.dataaccess.DataAccessException;
import util.GeneralException;

/**
 * Interfaz de persistencia.
 */
public interface Broker 
{
    public void save(String name, Object obj) throws DataAccessException;
    
    public Object load(String name) throws DataAccessException, GeneralException;
}
