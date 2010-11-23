package infrastructure.dataaccess;

/**
 * Excepcion del manejo de persistencia.
 * Sirve para mantener la compatibilidad
 * de las implementaciones de Map y
 * Collection persistentes con la interfaz.
 */
public class PersistenceException extends RuntimeException
{
    public PersistenceException(String msg, Exception ex)
    {
        super(msg, ex);
    }
}
