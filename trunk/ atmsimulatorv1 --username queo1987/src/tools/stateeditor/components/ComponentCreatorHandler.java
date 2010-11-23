package tools.stateeditor.components;

import infrastructure.dataaccess.InvalidIDException;
import java.awt.Component;
import java.util.Set;

public interface ComponentCreatorHandler
{
    /**
     * Agrega el ComponentCreator, segun el nombre de la clase.
     */
    public void addComponentCreator(String className, ComponentCreator cc);
    
    /**
     * Dado un objeto, devuelve un componente para su representacion.
     * Si no encuentra un ComponentCreator asignado, devuelve un
     * objeto creado con el Creator por defecto.
     */
    public Object createComponent(Object base);
}
