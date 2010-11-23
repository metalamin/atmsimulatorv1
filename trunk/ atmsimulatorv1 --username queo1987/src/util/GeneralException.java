package util;

/**
 * Excepcion de caracter general.
 * Esta simplemente por un tema de encapsulamiento.
 */
public class GeneralException extends Exception
{
    public GeneralException(String msg)
    {
        super(msg);
    }
    
    public GeneralException(Exception ex)
    {
        super(ex);
    }
    
    public GeneralException(String msg, Exception ex)
    {
        super(msg, ex);
    }
}
