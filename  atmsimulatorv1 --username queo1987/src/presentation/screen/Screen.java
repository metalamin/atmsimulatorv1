package presentation.screen;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.io.File;

public class Screen
{
    private HashMap map = new HashMap();
    private URL screen =null;
    private Rectangle rec;
    
    
    public Screen()
    {
    }
    
    public void setScreen(URL in)
    {
        screen = in;
    }
    
    public URL getScreen()
    {
        return screen;
    }
    
    public void addComponent(IComponent gc)
    {
        map.put(gc.getName(),gc);
    }
    
    public Collection getComponents()
    {
        return map.values();
    }
    
    public void removeComponent(Object o)
    {
        map.remove(o);
    }
    
    public void setBounds(int x, int y, int ancho, int alto)
    {
        rec = new Rectangle(x,y, ancho,alto);
    }
    
    public Rectangle getBounds()
    {
        return rec;
    }
    
    public IComponent getComponent(String name)
    {
        return (IComponent)map.get(name);
    }
    
    public void resize(Rectangle rec)
    {
        double nancho = rec.getWidth();
        double nalto = rec.getHeight();
        Rectangle oldrec = getBounds();
        double oldancho = oldrec.getWidth();
        double oldalto = oldrec.getHeight();
        double oldx = oldrec.getX();
        double oldy = oldrec.getY();
        setBounds(rec.x,rec.y,rec.width,rec.height);
        Iterator it = map.values().iterator();
        while(it.hasNext())
        {
            IComponent ic = (IComponent)it.next();
            Rectangle compBounds = ic.getBounds();
            double compX = compBounds.getX();
            double compY = compBounds.getY();
            double compWidth = compBounds.getWidth();
            double compHeight = compBounds.getHeight();
            double nX = compX/oldancho*nancho;//hallo el porcentaje y lo multiplico por el nuevo ancho
            double nY = compY/oldalto*nalto;
            double nWidth = compWidth/oldancho*nancho;
            double nHeight = compHeight/oldalto*nalto;
            System.out.println("*********");
            System.out.println(ic.getBounds());
            ic.setBounds((int)nX,(int)nY,(int)nWidth,(int)nHeight);
            System.out.println(ic.getBounds());
        }
        
    }
    
    
    public ScreenBean getBean()
    {
        ScreenBean pb = new ScreenBean();
        pb.setComponents(getComponents());
        if(screen!=null)
        {
            File file = new File(screen.getFile());
            pb.setUrl(file.getName());
        }
        else
            pb.setUrl("");
        pb.setBounds(getBounds());
        return pb;
    }
    
}