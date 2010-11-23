package domain.state;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import java.util.Iterator;
import java.util.Vector;
import domain.implementation.TimeOutBean;

/**
 * Beaner para los trigger por defecto.
 */
public class TriggerBeaner implements Beaner
{
    public Object toBean(Object obj)
    {
        TriggerImpl t_o = (TriggerImpl)obj;
        TriggerBean t_o_b = t_o.getBean();
        return t_o_b;
    }
    
    public Object fromBean(Object obj) throws GeneralException
    {
        TriggerBean t_o_b = (TriggerBean)obj;
        TriggerImpl t_o = new TriggerImpl(t_o_b.getType(), t_o_b.getThrower());
        t_o.setState(t_o_b.getState());
        
        Vector vec = t_o_b.getActions();
        Iterator itr = vec.iterator();
        while (itr.hasNext())
        {
            Object n_b = itr.next();
            Action act = (Action)BeanerFactory.getBeaner(n_b).fromBean(n_b);
            t_o.addAction(act);
        }
        return t_o;
    }
}
