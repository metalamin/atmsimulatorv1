package tools.screeneditor.graphics.validtypes;

import tools.screeneditor.MenuPropiedades;

public class LongMenuType implements MPBehaviorInterface{

    /** Creates a new instance of StringMenuType */
    private MenuPropiedades w = null;
    public LongMenuType() {

    }

    public void setMenuPropiedades(MenuPropiedades m)
    {
      w=m;
    }

    public String setComponentOnTable(Object o, int renglon)
    {
        return o.toString();
    }

    public Object[] getArguments(int row, Object o)
    {
        Long es = new Long(w.getTable().getValueAt(row,1).toString());
        Object[] arg = {es};
        return arg;
    }

    public Object getValidType(Object o)
    {
        return o;
    }

}