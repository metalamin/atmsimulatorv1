package domain.implementation;

import domain.state.Action;
import domain.state.Event;

/**
 * Clase que para un TimeOut en
 * particular.
 */
class Stop implements Action
{
    // El TimeOut que hay que resetear
    private TimeOut to_stop;

    public Stop(TimeOut to)
    {
        to_stop = to;
    }

    /**
     * Esta reaccion simplemente resetea
     * el timeout...
     */
    public void update(Event event)
    {
        to_stop.stop();
    }
}