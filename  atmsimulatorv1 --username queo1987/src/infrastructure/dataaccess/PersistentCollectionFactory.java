package infrastructure.dataaccess;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase de entrada a las colecciones
 * y mappings persistentes.
 * Se define por un tema de concurrencia
 * sobre los archivos.
 */
public final class PersistentCollectionFactory
{
    // Singleton por conveniencia.
    private static PersistentCollectionFactory inst = null;
    
    // Los mappings
    private Map mappings;
    
    private PersistentCollectionFactory()
    {
        mappings = new HashMap();
    }
    
    public static PersistentCollectionFactory getInstance()
    {
        if (inst == null)
        {
            inst = new PersistentCollectionFactory();
        }
        return inst;
    }
    
    /**
     * Devuelve un mapping para la conexion
     * especificada.
     * A lo sumo, va a devolver 1 por conexion.
     */
    public Map getPersistentMap(String connection)
    {
        Map result;
        if (mappings.containsKey(connection))
        {
            result = (Map)mappings.get(connection);
        }
        else
        {
            result = new PersistentMap(connection);
            mappings.put(connection, result);
        }
        return result;
    }
}
