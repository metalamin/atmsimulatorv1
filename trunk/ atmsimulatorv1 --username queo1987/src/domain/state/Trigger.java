package domain.state;

import util.GeneralException;

/**
 * Interfaz de los Trigger.
 */
public interface Trigger 
{
    /**
     * Agrega una accion en la posicion
     * por defecto.
     */
    public void addAction(Action action);
    
    /**
     * Agrega una accion el la posicion adecuada.
     */
    public void addAction(int posicion, Action action);
    
    /**
     * Disparada del evento.
     * Recorre las acciones segun el orden
     * y dispara el evento.
     */
    public void update(Event ev) throws GeneralException;
    
    /**
     * Devuelve el tipo al que responde este
     * Trigger.
     */
    public String getType();
    
    /**
     * Devuelve el thrower al que responde
     * este Trigger.
     */
    public String getThrower();
    
    /**
     * Setea el nombre del estado
     * donde esta este Trigger.
     */
    public void setState(String state);
}
