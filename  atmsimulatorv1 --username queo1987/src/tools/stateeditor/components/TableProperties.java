package tools.stateeditor.components;
import tools.stateeditor.graphics.validtypes.ValidTypesFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.lang.reflect.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import domain.state.TriggerBean;
import tools.stateeditor.graphics.JTableButtonModel;
import tools.stateeditor.graphics.RowEditorModel;
import tools.stateeditor.graphics.ActionTableCellEditor;
import tools.stateeditor.datatransform.TPDataTransformFactory;
import tools.stateeditor.observer.Subject;
import util.ClassUtils;


public class TableProperties implements TableModelListener, tools.stateeditor.observer.Observer
{
    JTable jTable1 = new JTable();// = new MultiCellTable();
    RowEditorModel rm;
    JButton jbutton1 = new JButton();
    JLayeredPane jlp = new JLayeredPane();
    Object comp;
    JTableButtonModel jbm;
    Frame ow;
    Vector rows;
    int renglon = 0;
    int ancho = 220;
    HashMap properties = new HashMap();
    HashMap numToName = new HashMap();
    HashMap nameToNum = new HashMap();
    ValidTypesFactory compFact = null;
    public TableProperties(Object com)
    {
        comp = com;
        //ow = owner;
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int getRenglon()
    {
        return renglon;
    }
    
    public Component getTable()
    {
        return jTable1;
    }
    
    public int getHeight()
    {
        return renglon+1;
    }
    
    public int getWidth()
    {
        return ancho;
    }
    
    public ValidTypesFactory getComponentFactory()
    {
        return compFact;
    }
    
    private void jbInit() throws Exception
    {
        System.out.println("Se crea la tabla");
        rows = new Vector();
        compFact = new ValidTypesFactory();
        //rm = new RowEditorModel();
        Vector columnNames = new Vector();
        columnNames.add("  ");
        columnNames.add("  ");
        columnNames.add("");        
        
        jbm = new JTableButtonModel(rows,columnNames);
        jTable1 = new JTable(/*rows,columnNames*/jbm);
        
        jTable1.getColumn(jTable1.getColumnName(2)).setPreferredWidth(20);
        jTable1.getColumn(jTable1.getColumnName(2)).setMaxWidth(20);
        TableCellRenderer defaultRenderer;
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        defaultRenderer = jTable1.getDefaultRenderer(JButton.class);
        ActionTableCellEditor sat = new ActionTableCellEditor(editor,defaultRenderer);
        jTable1.getColumn(jTable1.getColumnName(2)).setCellEditor(sat);
        jTable1.setDefaultRenderer(JButton.class, sat);
        jTable1.setPreferredScrollableViewportSize(new Dimension(400, 200));
        
        jTable1.setCellSelectionEnabled(true);
        //jTable1.setRowEditorModel(rm);
        
        jTable1.setBackground(SystemColor.menu);
        
        Iterator it = ClassUtils.getGetMethods(comp.getClass()).iterator();
        if(TriggerBean.class.isAssignableFrom(comp.getClass()))
        {
            Subject.getInstance().addObserver(this, Subject.PROPCHANGED);
            Subject.getInstance().addObserver(this, Subject.ACTIONSCHANGED);
            //Subject.getInstance().addObserver(this, Subject.REMOVEACTION);            
        }
        Subject.getInstance().addObserver(this, Subject.BUTTONADDED);         
        while(it.hasNext())
        {
            Method met = (Method)it.next();
            Object o = met.invoke(comp,(Object[])null);
            String s = met.getName().substring(3);
            addProperty(o,s);
        }
        System.out.println(rows.size());
        Subject.getInstance().lateNotify(Subject.BUTTONADDED);
        //this.setBounds(new Rectangle(0, 0, ancho, renglon+1));
        
        jTable1.setBounds(0,0, ancho, renglon+1);
        System.out.println(jTable1.getModel());
        jTable1.getModel().addTableModelListener(this);
        Subject.getInstance().removeObserver(this, Subject.BUTTONADDED);
        /*jTable1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(jb));
        JPanel jp = new JPanel();
        jp.setBounds(0,0,300,120);
        jp.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane();
        //jsp.setBounds(0,0, 400, 200);
        //jsp.getViewport().setView(null);
        jsp.getViewport().setView(jTable1);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jp.add(jsp,BorderLayout.CENTER);
        jlp.add(jp, JLayeredPane.DEFAULT_LAYER);*/
        
    }
   
    public void update(Object o, String type)
    {
        try{
            
        if(type.equals(Subject.PROPCHANGED))
        {
            /*
             *Los PROPCHANGED se que son Vectors, que traen en la primer elemento
             *el numero de fila y en el 2do el objeto que ha cambiado
             */
            Vector vec = (Vector)o;
            Integer num = (Integer)vec.get(0);
            Object data = vec.get(1);
            System.out.println(num);
            System.out.println(numToName);
            String name = (String)numToName.get(num);
            name = "set"+name;
            Class args[] = {data.getClass()};
            Object argsInst[] = {data};
            Method met = comp.getClass().getMethod(name, args);
            met.invoke(comp, argsInst);
            jTable1.setValueAt(data, num.intValue(),1);
            Subject.getInstance().notify(Subject.TABLETRIGGERCHANGED, comp);
        }
        else if(type.equals(Subject.BUTTONADDED))
        {
            System.out.println("SE AGREGO UN BOTON"+((JComponent)o).getBounds());
            JButton but = ((JButton)o);
            System.out.println(jTable1);
            System.out.println(but);
            System.out.println(jTable1.getValueAt((but.getY()/16), 2));
            if(ClassUtils.getInstance().isValidVectorType(but.getText()))
            {
                JButton tb = (JButton)jTable1.getValueAt((but.getY()/16), 2);
                ClassUtils.removeButtonActionListeners(tb);
                tb.setEnabled(true);
                //tb.setFocusable(true); 
                //tb.setFocusPainted(true); 
                ActionListener[] al = but.getActionListeners();
                tb.addActionListener(al[0]);
            }
            /*JTextField textField = new JTextField();
            textField.setBorder(BorderFactory.createEmptyBorder());
            DefaultCellEditor editor = new DefaultCellEditor(textField);
            editor.setClickCountToStart(1);
            StringActionTableCellEditor sa = new StringActionTableCellEditor(editor);
            rm.addEditorForRow(((JButton)o).getY()/16, sa);*/
            //jlp.add((JComponent)o, JLayeredPane.PALETTE_LAYER);
        }
        else if(type.equals(Subject.ACTIONSCHANGED))
        {
           Integer in = (Integer)nameToNum.get("Actions");
           int i = in.intValue();
           jTable1.setValueAt(((TriggerBean)comp).getActions(), i, 1);
        }
        /*else if(type.equals(Subject.REMOVEACTION))
        {
           Integer in = (Integer)nameToNum.get("Actions");
           int i = in.intValue();
           jTable1.setValueAt(((TriggerBean)comp).getActions(), i, 1);
        }*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void tableChanged(TableModelEvent e)
    {
        try
        {
            System.out.println("table changed");
            int row = e.getFirstRow();
            int column = e.getColumn();
            if(column == 1)
            {
                TableModel model = (TableModel)e.getSource();
                String columnName = model.getColumnName(column);
                Object data = model.getValueAt(row, column);
                String name = (String)numToName.get(new Integer(row));
                Object o = properties.get(name);
                data = TPDataTransformFactory.getInstance().getValidDataObject(o,data);
                /*if(Long.class.isInstance(o))
                {
                    data = new Long((String)data);
                }
                if(Integer.class.isInstance(o))
                {
                    data = new Integer((String)data);
                }*/
                name = "set"+name;
                Class args[] = {data.getClass()};
                Method met = comp.getClass().getMethod(name, args);
                
                
                //Object dataCasted = (returnType)data;                        
                Object argsInst[] = {data};
                met.invoke(comp, argsInst);
                if(TriggerBean.class.isAssignableFrom(comp.getClass()))
                {
                    Subject.getInstance().notify(Subject.TABLETRIGGERCHANGED, comp);
                    System.out.println("MODIFACRON TABLA TRIGGER");
                }
                else if(domain.state.Action.class.isInstance(comp))
                {
                    Subject.getInstance().notify(Subject.TABLEACTIONCHANGED, comp);
                    System.out.println("MODIFACRON TABLA ACTION");
                }
                else
                {
                    System.out.println("NO SOY NI TRIGGER NI ACTION");
                    System.out.println(comp.getClass());
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void addObservers()
    {
        if(TriggerBean.class.isAssignableFrom(comp.getClass()))
        {
            Subject.getInstance().addObserver(this, Subject.PROPCHANGED);
            Subject.getInstance().addObserver(this, Subject.ACTIONSCHANGED);
        }        
    }
    
    public void removeObservers()
    {
        Subject.getInstance().removeObserver(this, Subject.PROPCHANGED);
        Subject.getInstance().removeObserver(this, Subject.ACTIONSCHANGED);
        Subject.getInstance().removeObserver(this, Subject.BUTTONADDED);        
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
            System.out.println(numToName);
            nameToNum.put(s,new Integer((int)(renglon/16)));
            JButton bot = new JButton("...");
            bot.setEnabled(false);
            String col = compFact.setComponentOnTable(o,s, renglon);
            nrow.add(col);
            nrow.add(bot);
            rows.add(nrow);
            renglon+=16;
        }
    }
}