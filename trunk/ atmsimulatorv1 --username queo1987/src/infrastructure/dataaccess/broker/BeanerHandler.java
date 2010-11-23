package infrastructure.dataaccess.broker;

import util.GeneralException;

/**
 * Interfaz que cumple el BeanerHandler.
 */
public interface BeanerHandler
{
    /**
     * Devuelve el Beaner asociado a un objeto.
     * Si no lo encuentra, devuelve una 
     * instancia de DefaultBeaner.
     */
    public Beaner getBeaner(Object name);

    /**
     * Agrega un Beaner asociado a determinado nombre de clase.
     */
    public void addBeaner(String class_name, Beaner beaner) throws GeneralException;
}
