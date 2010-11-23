package domain.state;

import util.GeneralException;

/**
 * Exception lanzada al detectar errores
 * en el manejo de Triggers.
 */
public class InvalidTriggerException extends GeneralException
{
    private String type;
    private String thrower;
    private String state;
    
    public InvalidTriggerException(String msg, String type, String thrower, String state)
    {
        super("El trigger con type <" + type + "> y " +
                    "thrower <" + thrower + "> es inválido en el estado <" + state + "> : " + msg);
        this.type = type;
        this.thrower = thrower;
        this.state = state;
    }

    public String getType()
    {
        return type;
    }

    public String getThrower()
    {
        return thrower;
    }

    public String getState()
    {
        return state;
    }
}
