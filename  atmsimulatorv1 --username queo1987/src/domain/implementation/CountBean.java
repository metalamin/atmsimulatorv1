package domain.implementation;

import java.util.Vector;
import domain.state.TriggerBean;

/**
 *
 */
public class CountBean extends TriggerBean
{
    private Vector countTriggers;
    private int count;    
    
    public CountBean()
    {
    }

    public Vector getCountTriggers()
    {
        return countTriggers;
    }

    public void setCountTriggers(Vector countTriggers)
    {
        this.countTriggers = countTriggers;
    }

    public Integer getCount()
    {
        return new Integer(count);
    }

    public void setCount(Integer count)
    {
        this.count = count.intValue();
    }
    
}
