package domain.implementation;

import util.GeneralException;
import domain.statemachine.StateMachineFactory;
import domain.state.Action;
import domain.state.Event;

/**
 * Accion que evalua una condicion, y dispara
 * un evento seteable si esta se da.
 */
public class ConditionalAction implements Action
{
    //El tipo del evento que va a disparar
    private String type;
    //El thrower
    private String thrower;
    //La clave sobre la que va a comparar
    private String key;
    //Contra lo que va a comparar
    private String value;
    
    public ConditionalAction()
    {
    }

    public ConditionalAction(String key, String value, String type, String thrower)
    {
        setKey(key);
        setValue(value);
        setType(type);
        setThrower(thrower);
    }
    
    public void update(Event ev) throws GeneralException
    {
        //Se busca por la clave
        if (ev.getContext().containsKey(getKey()))
        {
            //Si es igual al valor...
            if (ev.getContext().get(key).equals(value))
            {
                //Se dispara el evento
                StateMachineFactory.getStateMachine().update(
                        new EventImpl(getType(), getThrower()));
            }
        }
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getThrower()
    {
        return thrower;
    }

    public void setThrower(String thrower)
    {
        this.thrower = thrower;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String toString()
    {
        return ("Conditional (" + getKey() + ", " + getValue() + ")");        
    }
}
