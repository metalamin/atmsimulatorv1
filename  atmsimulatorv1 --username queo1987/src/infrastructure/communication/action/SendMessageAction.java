package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import config.GlobalConfig;
import util.GeneralException;
import infrastructure.dataaccess.InvalidIDException;
import infrastructure.services.comm.message.Message;

/**
 * Esta accion envia el mensaje del contexto
 * por el canal de comunicaciones.
 */
public class SendMessageAction implements Action
{
    private String channelName;
    private String msgId;
    
    public SendMessageAction()
    {
    }
    
    public SendMessageAction(String channelName, String msgId)
    {
        setChannelName(channelName);
        setMsgId(msgId);
    }
    
    public void update(Event ev) throws GeneralException
    {
        try
        {
            Message message = (Message)GlobalConfig.getInstance().getProperty(getMsgId());
            CommunicationHandlerFactory.getCommunicationHandler().send(
                    getChannelName(), message);
        }
        catch (NullPointerException nex)
        {
            throw new InvalidIDException("No pudo obtenerse el mensaje del contexto");
        }
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public String toString()
    {
        return "Send message (" + getChannelName() + ", " + getMsgId() + ")";
    }
}
