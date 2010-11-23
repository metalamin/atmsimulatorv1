package tools.stateeditor.graphics.validtypes;

import javax.swing.JFrame;

public class IntegerType implements ValidTypesBehaviorInterface{

    /** Creates a new instance of StringMenuType */
    public IntegerType() {

    }

    public String setComponentOnTable(Object o, String s, int renglon)
    {
        return o.toString();
    }

    /*public Object[] getArguments(int row, Object o)
    {
        Integer es = new Integer(w.jTable1.getValueAt(row,1).toString());
        Object[] arg = {es};
        return arg;
    }*/

}