/*
 * JTableButtonModel.java
 *
 * Created on 27 de diciembre de 2005, 0:18
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tools.stateeditor.graphics;

import java.util.Vector;
import javax.swing.table.*;

/**
 *
 * @author jeronimo
 */
public class JTableButtonModel extends AbstractTableModel {
  
    private Vector __rows;// = new Vector(); 
    private Vector __columns;// = new Vector();
    
    public JTableButtonModel(Vector rows, Vector columns)
    {
        __rows = rows;
        __columns = columns;
    }

  public void addRow(Vector row)
  {
      __rows.add(row);
  }
    
  public String getColumnName(int column) { 
    return (String)__columns.get(column);
  }

  public int getRowCount() {
    return __rows.size();
  }

  public int getColumnCount() {
    return __columns.size();
  }

  public Object getValueAt(int row, int column) { 
      return ((Vector)__rows.get(row)).get(column);
  }

  public boolean isCellEditable(int row, int column) {
    if(column >0)
        return true;
    return false;
  }

  public Class getColumnClass(int column) {
    return getValueAt(0, column).getClass();
  }
 
  public void setValueAt(Object value, int row, int col) {
        if(col==1)
        {
            ((Vector)__rows.get(row)).removeElementAt(col);
            ((Vector)__rows.get(row)).add(col, value);
            fireTableCellUpdated(row, col);
        }
        
    }

 /* public void addTableModelListener(TableModelListener l)
  {
      ;
      
  }*/
  
}
