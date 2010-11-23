package infrastructure.communication.action;

import config.GlobalConfig;
import domain.state.Action;
import domain.state.Event;
import util.GeneralException;
import infrastructure.dataaccess.InvalidIDException;
import infrastructure.services.comm.message.Message;

/**
 * Action para setear un elemento dado
 * en el mensaje de salida.
 */
/**
 * @TODO Sincronizar con Alberto
 */  
public class SetElementAction implements Action
{
    private Object key;
    private Object element;
    private String msgId;
    private Object defaultValue;
    
    public SetElementAction()
    {
    }
    
    public SetElementAction(String msgId, Object key, Object element)
    {
        this.setMsgId(msgId);
        this.setKey(key);
        this.setElement(element);
    }
    
    public SetElementAction(String msgId, Object key, Object element, Object defaultValue) 
    {
        this.setMsgId(msgId);
        this.setKey(key);
        this.setElement(element);
        this.setDefaultValue(defaultValue);
    }        
    
    public void update(Event ev) throws GeneralException
    {
        // if element is not specified, search it in the event's context
        if (key!=null && ev.getContext().get(key)!=null){
            element = ev.getContext().get(key);
        }
        element = (element==null) ? defaultValue : element;

        try
        {
            if (element != null){            
                ((Message)GlobalConfig.getInstance().getProperty(getMsgId())).setElement(
                        getKey(), getElement());
            }
        }
        catch (NullPointerException nex)
        {
            throw new InvalidIDException("No pudo setearse el elemento " + getElement() +
                    " de clave " + getKey() + " en el mensaje");
        }
        //element = null;
    }
    
    public Object getKey()
    {
        return key;
    }
    
    public void setKey(Object key)
    {
        this.key = key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
    
    public Object getElement()
    {
        return element;
    }
    
    public void setElement(Object element)
    {
        this.element = element;
    }
    
    public void setElement(String element)
    {
        this.element = element;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public String toString()
    {
        return "Set Element (" + getMsgId() + ", " + getKey() + ")";
    }
    
}
