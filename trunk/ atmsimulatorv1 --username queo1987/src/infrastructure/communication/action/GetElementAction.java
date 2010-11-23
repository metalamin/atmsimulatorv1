package infrastructure.communication.action;

import config.GlobalConfig;
import domain.state.Action;
import domain.state.Event;
import util.GeneralException;
import infrastructure.dataaccess.InvalidIDException;
import infrastructure.services.comm.message.Message;

/**
 * Action para devolver un elemento del mensaje
 * del contexto.
 */
public class GetElementAction implements Action {
    private Object key;
    private Object element;
    private String msgId; //input or output message
    
    public GetElementAction() {
    }
    
    public GetElementAction(String msgId, Object key) {
        this.setMsgId(msgId);
        this.setKey(key);
    }
    
    public void update(Event ev) throws GeneralException {
        try {
            element = ((Message)GlobalConfig.getInstance().getProperty(getMsgId())).getElement(getKey());
        } catch (NullPointerException nex) {
            throw new InvalidIDException("No pudo obtenerse el elemento " + getKey() +
                    " del mensaje");
        }
        // Persist element in event's context for further actions to be executed
        ev.getContext().put(getKey(), element);
    }
    
    public Object getKey() {
        return key;
    }
    
    public void setKey(Object key) {
        this.key = key;
    }
    
    public Object getElement() {
        return element;
    }
    
    public void setElement(Object element) {
        this.element = element;
    }
    
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public String toString()
    {
        return "Get element(" + getMsgId() + ", " + getKey() + ")";
    }
    
}
