/*
 * JTableButtonMouseListener.java
 *
 * Created on 27 de diciembre de 2005, 1:33
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package tools.stateeditor.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jeronimo
 */
public class JTableButtonMouseListener extends MouseAdapter {
  private JTable __table;

  private void __forwardEventToButton(MouseEvent e) {
    TableColumnModel columnModel = __table.getColumnModel();
    int column = columnModel.getColumnIndexAtX(e.getX());
    int row    = e.getY() / __table.getRowHeight();
    Object value;
    JButton button;
    MouseEvent buttonEvent;
System.out.println("ENTRA");
    if(row >= __table.getRowCount() || row < 0 ||
       column >= __table.getColumnCount() || column < 0)
      return;

    value = __table.getValueAt(row, column);
System.out.println("ANTES SI");
    
    if(!(value instanceof JButton))
      return;

    button = (JButton)value;

    buttonEvent =
      (MouseEvent)SwingUtilities.convertMouseEvent(__table, e, button);
    System.out.println("SI");
    button.dispatchEvent(buttonEvent);
    button.doClick();
   /*  JFrame f = new JFrame();
         f.setBounds(0,0, 100,200);
         f.setVisible(true);*/
    // This is necessary so that when a button is pressed and released
    // it gets rendered properly.  Otherwise, the button may still appear
    // pressed down when it has been released.
    __table.repaint();
  }

  public JTableButtonMouseListener(JTable table) {
    __table = table;
  }

  public void mouseClicked(MouseEvent e) {
    System.out.println("MOUSE CLICKED");
      __forwardEventToButton(e);
  }
/*
  public void mouseEntered(MouseEvent e) {
        System.out.println("MOUSE CLICKED");
        __forwardEventToButton(e);
  }

  public void mouseExited(MouseEvent e) {
        System.out.println("MOUSE CLICKED");
        __forwardEventToButton(e);
  }

  public void mousePressed(MouseEvent e) {
        System.out.println("MOUSE CLICKED");
        __forwardEventToButton(e);
  }

  public void mouseReleased(MouseEvent e) {
        System.out.println("MOUSE CLICKED");
        __forwardEventToButton(e);
  }*/
}