package test.other;

import config.SystemConfig;
import domain.implementation.CountTrigger;
import domain.implementation.DebugAction;
import domain.implementation.EventImpl;
import domain.implementation.StateChange;
import domain.implementation.TimeOut;
import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.state.TriggerFactory;
import domain.statemachine.StateMachineFactory;

public class TestGlobalState
{
    
    public static void main(String[] args) 
    {
        try
        {
            SystemConfig.getInstance().configure();
            StateHandlerFactory.getStateHandler().addState("1");
            StateHandlerFactory.getStateHandler().addState("2");

            StateHandlerFactory.getStateHandler().addAction(
                    StateConstants.GLOBAL_STATE,  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    0, 
                    new DebugAction("STARTUP ESTADO GLOBAL"));
            
            StateHandlerFactory.getStateHandler().addAction(
                    StateConstants.GLOBAL_STATE,  
                    StateConstants.END_TYPE, 
                    StateConstants.END_THROWER,
                    0, 
                    new DebugAction("END ESTADO GLOBAL"));
                        
            StateHandlerFactory.getStateHandler().addAction("1",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    0, 
                    new DebugAction("ESTADO UNO"));

            StateHandlerFactory.getStateHandler().addAction("1",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    1, 
                    new StateChange("2"));
 
            StateHandlerFactory.getStateHandler().addAction("2",  
                    StateConstants.STARTUP_TYPE, 
                    StateConstants.STARTUP_THROWER,
                    0, 
                    new DebugAction("ESTADO DOS"));
            
            StateHandlerFactory.getStateHandler().addTrigger(
                    "2", TriggerFactory.createTrigger(
                    "TECLA", "ENTER"));

            TimeOut to = new TimeOut("TOTECLA", "TOENTER", 5000);
            CountTrigger ct = new CountTrigger("JOINTECLA", "JOINENTER", 2);
            StateHandlerFactory.getStateHandler().addTrigger("2", ct);
            StateHandlerFactory.getStateHandler().addTrigger("2", to);
            
            to.addResetTrigger(StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER);
            to.addStopTrigger("TECLA",  "ENTER");
            to.addAction(new DebugAction("FIN TIMEOUT"));
            ct.addCountTrigger("TECLA", "ENTER");
            ct.addAction(new DebugAction("FIN COUNTDOWN"));

            StateHandlerFactory.getStateHandler().addTrigger(
                    StateConstants.GLOBAL_STATE, TriggerFactory.createTrigger(
                    "TECLA", "ENTER"));

            StateHandlerFactory.getStateHandler().addTrigger(
                    StateConstants.GLOBAL_STATE, TriggerFactory.createTrigger(
                    "TECLA", "SEGUNDO ENTER"));
            
            StateHandlerFactory.getStateHandler().addAction(
                    "2",
                    "TECLA", "ENTER", 0, new DebugAction("PRIMER ENTER: DOS"));
            
            StateHandlerFactory.getStateHandler().addAction(
                    StateConstants.GLOBAL_STATE,
                    "TECLA", "ENTER", 0, new DebugAction("PRIMER ENTER: GLOBAL"));
            
            StateHandlerFactory.getStateHandler().addAction(
                    StateConstants.GLOBAL_STATE,
                    "TECLA", "SEGUNDO ENTER", 0, new DebugAction("SEGUNDO ENTER: GLOBAL"));
         
            StateHandlerFactory.getStateHandler().save("cfg/global_state.xml");
             StateHandlerFactory.getStateHandler().load("cfg/global_state.xml");
            
            StateMachineFactory.getStateMachine().startup("1");
            
            for (int i = 0; i<2; i++)
            {
                System.in.read();
                StateMachineFactory.getStateMachine().update(new EventImpl("TECLA", "ENTER"));
            }
            System.in.read();
            StateMachineFactory.getStateMachine().update(new EventImpl("TECLA", "SEGUNDO ENTER"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}
