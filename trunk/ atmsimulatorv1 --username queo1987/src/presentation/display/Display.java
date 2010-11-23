
package presentation.display;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.Iterator;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import presentation.screen.IComponent;
import presentation.screen.Screen;
import javax.swing.JLayeredPane;

/**
 *
 * @author Programacion
 */
public class Display
{
    
    /** Creates a new instance of Display */
    private Screen pant_act;
    private static Display mi_inst = null;
    private JFrame frame;
    private DictBehavior db;
    
    private Display()
    {
        db = new DictBehavior();
        frame = null;
    }
    
    public static Display getInstance()
    {
        if(mi_inst ==null)
            mi_inst = new Display();
        return mi_inst;
    }
    
    public Screen getPantalla()
    {
        return pant_act;
    }
    
    public void doDisplay(Screen p, Rectangle rec)
    {
        p.resize(rec);
        //p.setBounds(rec.x, rec.y,rec.width,rec.height);
        doDisplay(p);
    }
    
    public void doDisplay(Screen p)
    {
        pant_act = p;
        if (frame != null)
            frame.dispose();
        try
        {
            frame = new JFrame();
            
            /*
             * @TODO Sincronize with Jero
             */
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JScrollPane jsp = new JScrollPane();
            
            /*
             * @TODO Sincronize with Jero
             */
            jsp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            jsp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            
            JEditorPane jep  = new JEditorPane();
            JPanel panel = new JPanel();
            BorderLayout b = new BorderLayout();
            frame.setBounds(p.getBounds());
            frame.getContentPane().setLayout(b);
            frame.setDefaultLookAndFeelDecorated(true);
            jep.setContentType("text/html");
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            BorderLayout b2 = new BorderLayout();
            panel.setLayout(b2);
            //frame.getContentPane().add(jsp, BorderLayout.CENTER);
            jsp.getViewport().add(jep, null);
            jsp.setBounds(0,0,frame.getWidth(),frame.getHeight());
            jsp.getViewport().setBounds(0,0,frame.getWidth(),frame.getHeight());
            jep.setEditable(false);
            JLayeredPane jlp = new JLayeredPane();
            //jlp.setBounds(frame.getBounds());
            jlp.add(jsp, javax.swing.JLayeredPane.DEFAULT_LAYER);
            if (p.getScreen()!=null)
                jep.setPage(p.getScreen());
            jsp.requestFocus();
            
            Iterator it = p.getComponents().iterator();
            while(it.hasNext())
            {
                IComponent ic = (IComponent)it.next();
                db.setBehavior(ic);
                //frame.getLayeredPane().add((Component)ic);
                jlp.add((Component)ic, JLayeredPane.PALETTE_LAYER);
            }
            panel.add(jlp, BorderLayout.CENTER);
            
            /*
             * @TODO Sincronize with Jero
             */
            //frame.show();
            frame.setVisible(true);
            frame.repaint();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
     public synchronized void doDisplaySimulator(Screen p)
    {
        if (frame != null)
            removeComponents();
        pant_act = p;        
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame = Simulator.getInstance();
            JEditorPane jep  = Simulator.getInstance().getEditorPane();
            JPanel panel = Simulator.getInstance().getPanel(1);
            BorderLayout b = new BorderLayout();
            //Saco el layered pane
            JLayeredPane jl = (JLayeredPane)panel.getComponent(0);
            if (p.getScreen()!=null)
                jep.setPage(p.getScreen());
            /*
             * El setScreen es asynchronico por eso hay q esperar unos milisegundos
             * a que termine de cargar la URL.......
             */
            //wait(500);
            jep.requestFocus();            
            Iterator it = p.getComponents().iterator();
            while(it.hasNext())
            {
                IComponent ic = (IComponent)it.next();
                db.setBehavior(ic);
                Rectangle r = ic.getBounds();
                ic.setBounds((int)r.getX(),(int)r.getY()-4,(int)r.getWidth(),(int)r.getHeight());
                jl.add((Component)ic, JLayeredPane.PALETTE_LAYER);
            }
        /*
         * @TODO Sincronize with Jero
         */
        //frame.repaint();
        frame.setVisible(true);
        frame.repaint();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
     
     void removeComponents()
     {
         //Iterator it = pant_act.getComponents().iterator();
         JPanel panel = Simulator.getInstance().getPanel(1);
         JLayeredPane jl = (JLayeredPane)panel.getComponent(0);
         Component[] comps = jl.getComponentsInLayer(JLayeredPane.PALETTE_LAYER.intValue());
         int size = comps.length;
         int i=0;
         while(i<size)
         {
            jl.remove(comps[i]);
            i++;
         }         
     }
}
