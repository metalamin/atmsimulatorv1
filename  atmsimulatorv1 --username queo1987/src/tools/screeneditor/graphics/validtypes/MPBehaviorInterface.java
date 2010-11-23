package tools.screeneditor.graphics.validtypes;

import tools.screeneditor.MenuPropiedades;

/*
 *Interfaz que deben implementar todas las clases que aparecen en el menupropiedades
 */
public interface MPBehaviorInterface {

    //permite al objeto tener una referencia la menuPropiedades
    public void setMenuPropiedades(MenuPropiedades m);
    //Setea el objeto en la tabla en el renglon correspondiente. La clase que implemente esta
    //operacion sabe como poner el objeto en la tabla.
    public String setComponentOnTable(Object o, int renglon);
    //Transforma el objeto en la tabla que esta en la fila especificada y lo devuelve para que pueda ser
    //llamado mediante refractor
    public Object[] getArguments(int row, Object o);
    //Devuelve un objeto del type que implementa el objeto que implementa esta interfaz
    public Object getValidType(Object o);

}
