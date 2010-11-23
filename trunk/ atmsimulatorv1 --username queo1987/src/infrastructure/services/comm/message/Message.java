package infrastructure.services.comm.message;

/**
 * Definicion de los mensajes enviados y recibidos por
 * loa canales de comunicacion.
 */
public interface Message
{
    /**
     * Devuelve el elemento del mensaje
     * asociado a la clave "key".
     */
    public Object getElement(Object key);
    
    /**
     * Asocia un elemento a una clave.
     */    
    public void setElement(Object key, Object elemt);
}
