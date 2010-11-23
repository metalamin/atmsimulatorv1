package infrastructure.services.comm;

/**
 * Exception de paquete.
 */
public class CommException extends Exception
{
    public CommException(String message, Exception ex)
    {
        super(message, ex.getCause());
    }
    
    public CommException(Exception ex)
    {
        super(ex);
    }
}
