package test.other;

import config.SystemConfig;
import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.statemachine.StateMachineFactory;

public class TestMaquinaDeEstados
{
    public static void main(String[] args)
    {
        try
        {
            SystemConfig.getInstance().configure();
            StateHandlerFactory.getStateHandler().addState("1");
            
            StateHandlerFactory.getStateHandler().addAction("1",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    0, 
                    new DebugAction("UNO"));
            
            StateHandlerFactory.getStateHandler().addAction("1",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    1,
                    new Delay(3000));
            
            StateHandlerFactory.getStateHandler().addAction("1",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    2,
                    new DebugAction("DOS"));
            
            StateHandlerFactory.getStateHandler().save("cfg/prueba.xml");
            StateHandlerFactory.getStateHandler().load("cfg/prueba.xml");
            
            StateMachineFactory.getStateMachine().startup("1");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
             
    }
    
}
