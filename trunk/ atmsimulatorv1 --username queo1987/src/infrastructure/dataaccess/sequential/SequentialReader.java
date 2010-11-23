package infrastructure.dataaccess.sequential;

import infrastructure.dataaccess.DataAccessException;

/**
 * Interfaz de acceso para lectura
 */
public interface SequentialReader 
{
    public void connect(String conn_name) throws DataAccessException;
    
    public void close();
    
    public Object read() throws DataAccessException;
}
