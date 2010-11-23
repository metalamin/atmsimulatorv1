package domain.statemachine;

import util.GeneralException;

/**
 * Clase que implementa la sincronizacion.
 * Esta pone en cola los pedidos, para que se ejecuten de a uno.
 * Singleton por conveniencia.
 */
final class Synchronizer
{
    private static Synchronizer inst = null;

    private Synchronizer()
    {
    }

    public static Synchronizer getInstance()
    {
        if (inst == null)
        {
            inst = new Synchronizer();
        }

        return inst;
    }

    /**
     * Ejecucion de un Task
     */
    public synchronized void doTask(Task task) throws GeneralException
    {
        task.run();
    }
}