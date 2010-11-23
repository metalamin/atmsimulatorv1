/*
 * RowEditorModel.java
 *
 * Created on 26 de diciembre de 2005, 18:59
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
 import javax.swing.table.*;
 import java.util.*;
 public class RowEditorModel
 {
      private Hashtable data;
      public RowEditorModel()
      {
          data = new Hashtable();
      }
     public void addEditorForRow(int row, TableCellEditor e )
     {
         data.put(new Integer(row), e);
     }
     public void removeEditorForRow(int row)
     {
         data.remove(new Integer(row));
     }
     public TableCellEditor getEditor(int row)
     {
         return (TableCellEditor)data.get(new Integer(row));
     }
 }