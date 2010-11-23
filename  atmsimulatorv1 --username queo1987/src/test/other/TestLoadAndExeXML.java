package test.other;

import config.SystemConfig;
import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.statemachine.StateMachineFactory;


public class TestLoadAndExeXML
{
    
    public static void main(String[] args)
    {
        try
        {
            String archivo = "cfg/maqpru.xml";
            String estado = "Estado1";
            if (args.length >= 1)
            {
                archivo = args[0];
            }
            if (args.length > 1)
            {
                estado = args[1];
            }
            SystemConfig.getInstance().configure();
            
            StateHandlerFactory.getStateHandler().load(archivo);
            StateMachineFactory.getStateMachine().startup(estado);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
