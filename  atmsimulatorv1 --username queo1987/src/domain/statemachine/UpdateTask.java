package domain.statemachine;

import util.GeneralException;
import domain.state.Event;
import domain.state.StateHandlerFactory;

/**
 * Clase que representa el Task
 * de update.
 */
class UpdateTask implements Task
{
    private Event ev;

    public UpdateTask(Event evnt)
    {
        ev = evnt;
    }

    public void run() throws GeneralException
    {
        StateHandlerFactory.getStateHandler().update(ev);
    }	
}
