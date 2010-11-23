package infrastructure.services.comm.channel.rpc;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Factory para las comunicaciones RPC.
 * Por ahora, maneja UN SOLO CANAL y chau.
 */
public class RPCCommFactory implements CommFactory
{
    private Map context;
    
    /**
     * Seteo del contexto.
     */
    public void setContext(Map context)
    {
        this.context = context;
    }
    
    /**
     * Creacion de un mensaje nuevo.
     */
    public Message createMessage()
    {
        return new RPCMessage();
    }
    
    public Collection getChannelNames()
    {
        return Collections.singleton("UNICO");
    }
    
    /**
     * Creacion de un canal
     */
    public Channel createChannel(String channelName)
    {
        //Se obtienen los parametros del contexto...
        String myHost = (String)context.get(RPCCommConstants.MYHOST);         
        String otherHost = (String)context.get(RPCCommConstants.OTHERHOST);
        int myPort = ((Number)context.get(RPCCommConstants.MYPORT)).intValue();         
        int otherPort = ((Number)context.get(RPCCommConstants.OTHERPORT)).intValue();         
        Packager pckgr = (Packager)context.get(RPCCommConstants.PACKAGER);
        
        //Se crea el channel 
        Channel chnnl = new RPCChannel(myHost, myPort, otherHost, otherPort);
        if (pckgr != null)
        {
            chnnl.setPackager(pckgr);
        }
        
        return chnnl;
    }
}
