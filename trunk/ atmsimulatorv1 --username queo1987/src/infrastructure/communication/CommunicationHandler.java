package infrastructure.communication;

import infrastructure.communication.CommunicationException;
import util.GeneralException;
import java.util.Collection;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;

/**
 * Handler de comunicaciones para la maquina
 * de estados.
 *
 * Maneja N canales de comunicacion.
 * 
 * Cuando recibe mensajes nuevos, dispara un
 * evento dado por las constantes presentes
 * en CommunicationConstants y en el canal
 * en si, poniendo el mensaje en el
 * contexto del evento.
 *
 * Cuando ocurre un error al conectarse, enviar 
 * un mensaje o recibir un mensaje, dispara un
 * evento con tipo DISCONNECT_TYPE, y thrower
 * el canal del error.
 */
public interface CommunicationHandler
{
    //Seteo del factory
    public void setCommFactory(CommFactory factory);
    
    //Seteo del factory de mensajes
    public void setMessageFactory(MessageFactory mssf);
    
    //Creacion de un nuevo mensaje
    public Message createMessage();
    
    //Conexion de todos los canales
    public void connect() throws CommunicationException, GeneralException; 
    
    //Desconexion de todos los canales
    public void disconnect() throws CommunicationException; 
    
    //Conexion de un canal
    public void connect(String channelName) throws 
            CommunicationException, GeneralException; 
    
    //Desconexion de un canal
    public void disconnect(String channelName) throws CommunicationException;
    
    //Devolucion de los canales presentes
    public Collection getChannelNames();
    
    //Envio de un mensaje pour un canal dado
    public void send(String channelName, Message message) throws 
            CommunicationException, GeneralException; 

}
