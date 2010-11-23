package tools.stateeditor.beans;

import java.util.Iterator;
import domain.state.StateBean;
import domain.state.StateConstants;
import domain.state.StateHandlerBean;
import domain.state.TriggerBean;

/**
 * Utilidades sobre los Beans
 * definidos.
 */
public class BeanUtils
{
    /**
     * Devuelve el statebean de nombre indicado.
     */
    public static StateBean getStateBean(StateHandlerBean shb, String state)
    {
        boolean cond = false;
        StateBean sb = null;
        Iterator itr = shb.getStates().iterator();
        while (itr.hasNext() && !cond)
        {
            sb = (StateBean)itr.next();
            if (sb.getName().equals(state))
            {
                cond = true;
            }
        }
        StateBean ret = null;
        if (cond)
        {
            ret = sb;
        }
        return ret;
    }

    /**
     * Devuelve el TriggerBean indicado.
     */
    public static TriggerBean getTriggerBean(StateBean sb, String type, String thrower)
    {
        TriggerBean res = null;
        if (type.equals(StateConstants.STARTUP_TYPE) && thrower.equals(StateConstants.STARTUP_THROWER))
        {
            res = (TriggerBean)sb.getStartupTrigger();
        }
        else if (type.equals(StateConstants.END_TYPE) && thrower.equals(StateConstants.END_THROWER))
        {
            res = (TriggerBean)sb.getEndTrigger();
        }
        else
        {
            boolean cond = false;
            TriggerBean tb = null;
            Iterator itr = sb.getTriggers().iterator();
            while (!cond && itr.hasNext())
            {
                tb = (TriggerBean)itr.next();
                cond =  (type.equals(tb.getType()) && thrower.equals(tb.getThrower()));
            }
            if (cond)
            {
                res = tb;
            }
        }
        return res;            
    }
}
