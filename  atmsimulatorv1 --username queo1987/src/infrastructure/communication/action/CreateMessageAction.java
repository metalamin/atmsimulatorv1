package infrastructure.communication.action;

import infrastructure.communication.CommunicationHandlerFactory;
import config.GlobalConfig;
import util.GeneralException;
import domain.state.Action;
import domain.state.Event;
import infrastructure.services.comm.message.Message;

/**
 * Esta accion crea un mensaje de salida y lo setea en
 * el contexto.
 */
public class CreateMessageAction implements Action
{
    private String msgId;
    
    public CreateMessageAction(){};
    
    public CreateMessageAction(String msgId)
    {
        setMsgId(msgId);
    }
    
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
        return "Create message (" + getMsgId() + ")";
    }
}
