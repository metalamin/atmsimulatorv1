package tools.screeneditor.graphics.validtypes;

import tools.screeneditor.MenuPropiedades;

public class StringMenuType implements MPBehaviorInterface{

    /** Creates a new instance of StringMenuType */
    private MenuPropiedades w = null;
    public StringMenuType() {

    }

    public void setMenuPropiedades(MenuPropiedades m)
    {
      w=m;
    }

    public String setComponentOnTable(Object o, int renglon)
    {
        return (String)o;
    }

    public Object[] getArguments(int row, Object o)
    {
        String es = (String)w.getTable().getValueAt(row,1);
        Object[] arg = {es};
        return arg;
    }

    public Object getValidType(Object o)
    {
        return o;
    }

}
