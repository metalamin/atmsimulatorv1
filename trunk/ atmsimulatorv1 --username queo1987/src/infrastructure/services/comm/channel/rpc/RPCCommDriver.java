package infrastructure.services.comm.channel.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import infrastructure.services.comm.message.Message;

/**
 * Driver de comunicaciones RPC.
 */
public interface RPCCommDriver extends Remote
{
    /**
     * Se envia el mensaje a su destinatario.
     */
    public void setMessage(Object msg) throws RemoteException;
}
