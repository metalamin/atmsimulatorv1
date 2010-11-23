package domain.statemachine;
import util.GeneralException;

import domain.state.Event;

/**
 * Implementacion de la maquina de
 * estados.
 *
 * Esta implementacion sincroniza los pedidos.
 * Singleton por conveniencia.
 */
final class StateMachineImpl implements StateMachine
{
    private static StateMachineImpl inst = null;
    
    private StateMachineImpl()
    {
    }
    
    public static StateMachineImpl getInstance()
    {
        if (inst == null)
        {
            inst = new StateMachineImpl();
        }
        return inst;
    }
    
    public void startup(String state) throws GeneralException
    {
        Synchronizer.getInstance().doTask(new SetStateTask(state));
    }
    
    
     public void update(Event ev) throws GeneralException
     {
         Synchronizer.getInstance().doTask(new UpdateTask(ev));
     }
}
