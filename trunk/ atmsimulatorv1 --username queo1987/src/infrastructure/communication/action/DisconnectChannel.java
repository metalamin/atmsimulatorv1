package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import util.GeneralException;


/**
 * Action para desconectarse del servicio
 * de comunicaciones, a un canal específico
 */
public class DisconnectChannel implements Action
{
    private String channelName;

    public DisconnectChannel(){
    }    
    
    public DisconnectChannel(String channelName){
        setChannelName(channelName);
    }
    
    public void update(Event ev) throws GeneralException
    {
        CommunicationHandlerFactory.getCommunicationHandler().disconnect(getChannelName());
    }
    
    public String toString()
    {
        return "Disconnect (" + getChannelName() + ")";
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }
}
