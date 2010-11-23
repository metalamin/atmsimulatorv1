package tools.screeneditor;

import infrastructure.dataaccess.broker.BrokerFactory;
import tools.screeneditor.graphics.ExampleFileFilter;
import tools.screeneditor.graphics.validtypes.ComponentFactory;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import presentation.screen.IComponent;
import presentation.screen.Screen;
import presentation.screen.PasswordBox;
import presentation.screen.TextBox;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Menu extends JDialog
{
    JToggleButton newButton = new JToggleButton();
    JToggleButton newLabel = new JToggleButton();
    JToggleButton newTextBox = new JToggleButton();
    int numbut = 1;
    int numtext = 1;
    int numlabel = 1;
    JButton jButton1 = new JButton();
    URL myUrl;
    Editor p;
    JButton jButton2 = new JButton();
    ComponentFactory compFact;
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  JPanel jPanel2 = new JPanel();
  Border border2;
  TitledBorder titledBorder2;
  JPanel jPanel3 = new JPanel();
  Border border3;
  TitledBorder titledBorder3;
  JToggleButton newPassBox = new JToggleButton();
    public Menu(Editor h)
    {
        super(h,"CONTROLS",false);//,true,false,false,false);
        try
        {
            p = h;
            jbInit();            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(148, 145, 140),new Color(103, 101, 98));
    titledBorder1 = new TitledBorder(border1,"Components");
    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(148, 145, 140),new Color(103, 101, 98));
    titledBorder2 = new TitledBorder(border2,"URL");
    border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(148, 145, 140),new Color(103, 101, 98));
    titledBorder3 = new TitledBorder(border3,"Screen");
    newButton.setText("Button");
    newButton.setBounds(new Rectangle(9, 17, 108, 28));
        compFact = new ComponentFactory();
        //JFrame.setDefaultLookAndFeelDecorated(false);
        newButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newButton_actionPerformed(e);
            }
        });
        this.getContentPane().setLayout(null);
        newLabel.setText("Label");
        newLabel.setBounds(new Rectangle(9, 83, 108, 28));
        newLabel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newLabel_actionPerformed(e);
            }
        });
        newTextBox.setText("TextBox");
        newTextBox.setBounds(new Rectangle(9, 50, 108, 28));
        newTextBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newTextBox_actionPerformed(e);
            }
        });
        jButton1.setBounds(new Rectangle(6, 18, 107, 30));
        jButton1.setText("Open URL...");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        this.setResizable(true);
        jButton2.setBounds(new Rectangle(6, 131, 107, 28));
        jButton2.setText("Save As...");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton2_actionPerformed(e);
            }
        });
        jButton3.setBounds(new Rectangle(6, 97, 107, 28));
    jButton3.setText("Open Screen...");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jButton4.setBounds(new Rectangle(6, 58, 107, 28));
    jButton4.setToolTipText("");
    jButton4.setText("Screen Dim...");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setBounds(new Rectangle(6, 21, 107, 28));
    jButton5.setToolTipText("");
    jButton5.setText("New Screen");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setBounds(new Rectangle(6, 55, 107, 28));
    jButton6.setText("Clear URL");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jPanel1.setBorder(titledBorder1);
    jPanel1.setBounds(new Rectangle(3, 5, 132, 161));
    jPanel1.setLayout(null);
    jPanel2.setBorder(titledBorder2);
    jPanel2.setBounds(new Rectangle(3, 179, 134, 92));
    jPanel2.setLayout(null);
    jPanel3.setBorder(titledBorder3);
    jPanel3.setBounds(new Rectangle(3, 273, 135, 171));
    jPanel3.setLayout(null);
    newPassBox.setMaximumSize(new Dimension(115, 27));
    newPassBox.setMinimumSize(new Dimension(115, 27));
    newPassBox.setPreferredSize(new Dimension(115, 27));
    newPassBox.setToolTipText("");
    newPassBox.setText("PassBox");
    newPassBox.setBounds(new Rectangle(9, 118, 108, 28));
    newPassBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPassBox_actionPerformed(e);
      }
    });
    jPanel1.add(newButton, null);
    jPanel1.add(newTextBox, null);
    jPanel1.add(newLabel, null);
    jPanel1.add(newPassBox, null);
    this.getContentPane().add(jPanel2, null);
    jPanel2.add(jButton1, null);
    jPanel2.add(jButton6, null);
    this.getContentPane().add(jPanel3, null);
    jPanel3.add(jButton3, null);
    jPanel3.add(jButton4, null);
    jPanel3.add(jButton5, null);
    jPanel3.add(jButton2, null);
    this.getContentPane().add(jPanel1, null);
        this.setBounds(10,10,150,490);
    }
    public URL getUrl()
    {
        return myUrl;
    }
    void jButton1_actionPerformed(ActionEvent e)
    {
        //myUrl = new URL("http://java.sun.com/j2se/1.4.2/docs/api/java/net/URL.html");
        UrlSelectionDialog dialog = new UrlSelectionDialog(p,true);
        dialog.setVisible(true);
        if(dialog.accepted())
        {
            if(dialog.selectedOption()==0)
            {
                String url = JOptionPane.showInputDialog(this,"Please enter the Internet Url","Internet Url Input",JOptionPane.INFORMATION_MESSAGE);
                if(url != null)
                {
                    try
                    {
                        myUrl = new URL(url);
                        p.getEditorPane().setPage(myUrl);
                        p.getPantalla().setScreen(myUrl);
                    }
                    catch(Exception ex)
                    {
                        System.err.println("Attempted to read a bad URL: ");
                    }
                    
                }
            }
            else
            {
                JFileChooser jfc = new JFileChooser();
                int valor = jfc.showOpenDialog(this);
                if(valor == jfc.APPROVE_OPTION)
                {
                    File filemio = jfc.getSelectedFile();
                    p.getEditorPane().setEditable(false);
                    try
                    {
                        myUrl = filemio.toURL();
                        //System.out.println(myUrl.getPath());
                        p.getEditorPane().setPage(myUrl);
                        p.getPantalla().setScreen(myUrl);
                    }
                    catch (Exception ei)
                    {
                        System.err.println("Attempted to read a bad URL: ");
                    }

                }
            }
        }
    }

    void jButton2_actionPerformed(ActionEvent e)
    {
        try
        {
            JFileChooser jfc = new JFileChooser();
      ExampleFileFilter filter = new ExampleFileFilter();
      filter.addExtension("xml");
      filter.setDescription("XML files");
      jfc.setFileFilter(filter);
      int valor = jfc.showSaveDialog(this);
        
        if(valor == jfc.APPROVE_OPTION)
        {
            String arch = jfc.getSelectedFile().getPath();
            if(arch!=null)
            {
                Screen pant = new Screen();
                pant.setScreen(myUrl);
                Iterator it = p.getComponentsAdded().iterator();
                while(it.hasNext())
                {
                    IComponent c = (IComponent)it.next();
                    Iterator ite = c.getGetMethods().iterator();
                    IComponent c2 = (IComponent)c.getClass().newInstance();
                    while(ite.hasNext())
                    {
                          Method met = (Method)ite.next();
                          Object o = met.invoke(c,(Object[])null);
                          String s = "set"+met.getName().substring(3);
                          if(compFact.isSeteable(o))
                          {
                              System.out.println(s);
                              Class[] arg = {compFact.getValidType(o).getClass()};
                              Object[] arginst = {o};
                              Method me = c2.getClass().getMethod(s, arg);
                              me.invoke(c2, arginst);
                          }
                    }
                    pant.addComponent(c2);
                }
                Rectangle rec = p.getBounds();
                pant.setBounds(rec.x, rec.y,rec.width, rec.height);
                if(arch.substring(arch.length()-4).equals(".xml"))
                {
                    //System.out.println("YA PUSO XML");
                }
                else
                {
                    //System.out.println("NO PUSO .XML "+arch);
                    arch+=".xml";
                }
                BrokerFactory.getBroker().save(arch, pant);
            }
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    void newButton_actionPerformed(ActionEvent e)
    {
        newButton.setSelected(false);
        presentation.screen.Button b = new presentation.screen.Button();
        b.setName("button"+numbut);
        numbut++;
        b.setText(b.getName());
        p.addNewComponent(b);
    }

    void newLabel_actionPerformed(ActionEvent e)
    {
        newLabel.setSelected(false);

        presentation.screen.Label l = new presentation.screen.Label();
        l.setFocusable(true);
        l.setName("label"+numlabel);
        numlabel++;
        l.setText(l.getName());
        p.addNewComponent(l);
    }

    void newTextBox_actionPerformed(ActionEvent e)
    {
        newTextBox.setSelected(false);
        TextBox tb = new TextBox();
        tb.setName("textbox"+numtext);
        numtext++;
        tb.setText(tb.getName());
        p.addNewComponent(tb);
    }
  void newPassBox_actionPerformed(ActionEvent e) {
        newPassBox.setSelected(false);
        PasswordBox tb = new PasswordBox();
        tb.setName("passbox"+numtext);
        numtext++;
        tb.setText("");
        p.addNewComponent(tb);
  }
  void jButton3_actionPerformed(ActionEvent e) {

      JFileChooser jfc = new JFileChooser();
      ExampleFileFilter filter = new ExampleFileFilter();
      filter.addExtension("xml");
      filter.setDescription("XML files");
      jfc.setFileFilter(filter);

        int valor = jfc.showOpenDialog(this);
        try
        {
        if(valor == jfc.APPROVE_OPTION)
        {
            File filemio = jfc.getSelectedFile();
            Screen pan = (Screen)BrokerFactory.getBroker().load(filemio.getPath());
            Vector vec = p.getComponentsAdded();
            Iterator it = vec.iterator();
            while(it.hasNext())
            {
                JComponent jcomp = (JComponent)it.next();
                p.getLayered().remove(jcomp);
            }
            vec.removeAllElements();
            it = pan.getComponents().iterator();
            while(it.hasNext())
            {
                IComponent ic = (IComponent)it.next();
                p.addComponentOnLayer(ic);
                //p.getLayeredPane().add((JComponent)ic,JLayeredPane.DEFAULT_LAYER);
                //p.getComponentsAdded().add(ic);
            }
            myUrl = pan.getScreen();
            if(myUrl != null)
            {
                p.getEditorPane().setPage(myUrl);
                p.getPantalla().setScreen(myUrl);
            }
            p.repaint();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

  }

  void jButton4_actionPerformed(ActionEvent e) {

  String s1 = JOptionPane.showInputDialog(this, "", "Insert Width", JOptionPane.INFORMATION_MESSAGE);
  if(s1!=null)
  {
    String s2 = JOptionPane.showInputDialog(this,"","Insert Height",JOptionPane.INFORMATION_MESSAGE);
    if(s2!=null)
    {
        Integer i1 = new Integer(s1);
        Integer i2 = new Integer(s2);
        p.setBounds(0,0, i1.intValue(),i2.intValue());
        p.getScollPane().setBounds(p.getBounds());
        p.getScollPane().getViewport().setBounds(p.getBounds());
        p.repaint();
    }
  }
  }

  void clearURL() throws Exception
  {
      p.getEditorPane().setContentType("text/plain");
      p.getEditorPane().setText("");
      p.getEditorPane().setContentType("text/html");
      p.getPantalla().setScreen(null);
      myUrl = null;

     // p.getEditorPane().setPage(myUrl);
  }

  void clearScreen() throws Exception
  {
        clearURL();
        Vector vec = p.getComponentsAdded();
        Iterator it = vec.iterator();
        while(it.hasNext())
        {
            JComponent jcomp = (JComponent)it.next();
            p.getLayered().remove(jcomp);
        }
        vec.removeAllElements();
        p.repaint();
  }

  void jButton6_actionPerformed(ActionEvent e) {
  try{
  clearURL();
  }
  catch(Exception ex)
  {
    System.out.println("OUCH!");
    ex.printStackTrace();
  }
  }

  void jButton5_actionPerformed(ActionEvent e) {
  try
  {
  clearScreen();
  }
  catch(Exception ex)
  {
  ex.printStackTrace();
  }
  }



}
