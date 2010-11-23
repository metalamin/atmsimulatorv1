package domain.implementation;

import domain.state.Action;
import domain.state.Event;

/**
 * Clase que resetea un TimeOut en
 * particular.
 */
class Reset implements Action
{
    // El TimeOut que hay que resetear
    private TimeOut to_reset;

    public Reset(TimeOut to)
    {
        to_reset = to;
    }

    /**
     * Esta reaccion simplemente resetea
     * el timeout...
     */
    public void update(Event event)
    {
        to_reset.reset();
    }
}
