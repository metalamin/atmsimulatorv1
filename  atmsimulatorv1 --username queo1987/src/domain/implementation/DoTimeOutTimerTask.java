package domain.implementation;

import java.util.TimerTask;

/**
 * Clase que ejecuta el "wait" de tanto
 * tiempo y, si llega al final, avisa al
 * TimeOut que termino...
 */
class DoTimeOutTimerTask extends TimerTask
{
    private TimeOut t_o;

    public DoTimeOutTimerTask(TimeOut to)
    {
        t_o = to;
    }

    public void run()
    {
        t_o.forceUpdate();
    }
}
