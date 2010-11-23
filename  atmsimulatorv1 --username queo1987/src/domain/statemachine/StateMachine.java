package domain.statemachine;

import util.GeneralException;
import domain.state.Event;

/**
 * Interfaz que presenta la maquina de
 * estados operacional a las capas
 * superiores.
 * Agrega a las funcionalidades del
 * handler de estados la sincronizacion.
 */
public interface StateMachine extends IUpdateListener
{
    /**
     * Inicializacion de la maquina de
     * estados.
     */
    public void startup(String state) throws GeneralException;
    
    /**
     * Ejecucion de un update.
     */
     public void update(Event ev) throws GeneralException;
}
