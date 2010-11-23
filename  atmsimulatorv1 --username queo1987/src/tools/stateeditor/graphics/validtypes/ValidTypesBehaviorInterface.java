package tools.stateeditor.graphics.validtypes;

import javax.swing.JFrame;

/*
 *Interfaz que deben implementar todas las clases que aparecen en el menupropiedades
 */
public interface ValidTypesBehaviorInterface {

    //Setea el objeto en la tabla en el renglon correspondiente. La clase que implemente esta
    //operacion sabe como poner el objeto en la tabla.
    public String setComponentOnTable(Object o, String s, int renglon); 
}
