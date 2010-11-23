package infrastructure.services.comm.channel.rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.CommException;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Implementacion RPC de un canal de comunicaciones.
 * Este canal es bidireccional, y funciona a partir de
 * RPCCommDriverImpl, uno para enviar y otro para recibir
 * mensajes.
 */
public class RPCChannel implements Channel
{
    //Nombre de mi host
    private String myHost;
    //Puerto por el que escucho
    private int myPort;
    //Host con el que me quiero conectar
    private String otherHost;
    //Puerto sobre el que me voy a conectar.
    private int otherPort;
    //Driver sobre el que escucho
    private RPCCommDriverImpl listen;
    //Driver sobre el que mando
    private RPCCommDriver send;
    //Packager
    private Packager packgr;
    //Connected?
    private boolean cnnctd;
    
    public RPCChannel(String myHost, int myPort, String otherHost, int otherPort)
    {
        this.myPort = myPort;
        this.myHost = myHost;
        this.otherHost = otherHost;
        this.otherPort = otherPort;
        //Los canales empiezan siendo null hasta que la conexion se
        //efectue...
        listen = null;
        send = null;
        //Packager por defecto
        packgr = new RPCPackager();
        cnnctd = false;
    }
    
    /**
     * Devuelve el nombre que voy a bindear
     */
    private String getBindName()
    {
        return "//" + myHost + "::" + myPort + 
                "/RPCCommDriver" +
                "_" + myHost + "_" + myPort;
    }
    
    /**
     * Devuelve el nombre que
     * voy a buscar
     */
    private String getLookupName()
    {
        return "//" + otherHost + "::" + otherPort + 
                "/RPCCommDriver" +
                "_" + otherHost + "_" + otherPort;
    }
    
    /**
     * Conexion.
     * Se bindea el driver sobre el que voy a
     * escuchar los mensajes.
     */
    public void connect() throws CommException
    {
        try
        {
            listen = new RPCCommDriverImpl();
            //Se realiza el bind...
            String name = getBindName();
            
            /**
             * Se bindea el nombre
             * con el objeto.
             */
            Naming.rebind(name, listen);
            cnnctd = true;
        }
        catch (MalformedURLException muex)
        {
            throw new CommException("URL mal formada", muex);
        }
        catch (RemoteException rex)
        {
            rex.printStackTrace();
            throw new CommException("Error en el bind", rex);
        }
    }
    
    /**
     * Desconexion.
     */
    public void disconnect() throws CommException
    {
        try
        {
            cnnctd = false;
            //Se realiza el bind...
            String name = getBindName();
            
            /**
             * Se bindea el nombre
             * con el objeto.
             */
            Naming.unbind(name);
        }
        catch (NotBoundException nbx)
        {}
        catch (MalformedURLException muex)
        {
            throw new CommException("URL mal formada", muex);
        }
        catch (RemoteException rex)
        {
            throw new CommException("Error en la conexión", rex);
        }
    }
    
    /**
     * Envio de mensajes.
     * Para la primera vez, me conecto...
     */
    public void send(Message msg) throws CommException
    {
        if (send == null)
        {
            try
            {
                String name = getLookupName();
                send = (RPCCommDriver)Naming.lookup(name);
            }
            catch (NotBoundException rex)
            {
                send = null;
                throw new CommException("Driver no encontrado", rex);
            }
            catch (MalformedURLException mue)
            {
                send = null;
                throw new CommException("URL mal formada", mue);
            }
            catch (RemoteException remx)
            {
                send = null;
                throw new CommException("Error en la conexión", remx);
            }
        }
        
        Object pckdMssg = packgr.pack(msg);
        try
        {
            send.setMessage(pckdMssg);
        }
        catch (RemoteException remx)
        {
            throw new CommException("Error en la conexión", remx);
        }
    }
    
    /**
     * Recepcion de mensajes.
     */
    public Message receive() throws CommException
    {
        Object omssg = listen.readMessage(this);
        Message mssg = packgr.unpack(omssg);
        return mssg;
    }
    
    public boolean isConnected()
    {
        return cnnctd;
    }
    
    /**
     * Seteo del packager.
     */
    public void setPackager(Packager pckgr)
    {
        packgr = pckgr;
    }
    
    /**
     * Devolucion del packager.
     */
    public Packager getPackager()
    {
        return packgr;
    }
    
}
