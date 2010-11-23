package domain.state;

import infrastructure.dataaccess.broker.Beaner;
import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import domain.state.InvalidStateException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Clase que sabe como guardar y
 * levantar tooodos los estados 
 * de la maquina.
 */
public class StateHandlerBeaner implements Beaner
{
    //Map State->Trigger a agregar.
    private Map invalidTriggers;
    
    public Object toBean(Object obj)
    {
        StateHandlerImpl sti = (StateHandlerImpl)obj;
        StateHandlerBean shb = sti.getBean();
        return shb;
    }
    
    public Object fromBean(Object obj) throws GeneralException
    {
        invalidTriggers = new LinkedHashMap();
        StateHandler sh = StateHandlerFactory.getStateHandler();
        sh.clean();
        StateHandlerBean shb = (StateHandlerBean)obj;
        Collection ests = shb.getStates();
        /**
         * Se agregan todos los estados
         */
        Iterator itr = ests.iterator();
        while (itr.hasNext())
        {
            StateBean sb = (StateBean)itr.next();
            sh.addState(sb.getName());
        }

        /**
         * Ahora, para cada estado, se van a agregar
         * los Trigger
         */
        itr = ests.iterator();
        while (itr.hasNext())
        {
            StateBean sb = (StateBean)itr.next();
            /**
             * Se resetean los triggers.
             */
            Iterator it_tris = sb.getTriggers().iterator();
            while (it_tris.hasNext())
            {
                TriggerBean nxt = (TriggerBean)it_tris.next();
                //Se setea el estado, para asegurarse.
                nxt.setState(sb.getName());
                try
                {
                    Trigger tri = (Trigger)BeanerFactory.getBeaner(nxt).fromBean(nxt);
                    sh.addTrigger(sb.getName(), tri);
                }
                catch (InvalidStateException iex)
                {
                    invalidTriggers.put(sb.getName(), nxt);
                }
            }
            /**
             * Ahora, se resetean los trigger de inicio
             * y fin.
             */
            TriggerBean ini = (TriggerBean)sb.getStartupTrigger();
            //Para agregarlo al final
            int the_end = ini.getActions().size()+1;
            Iterator it_act = ini.getActions().iterator();
            while (it_act.hasNext())
            {
                Object nxt = it_act.next();
                sh.addAction(sb.getName(), ini.getType(), ini.getThrower(), the_end, (Action)BeanerFactory.getBeaner(nxt).fromBean(nxt)); 
            }

            TriggerBean fin = (TriggerBean)sb.getEndTrigger();
            the_end = fin.getActions().size()+1;
            it_act = fin.getActions().iterator();
            while (it_act.hasNext())
            {
                Object nxt = it_act.next();
                sh.addAction(sb.getName(), fin.getType(), fin.getThrower(), the_end, (Action)BeanerFactory.getBeaner(nxt).fromBean(nxt)); 
            }
        }
        
        /**
         * Se agregan los Trigger problematicos
         */
        itr = invalidTriggers.keySet().iterator();
        while(itr.hasNext())
        {
            String nombre = (String)itr.next();
            Object nxt = invalidTriggers.get(nombre);
            Trigger tri = (Trigger)BeanerFactory.getBeaner(nxt).fromBean(nxt);
            sh.addTrigger(nombre, tri);
        }
        return sh;
    }
}
