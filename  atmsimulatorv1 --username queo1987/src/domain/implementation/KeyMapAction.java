package domain.implementation;

import util.GeneralException;
import domain.state.StateHandlerFactory;

import domain.state.Action;
import domain.state.Event;

/**
 * This class link the correspondent object of a source key to a target key.
 */
/**
 * @TODO Sincronizar esta clase con Alberto
 */
public class KeyMapAction implements Action
{
    private String sourceKey;
    private String targetKey;
    
    public KeyMapAction()
    {
    }
    
    public KeyMapAction(String sourceKey, String targetKey)
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
        event.getContext().put(targetKey, event.getContext().get(sourceKey));
    }
    
    public String toString()
    {
        return "Key Map (" + getSourceKey() + ", " + getTargetKey() + ")";
    }    
}