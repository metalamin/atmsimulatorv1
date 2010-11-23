package domain.state;

/**
 * Factory para los Trigger por defecto.
 */
public class TriggerFactory 
{
    public static Trigger createTrigger(String type, String thrower)
    {
        return new TriggerImpl(type, thrower);
    }
}
