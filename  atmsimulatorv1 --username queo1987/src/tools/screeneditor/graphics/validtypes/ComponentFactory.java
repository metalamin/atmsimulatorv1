package tools.screeneditor.graphics.validtypes;
import config.Config;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import tools.screeneditor.MenuPropiedades;


public class ComponentFactory {

/* Esta clase se ocupa de dar el comportamiento de los distintos tipos permitidos
 * de las propiedades de los Components.
 */
    private MenuPropiedades w = null;
    private Map tipos;//tipos son los tipos permitidos para mostrar en el menu propiedades
    private Vector values;//values creo que son las cosas q se pueden setear( q permite la interfaz)
    /*
     *Para agregar comportamiento de un nuevo tipo de datos hay que agregarlo
     *aca al Map y crear una clase que implemente la interfaz MPBehaviorInterface
     *y con eso ya queda
     */
    public ComponentFactory()
    {
        try{
        values = new Vector();
        Config.getInstance().setConnectionName("cfg/properties.xml");
        String vals = Config.getInstance().getProperty("VALUES");
        StringTokenizer st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            values.add(st.nextToken());
        }
        tipos = new LinkedHashMap();
        vals = Config.getInstance().getProperty("TYPES");
        st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            String s = st.nextToken();
            String clase = Config.getInstance().getProperty(s);
            MPBehaviorInterface o = (MPBehaviorInterface)Class.forName(clase).newInstance();
            tipos.put(s,o);
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public ComponentFactory(MenuPropiedades m)throws Exception{
        w = m;
        values = new Vector();
        String vals = Config.getInstance().getProperty("VALUES");
        StringTokenizer st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            values.add(st.nextToken());
        }
        /*values.add(String.class.getName());
        values.add(Rectangle.class.getName());
        values.add(javax.swing.plaf.ColorUIResource.class.getName());
        values.add(javax.swing.plaf.FontUIResource.class.getName());*/
        tipos = new LinkedHashMap();
        vals = Config.getInstance().getProperty("TYPES");
        st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            String s = st.nextToken();
            //System.out.println(s);
            String clase = Config.getInstance().getProperty(s);
            //System.out.println(clase);
            MPBehaviorInterface o = (MPBehaviorInterface)Class.forName(clase).newInstance();
            o.setMenuPropiedades(m);
            tipos.put(s,o);
        }


    }
    public void setMenuPropiedades(MenuPropiedades m)throws Exception
    {
        w = m;
       tipos = new LinkedHashMap();
        String vals = Config.getInstance().getProperty("TYPES");
        StringTokenizer st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            String s = st.nextToken();
            String clase = Config.getInstance().getProperty(s);
            MPBehaviorInterface o = (MPBehaviorInterface)Class.forName(clase).newInstance();
            o.setMenuPropiedades(m);
            tipos.put(s,o);
        }

    }

public boolean propertyAllowed(Object o)
{
    if(o==null)
        return false;

    String name = o.getClass().getName();
    Set vals = tipos.keySet();
    return vals.contains(name);
}

public boolean isSeteable(Object o)
{
    String name = o.getClass().getName();
    return values.contains(name);
}
public Object getValidType(Object o)
{
    MPBehaviorInterface mbi = (MPBehaviorInterface)tipos.get(o.getClass().getName());
    //Hay objetos que no quiero que se muestren en la tabla pero son seteables (setBounds por ejemplo);
    if(mbi!=null)
        return mbi.getValidType(o);
    return o;
}

public String setComponentOnTable(Object o, String s, int renglon)
{
    String col = null;
    MPBehaviorInterface tip = (MPBehaviorInterface)tipos.get(o.getClass().getName());
    col = tip.setComponentOnTable(o, renglon);
    renglon += 16;
    return col;
}

 public Object[] getArguments(int row, Object o)
 {
    MPBehaviorInterface tip = (MPBehaviorInterface)tipos.get(o.getClass().getName());
    return tip.getArguments(row,o);
 }
}