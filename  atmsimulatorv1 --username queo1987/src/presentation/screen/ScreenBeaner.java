package presentation.screen;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;


/**
 * Beaner para guardar
 * los TimeOut.
 */
public class ScreenBeaner implements Beaner
{
    public Object toBean(Object obj)
    {
        Screen pan = (Screen)obj;
        ScreenBean pb = pan.getBean();
        Iterator it = pb.getComponents().iterator();
        Vector new_components = new Vector();
        while (it.hasNext())
        {
            IComponent ic = (IComponent)it.next();
            new_components.add(BeanerFactory.getBeaner(ic).toBean(ic));            
        }
        pb.setComponents(new_components);
        return pb;
    }
    
    public Object fromBean(Object obj)
    {
        ScreenBean pb = (ScreenBean)obj;
        Screen pan = new Screen();
        try
        {
            String s = pb.getUrl();
            if(!s.equals(new String("")))
            {
                File f = new File(s);
                //System.out.println(f.getPath());
                URL pepe = f.toURL();
                pan.setScreen(pepe);
            }            
            Rectangle r = pb.getBounds();
            pan.setBounds(r.x, r.y, r.width,r.height);
            Iterator it = pb.getComponents().iterator();
            while(it.hasNext())
            {
                pan.addComponent((IComponent)it.next());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return pan;
    }
}
