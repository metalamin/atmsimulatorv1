package domain.state;

/**
 * Constantes de los estados.
 */
public interface StateConstants 
{
    // Constantes...
    // Type de la inicializacion
    public final static String STARTUP_TYPE    	= "STATE_STARTUP_TYPE";

    // Thrower de la inicializacion
    public final static String STARTUP_THROWER 	= "STATE_STARTUP_THROWER";

    // Type de la finalizacion
    public final static String END_TYPE 	= "STATE_END_TYPE";

    // Thrpwer de la finalizacion
    public final static String END_THROWER 	= "STATE_END_THROWER";
    
    // Identificador del estado global
    public final static String GLOBAL_STATE     = "GLOBAL_STATE";
    
    //Identificador del estado inicial
    public final static String INITIAL_STATE    = "INITIAL_STATE";
}
