package infrastructure.communication;

import infrastructure.communication.CommunicationException;
import util.GeneralException;
import domain.implementation.EventImpl;
import domain.state.Event;
import domain.statemachine.StateMachineFactory;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.CommException;
import infrastructure.services.comm.message.Message;

/**
 * Clase que se dedica a recibir mensajes de
 * un canal de comunicacion, y a disparar el
 * evento en el momento apropiado.
 * Extiende Thread para poder correr en
 * paralelo.
 */
class Receiver extends Thread
{
    //Canal de comunicaciones. DEBE estar conectado.
    private Channel canal;
    //Mensaje.
    private Message mssg;
    //Excepcion reportada.
    private CommException reportedException;
    //GeneralException reportada
    private GeneralException reportedGeneralException;
    //Tipo del mensaje levantado
    private String messageType;
    //Thrower del mensaje levantado
    private String messageThrower;
    //Indicador de "stop"
    private boolean stopCond;
    
    public Receiver(Channel canal, String mssgType, String mssgThrwr)
    {
        this.canal = canal;
        this.mssg = null;
        this.messageType = mssgType;
        this.messageThrower = mssgThrwr;
        reportedException = null;
        stopCond = false;
        reportedGeneralException = null;
    }
    
    public void doStop()
    {
        stopCond = true;
    }
    
    private void sendDisconnect()
    {
        try
        {
            /**
             * Se envia un evento de desconexion
             */
            Event ev = new EventImpl(CommunicationConstants.DISCONNECT_TYPE, 
                    this.messageThrower);
            StateMachineFactory.getStateMachine().update(ev);
            //Se desconecta el canal a mano, por un tema de
            //posibles demoras en la implementacion...
            try
            {
                canal.disconnect();
            }
            catch (CommException cex)
            {
                //...
            }
        }
        catch (GeneralException gex)
        {
            reportedGeneralException = gex;
        }
    }
    
    private void sendConnect()
    {
        try
        {
            /**
             * Se envia un evento de desconexion
             */
            Event ev = new EventImpl(CommunicationConstants.CONNECT_TYPE, 
                    this.messageThrower);
            StateMachineFactory.getStateMachine().update(ev);
            
        }
        catch (GeneralException gex)
        {
            reportedGeneralException = gex;
        }
    }
    
    public void run()
    {
        try
        {
            canal.connect();
            sendConnect();
            while (!stopCond)
            {
                try
                {
                    if (canal.isConnected())
                    {
                        //Se recibe el mensaje
                        mssg = canal.receive();
                        reportedException = null;
                        //y se envia el update...
                        Event ev = new EventImpl(this.messageType, this.messageThrower);
                        ev.getContext().put(CommunicationConstants.MESSAGE, mssg);
                        StateMachineFactory.getStateMachine().update(ev);
                        reportedGeneralException = null;
                    }
                }
                catch (CommException cex)
                {
                    reportedException = cex;
                    sendDisconnect();
                }
                catch (GeneralException gex)
                {
                    reportedGeneralException = gex;
                    sendDisconnect();
                }
            }
        }
        catch (CommException cex)
        {
            reportedException = cex;
            sendDisconnect();
        }
    }
}
