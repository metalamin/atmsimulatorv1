package domain.state;

import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import domain.state.InvalidTriggerException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import domain.implementation.EventImpl;

/**
 * Esta clase encapsula el comportamiento de los estados para las
 * máquinas de estado.
 * Tiene asociados una serie de Triggers definibles, mas dos predeterminados, 
 * los cuales, a su vez, definen las acciones a ejecutar para los eventos.
 * Los Triggers predefinidos son los de inicializacion y finalizacion del
 * estado, y son alcanzables a traves de las constantes presentes en 
 * StateConstants.
 *
 * @see Trigger, Action, Event, StateConstants
 */
class State 
{
    /** 
     * Trigger de inicializacion.
     * Es disparado al entrar al estado.
     */
    private Trigger inicializacion;

    /**
     * Trigger de finalizacion.
     * Es disparado al abandonar el estado.
     */
    private Trigger finalizacion;

    /**
     * Mapping: type->(Map thrower->Trigger)
     * La busqueda de los triggers se hace, en primer instancia por tipo,
     * y luego por thrower (en el mapping asociado al tipo).
     */
    private Map hashportipo;

    /**
     * Nombre del estado.
     */
    private String name;

    /**
     * Crea un estado con el nombre indicado, agregandole los Trigger
     * predefinidos.
     */
    public State(String nombre)
    {
        name           = nombre; 
        inicializacion = new TriggerImpl(StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER);
        finalizacion   = new TriggerImpl(StateConstants.END_TYPE, StateConstants.END_THROWER);
        hashportipo    = new LinkedHashMap();
    }
    
    /**
     * Agrega un Trigger al estado.
     * @throws InvalidTriggerException si ya se encuentra un Trigger asociado
     * al mismo Type y Thrower
     */
    public void addTrigger(Trigger trigger) throws InvalidTriggerException
    {
        /**
         * Si el Trigger es el de inicializacion o el de
         * finalizacion, se sobreescribe. Estos 2 son los unicos
         * casos permitidos, ya que son los unicos que vienen
         * "de fabrica".
         */
        if (trigger.getThrower().equals(StateConstants.STARTUP_THROWER) &&
                trigger.getType().equals(StateConstants.STARTUP_TYPE))
        {
            inicializacion = trigger;
        }
        else if (trigger.getThrower().equals(StateConstants.END_THROWER) &&
                trigger.getType().equals(StateConstants.END_TYPE))
        {
            finalizacion = trigger;
        }
        else
        {

            Map aux; 
            //La primer indireccion es por tipo...
            if (!hashportipo.containsKey(trigger.getType()))
            {
                aux = new LinkedHashMap();
                hashportipo.put(trigger.getType(), aux);
            }
            else
            {
                aux = (Map)hashportipo.get(trigger.getType());
            }

            if (aux.containsKey(trigger.getThrower()))
            {
                throw new InvalidTriggerException("No puede agregarse nuevamente", trigger.getType(), trigger.getThrower(), getName());
            }

            //A ese Map, se le agrega por thrower.
            aux.put(trigger.getThrower(), trigger);
        }
        
        //Seteo del estado para que funcione...
        trigger.setState(getName());
    }


    /**
     * Devuelve el Trigger aosicado al Type y Thrower indicado, o null si no
     * se asocio ninguno.
     */
    private Trigger getTrigger(String type, String thrower)
    {
        if (!hashportipo.containsKey(type))
        {
            return null;
        }
        Map aux = (Map)hashportipo.get(type);

        if (!aux.containsKey(thrower))
        {
            return null;
        }

        Trigger tr = (Trigger)aux.get(thrower);
        return tr;
    }

    /**
     * Agrega un Action, en el Trigger indicado por thrower y type, en el lugar
     * place.
     * place es el lugar donde se ejcutará el Action. Este indicador no tiene
     * poque permanecer constante, es simplemente un indicativo al momento de
     * agregar el Action. Por ejemplo, si se ejecuta la siguiente secuencia:
     *      addAction("type", "thrower", 1, action1);
     *      addAction("type", "thrower", 4, action2);
     *      addAction("type", "thrower", 3, action3);
     * el orden de ejecucion sera: action1, action3, action2.
     */
    public void addAction(String type, String thrower, int place, Action action) throws InvalidTriggerException
    {
        Trigger tr = getTrigger(type, thrower);

        if (tr == null)
        {
            //Me fijo, por las dudas, si no es el de startup o el de end...
            if ((type.equals(inicializacion.getType())) 
                && (thrower.equals(inicializacion.getThrower())))
            {
                tr = inicializacion;
            }
            else if ((type.equals(finalizacion.getType())) 
                && (thrower.equals(finalizacion.getThrower())))
            {
                tr = finalizacion;
            }
            else
            {
                throw new InvalidTriggerException("No se encuentra agregado.", type, thrower, getName());
            }
        }
        //Finalmente, se le agrega al Trigger...
        tr.addAction(place, action);
    }
    
    /**
     * Devuelve el nombre del estado.
     */
    public String getName()
    {
        return name;    
    }

    /**
     * Inicializacion del estado.
     * Dispara el trigger de inicializacion, pasando null como evento asociado.
     */
    public void startup() throws GeneralException
    {
        /*
         * TODO Ver con Albert, el tema es que preciso el contexto inicializado
         * en todos los triggers para usarlo como area de datos compartida
         */
        Event ev = new EventImpl(StateConstants.STARTUP_TYPE,
                                 StateConstants.STARTUP_THROWER);
        inicializacion.update(ev);
    }

    /**
     * Finalizacon del estado.
     * Dispara el trigger de finalizacion, pasando null como evento asociado.
     */
    public void end() throws GeneralException
    {
        /*
         * TODO Ver con Albert, el tema es que preciso el contexto inicializado
         * en todos los triggers para usarlo como area de datos compartida
         */
        Event ev = new EventImpl(StateConstants.STARTUP_TYPE,
                                 StateConstants.STARTUP_THROWER);
        finalizacion.update(ev);
    }

    /**
     * Actualizacion del estado.
     * Segun el tipo y disparador del evento, se disparara el Trigger
     * asociado.
     * @throws InvalidTriggerException si no se encuentra asociado ningun
     * Trigger.
     */
    public void update(Event ev) throws InvalidTriggerException, GeneralException
    {
        Trigger tr = getTrigger(ev.getType(), ev.getThrower());

        if (tr == null)
        {
            throw new InvalidTriggerException("No se encuentra agregado.", ev.getType(), ev.getThrower(), getName());
        }
        tr.update(ev);
    }

    /**
     * Devuelve una representacion en formato JavaBean del estado.
     */
    public StateBean getBean()
    {
        StateBean st = new StateBean();
        st.setName(getName());
        st.setStartupTrigger((TriggerBean)BeanerFactory.getBeaner(inicializacion).toBean(inicializacion));
        st.setEndTrigger((TriggerBean)BeanerFactory.getBeaner(finalizacion).toBean(finalizacion));
        Vector triggers = new Vector();
        Iterator itr = hashportipo.values().iterator();
        while (itr.hasNext())
        {
            Map mp = (Map)itr.next();
            Iterator itr_trig = mp.values().iterator();
            while (itr_trig.hasNext())
            {
                Trigger trg = (Trigger)itr_trig.next();
                triggers.add(BeanerFactory.getBeaner(trg).toBean(trg));
            }
        }
        st.setTriggers(triggers);
        return st;
    }
}