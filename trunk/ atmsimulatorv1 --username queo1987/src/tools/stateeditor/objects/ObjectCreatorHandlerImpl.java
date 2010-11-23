package tools.stateeditor.objects;

import infrastructure.dataaccess.InvalidIDException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import infrastructure.dataaccess.Connectable;
import util.Creator;
import infrastructure.dataaccess.PersistentCollectionFactory;


/**
 * Implementacion de ObjectCreatorHandler.
 * Esta implementacion saca los componentes de
 * un Map persistente.
 */
final class ObjectCreatorHandlerImpl implements ObjectCreatorHandler, Connectable
{
    private static ObjectCreatorHandlerImpl inst = null;
    
    private Map componentes;
    
    private ObjectCreatorHandlerImpl()
    {
        componentes = new HashMap();
    }
    
    public static ObjectCreatorHandlerImpl getInstance()
    {
        if (inst == null)
        {
            inst = new ObjectCreatorHandlerImpl();
        }
        return inst;
    }
    
    public void setConnectionName(String conn_name)
    {
        componentes = PersistentCollectionFactory.getInstance().getPersistentMap(conn_name);
    }
    
    public Set getAvailableTypes()
    {
        return componentes.keySet();
    }
    
    public Set getAvailableNames(String type)
    {
        Set ret;
        System.out.println(componentes);
        if (!componentes.containsKey(type))
        {
            System.out.println("NO TE TENGO");
            ret = new HashSet();
        }
        else
        {
            System.out.println("TE TENGO");
            Map mp = (Map)componentes.get(type);        
            ret = new TreeSet(mp.keySet());
            System.out.println(ret);
        }
        return ret;
    }
    
    public Object create(String type, String name) throws InvalidIDException 
    {
        if (!componentes.containsKey(type))
        {
            throw new InvalidIDException("El tipo " + type + " no es válido para la creación de componentes");
        }
        Map mp = (Map)componentes.get(type);        
        if (!mp.containsKey(name))
        {
            throw new InvalidIDException("El nombre " + name + " no es válido para la creación de componentes");
        }
        Creator cc = (Creator)mp.get(name);
        return cc.create();
    }
    
    public void addCreator(String type, String name, Creator cc)
    {
        Map mp;
        if (!componentes.containsKey(type))
        {
            mp = new HashMap();
        }
        else
        {
            mp = (Map)componentes.get(type);
        }
        mp.put(name, cc);
        componentes.put(type, mp);
    }
    
    public String toString()
    {
        String str = "";
        Iterator itr = componentes.keySet().iterator();
        while (itr.hasNext())
        {
            String name = (String)itr.next();
            str += name + "\n";
            Map mp = (Map)componentes.get(name);
            Iterator it2 = mp.keySet().iterator();
            while (it2.hasNext())
            {
                String ii = (String)it2.next();
                Object ob = mp.get(ii);                
                str += "\t" + ii + ": " + ob + "\n";
            }
        }
        return str;
    }
}
