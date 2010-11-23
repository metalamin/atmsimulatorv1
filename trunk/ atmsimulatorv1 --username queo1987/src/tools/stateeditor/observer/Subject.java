package tools.stateeditor.observer;

import java.util.*;

/**
 *
 * @author jeronimo
 */
public class Subject {
   
    public static String TABLEACTIONCHANGED = "TABLEACTIONCHANGED";
    public static String TABLETRIGGERCHANGED = "TABLETRIGGERCHANGED";
    public static String SELECTTRIGGERCHANGED = "SELECTRIGGERCHANGED";
    public static String PROPCHANGED = "PROPCHANGED";
    public static String ACTIONSCHANGED = "ACTIONSCHANGED";
    public static String BUTTONADDED = "BUTTONADDED";
    public static String STATEGRAPHCHANGED = "STATEGRAPHCHANGED";    
    private static  Subject inst = null;
    private HashMap observers;
    private HashMap lateObjects;
    /** Creates a new instance of Subject */
    private Subject() {
        observers= new HashMap();
        lateObjects = new HashMap();
    }
    
    public static Subject getInstance()
    {
        if(inst == null)
            inst = new Subject();
        return inst;
    }
    
    public void addObserver(Observer o, String type)
    {
        Vector vec =null;
        if(observers.containsKey(type))
        {
            vec = (Vector)observers.get(type);
            vec.add(o);
        }    
        else
        {
            vec = new Vector();
            vec.add(o);
            observers.put(type, vec);  
            vec = new Vector();
            lateObjects.put(type,vec);
        }
    }
    
    public void removeObserver(Observer o, String type)
    {
        Vector vec =null;
        int i = 0;
        if(observers.containsKey(type))
        {
            vec = (Vector)observers.get(type);
            while(i<vec.size())
            {
                Object ob = vec.get(i);
                if(ob.equals(o))
                {
                    vec.remove(i);
                    break;
                }
                i++;
            }
        }            
    }
    
    public void notify(String type, Object o)
    {
        if(observers.containsKey(type))
        {
            Vector vec = (Vector)observers.get(type);
            Iterator it = ((Vector)vec.clone()).iterator();
            while(it.hasNext())
            {
                Observer obs = (Observer)it.next();
                obs.update(o, type);
                System.out.println("NOTIFICO, TIPO = "+type);
            }
        }
    }
    
    public void addLateObject(String type, Object o)
    {
        if(lateObjects.containsKey(type))
        {
            Vector vec = (Vector)lateObjects.get(type);
            vec.add(o);            
        }
    }
    
    public void lateNotify(String type)
    {
        if(lateObjects.containsKey(type))
        {
            Vector vec = (Vector)lateObjects.get(type);
            Vector vecObservers = (Vector)observers.get(type);
            Iterator it = vecObservers.iterator();
            while(it.hasNext())
            {
                Observer obs = (Observer)it.next();
                Iterator it2 = vec.iterator();
                while(it2.hasNext())
                {
                    Object o = it2.next();
                    obs.update(o, type);
                    System.out.println("NOTIFICO, TIPO = "+type);    
                }
                
            }
            vec.removeAllElements();
            //lateObjects.put(type,new Vector());
        }
    }
    
}
