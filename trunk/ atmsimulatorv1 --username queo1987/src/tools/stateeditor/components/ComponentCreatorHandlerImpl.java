package tools.stateeditor.components;

import infrastructure.dataaccess.InvalidIDException;
import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import domain.implementation.Delay;
import infrastructure.dataaccess.Connectable;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Implementacion de ComponentCreatorHandler.
 * Esta implementacion saca los componentes de
 * un Map persistente.
 */
final class ComponentCreatorHandlerImpl implements ComponentCreatorHandler, Connectable
{
    private static ComponentCreatorHandlerImpl inst = null;
    
    private Map componentes;
    private TableProperties tpinst;
    
    private ComponentCreatorHandlerImpl()
    {
        componentes = new HashMap();
        tpinst = null;
    }
    
    public static ComponentCreatorHandlerImpl getInstance()
    {
        if (inst == null)
        {
            inst = new ComponentCreatorHandlerImpl();
        }
        return inst;
    }
    
    public void setConnectionName(String conn_name)
    {
        componentes = PersistentCollectionFactory.getInstance().getPersistentMap(conn_name);
    }
    
    public Object createComponent(Object base)
    {
        Object ret;
        if (componentes.containsKey(base.getClass().getName()))
        {
            ComponentCreator cc = (ComponentCreator)componentes.get(base.getClass().getName());
            ret = cc.createComponent(base);
        }
        else
        {
            ret = TablePropertiesComponentCreator.getInstance().createComponent(base);
        }
        return ret;
    }
    
    public void addComponentCreator(String className, ComponentCreator cc)
    {
        componentes.put(className, cc);
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
