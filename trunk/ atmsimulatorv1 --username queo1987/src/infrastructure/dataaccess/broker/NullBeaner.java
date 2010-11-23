package infrastructure.dataaccess.broker;

/**
 * Beaner NULL.
 * Devuelve NULL, para objetos no guardables.
 */
public class NullBeaner implements Beaner
{
    public Object toBean(Object entry)
    {
        return null;
    }
    
    public Object fromBean(Object bean)
    {
        return null;
    }  
}
