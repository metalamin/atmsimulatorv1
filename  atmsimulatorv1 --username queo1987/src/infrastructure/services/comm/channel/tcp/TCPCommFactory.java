package infrastructure.services.comm.channel.tcp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.Packager;

/**
 * Factory para las comunicaciones TCP
 */
public class TCPCommFactory implements CommFactory
{
    private Map channels;
    private Map myContext;
    
    /**
     * Seteo del contexto.
     */
    public void setContext(Map context)
    {
        myContext = context;
        channels = new HashMap();
        Collection infos = (Collection)context.get(TCPCommConstants.INFOSTRUCTURES);
        Iterator itr = infos.iterator();
        while (itr.hasNext())
        {
            TCPChannelInfo tcpci = (TCPChannelInfo)itr.next();
            channels.put(tcpci.getName(), tcpci);
        }
    }
    
    public Collection getChannelNames()
    {
        return channels.keySet();
    }
    
    
    /**
     * Creacion de un canal
     */
    public Channel createChannel(String channelName)
    {
        Packager pckgr = (Packager)myContext.get(TCPCommConstants.PACKAGER);
        
        TCPChannelInfo info = (TCPChannelInfo)channels.get(channelName);
        
        int port = info.getPort();
        //Se diferencia por tipo...
        String type = info.getType();
        
        Channel chnnl;
        //Cliente
        if (type.equals(TCPCommConstants.CLIENT_TYPE))
        {
            String host = ((TCPClientInfo)info).getHost();
            chnnl = new TCPClient(host, port);
        }
        //Servidor
        else
        {
            chnnl = new TCPServer(port);            
        }
        
        if (pckgr != null)
        {
            chnnl.setPackager(pckgr);
        }
        return chnnl;
    }
}
