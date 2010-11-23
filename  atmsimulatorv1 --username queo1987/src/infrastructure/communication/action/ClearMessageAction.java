package infrastructure.communication.action;

import domain.state.Action;
import domain.state.Event;
import infrastructure.communication.CommunicationHandlerFactory;
import config.GlobalConfig;
import util.GeneralException;
import infrastructure.services.comm.message.Message;

/**
 * This action resets outgoing message
 */
public class ClearMessageAction implements Action
{
    private String msgId;
    
    public ClearMessageAction(){};

    public ClearMessageAction(String msgId)
    {
        setMsgId(msgId);
    };
    
    public void update(Event ev) throws GeneralException
    {
        Message mssg = CommunicationHandlerFactory.getCommunicationHandler().createMessage();
        GlobalConfig.getInstance().addProperty(getMsgId(), mssg);
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public String toString()
    {
        return "Clear message (" + getMsgId() + ")";
    }
}
