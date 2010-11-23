package infrastructure.dataaccess.broker;

/**
 * Factory para el BeanerHandler.
 */
public class BeanerHandlerFactory
{
    public static BeanerHandler getBeanerHandler()
    {
        return BeanerHandlerImpl.getInstance();
    }
}
