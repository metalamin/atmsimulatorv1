package infrastructure.services.comm.channel;

import infrastructure.services.comm.CommException;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Definicion del canal de comunicaciones.
 */
public interface Channel
{
    /**
     * Conexion.
     */
    public void connect() throws CommException;
    
    /**
     * Desconexion.
     */
    public void disconnect() throws CommException;
    
    /**
     * Envio de mensajes.
     */
    public void send(Message msg) throws CommException;
    
    /**
     * Recepcion de mensajes.
     */
    public Message receive() throws CommException;
    
    /**
     * Query sobre la conexion
     */
    public boolean isConnected();
    
    /**
     * Seteo del packager.
     */
    public void setPackager(Packager pckgr);
    
    /**
     * Devolucion del packager.
     */
    public Packager getPackager();
}
