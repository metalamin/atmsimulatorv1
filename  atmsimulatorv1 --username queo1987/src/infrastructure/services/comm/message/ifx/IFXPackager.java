package infrastructure.services.comm.message.ifx;

import java.io.InputStream;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Implementacion IFX del packager.
 */
public class IFXPackager implements Packager
{
    /**
     * Traduce un mensaje a su representacion String
     * que es con la que intercambia mensajes IFX con 
     * otros sistemas
     */
    public Object pack(Message msg)
    {
        return ((IFXMessage)msg).toString();
    }
    
    /**
     * Traduce un mensaje empaquetado (formato String) a su
     * representacion interna (IFXMessage).
     */
    public Message unpack(Object pckdMsg)
    {
        return new IFXMessage((String)pckdMsg);
    }
}
