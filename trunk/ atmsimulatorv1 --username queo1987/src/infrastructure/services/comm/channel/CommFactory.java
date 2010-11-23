package infrastructure.services.comm.channel;

import java.util.Collection;
import java.util.Map;

/**
 * Factory para las comunicaciones.
 * Funciona a partir de un contexto de
 * comunicaciones, y devuelve los elementos
 * necesarios para establecer los canales.
 */
public interface CommFactory
{
    /**
     * Seteo del contexto.
     */
    public void setContext(Map context);
    
    /**
     * Devuelve los nombres de los canales
     * que puede crear
     */
    public Collection getChannelNames();
    
    /**
     * Creacion de un canal
     */
    public Channel createChannel(String channelName);
}
