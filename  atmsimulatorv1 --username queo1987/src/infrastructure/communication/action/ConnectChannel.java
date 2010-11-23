package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import util.GeneralException;



/**
 * Action para conectarse con el servicio
 * de comunicaciones, a un canal específico
 */
public class ConnectChannel implements Action
{
    private String channelName;

    public ConnectChannel(){
    }
    
    public ConnectChannel(String channelName){
        setChannelName(channelName);
    }
    
    public void update(Event ev) throws GeneralException
    {
        CommunicationHandlerFactory.getCommunicationHandler().connect(getChannelName());
    }
    
    public String toString()
    {
        return "Connect (" + getChannelName() + ")";
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
