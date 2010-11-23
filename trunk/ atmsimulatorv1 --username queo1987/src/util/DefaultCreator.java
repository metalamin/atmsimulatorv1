package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Creador por defecto.
 * Sirve mucho para cosas del tipo "bean".
 */
public class DefaultCreator implements Creator
{
    private String baseClass;
    private Object baseObject;
    
    public DefaultCreator(Class clase, Object base)
    {
        setBaseClass(clase.getName());
        setBaseObject(base);
    }
    
    public DefaultCreator(Class clase)
    {
        setBaseClass(clase.getName());
        setBaseObject(null);
    }
    
    public DefaultCreator()
    {
    }
    
    /** 
     * Devuelve NULL si hay algun error.
     */
    public Object create()
    {
        Object res = null;
        try
        {
            Class clase = Class.forName(getBaseClass());
            // Se crea la nueva instancia.
            res = clase.newInstance();
            // Si hay un objeto base, se setean las propiedades...
            if (getBaseObject() != null)
            {
                Map resultados = new HashMap();
                Vector gets = ClassUtils.getGetMethods(clase);
                Iterator itg = gets.iterator();
                while (itg.hasNext())
                {
                     Method met = (Method)itg.next();
                     // Resultado...
                     Object temp = (Object)met.invoke(getBaseObject(), (Object[])null);
                     //...que se setea en el mapping
                     resultados.put(met.getName().substring(3), temp);
                }
                // Y se invocan, seteandose las propiedades
                Iterator itp = ClassUtils.getSetMethods(clase).iterator();
                while (itp.hasNext())
                {
                    Method metodo = (Method)itp.next();
                    Object value = (Object)resultados.get(metodo.getName().substring(3));
                    metodo.invoke(res, value);
                }
            }
        }
        catch (InstantiationException iex)
        {
            res = null;
        }
        catch (IllegalAccessException iaex)
        {
            res = null;
        }
        catch (InvocationTargetException itex)
        {
            res = null;
        }
        catch (ClassNotFoundException cnex)
        {
            res = null;
        }
        return res;
    }

    public String getBaseClass()
    {
        return baseClass;
    }

    public void setBaseClass(String baseClass)
    {
        this.baseClass = baseClass;
    }

    public Object getBaseObject()
    {
        return baseObject;
    }

    public void setBaseObject(Object baseObject)
    {
        this.baseObject = baseObject;
    }
   
}
