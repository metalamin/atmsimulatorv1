package domain.implementation;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import java.util.Iterator;
import java.util.Vector;
import domain.state.Action;

/**
 * Beaner para guardar
 * los TimeOut.
 */
public class TimeOutBeaner implements Beaner
{
    public Object toBean(Object obj)
    {
        TimeOut t_o = (TimeOut)obj;
        TimeOutBean t_o_b = t_o.getBean();
        return t_o_b;
    }
    
    public Object fromBean(Object obj) throws GeneralException
    {
        TimeOutBean t_o_b = (TimeOutBean)obj;
        TimeOut t_o = new TimeOut(t_o_b.getType(), t_o_b.getThrower(), t_o_b.getWaitTime(), t_o_b.getConversionUnits());
        
        t_o.setState(t_o_b.getState());
        
        Vector vec = t_o_b.getActions();
        Iterator itr = vec.iterator();
        while (itr.hasNext())
        {
            Object n_b = itr.next();
            Action act = (Action)BeanerFactory.getBeaner(n_b).fromBean(n_b);
            t_o.addAction(act);
        }
        
        Vector res = t_o_b.getResetTriggers();
        itr = res.iterator();
        while (itr.hasNext())
        {
            TriggerValues tv = (TriggerValues)itr.next();
            t_o.addResetTrigger(tv.getType(), tv.getThrower());
        }

        Vector stp = t_o_b.getStopTriggers();
        itr = stp.iterator();
        while (itr.hasNext())
        {
            TriggerValues tv = (TriggerValues)itr.next();
            t_o.addStopTrigger(tv.getType(), tv.getThrower());
        }
        
        return t_o;
    }
}
