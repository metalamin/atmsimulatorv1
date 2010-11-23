package domain.state;

import util.GeneralException;

/**
 * Interfaz para las acciones.
 * Las clases que implementen esta interfaz deben proveer, en el m�todo
 * provisto, una reacci�n asociada al evento disparado.
 * @see Trigger, Event
 */
public interface Action
{
    /**
     * Reacci�n asociada al evento.
     */
    public void update(Event event) throws GeneralException;
}
