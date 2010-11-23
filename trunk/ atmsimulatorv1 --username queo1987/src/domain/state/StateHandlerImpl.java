package domain.state;

import infrastructure.dataaccess.broker.BrokerFactory;
import infrastructure.dataaccess.DataAccessException;
import util.GeneralException;
import domain.state.InvalidStateException;
import domain.state.InvalidTriggerException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Implementacion del handler de estados.
 * Supone que hasta aca llega todo en su
 * orden y armoniosamente (como dijo Peron).
 * Singleton por conveniencia.
 *
 * En su funcionamiento, cuenta con un
 * estado global, que tiene, como todos, sus
 * triggers y acciones asociados. Ese estado sera
 * alcanzable desde todos, pero funcionara con
 * "regla de scope": si un estado "local" tiene asociado
 * un trigger con los mismos type y thrower que uno en
 * el estado global, se ejecutaran esos.
 *
 * El funcionamiento es asi:
 *  Startup: StartupGlobal - StartUp elegido.
 *  End: End Elegido - end global.
 *  Update: Update elegido == ERROR -> Update global
 *
 * Este estado sera agregado a la maquina cuando se
 * trate de alcanzar por primera vez.
 */
final class StateHandlerImpl implements StateHandler
{
    private static StateHandlerImpl inst = null;

    // Los estados.
    private Hashtable states;
    
    // El estado actual
    private State actual;

    private StateHandlerImpl()
    {
        states = new Hashtable();
        actual = null;
    }

    public static StateHandlerImpl getInstance()
    {
        if (inst == null)
        {
            inst = new StateHandlerImpl();
        }
        return inst;
    }
    
    private State getState(String state)  throws InvalidStateException
    {
        if (!states.containsKey(state) && state != StateConstants.GLOBAL_STATE)
        {
System.out.println(this + "->" + states.keySet());            
            throw new InvalidStateException("El estado <" + state + "> no se encuentra.");
        }
        /**
         * Se agrega el estado global cuando se trata de alcanzar por primera
         * vez.
         */
        else if (state == StateConstants.GLOBAL_STATE)
        {
            try
            {
                //Se agrega el estado global
                addState(StateConstants.GLOBAL_STATE);
            }
            catch (InvalidStateException iex)
            {
                //Imposible que la tire
            }
        }
        return (State)states.get(state);
    }
    
    /**
     * Agregado de un estado...
     */
    public void addState(String state) throws InvalidStateException
    {
        if (states.containsKey(state))
        {
            throw new InvalidStateException("El estado <" + state + "> ya se encuentra creado.");
        }
        State stat = new State(state);
        states.put(state, stat);
    }
    
    /**
     * Agregado de un Trigger.
     */
    public void addTrigger(String state, Trigger tr) throws InvalidStateException, InvalidTriggerException
    {
        State st = getState(state);
        st.addTrigger(tr);
    }
    
    /**
     * Agregado de acciones.
     */
    public void addAction(String state, String type, String thrower, int place, Action act) throws InvalidStateException, InvalidTriggerException
    {
        State st = getState(state);
        st.addAction(type, thrower, place, act);
    }
    
    /**
     * Seteo del estado actual...
     * Se realizan el inicio y final del
     * estado global.
     */
    public void setState(String st) throws InvalidStateException, GeneralException 
    {
        State global_state = getState(StateConstants.GLOBAL_STATE);
        if (actual != null)
        {
            actual.end();
            if (!global_state.equals(actual))
            {
                // Se finaliza el estado global
                global_state.end();
            }
        }
        actual = getState(st);
        //Se inicializa el estado global
        global_state.startup();
        if (!global_state.equals(actual))
        {
            actual.startup();
        }
    }
    
    /**
     * Ejecucion de un update...
     */
    public void update(Event ev) throws GeneralException 
    {
        if (actual != null)
        {
            try
            {
                actual.update(ev);
            }
            /**
             * Si el trigger es incorrecto,
             * se ejecuta en el estado
             * general.
             */
            catch (InvalidTriggerException itex)
            {
                State global_state = getState(StateConstants.GLOBAL_STATE);
                global_state.update(ev);
            }
        }
        else
        {
            State global_state = getState(StateConstants.GLOBAL_STATE);
            global_state.update(ev);
        }
    }
    
    public Set getStates()
    {
        return states.keySet();
    }
    
    public StateBean getStateBean(String state) throws InvalidStateException
    {
        return getState(state).getBean();
    }
    
    
    public StateHandlerBean getBean()
    {
        StateHandlerBean shb = new StateHandlerBean();
        Vector states_bnrs = new Vector();
        Iterator itr = getStates().iterator();
        while (itr.hasNext())
        {
            try
            {
                states_bnrs.add(getStateBean((String)itr.next()));
            }
            catch (InvalidStateException iex)
            {
                //Imposible que la tire...
            }
        }
        shb.setStates(states_bnrs);
        return shb;
    }
    
    public void save(String connection) throws DataAccessException
    {
        BrokerFactory.getBroker().save(connection, this);
    }
    
    public void clean()
    {
        /**
         * Se deja el handler limpito...
         */
        states = new Hashtable();
        actual = null;
    }
    
    public void load(String connection) throws DataAccessException, GeneralException
    {
        /**
         * Se deja el handler limpito...
         */
        clean();
        /**
         * Como es Singleton, vamo que es tarde.
         */
        BrokerFactory.getBroker().load(connection);
    }
}