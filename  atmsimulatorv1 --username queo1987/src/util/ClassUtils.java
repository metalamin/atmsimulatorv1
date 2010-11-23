package util;

import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import domain.implementation.TriggerValues;
import domain.state.StateBean;
import domain.state.TriggerBean;
import tools.stateeditor.StateEditor;

/**
 * Clase de utilidades sobre las clases.
 */
public class ClassUtils
{
    /**
     * Devuelve un Vector con
     * los metodos de "get" de
     * una clase.
     */
    public static String acciones = "ACTIONS";
    public static String triggers = "TRIGGERS";
    private HashMap properties;
    private static ClassUtils inst = null;
    
    private ClassUtils()
    {
        properties = new HashMap();
        properties.put("STOPTRIGGERS", triggers);
        properties.put("RESETTRIGGERS", triggers);
        properties.put("COUNTTRIGGERS", triggers);                
        properties.put("ACTIONS", acciones);
    }
    
    public static ClassUtils getInstance()
    {
        if(inst == null)
            inst = new ClassUtils();
        return inst;
    }
    public static Vector getGetMethods(Class clase)
    {
        Vector result = new Vector();
        Method[] m = clase.getMethods();
        int i =0;
        String s = new String("get");
        while(i< m.length)
        {
            Method met = m[i];
            if(met.getName().startsWith(s))
            {
                result.add(met);
            }
            i++;
        }
        return result;
    }
    
    /**
     * Idem al anterior, pero
     * con los "set".
     */
    public static Vector getSetMethods(Class clase)
    {
        Vector result = new Vector();
        Method[] m = clase.getMethods();
        int i =0;
        String s = new String("set");
        while(i< m.length)
        {
            Method met = m[i];
            if(met.getName().startsWith(s))
            {
                result.add(met);
            }
            i++;
        }
        return result;
    }
    
    public static void removeButtonActionListeners(JButton j)
    {
        ActionListener[] al = j.getActionListeners();
        for(int i = 0; i < al.length; i++)
        {
            j.removeActionListener(al[i]);
        }
    }
    
    public boolean isValidVectorType(String s)
    {
        Object o = properties.get(s.toUpperCase());
        if((o != null) && (o.equals(triggers)))
        {
            return true;
        }
        return false;            
    }
    
  /*  public Object getPropertyType(Object o)
    {
        return properties.get(o);
    }
 */   
    public HashMap loadPropertyMap(String s, Vector vals)
    {
        Object o = properties.get(s.toUpperCase());
        HashMap hm = null;
        System.out.println(o);
        if(o==null)
            return null;
        StateBean sb = StateEditor.getInstance().getActualState();
        if(o.equals(triggers))
        {
            Vector vec = (Vector)sb.getTriggers().clone();
            vec.add(sb.getStartupTrigger());
            vec.add(sb.getEndTrigger());
            Iterator it = vec.iterator();
            TriggerBean ab = StateEditor.getInstance().getActualTrigger();
            hm =new HashMap();
            while(it.hasNext())
            {
                TriggerBean tb = (TriggerBean)it.next();
                TriggerValues tv = new TriggerValues(tb.getType(),tb.getThrower());
                if(!tb.equals(ab) && !vals.contains(tv))
                {                    
                    hm.put(tv.toString(),tv);
                }
            }
        }
        else if(o.equals(acciones))
        {
            
        }
        return hm;
    }
  /*  
    public static Object getValidObject(Long o, Object data)
    {
        data = new Long((String)data);
        return data;
    }
    
    public static Object getValidObject(Integer o, Object data)
    {
        data = new Integer((String)data);
        return data;
    }
    
    public static Object getValidObject(String o, Object data)
    {
        return data;
    }       
   */ 
    
}
