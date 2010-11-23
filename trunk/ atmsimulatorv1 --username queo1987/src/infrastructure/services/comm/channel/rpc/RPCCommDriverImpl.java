package infrastructure.services.comm.channel.rpc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import infrastructure.services.comm.message.Message;

/**
 * Implementacion del driver de comunicaciones RPC.
 */
class RPCCommDriverImpl extends UnicastRemoteObject implements RPCCommDriver
{
    // Mensaje intercambiado.
    private Object mssg;
    
    // Channel que espera por el mensaje..
    private Object waiter;
    
    public RPCCommDriverImpl() throws RemoteException
    {
        super();
        //En principio, no hay mensaje...
        mssg = null;
        waiter = null;
    }
    
    public void setMessage(Object mssg) throws RemoteException
    {
        this.mssg = mssg;
        if (waiter != null)
        {
            synchronized(waiter)
            {
                waiter.notify();
            }
        }
    }
    
    /**
     * Lectura del mensaje.
     * Si no hay, duerme al reader hasta que haya.
     */
    public Object readMessage(Object waiter)
    {
        if (mssg == null)
        {
            synchronized(waiter)
            {
                this.waiter = waiter;
                try
                {
                    waiter.wait();
                }
                catch (InterruptedException iex)
                {
                    iex.printStackTrace();
                }
                waiter = null;
            }
        }
 
        Object ret = mssg;
        mssg = null;
        return ret;
    }
}
