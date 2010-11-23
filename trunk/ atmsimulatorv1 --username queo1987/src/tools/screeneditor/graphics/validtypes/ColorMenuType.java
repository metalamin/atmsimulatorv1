package tools.screeneditor.graphics.validtypes;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import tools.screeneditor.MenuPropiedades;

public class ColorMenuType implements MPBehaviorInterface {

    /** Creates a new instance of ColorMenuType */
    private MenuPropiedades w = null;
    public ColorMenuType() {

    }

    public void setMenuPropiedades(MenuPropiedades m)
    {
      w=m;
    }


    public Object getValidType(Object o)
    {
        return new Color(0,0,0);
    }

    public String setComponentOnTable(Object o, int renglon)
    {
        Color color = (Color)o;
        String col = color.getRed()+", "+color.getGreen()+", "+color.getBlue();
        JButton jb = new JButton();
        jb.setBounds(new Rectangle(200, w.getRenglon(), 19, 16));
        jb.setText("...");

        jb.addActionListener(new java.awt.event.ActionListener() {
          private int num = (int)w.getRenglon()/16;
          public void actionPerformed(ActionEvent e) {
          w.actionPerformedColor(e,num);
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
