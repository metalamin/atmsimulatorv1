package infrastructure.communication;

import infrastructure.communication.CommunicationException;
import util.GeneralException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import domain.implementation.EventImpl;
import domain.state.Event;
import domain.statemachine.StateMachineFactory;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.CommException;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.message.Packager;


/**
 * Implementacion del handler de comunicaciones para
 * la maquina de estados.
 * Singleton.
 */
final class CommunicationHandlerImpl implements CommunicationHandler
{
    //Singleton.
    private static CommunicationHandlerImpl inst = null;
    
    //Factory
    private CommFactory factory;
    
    private MessageFactory messageFactory;
    
    //Mapping nombre -> channel
    private Map channels;
    
    //Mapping nombre -> receiver
    private Map receivers;
    
    private CommunicationHandlerImpl()
    {
        factory = null;
        channels = new HashMap();
        receivers = new HashMap(); 
        CommunicationConfig.getInstance().doConfig(this);
    }
    
    public static CommunicationHandlerImpl getInstance()
    {
        if (inst == null)
        {
            inst = new CommunicationHandlerImpl();
        }
        return inst;
    }
    
    //Seteo del factory
    public void setCommFactory(CommFactory factory)
    {
        this.factory = factory;
    }
    
    public void setMessageFactory(MessageFactory messageFactory)
    {
        this.messageFactory = messageFactory;
    }
    
    //Conexion
    public void connect() throws CommunicationException, GeneralException 
    {
        Collection colChannels = factory.getChannelNames();
        Iterator itr = colChannels.iterator();
        while (itr.hasNext())
        {
            String channelName = (String)itr.next();
            connect(channelName);
        }
    }
    
    //Desconexion
    public void disconnect() throws CommunicationException
    {
        Iterator itr = getChannelNames().iterator();
        while (itr.hasNext())
        {
            String channelName = (String)itr.next();
            disconnect(channelName);
        }
    }
    
    //Conexion de un canal
    public void connect(String channelName) throws 
            CommunicationException, GeneralException
    {
        //Si ya se encuentra agregado...
        if (channels.containsKey(channelName))
        {
            Channel chnnl = (Channel)channels.get(channelName);
            /**
             * Si no se puede conectar, se envia el evento de
             * desconexion.
             */
            try
            {
                if (!chnnl.isConnected())
                {
                    chnnl.connect();
                    //Se envia un evento de conexion...
                    Event ev = new EventImpl(CommunicationConstants.CONNECT_TYPE,
                            channelName);
                    StateMachineFactory.getStateMachine().update(ev);
                }
            }
            catch (CommException cex)
            {
                //Se envia un evento de desconexion...
                Event ev = new EventImpl(CommunicationConstants.DISCONNECT_TYPE,
                        channelName);
                StateMachineFactory.getStateMachine().update(ev);
            }
        }
        else
        {
            Channel chnnl = factory.createChannel(channelName);
            Packager pckgr = messageFactory.createPackager();
            if (pckgr != null)
            {
                chnnl.setPackager(pckgr);
            }
            Receiver recvr = new Receiver(chnnl,
                    CommunicationConstants.COMMUNICATION_TYPE,
                    channelName);
            recvr.start();
            receivers.put(channelName, recvr);
            channels.put(channelName, chnnl);
        }
    }
    
    //Desconexion de un canal
    public void disconnect(String channelName) throws CommunicationException
    {
        try
        {
            if (channels.containsKey(channelName))
            {
                Channel channel = (Channel)channels.get(channelName);
                if (channel.isConnected())
                {
                    Receiver recvr = (Receiver)receivers.get(channelName);
                    recvr.doStop();
                    channel.disconnect();
                    channels.remove(channelName);
                    receivers.remove(channelName);
                }
            }
        }
        catch (CommException cex)
        {
            throw new CommunicationException("Error al desconectar", cex);
        }
    }
    
    
    //Creacion de un nuevo mensaje
    public Message createMessage()
    {
        return messageFactory.createMessage();
    }
    
    
    //Devolucion de los canales presentes
    public Collection getChannelNames()
    {
        return channels.keySet();
    }
    
    //Envio de un mensaje pour un canal dado
    public void send(String channelName, Message message) 
        throws CommunicationException, GeneralException
    {
        try
        {
            Channel channel = (Channel)channels.get(channelName);
            channel.send(message);
        }
        catch(CommException cex)
        {
            Event ev = new EventImpl(CommunicationConstants.DISCONNECT_TYPE,
                    channelName);
            StateMachineFactory.getStateMachine().update(ev);
        }
    }
   
}
