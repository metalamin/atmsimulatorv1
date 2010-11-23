package test.other;
import presentation.display.Simulator;
import java.awt.Rectangle;
import domain.implementation.StateChange;
import domain.state.StateConstants;
import domain.state.StateHandler;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;
import domain.statemachine.StateMachineFactory;
import presentation.display.ButtonEvent;
import presentation.display.DisplayAction;
import presentation.display.SetTextAction;

public class TestDisplay
{
    public static void main(String[] args)
    {
        try
        {
            StateHandler st = StateHandlerFactory.getStateHandler();
            
            /**
             * Se agregan 2 estados.
             */
            st.addState("ESTADO1");
            st.addState("ESTADO2");
            
            /**
             * Cambio de estado 1->2
             */
            Trigger tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button2");
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new StateChange("ESTADO2"));
            
            /**
             * Trigger por que si.
             */
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button1");
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new SetTextAction("button1", "Apreta el otro"));

            /**
             * Cambio de estado 2->1
             */
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button1");
            st.addTrigger("ESTADO2",  tr);
            tr.addAction(new StateChange("ESTADO1"));

            /**
             * Cuando inicia cada estado, despliega la pantalla
             * correspondiente.
             */
            DisplayAction da = new DisplayAction();
            da.setScreen("pantalla3.xml");
            da.setBounds(new Rectangle(0,0, 800,600));
            st.addAction("ESTADO1",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            
            DisplayAction da2 = new DisplayAction();
            da2.setScreen("pantalla4.xml");
            da2.setBounds(new Rectangle(0,0, 600,480));
            
            st.addAction("ESTADO2",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da2);
            
            /**
             * Listo el pollo, arrancamos...
             */
            Simulator sim = Simulator.getInstance();
            sim.setBounds(0,0, 800,600);
            sim.setVisible(true);
            StateMachineFactory.getStateMachine().startup("ESTADO1");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
