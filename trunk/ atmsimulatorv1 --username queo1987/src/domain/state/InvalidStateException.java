package domain.state;

import util.GeneralException;

/**
 * Exception lanzada al detectar un error
 * en el manejo de estados.
 */
public class InvalidStateException extends GeneralException
{
    public InvalidStateException(String msg)
    {
        super(msg);
    }
}
