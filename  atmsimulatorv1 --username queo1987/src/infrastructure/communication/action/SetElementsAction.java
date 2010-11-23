package infrastructure.communication.action;

import config.GlobalConfig;
import util.GeneralException;
import java.util.HashMap;
import domain.state.Action;
import domain.state.Event;
import java.util.ArrayList;
import infrastructure.services.comm.message.Message;
/**
 * Action para setear un vector de elementos dado
 * en el mensaje de salida.
 */
public class SetElementsAction implements Action
{
    public static String KEYS = "SET_ELEMENTS_ACTION.KEYS";
    public static String ELEMENTS = "SET_ELEMENTS_ACTION.ELEMENTS";
            
    private String msgId;
    private ArrayList keys;
    private HashMap elements;
    
    public SetElementsAction()
    {
    }
    
    public SetElementsAction(String msgId, ArrayList keys, HashMap elements) 
    {
        setMsgId(msgId);
        this.keys = keys;
        this.elements = elements;
    }
    
    public void update(Event ev) throws GeneralException
    {
        Message msg=null;
        msg =  (Message)GlobalConfig.getInstance().getProperty(getMsgId());
        if (msg != null){
            // tiene preferencia la informacion que venga en el evento
            if (ev.getContext().get(KEYS)!=null && ev.getContext().get(ELEMENTS)!=null ){
                this.keys = (ArrayList)ev.getContext().get(KEYS);
                this.elements = (HashMap)ev.getContext().get(ELEMENTS);            
            }

            if (keys!=null && elements!=null){
                int i=0;
                while (i < keys.size()){
                    String key = (String)keys.get(i);
                    String element = (String)elements.get(key);
                }
            }
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public String toString()
    {
        return "Set elements (" + getMsgId() + ")";
    }
}
