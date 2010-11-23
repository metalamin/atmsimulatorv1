package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import util.GeneralException;

/**
 * Action para desconexion del servicio
 * de comunicaciones.
 */
public class DisconnectAction implements Action
{
    public void update(Event ev) throws GeneralException
    {
        CommunicationHandlerFactory.getCommunicationHandler().disconnect();
    }    
    
    public String toString()
    {
        return "Disconnect";
    }
}