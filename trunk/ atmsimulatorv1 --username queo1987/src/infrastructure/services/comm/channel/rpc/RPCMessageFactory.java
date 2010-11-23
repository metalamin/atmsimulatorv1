package infrastructure.services.comm.channel.rpc;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.message.Packager;

public class RPCMessageFactory implements MessageFactory
{
    public Message createMessage()
    {
        return new RPCMessage();
    }
    
    public Packager createPackager()
    {
        return new RPCPackager();
    }
}
