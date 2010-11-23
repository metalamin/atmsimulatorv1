package infrastructure.communication;

import infrastructure.dataaccess.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.dataaccess.Connectable;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Configuracion de las comunicaciones.
 */
public final class CommunicationConfig implements Connectable
{
    private static CommunicationConfig inst = null;
    
    private Map config;
    
    private CommunicationConfig() 
    {
        config = new HashMap();
    }
    
    public static CommunicationConfig getInstance()
    {
        if (inst == null)
        {
            inst = new CommunicationConfig();
        }
        return inst;
    }
    
    /**
     * Pasa a trabajar con una nueva conexion...
     */
    public void setConnectionName(String name)
    {
        /**
         * Se saca la info...
         */
        try
        {
            Map aux = PersistentCollectionFactory.getInstance().getPersistentMap(name);
            config = aux;
        }
        catch (PersistenceException gex)
        {
            throw gex;
        }
    }
    
    public void setContext(Map context)
    {
        config.put("CONTEXT", context);
    }
    
    public void setCommFactory(CommFactory cf)
    {
        config.put("COMMFACTORY", cf);
    }
    
    public void setMessageFactory(MessageFactory mf)
    {
        config.put("MESSAGEFACTORY", mf);
    }
    
    public void doConfig(CommunicationHandler ch)
    {
        CommFactory cf = (CommFactory)config.get("COMMFACTORY");
        MessageFactory mf = (MessageFactory)config.get("MESSAGEFACTORY");
        Map context = (Map)config.get("CONTEXT");
        cf.setContext(context);
        
        ch.setCommFactory(cf);
        ch.setMessageFactory(mf);
    }
}
