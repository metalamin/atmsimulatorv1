package domain.state;

import infrastructure.dataaccess.broker.BeanerFactory;
import util.GeneralException;
import java.util.Vector;
import java.util.Iterator;

/**
 * Clase que representa los
 * Triggers.
 * Provee comportamiento 
 * basico a los mismos.
 */
 
class TriggerImpl implements Trigger
{
    //Las acciones asociadas.
    private Vector acciones;

    //Los identificadores: tipo y thrower.
    private String tipo;
    private String thrwr;
    
    // Identificador del estado
    private String state;
    

    public TriggerImpl(String type, String thrower)
    {
        acciones = new Vector();
        tipo     = type;
        thrwr    = thrower;
    }

    public void setState(String st)
    {
        state = st;
    }
    
    public String getState()
    {
        return state;
    }
    
    public String getType()
    {
        return tipo;
    }
 
    public String getThrower()
    {
        return thrwr;
    }
 
    /**
     * El agregado de acciones.
     */
    //Agrega una al final...
    public void addAction(Action action)
    {
        acciones.add(action);
    }

    /**
     * Agrega una accion el la posicion adecuada.
     * Esta implementacion chequea si entra, si no,
     * la appendea (similar a addAction(action).
     */
    public void addAction(int posicion, Action action)
    {
        if (acciones.size() < posicion)
        {
            addAction(action);
        }
        else
        {
            acciones.add(posicion, action);
        }
    }

    /**
     * Disparada del evento.
     * Recorre las acciones segun el orden
     * y dispara el evento.
     */
    public void update(Event ev) throws GeneralException   
    {
        Iterator it_actions = acciones.iterator();
        while (it_actions.hasNext())
        {
            Action act = (Action)it_actions.next();
            act.update(ev);
        }
    }
    
    /**
     * Devuelve una representacion "en Bean"
     * del objeto.
     */
    public TriggerBean getBean()
    {
        TriggerBean t_b = new TriggerBean();
        t_b.setState(getState());
        t_b.setType(getType());
        t_b.setThrower(getThrower());
        Vector new_actions = new Vector();
        Iterator itr = acciones.iterator();
        while (itr.hasNext())
        {
            Action act = (Action)itr.next();
            Object nobj = BeanerFactory.getBeaner(act).toBean(act);
            if (nobj != null)
            {
                new_actions.add(nobj);
            }
        }
        t_b.setActions(new_actions);
        return t_b;
    }
}