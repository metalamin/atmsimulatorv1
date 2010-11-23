package infrastructure.communication;

import infrastructure.services.comm.CommException;
import util.GeneralException;

/**
 * Exception del paquete de comunicaciones.
 * No es mucho mas que un wrapper alrededor de
 * CommException
 */
public class CommunicationException extends GeneralException
{
    public CommunicationException(String message, CommException cex)
    {
        super(message, cex);
    }
}
