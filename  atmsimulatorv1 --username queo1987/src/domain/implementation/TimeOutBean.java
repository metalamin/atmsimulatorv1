package domain.implementation;

import java.util.Vector;
import domain.state.TriggerBean;

public class TimeOutBean extends TriggerBean
{
    // El tiempo, expresado en unidades...
    private long waitTime;
    
    // Las unidades de conversion...
    private int conversionUnits;
    
    // Triggers de stop
    private Vector stopTriggers;
    
    // Triggers de reset
    private Vector resetTriggers;
    
    public TimeOutBean()
    {
        super();
        stopTriggers = new Vector();
        resetTriggers = new Vector();
    }
    
    public Long getWaitTime()
    {
        return new Long(waitTime);
    }
    
    public void setWaitTime(Long waitTime)
    {
        this.waitTime = waitTime.longValue();
    }
    
    public Integer getConversionUnits()
    {
        return new Integer(conversionUnits);
    }
    
    public void setConversionUnits(Integer conversionUnits)
    {
        this.conversionUnits = conversionUnits.intValue();
    }
    
    public Vector getStopTriggers()
    {
        return stopTriggers;
    }
    
    public void setStopTriggers(Vector stopTriggers)
    {
        this.stopTriggers = stopTriggers;
    }
    
    public Vector getResetTriggers()
    {
        return resetTriggers;
    }
    
    public void setResetTriggers(Vector resetTriggers)
    {
        this.resetTriggers = resetTriggers;
    }
}
