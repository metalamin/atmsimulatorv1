package tools.screeneditor;

import tools.screeneditor.graphics.AdvancedFontChooser;
import tools.screeneditor.graphics.validtypes.ComponentFactory;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.AttributeSet;
import java.awt.event.*;
import java.lang.reflect.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import presentation.screen.IComponent;


public class MenuPropiedades extends JWindow{
  JPanel jPanel1 = new JPanel();
  JTable jTable1 = new JTable();
  JColorChooser jColorChooser1 = new JColorChooser();
  JButton jbutton1 = new JButton();
  Component comp;
  Frame ow;
  Vector rows = new Vector();
  int renglon = 0;
  int ancho = 220;
  HashMap properties = new HashMap();
  HashMap numToName = new HashMap();
  HashMap nameToNum = new HashMap();
  ComponentFactory compFact = null;
  public MenuPropiedades(Frame owner, Component com) {
    super(owner);
    comp = com;
    ow = owner;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public int getRenglon()
  {
    return renglon;
  }
    
  public int getHeight()
  {
    return renglon+1;
  }

  public int getWidth()
  {
    return ancho;
  }
  public JTable getTable()
  {
      return jTable1;
  }
  public ComponentFactory getComponentFactory()
  {
      return compFact;
  }

  private void jbInit() throws Exception {

    compFact = new ComponentFactory(this);
    Vector columnNames = new Vector();
    columnNames.add("  ");
    columnNames.add("  ");
    Iterator it = ((IComponent)comp).getGetMethods().iterator();
    while(it.hasNext())
    {
      Method met = (Method)it.next();
      Object o = met.invoke(comp,(Object[])null);
      String s = met.getName().substring(3);
      addProperty(o,s);
    }
    jColorChooser1.setBounds(new Rectangle(78, 18, 67, 11));
    this.getContentPane().setLayout(null);
    jPanel1.setBounds(new Rectangle(0, 0, ancho, renglon+1));
    jTable1 = new JTable(rows,columnNames);
    jPanel1.setLayout(null);
    jTable1.setBounds(new Rectangle(0, 0, ancho, renglon+1));
    jTable1.setBackground(SystemColor.menu);
    jTable1.setCellSelectionEnabled(true);
    this.setForeground(new Color(120, 0, 0));
    this.getContentPane().add(jPanel1, null);
    jPanel1.add(jTable1, null);
    this.addWindowFocusListener(new WindowAdapter()
    {
      	public void windowLostFocus(WindowEvent e)
       	{
          setProperties();
          dispose();
       	}
    });
    
  }
  public void actionPerformedColor(ActionEvent e, int num) {
  WindowFocusListener[] wfs = this.getWindowFocusListeners();
  this.removeWindowFocusListener(wfs[0]);
  String s = (String)numToName.get(new Integer(num));
  ColorUIResource c = (ColorUIResource)properties.get(s);
  jColorChooser1.setColor(c);
  JColorChooser.createDialog(jbutton1,"ELIJA UN COLOR",true,jColorChooser1,null,null).show();
  Color cco = (Color)jColorChooser1.getColor();
  c = new ColorUIResource(cco);
  properties.put(s,c);
  this.requestFocus();
  this.addWindowFocusListener(wfs[0]);
  jTable1.setValueAt(c.getRed()+", "+c.getGreen()+", "+c.getBlue(),num,1);

  }
  public void actionPerformedFont(ActionEvent e, int num) {

  WindowFocusListener[] wfs = this.getWindowFocusListeners();
  this.removeWindowFocusListener(wfs[0]);

  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fontNames = ge.getAvailableFontFamilyNames();
    String[] fontSizes = new String[] { "8", "9", "10", "11", "12", "14", "16",
        "18", "20", "22", "24", "26", "28", "36", "48", "72" };

    AdvancedFontChooser dlg = new AdvancedFontChooser(null, fontNames, fontSizes);
    SimpleAttributeSet a = new SimpleAttributeSet();
    String s = (String)numToName.get(new Integer(num));
    FontUIResource f = (FontUIResource)properties.get(s);
    StyleConstants.setFontFamily(a, f.getName());
    StyleConstants.setFontSize(a, f.getSize());
    StyleConstants.setBold(a, f.isBold());
    dlg.setAttributes(a);
    dlg.show();
    this.requestFocus();
    this.addWindowFocusListener(wfs[0]);
    if(dlg.getOption() == JOptionPane.OK_OPTION)
    {
      AttributeSet as =dlg.getAttributes();
      String name = StyleConstants.getFontFamily(as);

      int size = StyleConstants.getFontSize(as);
      int style = 0;
      if(StyleConstants.isBold(as))
        style = Font.BOLD;
      if(StyleConstants.isItalic(as))
        style+=Font.ITALIC;
      jTable1.setValueAt(name+" "+size ,num,1);

      f = new FontUIResource(name,style,size);
      properties.put(s,f);

    }
  }

  private void setProperties()
  {
    Iterator it = ((IComponent)comp).getSetMethods().iterator();
    try
    {
    while(it.hasNext())
    {
      Method met = (Method)it.next();
      String s = met.getName().substring(3);
      Object obj = properties.get(s);
      Integer integ = (Integer)nameToNum.get(s);
      if(integ != null)
      {
        int val = integ.intValue();
        Object arg[] = compFact.getArguments(val, obj);  
        Object o = met.invoke(comp,arg);      
      }
      
    }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }

  }

  private void addProperty(Object o,String s)
  {
  boolean b = false;
  if(compFact.propertyAllowed(o))
  {
      Vector nrow = new Vector();
      nrow.add(s);
      properties.put(s,o);
      numToName.put(new Integer((int)(renglon/16)),s);
      nameToNum.put(s,new Integer((int)(renglon/16)));
      String col = compFact.setComponentOnTable(o,s, renglon);
      nrow.add(col);
      rows.add(nrow);
      renglon+=16;
  }
  }
}