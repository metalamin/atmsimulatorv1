package infrastructure.communication;

/**
 * Factory para el comm handler.
 */
public class CommunicationHandlerFactory
{
    public static CommunicationHandler getCommunicationHandler()
    {
        return CommunicationHandlerImpl.getInstance();
    }
}
