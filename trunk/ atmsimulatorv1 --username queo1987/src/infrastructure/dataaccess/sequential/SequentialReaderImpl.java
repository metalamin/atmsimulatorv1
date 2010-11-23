package infrastructure.dataaccess.sequential;

import infrastructure.dataaccess.DataAccessException;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Implementacion de la lectura secuencial.
 */
final class SequentialReaderImpl implements SequentialReader
{
    private static SequentialReaderImpl inst = null;
    
    private XMLDecoder decoder;
    
    private SequentialReaderImpl() 
    {
        decoder = null;
    }
    
    public static SequentialReaderImpl getInstance()
    {
        if (inst == null)
            inst = new SequentialReaderImpl();
        return inst;
    }
    
    public void connect(String conn_name) throws DataAccessException 
    {
        if (decoder != null)
        {
            close();
        }
        try
        {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(conn_name)));
        }
        catch (FileNotFoundException fex)
        {
            throw new DataAccessException("Nombre de conexión incorrecto.", fex);
        }
    }
    
    public void close()
    {
        if (decoder == null)
        {
            return;
        }
        decoder.close();
        decoder = null;
    }
    
    public Object read() throws DataAccessException
    {
        try
        {
            return decoder.readObject();
        }
        catch (ArrayIndexOutOfBoundsException aox)
        {
            throw new DataAccessException("No se encuentran más objetos para leer.", aox);
        }
    }
}
