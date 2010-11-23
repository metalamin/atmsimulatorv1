package domain.state;

import infrastructure.dataaccess.DataAccessException;
import util.GeneralException;
import domain.state.InvalidStateException;
import domain.state.InvalidTriggerException;
import java.util.Set;

/**
 * Interfaz que presenta el handler
 * de estados.
 */
public interface StateHandler
{
    /**
     * Agregado de un estado...
     */
    public void addState(String state) throws InvalidStateException;
    
    /**
     * Agregado de un Trigger.
     */
    public void addTrigger(String state, Trigger tr) throws InvalidStateException, InvalidTriggerException;
    
    /**
     * Agregado de acciones.
     */
    public void addAction(String state, String type, String thrower, int place, Action act) throws InvalidStateException, InvalidTriggerException;
    
    /**
     * Seteo del estado actual...
     */
    public void setState(String st) throws InvalidStateException, GeneralException;
    
    /**
     * Ejecucion de un update...
     */
    public void update(Event ev) throws GeneralException;
    
    /**
     * Devuelve la coleccion con los
     * nombres de los estados.
     */
    public Set getStates();
    
    /**
     * Devuelve un Bean representando al estado
     * indicado.
     */
    public StateBean getStateBean(String state) throws InvalidStateException;
 	
    /**
     * Guarda la maquina de estados, en la
     * conexion seleccionada.
     */
    public void save(String connection) throws DataAccessException;
    
    /**
     * Abre una maquina de estados previamente
     * guardada en la conexion seleccionada.
     */
    public void load(String connection) throws DataAccessException, GeneralException;
    
    /**
     * Borra la maquina de estados.
     */
    public void clean();
}
