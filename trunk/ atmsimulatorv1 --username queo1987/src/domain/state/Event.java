package domain.state;

import java.util.Map;

/**
 * Interfaz que representa un evento disparable.
 * Una implementación de esta clase es pasa al Trigger y Actions 
 * correspondientes, según el tipo (Type) y disparador (Thrower).
 * Se define además un contexto del evento.
 */
  
public interface Event
{
	/**
         * Devuelve el tipo asociado al evento.
         */
	public String getType();
	
	/**
         * Devuelve el identificador asociado al disparador
         * del evento.
         */
	public String getThrower();
        
        /**
         * Devuelve el contexto del evento.
         */
        public Map getContext();
}
