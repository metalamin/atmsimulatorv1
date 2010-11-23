package tools.screeneditor.graphics.validtypes;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import tools.screeneditor.MenuPropiedades;

/**
 *
 * @author jeronimo
 */
public class FontMenuType implements MPBehaviorInterface {

    /** Creates a new instance of FontMenuType */
   private MenuPropiedades w = null;
    public FontMenuType() {
    }

    public void setMenuPropiedades(MenuPropiedades m)
    {
      w=m;
    }


    public Object getValidType(Object o)
    {
        return new Font("Arial",0,10);
    }

     public String setComponentOnTable(Object o, int renglon)
    {
        String col = ((Font)o).getName()+" "+((Font)o).getSize();
        JButton jb = new JButton();
        jb.setBounds(new Rectangle(200, w.getRenglon(), 19, 16));
        jb.setText("...");
        jb.addActionListener(new java.awt.event.ActionListener() {
          private int num = (int)w.getRenglon()/16;
          public void actionPerformed(ActionEvent e) {
          w.actionPerformedFont(e,num);
          }
        });
        w.getContentPane().add(jb, null);
        return col;
    }

     public Object[] getArguments(int row, Object o)
    {
         Object[] arg = {o};
         return arg;
     }

}
