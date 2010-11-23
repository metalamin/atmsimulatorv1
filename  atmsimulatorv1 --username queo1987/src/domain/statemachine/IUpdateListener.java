package domain.statemachine;

import util.GeneralException;
import domain.state.Event;
/**
 * @author Arya Baher
 * Interfaz de un listener con una �nica operaci�n update
 */
public interface IUpdateListener {
    /**
     * Ejecucion de un update.
     */
     public void update(Event ev) throws GeneralException;    
}
