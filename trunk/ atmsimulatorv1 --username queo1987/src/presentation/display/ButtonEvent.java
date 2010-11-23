package presentation.display;

import domain.implementation.EventImpl;
import domain.state.Event;

/**
 * Evento disparado por el boton.
 * Tiene predefinido el tipo, y no
 * se puede cambiar.
 */
public class ButtonEvent extends EventImpl implements Event
{
    public static final String BUTTON_EVENT_TYPE = "BUTTON_PRESSED";
    
    private String thrower;
    
    public ButtonEvent(String thrower) 
    {
        super();
        setThrower(thrower);
    }

    /** Devuelve el tipo predefinido **/
    public String getType() 
    {
        return BUTTON_EVENT_TYPE;
    }
    
    /**  Se sobreescribe el setType para que tire un exception **/
    public void setType(String type)
    {
        throw new UnsupportedOperationException("No puede sobreescribirse el tipo.");
    }
}
