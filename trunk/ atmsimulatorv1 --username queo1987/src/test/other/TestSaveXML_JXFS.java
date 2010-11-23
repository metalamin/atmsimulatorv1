package test.other;
import config.SystemConfig;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.msd.MsdDisplayAction;
import infrastructure.devices.controllers.msd.MsdReadAction;
import infrastructure.devices.controllers.tio.ITioConst;
import infrastructure.devices.controllers.tio.TioDisplayAction;
import infrastructure.devices.controllers.tio.TioReadAction;
import infrastructure.devices.general.IDeviceConst;
import domain.implementation.StateChange;
//import logica.implementation.TimeUnits;
import domain.state.StateConstants;
import domain.state.StateHandler;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;
import presentation.display.ButtonEvent;
import presentation.display.DisplayAction;
import presentation.display.SetTextAction;

/**
 *
 */
public class TestSaveXML_JXFS
{
    public static void main(String[] args) 
    {
        try
        {
            SystemConfig.getInstance().configure();
            StateHandler st = StateHandlerFactory.getStateHandler();
            
            /**
             * Se agregan 2 estados.
             */
            st.addState("ESTADO1");
            st.addState("ESTADO2");
            st.addState("ESTADO3");
            
             // ESTADO 1
            
            // Despliega la pantalla
            DisplayAction da = new DisplayAction();
            da.setScreen("cfg/pantalla1.xml");
            st.addAction("ESTADO1",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Despliega la lectora de banda
            Trigger tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, IMsdConst.MSD_THROWER);
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new MsdDisplayAction());
            
            // Despliega el teclado
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new TioDisplayAction());
            
            // Cambio de estado 1->2
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button2");
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new StateChange("ESTADO2"));
           
            // Trigger por que si.
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button1");
            st.addTrigger("ESTADO1",  tr);
//            tr.addAction(new Delay(2, TimeUnits.SEGUNDOS));
            tr.addAction(new SetTextAction("button1", "Apreta el otro"));

            /**
             * ESTADO 2
             */
            
            /**
             * Dispara una operacion de lectura de banda magnetica
             */
            MsdReadAction mra = new MsdReadAction();
            st.addAction("ESTADO2",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, mra);

            /**
             * Cambio de estado 2->3
             */
            tr = TriggerFactory.createTrigger(IMsdConst.O_MSD_READDATA,  IMsdConst.MSD_THROWER);
            st.addTrigger("ESTADO2",  tr);
            tr.addAction(new StateChange("ESTADO3"));

            
            // ESTADO 3
           
            // Despliega la pantalla
            DisplayAction da2 = new DisplayAction();
            da2.setScreen("cfg/pantalla2.xml");
            st.addAction("ESTADO3",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da2);

            /**
             * Dispara una operacion de lectura de teclado
             */
            TioReadAction tra = new TioReadAction();
            st.addAction("ESTADO3",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);
           
            // Cambio de estado 3->1
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO3",  tr);
            tr.addAction(new StateChange("ESTADO1"));
            
            // Cambio de estado 3->1
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "button1");
            st.addTrigger("ESTADO3",  tr);
            tr.addAction(new StateChange("ESTADO1"));
            

            /**
             * Listo el pollo, arrancamos...
             */
            StateHandlerFactory.getStateHandler().save("cfg/MAQ_ESTADOS_MSD_PRM.xml");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    } 
}
