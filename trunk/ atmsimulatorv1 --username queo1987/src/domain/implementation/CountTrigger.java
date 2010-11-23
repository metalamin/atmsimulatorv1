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
import java.util.Vector;
import java.util.Iterator;


/**
 * Clase que representa un "Count" de
 * X updates.
 * Esta clase espera a que se cumplan X updates
 * de tipos y thrower seteables, y ahi dispara
 * su secuencia de acciones. 
 */
public class CountTrigger implements Trigger
{
    // El type...
    private String tipo;
    
    // Thrower...
    private String thrwr;
    
    // State
    private String estado;
    
    // Acciones...
    private Vector actions;
    
    // Los trigger de reset...
    private Vector count_triggers;
    
    // El numero de espera...
    private int count;
    
    public CountTrigger(String type, String thrower, int count)
    {
        tipo  = type;
        thrwr = thrower;
        actions = new Vector();
        count_triggers = new Vector();
        this.count = count;
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
     * conteo.
     */
    protected void updateCount()
    {
        count--;
        if (count <= 0)
        {
            try
            {
                EventImpl event = new EventImpl(this.getType(), this.getThrower());
                //Se llama al StateMachine para que corra sincronizado...
                StateMachineFactory.getStateMachine().update(event);
            }
            catch (GeneralException ex)
            {
                //No llega...
            }
        }
    }

    /**
     * Todos estos son los Trigger
     * que van a decrementar el contador.
     */
    public void addCountTrigger(String type, String thrower) throws InvalidStateException, InvalidTriggerException
    {
        StateHandlerFactory.getStateHandler().addAction(getState(), type, thrower, 0, new CountAction(this));
        count_triggers.add(new TriggerValues(type, thrower));
    }
    
    /**
     * Devuelve un Bean describiendo este objeto
     */
    public CountBean getBean()
    {
        CountBean tob = new CountBean();
        tob.setState(estado);
        tob.setThrower(thrwr);
        tob.setType(tipo);
        tob.setCountTriggers(count_triggers);
        tob.setCount(count);
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