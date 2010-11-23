package domain.state;
import java.util.Vector;

/**
 * Bean para los Triggers por defecto.
 */
public class TriggerBean
{
    private Vector acciones;
    private String type;
    private String thrower;
    private String state;

    public TriggerBean()
    {
        acciones = new Vector();
    }

    public Vector getActions()
    {
        return acciones;
    }

    public void setActions(Vector acciones)
    {
        this.acciones = new Vector(acciones);
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

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    
    public boolean equals(TriggerBean o)
    {
        return this.thrower.equals(o.getThrower()) && this.type.equals(o.getType());
    }
}
