package tools.stateeditor.graphics.validtypes;
import config.Config;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFrame;

public class ValidTypesFactory {

/* Esta clase se ocupa de dar el comportamiento de los distintos tipos permitidos
 * de las propiedades de los Components.
 */
    private Map tipos;//tipos son los tipos de datos permitidos como key, y como objeto el la clase que maneja ese tipo de datos
    /*
     *Para agregar comportamiento de un nuevo tipo de datos hay que agregarlo
     *aca al Map y crear una clase que implemente la interfaz MPBehaviorInterface
     *y con eso ya queda
     */
    public ValidTypesFactory()
    {
        try{
        Config.getInstance().setConnectionName("cfg/validTypes.xml");
        /*String vals = Config.getInstance().getProperty("VALUES");
        StringTokenizer st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            values.add(st.nextToken());
        }*/
        tipos = new LinkedHashMap();
        String vals = Config.getInstance().getProperty("TYPES");
        StringTokenizer st = new StringTokenizer(vals,",");
        while(st.hasMoreTokens())
        {
            String s = st.nextToken();
            String clase = Config.getInstance().getProperty(s);
            ValidTypesBehaviorInterface o = (ValidTypesBehaviorInterface)Class.forName(clase).newInstance();
            tipos.put(s,o);
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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

/*public boolean isSeteable(Object o)
{
    String name = o.getClass().getName();
    return values.contains(name);
}
/*public Object getValidType(Object o)
{
    ValidTypesBehaviorInterface mbi = (ValidTypesBehaviorInterface)tipos.get(o.getClass().getName());
    //Hay objetos que no quiero que se muestren en la tabla pero son seteables (setBounds por ejemplo);
    if(mbi!=null)
        return mbi.getValidType(o);
    return o;
}*/

public String setComponentOnTable(Object o, String s, int renglon)
{
    String col = null;
    ValidTypesBehaviorInterface tip = (ValidTypesBehaviorInterface)tipos.get(o.getClass().getName());
    col = tip.setComponentOnTable(o, s, renglon);
    renglon += 16;
    return col;
}
/*
 public Object[] getArguments(int row, Object o)
 {
    ValidTypesBehaviorInterface tip = (ValidTypesBehaviorInterface)tipos.get(o.getClass().getName());
    return tip.getArguments(row,o);
 }*/
}