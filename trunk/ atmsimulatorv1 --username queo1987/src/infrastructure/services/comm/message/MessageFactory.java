package infrastructure.services.comm.message;

/**
 * Factory para los mensajes
 */
public interface MessageFactory
{
    public Message createMessage();
    
    public Packager createPackager();
}
