package infrastructure.dataaccess.broker;

/**
 * Beaner por defecto.
 * NO HACE NADA.
 */
public class DefaultBeaner implements Beaner
{
    public Object toBean(Object entry)
    {
        return entry;
    }
    
    public Object fromBean(Object bean)
    {
        return bean;
    }
}
