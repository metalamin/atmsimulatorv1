package tools.stateeditor.objects;

import infrastructure.dataaccess.InvalidIDException;
import java.util.Set;
import util.Creator;

/**
 * Interfaz que cumple el creador de componentes.
 */
public interface ObjectCreatorHandler
{
    /**
     * Agrega el ComponentCreator.
     */
    public void addCreator(String type, String name, Creator cc);
    
    /**
     * Dado un tipo de componente, y un nombre, crea
     * un componente para su representacion.
     */
    public Object create(String type, String name) throws InvalidIDException;
    
    /**
     * Devuelve los tipos accesibles.
     */
    public Set getAvailableTypes();
    
    /**
     * Devuelve los nombres accesibles para un
     * determinado tipo.
     */
    public Set getAvailableNames(String tipe);
}
