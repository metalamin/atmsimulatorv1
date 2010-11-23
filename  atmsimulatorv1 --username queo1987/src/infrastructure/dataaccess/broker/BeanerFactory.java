package infrastructure.dataaccess.broker;

/**
 * Factory para Beaners.
 */
public class BeanerFactory 
{
    public static Beaner getBeaner(Object name)
    {
        return BeanerHandlerImpl.getInstance().getBeaner(name);
    }
}
