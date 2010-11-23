package domain.state;

/**
 * Punto de entrada a la maquina de estados.
 */
public class StateHandlerFactory
{
    public static StateHandler getStateHandler()
    {
        return StateHandlerImpl.getInstance();
    }
}
