package tools.screeneditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import presentation.screen.IComponent;
import presentation.screen.Screen;
import config.*;


public class Editor extends JFrame
{
    JPanel jPanel1 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JEditorPane jEditorPane1 = new JEditorPane();
    Screen screen = new Screen();
    Point sPoint = new Point(-1, -1);
    Point ePoint = new Point(-1, -1);
    Point sauxPoint = new Point(-1,-1);
    Point eauxPoint = new Point(-1,-1);
    IComponent lastComponentAdded = null;
    JLayeredPane layered = new JLayeredPane();
    int numbut = 1;
    int numlabel=1;
    int numtext = 1;
    boolean resize = false;
    int difSX=0;
    int difSY=0;
    int difEX=0;
    int difEY=0;
    Vector components = new Vector();
    Menu mi_menu;
    BorderLayout borderLayout1 = new BorderLayout();
    public Editor()
    {
        try
        {
            jbInit();            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addMenu(Menu m)
    {
        mi_menu = m;
        try
        {
        mi_menu.clearURL();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public JScrollPane getScollPane()
    {
        return jScrollPane1;
    }
    public JEditorPane getEditorPane()
    {
        return jEditorPane1;
    }
    public JLayeredPane getLayered()
    {
        return layered;
    }
    public void addNewComponent(IComponent co)
    {
        lastComponentAdded = co;
    }
    
    public Vector getComponentsAdded()
    {
       /* Iterator it = components.iterator();
        while(it.hasNext())
        {
            JComponent comp = (JComponent)it.next();
            comp.removeMouseMotionListener(comp.getMouseMotionListeners()[0]);
            comp.removeMouseListener(comp.getMouseListeners()[0]);
            comp.removeKeyListener(comp.getKeyListeners()[0]);
        }*/
        return components;
    }
    
    public Screen getPantalla()
    {
        return screen;
    }
    private void jbInit() throws Exception
    {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setDefaultCloseOperation(3);
        //this.setResizable(false);
        this.setBounds(r);
        this.setTitle("SCREEN EDITOR");
        layered.setBounds(r);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        jEditorPane1.addMouseListener(new java.awt.event.MouseListener()
        {
            public void mouseClicked(MouseEvent e)
            {};
            public void mouseEntered(MouseEvent e)
            {};
            public void mouseExited(MouseEvent e)
            {};
            public void mouseReleased(MouseEvent e)
            {
                mouseReleasedOnEditorPane(e);
            };
            public void mousePressed(MouseEvent e)
            {
                mousePressedOnEditorPane(e);
            }
        });
        jEditorPane1.addMouseMotionListener(new java.awt.event.MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                mouseDraggedOnEditorPane(e);
            }
            public void mouseMoved(MouseEvent e)
            {
                
            }
        });
        
        this.getContentPane().setLayout(borderLayout1);
        
        this.setDefaultLookAndFeelDecorated(true);
        jEditorPane1.setContentType("text/html");
        this.getContentPane().add(layered, BorderLayout.CENTER);
        
        //this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        //this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        
        layered.add(jPanel1, JLayeredPane.DEFAULT_LAYER);
        layered.add(jScrollPane1, JLayeredPane.DEFAULT_LAYER);
        jScrollPane1.getViewport().add(jEditorPane1, null);
        
    }
    
    void mousePressedOnEditorPane(MouseEvent e)
    {
        if(lastComponentAdded != null)
        {
            e.consume();
            
            sPoint.x = e.getX();
            sPoint.y = e.getY();
        }
    }
    public void mouseDraggedOnEditorPane(MouseEvent e)
    {
        if(lastComponentAdded!=null)
        {
            e.consume();
            ePoint.x = e.getX();
            ePoint.y = e.getY();
            
            repaint();
        }
    }
    
    public void addComponentOnLayer(IComponent component)
    {
        layered.add((JComponent)component, JLayeredPane.PALETTE_LAYER);
            ((Component)component).addKeyListener(new java.awt.event.KeyListener()
            {
                public void keyPressed(KeyEvent e)
                {};
                public void keyReleased(KeyEvent e)
                {
                    keyReleasedOnComponent(e);
                };
                public void keyTyped(KeyEvent e)
                {}
                
            });
            ((Component)component).addMouseListener(new java.awt.event.MouseListener()
            {
                public void mouseClicked(MouseEvent e)
                {};
                public void mouseEntered(MouseEvent e)
                {
                };
                public void mouseExited(MouseEvent e)
                {};
                public void mouseReleased(MouseEvent e)
                {
                    mouseReleasedOnComponent(e);
                };
                public void mousePressed(MouseEvent e)
                {
                    mousePressedOnComponent(e);
                }
            });
            ((Component)component).addMouseMotionListener(new java.awt.event.MouseMotionListener()
            {
                public void mouseDragged(MouseEvent e)
                {
                    mouseDraggedOnComponent(e);
                }
                public void mouseMoved(MouseEvent e)
                {
                    mouseMovedOnComponent(e);
                }
            });
            components.add(component);
            
    }
    
    public void mouseReleasedOnEditorPane(MouseEvent e)
    {
        e.consume();
        
        if(lastComponentAdded !=null)
        {
            ePoint.x = e.getX();
            ePoint.y = e.getY();
            
            if ((ePoint.x < sPoint.x) & (ePoint.y < sPoint.y))
                lastComponentAdded.setBounds(ePoint.x, ePoint.y, sPoint.x- ePoint.x,
                        sPoint.y - ePoint.y);
            else if (ePoint.x < sPoint.x)
                lastComponentAdded.setBounds(ePoint.x, sPoint.y, sPoint.x-ePoint.x,
                        ePoint.y-sPoint.y);
            else if (ePoint.y < sPoint.y)
                lastComponentAdded.setBounds(sPoint.x, ePoint.y, ePoint.x-sPoint.x,
                        sPoint.y-ePoint.y);
            else
                lastComponentAdded.setBounds(sPoint.x, sPoint.y, ePoint.x-sPoint.x,
                        ePoint.y-sPoint.y);
            addComponentOnLayer(lastComponentAdded);
            
            
            sPoint.x = -1;
            sPoint.y = -1;
            ePoint.x = -1;
            ePoint.y = -1;
            repaint();
            lastComponentAdded = null;
        }
    }
    
    void keyReleasedOnComponent(KeyEvent e)
    {
        e.consume();
        if(e.getKeyCode() == e.VK_DELETE)
        {
            JComponent j = (JComponent)e.getComponent();
            components.remove(j);
            layered.remove(j);
            repaint();
        }
    }
    
    void mouseMovedOnComponent(MouseEvent e)
    {
        e.consume();
        Component c = e.getComponent();
        if(e.getX()<=2 && e.getY()<=2)
        {
            this.setCursor(Cursor.NW_RESIZE_CURSOR);
        }
        else if(c.getWidth()-e.getX()<=2 && c.getHeight()-e.getY()<=2)
        {
            this.setCursor(Cursor.SE_RESIZE_CURSOR);
        }
        else if(c.getWidth()-e.getX()<=2 && e.getY()<=2)
        {
            this.setCursor(Cursor.NE_RESIZE_CURSOR);
        }
        else if(e.getY()<=2)
        {
            this.setCursor(Cursor.N_RESIZE_CURSOR);
        }
        else if(e.getX()<=2 && c.getHeight()-e.getY()<=2)
        {
            this.setCursor(Cursor.SW_RESIZE_CURSOR);
        }
        else if(c.getHeight()-e.getY()<=2)
        {
            this.setCursor(Cursor.S_RESIZE_CURSOR);
        }
        else if(e.getX()<=2)
        {
            this.setCursor(Cursor.W_RESIZE_CURSOR);
        }
        else if(c.getWidth()-e.getX()<=2)
        {
            this.setCursor(Cursor.E_RESIZE_CURSOR);
        }
        else
            this.setCursor(Cursor.DEFAULT_CURSOR);
        
        if(this.getCursor().getType() != Cursor.DEFAULT_CURSOR)
            resize=true;
    }
    void mousePressedOnComponent(MouseEvent e)
    {
        e.consume();
        e.getComponent().requestFocus();
        if(SwingUtilities.isRightMouseButton(e))
        {
            final MenuPropiedades mp = new MenuPropiedades(this,e.getComponent());
            mp.setBounds(e.getComponent().getX()+e.getX()+3,e.getComponent().getY()+e.getY()+30,mp.getWidth(),mp.getHeight());//aaa
            mp.show();
            mp.requestFocus();
        }
        else
        {
            if(!resize)
                this.setCursor(Cursor.HAND_CURSOR);
            
            sPoint.x = e.getComponent().getX();
            sPoint.y = e.getComponent().getY();
            sauxPoint.x = sPoint.x;
            sauxPoint.y = sPoint.y;
            ePoint.x = e.getComponent().getX()+e.getComponent().getWidth();
            ePoint.y = e.getComponent().getY()+e.getComponent().getHeight();
            eauxPoint.x = ePoint.x;
            eauxPoint.y = ePoint.y;
            difSX = e.getX()-sPoint.x;
            difSY = e.getY()-sPoint.y;
            difEX = e.getComponent().getX()+e.getComponent().getWidth()-e.getX();
            difEY = e.getComponent().getY()+e.getComponent().getHeight()-e.getY();
        }
        
    }
    public void mouseDraggedOnComponent(MouseEvent e)
    {
        e.consume();
        //jEditorPane1.setText(e.getX()+" "+e.getY());
        if(this.getCursor().getType()==Cursor.N_RESIZE_CURSOR)
        {
            sPoint.y = e.getY()-difSY;
        }
        else if(this.getCursor().getType()==Cursor.NW_RESIZE_CURSOR)
        {
            sPoint.x = e.getX()-difSX;
            sPoint.y = e.getY()-difSY;
        }
        else if(this.getCursor().getType()==Cursor.NE_RESIZE_CURSOR)
        {
            ePoint.x = e.getX()+difEX;
            sPoint.y = e.getY()-difSY;
        }
        else if(this.getCursor().getType()==Cursor.E_RESIZE_CURSOR)
        {
            ePoint.x = e.getX()+difEX;
        }
        else if(this.getCursor().getType()== Cursor.W_RESIZE_CURSOR)
        {
            sPoint.x = e.getX()-difSX;
        }
        else if(this.getCursor().getType()== Cursor.S_RESIZE_CURSOR)
        {
            ePoint.y = e.getY()+difEY;
        }
        else if(this.getCursor().getType()== Cursor.SE_RESIZE_CURSOR)
        {
            ePoint.x = e.getX()+difEX;
            ePoint.y = e.getY()+difEY;
        }
        else if(this.getCursor().getType()== Cursor.SW_RESIZE_CURSOR)
        {
            sPoint.x = e.getX()-difSX;
            ePoint.y = e.getY()+difEY;
        }
        else
        {
            sPoint.x = e.getX()-difSX;
            sPoint.y = e.getY()-difSY;
            ePoint.x = e.getX()+difEX;
            ePoint.y = e.getY()+difEY;
            
        }
        repaint();
    }
    
    public void mouseReleasedOnComponent(MouseEvent e)
    {
        e.consume();
        
        if(SwingUtilities.isLeftMouseButton(e))
        {
            if(!resize)
            {
                sPoint.x = e.getX()-difSX;
                sPoint.y = e.getY()-difSY;
                e.getComponent().setLocation(sPoint);
            }
            else
            {
                e.getComponent().setBounds(sPoint.x,sPoint.y,ePoint.x-sPoint.x,ePoint.y-sPoint.y);
            }
            
        }
        sPoint.x = -1;
        resize = false;
        sPoint.y = -1;
        ePoint.x = -1;
        ePoint.y = -1;
        repaint();
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    public void paint(Graphics g)
    {
        jEditorPane1.repaint();
        //mi_menu.repaint();
       
        if(sPoint.x !=-1)
            if ((ePoint.x < sPoint.x) & (ePoint.y < sPoint.y))
                g.drawRect(ePoint.x, ePoint.y, sPoint.x- ePoint.x,
                        sPoint.y - ePoint.y);
            else if (ePoint.x < sPoint.x)
                g.drawRect(ePoint.x, sPoint.y, sPoint.x-ePoint.x,
                        ePoint.y-sPoint.y);
            else if (ePoint.y < sPoint.y)
                g.drawRect(sPoint.x, ePoint.y, ePoint.x-sPoint.x,
                        sPoint.y-ePoint.y);
            else
                g.drawRect((int)sPoint.x+3, (int)sPoint.y+30, ePoint.x-sPoint.x,//aaa
                        ePoint.y-sPoint.y);
    }
    public static void main(String args[])
    {
        try
        {
        SystemConfig.getInstance().configure();
        Editor ed = new Editor();
        Menu men = new Menu(ed);
        ed.setVisible(true);
        men.setVisible(true);
        //ed.getLayeredPane().add(men);
        //men.setVisible(true);
        //men.toFront();
        
        //hola.setVisible(true);
        JScrollPane jsp = ed.getScollPane();
        jsp.setBounds(ed.getBounds());//=0,0,1024,768);
        //jsp.getViewport().setBounds(0,0,1024,700);
        jsp.requestFocus();
        ed.addMenu(men);
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
}