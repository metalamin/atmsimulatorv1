package infrastructure.communication.action;

import infrastructure.communication.CommunicationConstants;
import infrastructure.dataaccess.broker.BeanerHandlerFactory;
import util.GeneralException;
import infrastructure.dataaccess.InvalidIDException;
import java.util.Iterator;
import domain.state.Action;
import domain.state.Event;
import infrastructure.services.comm.message.Message;
import infrastructure.dataaccess.XMLCollectionReader;

/**
 * Esta accion responde a un comando externo.
 * El comando viene dado en el mensaje de entrada, 
 * en una clave configurable.
 *
 * El formato del comando es un STRING que
 * describe las ACCIONES levantables mediante 
 * lecturas sucesivas a un XMLDecoder.
 */
public class ExternalCommandAction implements Action
{
    private String key;
    
    public ExternalCommandAction()
    {
    }
    
    public ExternalCommandAction(String key)
    {
        setKey(key);
    }
    
    public void update(Event ev) throws GeneralException
    {
        try
        {
            String formattedMessage = (String)((Message)ev.getContext().get(
                    CommunicationConstants.MESSAGE)).getElement(getKey());
            XMLCollectionReader xmlcr = new XMLCollectionReader(formattedMessage);
            Iterator itr = xmlcr.iterator();
            while (itr.hasNext())
            {
                Object nxtAc = itr.next();
                //Se obtiene la accion del beaner
                Action action = (Action)BeanerHandlerFactory.getBeanerHandler().getBeaner(
                        nxtAc).fromBean(nxtAc);
                //Y se ejecuta
                action.update(ev);
            }
        }
        catch (NullPointerException nex)
        {
            throw new InvalidIDException("No pudo obtenerse el elemento " + getKey() +
                    " del mensaje");
        }
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
    
    public String toString()
    {
        return "External command (" + getKey() + ")";
    }
}
