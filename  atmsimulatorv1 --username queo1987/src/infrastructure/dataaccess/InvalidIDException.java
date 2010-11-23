package infrastructure.dataaccess;

import util.GeneralException;

/**
 * Exception lanzada al detectar un error
 * al identificar algun componente.
 */
public class InvalidIDException extends GeneralException
{
    public InvalidIDException(String msg)
    {
        super(msg);
    }
}
 

