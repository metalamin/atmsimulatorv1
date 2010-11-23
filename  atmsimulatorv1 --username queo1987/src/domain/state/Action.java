package domain.state;

import util.GeneralException;

/**
 * Interfaz para las acciones.
 * Las clases que implementen esta interfaz deben proveer, en el método
 * provisto, una reacción asociada al evento disparado.
 * @see Trigger, Event
 */
public interface Action
{
    /**
     * Reacción asociada al evento.
     */
    public void update(Event event) throws GeneralException;
}
