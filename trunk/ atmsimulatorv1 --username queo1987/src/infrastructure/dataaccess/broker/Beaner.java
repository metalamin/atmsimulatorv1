package infrastructure.dataaccess.broker;

import util.GeneralException;

/**
 * "Fabrica de Beans"...
 * Las clases que implementen esta interfaz deben proveer
 * los beans a partir de los objetos, y viceversa.
 */
public interface Beaner 
{
    /**
     * Dado un objeto, lo devuelve en forma de Bean
     */
    public Object toBean(Object entry);
    
    /**
     * Dado un objeto en forma de Bean, lo devuelve
     * en forma del objeto original.
     */
    public Object fromBean(Object bean) throws GeneralException;
}
