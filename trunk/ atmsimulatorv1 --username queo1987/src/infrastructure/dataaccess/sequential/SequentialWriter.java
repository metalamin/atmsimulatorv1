package infrastructure.dataaccess.sequential;

import infrastructure.dataaccess.DataAccessException;

/**
 * Interfaz de acceso a la escritura.
 */
public interface SequentialWriter 
{
    // Abre una conexion con el nombre especificado
    public void connect(String conn_name) throws DataAccessException;
    
    // Cierra la conexion.
    public void close();
    
    // Salva el object, appendeandolo.
    public void write(Object ob);
}
