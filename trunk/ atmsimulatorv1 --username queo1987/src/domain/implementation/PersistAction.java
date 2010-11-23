package domain.implementation;

import config.GlobalConfig;
import util.GeneralException;

import domain.state.Action;
import domain.state.Event;

/**
 * This class link the correspondent object of a source key in the event's context
 * to a target key in a persistent context like GlobalConfig.
 */
/**
 * @TODO Sincronizar esta clase con Alberto
 */
public class PersistAction implements Action
{
    private String sourceKey;
    private String targetKey;
    
    public PersistAction()
    {
    }
    
    public PersistAction(String sourceKey, String targetKey)
    {
        this.sourceKey = sourceKey;
        this.targetKey = targetKey;
    }
    
    public void setSourceKey(String sourceKey)
    {
        this.sourceKey = sourceKey;
    }
    
    public String getSourceKey()
    {
        return sourceKey;
    }

    public void setTargetKey(String targetKey)
    {
        this.targetKey = targetKey;
    }
    
    public String getTargetKey()
    {
        return targetKey;
    }
    
    public void update(Event event) throws GeneralException
    {
        // Link sourceKey object to targetKey
        if (targetKey==null){
            targetKey = sourceKey;
        }
        GlobalConfig.getInstance().addProperty(targetKey, event.getContext().get(sourceKey));
    }
    
    public String toString()
    {
        return "Persist (" + getSourceKey() + ", " + getTargetKey() + ")";
    }    
}