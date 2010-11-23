package domain.statemachine;

/**
 * Factory para el StateMachine
 */
public class StateMachineFactory 
{
    public static StateMachine getStateMachine()
    {
        return StateMachineImpl.getInstance();
    }
}
