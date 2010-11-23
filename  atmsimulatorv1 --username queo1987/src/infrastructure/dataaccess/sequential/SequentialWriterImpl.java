package infrastructure.dataaccess.sequential;
import infrastructure.dataaccess.DataAccessException;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
/**
 *
 * @author Programacion
 */
final class SequentialWriterImpl implements SequentialWriter
{
    private static SequentialWriterImpl inst = null;
    
    private XMLEncoder encoder;
    
    private SequentialWriterImpl() 
    {
        encoder = null;
    }
    
    public static SequentialWriterImpl getInstance()
    {
        if (inst == null)
            inst = new SequentialWriterImpl();
        return inst;
    }
    
    public void connect(String conn_name) throws DataAccessException
    {
        if (encoder != null)
        {
            close();
        }
        try
        {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(conn_name)));
        }
        catch (FileNotFoundException fex)
        {
            throw new DataAccessException("Nombre de conexión incorrecto.", fex);
        }
    }
    
    public void close()
    {
        if (encoder == null)
        {
            return;
        }
        encoder.close();
        encoder = null;
    }
    
    public void write(Object obj)
    {
        encoder.writeObject(obj);
    }
}
