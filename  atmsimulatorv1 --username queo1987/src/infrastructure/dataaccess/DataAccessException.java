package infrastructure.dataaccess;

import util.GeneralException;

/**
 * Exception lanzada por el acceso a 
 * los datos.
 */
public class DataAccessException extends GeneralException
{
    public DataAccessException(String msg, Exception ex)
    {
        super(msg, ex);
    }
}
