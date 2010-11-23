package domain.statemachine;
import util.GeneralException;
import domain.state.StateHandlerFactory;

/**
 * Task que ejecuta un "setState".
 */
class SetStateTask implements Task
{
    private String stat;

    public SetStateTask(String est)
    {
        stat = est;
    }

    public void run() throws GeneralException
    {
        StateHandlerFactory.getStateHandler().setState(stat);
    }
}