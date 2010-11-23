package config;

import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.statemachine.StateMachineFactory;
import presentation.splash.SplashScreen;

/**
 * Punto de entrada a la aplicación.
 * El uso de la clase en:
 *  Main <archivo de máquina de estado> [<archivo de configuracion> 
 *      <archivo de condifugradores>]
 */
public class Main
{
    private static void printMessage()
    {
        System.out.println("Uso:");
        System.out.println("\tMain <archivo de máquina de estado> [<archivo de " +
                "configuración>] [<archivo de configuradores>]");
    }
    
    /**
     * El primer argumento es el archivo de máquinas de estado, 
     * y es obligatorio.
     * El segundo y tercero son opcionales, e indican:
     *  - El archivo con las variables de sistema (configuracion).
     *  - El archivo con los configuradores para los plugin.
     */
    public static void main(String[] args)
    {
        try
        {
            SplashScreen splash = new SplashScreen(10000);
            splash.showSplash();

            if (args.length == 0 || args.length > 3)
            {
                printMessage();
                System.exit(0);
            }
            
            //Archivo de configuración
            if (args.length > 1)
            {
                String cfgFile = args[1];
                SystemConfig.getInstance().setConnectionName(cfgFile);
                if (args.length == 3)
                {
                    String configuratorsFile = args[2];
                    SystemConfig.getInstance().setConfiguratorsConnectionName(
                            configuratorsFile);
                }
            }
            else{
                System.out.println(args[0]);
            }
            //Se configura el sistema
            SystemConfig.getInstance().configure();
            //Se realiza la carga de la máquina de estados
            String stateMachineFile = args[0];
            StateHandlerFactory.getStateHandler().load(stateMachineFile);
            //Y se inicializa...
            StateMachineFactory.getStateMachine().startup(
                    StateConstants.INITIAL_STATE);
        }
        catch (Exception ex)
        {
            System.out.println("Error al ejecutar el sistema:" + 
                    ex.getMessage());
            System.exit(0);
        }
    }
    
}
