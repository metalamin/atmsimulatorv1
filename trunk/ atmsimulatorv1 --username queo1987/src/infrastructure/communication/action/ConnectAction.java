package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import util.GeneralException;

/**
 * Action para conectarse con el servicio
 * de comunicaciones.
 */
public class ConnectAction implements Action
{
    public void update(Event ev) throws GeneralException
    {
        CommunicationHandlerFactory.getCommunicationHandler().connect();
    }
    
    public String toString()
    {
        return "Connect";
    }
}
