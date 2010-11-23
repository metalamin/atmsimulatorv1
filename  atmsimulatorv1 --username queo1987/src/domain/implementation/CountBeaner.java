package domain.implementation;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import java.util.Iterator;
import java.util.Vector;
import domain.state.Action;

/**
 * Beaner para guardar
 * los CountTrigger.
 */
public class CountBeaner implements Beaner
{
    public Object toBean(Object obj)
    {
        CountTrigger t_o = (CountTrigger)obj;
        CountBean t_o_b = t_o.getBean();
        return t_o_b;
    }
    
    public Object fromBean(Object obj) throws GeneralException
    {
        CountBean t_o_b = (CountBean)obj;
        CountTrigger t_o = new CountTrigger(t_o_b.getType(), t_o_b.getThrower(), t_o_b.getCount());
        
        t_o.setState(t_o_b.getState());
        
        Vector vec = t_o_b.getActions();
        Iterator itr = vec.iterator();
        while (itr.hasNext())
        {
            Object n_b = itr.next();
            Action act = (Action)BeanerFactory.getBeaner(n_b).fromBean(n_b);
            t_o.addAction(act);
        }
        
        Vector res = t_o_b.getCountTriggers();
        itr = res.iterator();
        while (itr.hasNext())
        {
            TriggerValues tv = (TriggerValues)itr.next();
            t_o.addCountTrigger(tv.getType(), tv.getThrower());
        }

        return t_o;
    }
}
