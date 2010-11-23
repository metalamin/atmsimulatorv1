package tools.stateeditor.graphics.validtypes;

import javax.swing.JFrame;

public class StringType implements ValidTypesBehaviorInterface{

    /** Creates a new instance of StringMenuType */
    public StringType() {

    }

    public String setComponentOnTable(Object o, String s, int renglon)
    {
        return (String)o;
    }

    /*public Object[] getArguments(int row, Object o)
    {
        String es = (String)w.jTable1.getValueAt(row,1);
        Object[] arg = {es};
        return arg;
    }*/
}
