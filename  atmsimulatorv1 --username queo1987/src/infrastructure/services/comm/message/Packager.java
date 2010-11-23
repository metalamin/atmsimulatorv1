package infrastructure.services.comm.message;

/**
 * Definicion de un packager para los mensajes.
 */
public interface Packager
{
    /**
     * Traduce un mensaje a su representacion en
     * bytes.
     */
    public Object pack(Message msg);
    
    /**
     * Traduce un mensaje empaquetado a su
     * representacion "normal".
     */
    public Message unpack(Object pckdMsg);
}
