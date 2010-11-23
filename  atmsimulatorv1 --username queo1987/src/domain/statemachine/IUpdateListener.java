package domain.statemachine;

import util.GeneralException;
import domain.state.Event;
/**
 * @author Arya Baher
 * Interfaz de un listener con una única operación update
 */
public interface IUpdateListener {
    /**
     * Ejecucion de un update.
     */
     public void update(Event ev) throws GeneralException;    
}
