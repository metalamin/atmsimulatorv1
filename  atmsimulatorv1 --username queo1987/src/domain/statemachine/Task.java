package domain.statemachine;

import util.GeneralException;


/**
 * Tarea para ser sincronizada
 * por el sincronizador.
 * Dispara StateException por 
 * conveniencia.
 */
public interface Task
{
    public void run() throws GeneralException;
    
}
