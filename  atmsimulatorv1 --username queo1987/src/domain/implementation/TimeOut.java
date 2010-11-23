package domain.implementation;

import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import domain.state.InvalidStateException;
import domain.state.InvalidTriggerException;
import domain.state.Trigger;
import domain.state.Event;
import domain.state.Action;
import domain.state.StateHandlerFactory;

import domain.statemachine.StateMachineFactory;

import java.util.Timer;
import java.util.Vector;
import java.util.Iterator;


/**
 * Clase que representa un "TimeOut" de
 * X unidades de tiempo.
 * 
 * Provee las utilidades de "start" y "reset".
 */
public class TimeOut implements Trigger
{
    // El type...
    private String tipo;
    
    // Thrower...
    private String thrwr;
    
    // State
    private String estado;
    
    // Acciones...
    private Vector actions;
    
    // El tiempo, expresado en unidades...
    private long tiempo_espera;

    // Las unidades de conversion...
    private int conversion_unit;

    // El Timer de ejecucion...
    private Timer timer;

    // Y la tarea a ejecutar...
    private DoTimeOutTimerTask task;
    
    // Los trigger de reset...
    private Vector reset_triggers;
    
    // Los trigger de stop...
    private Vector stop_triggers;

    public TimeOut(String type, String thrower, long milliseconds)
    {
        this(type, thrower, milliseconds, 1);
    }

    public TimeOut(String type, String thrower, long time, int conversion)
    {
        tipo  = type;
        thrwr = thrower;
        tiempo_espera  = time;
        conversion_unit = conversion;
        actions = new Vector();
        timer = new Timer();
        task  = new DoTimeOutTimerTask(this);
        reset_triggers = new Vector();
        stop_triggers = new Vector();
    }
    
    /**
     * Agrega una accion en la posicion
     * por defecto.
     */
    public void addAction(Action action)
    {
        addAction(actions.size(), action);
    }
    
    /**
     * Agrega una accion el la posicion adecuada.
     */
    public void addAction(int posicion, Action action)
    {
        if (posicion > actions.size())
        {
            actions.add(action);
            return;
        }
        actions.add(posicion, action);
    }
    
    /**
     * Disparada del evento.
     * Recorre las acciones segun el orden
     * y dispara el evento.
     */
    public void update(Event ev) throws GeneralException
    {
        Iterator itr = actions.iterator();
        while (itr.hasNext())
        {
            Action act = (Action)itr.next();
            act.update(ev);
        }
    }
    
    /**
     * Devuelve el tipo al que responde este
     * Trigger.
     */
    public String getType()
    {
        return tipo;
    }
    
    /**
     * Devuelve el thrower al que responde
     * este Trigger.
     */
    public String getThrower()
    {
        return thrwr;
    }
    
    /**
     * Setea el nombre del estado
     * donde esta este Trigger.
     */
    public void setState(String state)
    {
        estado = state;
    }
    
    private String getState()
    {
        return estado;
    }

    /**
     * Metodo llamado cuando se termina el
     * tiempo de espera.
     */
    protected void forceUpdate()
    {
        try
        {
            EventImpl event = new EventImpl(this.getType(), this.getThrower());

            //Se llama al StateMachine para que corra sincronizado...
            StateMachineFactory.getStateMachine().update(event);
            //Termino, entonces, cancelo...
            task.cancel();
        }
        catch (GeneralException ex)
        {
            //No llega...
        }
    }

    /**
     * Se re-setea el timer.
     * Este metodo debe llamarse tambien
     * para que arranque.
     */
    public void reset()
    {
        task.cancel();
        task = new DoTimeOutTimerTask(this);
        timer.schedule(task, tiempo_espera*conversion_unit);
    }

    /**
     * Se frena el timer.
     */
    public void stop()
    {
        task.cancel();
    }

    /**
     * Agregado de disparadores.
     * Todos estos son los Trigger
     * que van a resetear este TimeOut.
     *
     * Por ahora, tiene 2 pres:
     *	1) Este TimeOut debe tener un State seteado.
     *	2) Los triggers deben estar suscritos al State, si 
     *	   se suscriben despues, no va a andar.
     *
     * VER BIEN COMO HACEMOS CON ESTO (puede dejarse asi,
     * y que el encargado de hacerlo en orden sea el creador).
     */
    public void addResetTrigger(String type, String thrower) throws InvalidStateException, InvalidTriggerException
    {
        StateHandlerFactory.getStateHandler().addAction(getState(), type, thrower, 0, new Reset(this));
        reset_triggers.add(new TriggerValues(type, thrower));
    }

    public void addStopTrigger(String type, String thrower) throws InvalidStateException, InvalidTriggerException
    {
        StateHandlerFactory.getStateHandler().addAction(getState(),type, thrower, 0, new Stop(this));
        stop_triggers.add(new TriggerValues(type, thrower));
    }
    
    /**
     * Devuelve un Bean describiendo este objeto
     */
    public TimeOutBean getBean()
    {
        TimeOutBean tob = new TimeOutBean();
        tob.setConversionUnits(conversion_unit);
        tob.setState(estado);
        tob.setThrower(thrwr);
        tob.setType(tipo);
        tob.setWaitTime(tiempo_espera);
        tob.setResetTriggers(reset_triggers);
        tob.setStopTriggers(stop_triggers);
        Vector new_actions = new Vector();
        Iterator itr = actions.iterator();
        while (itr.hasNext())
        {
            Action act = (Action)itr.next();
            Object nobj = BeanerFactory.getBeaner(act).toBean(act);
            if (nobj != null)
            {
                new_actions.add(nobj);
            }
        }
        tob.setActions(new_actions);
        return tob;
    }
}