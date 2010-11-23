/*
 * ActionTableCellEditor.java
 *
 * Created on 14 de diciembre de 2005, 0:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tools.stateeditor.graphics;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author jeronimo
 */
public class ActionTableCellEditor implements TableCellEditor, TableCellRenderer, ActionListener{ 
    //public final Icon DOTDOTDOT_ICON = new ImageIcon(getClass().getResource("dotdotdot.gif")); 
    private TableCellRenderer __defaultRenderer;
    private TableCellEditor editor; 
    //private JButton customEditorButton = new JButton("..."); 
    protected Component Myvalue;
    protected JTable table; 
    protected int row, column; 
 
    public ActionTableCellEditor(TableCellEditor editor, TableCellRenderer renderer){ 
        this.editor = editor; 
        //customEditorButton.addActionListener(this); 
        __defaultRenderer = renderer;
        // ui-tweaking 
        /*customEditorButton.setFocusable(false); 
        customEditorButton.setEnabled(false); 
        //customEditorButton.BO
        customEditorButton.setFocusPainted(false); 
        customEditorButton.setMargin(new Insets(0, 0, 0, 0)); */
    } 
 
    public Component getTableCellRendererComponent(JTable table, Object value,
						 boolean isSelected,
						 boolean hasFocus,
						 int row, int column)
  {
    if(value instanceof Component)
    {  
        //System.out.println("DEVUELVO VALUE "+value);
        Myvalue = (Component)value;
        return (Component)value;
    }
   // System.out.println("DEVUELVO DEFAULTVALUE "+value);
    return __defaultRenderer.getTableCellRendererComponent(
	   table, value, isSelected, hasFocus, row, column);
  }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){ 
        //JPanel panel = new JPanel(new BorderLayout()); 
        //panel.add(editor.getTableCellEditorComponent(table, value, isSelected, row, column),BorderLayout.WEST); 
        //panel.add(customEditorButton, BorderLayout.EAST); 
        this.table = table; 
        this.row = row; 
        this.column = column; 
      if(value instanceof Component)
    {  
        //System.out.println("DEVUELVO CELLEDITORVALUE "+value);
        Myvalue = (Component)value;
        return (Component)value;
    }
      //  System.out.println("DEVUELVO DEFAULTCELLEDITORVALUE "+value);
    return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
    } 
 
    public Object getCellEditorValue(){ 
       if(Myvalue instanceof Component)
      return Myvalue;
    return editor.getCellEditorValue(); 
    } 
 
    public boolean isCellEditable(EventObject anEvent){ 
        return editor.isCellEditable(anEvent); 
    } 
 
    public boolean shouldSelectCell(EventObject anEvent){ 
        return editor.shouldSelectCell(anEvent); 
    } 
 
    public boolean stopCellEditing(){ 
        return editor.stopCellEditing(); 
    } 
 
    public void cancelCellEditing(){ 
        editor.cancelCellEditing(); 
    } 
 
    public void addCellEditorListener(CellEditorListener l){ 
        editor.addCellEditorListener(l); 
    } 
 
    public void removeCellEditorListener(CellEditorListener l){ 
        editor.removeCellEditorListener(l); 
    } 
 
    public final void actionPerformed(ActionEvent e){ 
        editor.cancelCellEditing(); 
        editCell(table, row, column); 
    } 
 
    protected void editCell(JTable table, int row, int column)
    {
    }
 
}