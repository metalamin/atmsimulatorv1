/*
 * MultiCellTable.java
 *
 * Created on 26 de diciembre de 2005, 18:56
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tools.stateeditor.graphics;

/**
 *
 * @author jeronimo
 */
import javax.swing.*;
 import javax.swing.table.*;
 import java.util.Vector;

 public class MultiCellTable extends JTable
 {
     protected RowEditorModel rm;

     public MultiCellTable()
     {
         super();
         rm = null;
     }

     public MultiCellTable(TableModel tm)
     {
         super(tm);
         rm = null;
     }

     public MultiCellTable(TableModel tm, TableColumnModel cm)
     {
         super(tm,cm);
         rm = null;
     }

     public MultiCellTable(TableModel tm, TableColumnModel cm,
      ListSelectionModel sm)
     {
         super(tm,cm,sm);
         rm = null;
     }

     public MultiCellTable(int rows, int cols)
     {
         super(rows,cols);
         rm = null;
     }

     public MultiCellTable(final Vector rowData, final Vector columnNames)
     {
         super(rowData, columnNames);
         rm = null;
     }

     public MultiCellTable(final Object[][] rowData, final Object[] colNames)
     {
         super(rowData, colNames);
         rm = null;
     }

    // new constructor
     public MultiCellTable(TableModel tm, RowEditorModel rm)
     {
         super(tm,null,null);
         this.rm = rm;
     }

     public void setRowEditorModel(RowEditorModel rm)
     {
         this.rm = rm;
     }

     public RowEditorModel getRowEditorModel()
     {
         return rm;
   }
     
     public TableCellEditor getCellEditor(int row, int col)
     {
         TableCellEditor tmpEditor = null;
         if (rm!=null)
             tmpEditor = rm.getEditor(row);
         if (tmpEditor!=null)
             return tmpEditor;
         return super.getCellEditor(row,col);
     }
 }


