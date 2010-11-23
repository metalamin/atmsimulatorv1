package infrastructure.services.comm.channel.tcp;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Implementacion TCP del packager.
 */
class DefaultTCPPackager implements Packager
{
    /**
     * Traduce un mensaje a su representacion en
     * bytes.
     */
    public Object pack(Message msg)
    {
        return msg;
    }
    
    /**
     * Traduce un mensaje empaquetado a su
     * representacion "normal".
     */
    public Message unpack(Object pckdMsg)
    {
        return (Message)pckdMsg;
    }
}
