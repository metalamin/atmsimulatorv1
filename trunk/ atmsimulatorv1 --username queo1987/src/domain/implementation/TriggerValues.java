package domain.implementation;

/**
 * Clase de utilidad para guardar los valores
 * de un trigger (i.e., type y thrower).
 */
public class TriggerValues 
{
    private String type;
    private String thrower;
    
    public TriggerValues() 
    {}
    
    public TriggerValues(String type, String thrower)
    {
        setType(type);
        setThrower(thrower);
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
    
    public String toString()
    {
        return this.thrower+"-"+this.type;
    }
    
    public boolean equals(Object o)
    {
        if(TriggerValues.class.isInstance(o))
        {
            TriggerValues tv = (TriggerValues)o;
            if(thrower.equals(tv.getThrower()) && type.equals(tv.getType()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
