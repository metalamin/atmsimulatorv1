package infrastructure.dataaccess.sequential;

/**
 * Punto de acceso.
 */
public class SequentialFactory 
{
    public static SequentialWriter getSequentialWriter()
    {
        return SequentialWriterImpl.getInstance();
    }

    public static SequentialReader getSequentialReader()
    {
        return SequentialReaderImpl.getInstance();
    }
}
