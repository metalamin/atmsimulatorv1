package domain.state;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import java.util.Iterator;
import java.util.Vector;

/**
 * Beaner para los estados.
 * @see Beaner
 */
public class StateBeaner implements Beaner
{
    public Object toBean(Object obj)
    {
        State st = (State)obj;
        StateBean st_b = st.getBean();
        return st_b;
    }
    
    public Object fromBean(Object bean)
    {
        /**
         * Esto lo maneja el StateHandlerBeaner.
         */
        return bean;
    }
}
