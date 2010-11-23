package infrastructure.dataaccess.broker;
import util.GeneralException;
import java.util.HashMap;
import java.util.Map;
import infrastructure.dataaccess.PersistenceException;
import infrastructure.dataaccess.Connectable;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Diccionario de Beaners.
 * Saca la informacion de archivo.
 */
final class BeanerHandlerImpl implements BeanerHandler, Connectable
{
    private static BeanerHandlerImpl inst = null;
    
    // Nombre de la conexion.
    private String conn_name;
    
    // El Mapping.
    private Map beaners;
    
    private BeanerHandlerImpl()
    {
        conn_name = null;
        beaners = new HashMap();
    }
    
    public static BeanerHandlerImpl getInstance()
    {
        if (inst == null)
            inst = new BeanerHandlerImpl();
        return inst;
    }

    /**
     * Pasa a trabajar con una nueva conexion...
     */
    public void setConnectionName(String name)
    {
        this.conn_name = name;
        /**
         * Se saca la info...
         */
        try
        {
            Map aux_beaners = PersistentCollectionFactory.getInstance().getPersistentMap(name);
            beaners = aux_beaners;
        }
        catch (PersistenceException gex)
        {
            /**
             * Esta si que esta mal...
             */
            conn_name = null;
            throw gex;
        }
    }
    
    public Beaner getBeaner(Object name)
    {
        Beaner b = (Beaner)beaners.get(name.getClass().getName());
        if (b == null)
        {
            b = new DefaultBeaner();
        }
        return b;
    }

    public void addBeaner(String class_name, Beaner beaner) throws GeneralException
    {
        beaners.put(class_name, beaner);
    }
}
