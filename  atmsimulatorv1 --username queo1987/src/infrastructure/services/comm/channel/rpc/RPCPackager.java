package infrastructure.services.comm.channel.rpc;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Implementacion RPC del packager.
 *
 * ATENCION:
 *  Su codigo es SUMAMENTE COMPLICADO, y puede causar
 *  daños permanentes si se trata de entender sin un
 *  tecnico calificado de la NASA al lado.
 */
public class RPCPackager implements Packager
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
