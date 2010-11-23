package test.other;
import infrastructure.communication.CommunicationConstants;
import infrastructure.communication.action.ConnectAction;
import infrastructure.communication.action.SendMessageAction;
import infrastructure.communication.action.SetElementAction;
import config.SystemConfig;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.msd.MsdDisplayAction;
import infrastructure.devices.controllers.msd.MsdReadAction;
import infrastructure.devices.controllers.tio.ITioConst;
import infrastructure.devices.controllers.tio.TioDisplayAction;
import infrastructure.devices.controllers.tio.TioReadAction;
import infrastructure.devices.general.IDeviceConst;
import domain.implementation.Delay;
import domain.implementation.StateChange;
import domain.state.Action;
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
public class TestSaveXML_Prototype
{
    public static void main(String[] args) 
    {
        DisplayAction da;
        Trigger tr;
        MsdReadAction mra;
        TioReadAction tra;
        SetTextAction sta;
        SendMessageAction sma;
        Action act;
        Delay delay;
        
        try
        {
            SystemConfig.getInstance().configure();
            StateHandler st = StateHandlerFactory.getStateHandler();
            
            /**
             * Se agregan los estados.
             */
            st.addState("ESTADO_alfa");
            st.addState("ESTADO1");
            st.addState("ESTADO2");
            st.addState("ESTADO3");
            st.addState("ESTADO4");
            st.addState("ESTADO5");
            st.addState("ESTADO6");
            st.addState("ESTADO7");
            st.addState("ESTADO8");

            // ESTADO 0 -- Inicializando el sistema
            
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_Alfa.xml");
            st.addAction("ESTADO_alfa",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);
            act = new ConnectAction();
            st.addAction("ESTADO_alfa",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);
            
            //delay = new Delay(10000);
            //st.addAction("ESTADO_alfa",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, delay);            
            //st.addAction("ESTADO_alfa", StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 1, new StateChange("ESTADO1"));            

            // Despliega la lectora de banda
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, IMsdConst.MSD_THROWER);
            st.addTrigger("ESTADO_alfa",  tr);
            tr.addAction(new MsdDisplayAction());
            tr.addAction(new StateChange("ESTADO1"));
            
            // Despliega el teclado
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO_alfa",  tr);
            tr.addAction(new TioDisplayAction());
            
            // ESTADO 1 -- Inserte tarjeta

            // Despliega el teclado -- ARYA
            tr = TriggerFactory.createTrigger(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new TioDisplayAction());
            
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_InsertarTarjeta.xml");
            st.addAction("ESTADO1",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Lee de la banda magnetica
            st.addAction("ESTADO1",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, new MsdReadAction());

            // Cambio de estado 2->3
            tr = TriggerFactory.createTrigger(IMsdConst.O_MSD_READDATA,  IMsdConst.MSD_THROWER);
            st.addTrigger("ESTADO1",  tr);
            tr.addAction(new StateChange("ESTADO2"));
            
            /**
             * ESTADO 2 -- Ingrese PIN
             */

            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_IngresarPin.xml");
            st.addAction("ESTADO2",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            /**
             * Dispara una operacion de lectura de teclado
             */
            tra = new TioReadAction();
            st.addAction("ESTADO2",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);
           

            // Lee digitos y los despliega en pantalla
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO2",  tr);
            sta = new SetTextAction();
            sta.setComp("pwdPin");
            tr.addAction(sta);
            
            // Cambio de estado cuando presiona el Enter
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO2",  tr);
            tr.addAction(new StateChange("ESTADO3"));            

            
            // ESTADO 3 -- Seleccione operacion
           
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_SeleccionarOperacion.xml");
            st.addAction("ESTADO3",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Cambio de estado 3->4
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnRetiro");
            st.addTrigger("ESTADO3",  tr);
            tr.addAction(new StateChange("ESTADO4"));

            // ESTADO 4 -- Seleccione cuenta
           
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_SeleccionarCuenta2.xml");
            st.addAction("ESTADO4",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Cambio de estado
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnCtaCtePesos");
            st.addTrigger("ESTADO4",  tr);
            tr.addAction(new StateChange("ESTADO5"));            

            // Cambio de estado
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnCtaCredito");
            st.addTrigger("ESTADO4",  tr);
            tr.addAction(new StateChange("ESTADO1"));            
            
            // ESTADO 5 -- Seleccione moneda
           
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_SeleccionarMoneda.xml");
            st.addAction("ESTADO5",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Cambio de estado
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnPesos");
            st.addTrigger("ESTADO5",  tr);
            tr.addAction(new StateChange("ESTADO6"));            
            
            // ESTADO 6 -- Digite importe
           
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_DigitarImporte.xml");
            st.addAction("ESTADO6",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            /**
             * Dispara una operacion de lectura de teclado
             */
            tra = new TioReadAction();
            st.addAction("ESTADO6",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, tra);

            // Lee digitos y los despliega en pantalla
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ_KEY,  ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO6",  tr);
            sta = new SetTextAction();
            sta.setComp("txtImporte");
            tr.addAction(sta);
            
            // Cambio de estado
            tr = TriggerFactory.createTrigger(ITioConst.O_TIO_READ,  ITioConst.TIO_THROWER);
            st.addTrigger("ESTADO6",  tr);
            tr.addAction(new StateChange("ESTADO7"));

            // ESTADO 7 -- Procesando
            
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_EnProceso.xml");
            st.addAction("ESTADO7",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

//            act = new ConnectAction();
//            st.addAction("ESTADO7",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);
            act = new SendMessageAction();
            st.addAction("ESTADO7",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, act);
            
//            delay = new Delay(5000);
//            st.addAction("ESTADO7",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, delay);            
//            st.addAction("ESTADO7", StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, new StateChange("ESTADO8"));            

            tr = TriggerFactory.createTrigger(CommunicationConstants.COMMUNICATION_TYPE,
                                              "HOSTCHANNEL");
            st.addTrigger("ESTADO7", tr);            
            tr.addAction(new StateChange("ESTADO8"));
//            act = new DisconnectAction();
//            tr.addAction(act);
            
            // ESTADO 8 -- Otra Operacion?
            
            // Despliega la pantalla
            da = new DisplayAction();
            da.setScreen("cfg/P1_OtraOperacion.xml");
            st.addAction("ESTADO8",  StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER, 0, da);

            // Cambio de estado para nueva operacion
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnSi");
            st.addTrigger("ESTADO8",  tr);
            tr.addAction(new StateChange("ESTADO3"));            

            // Cambio de estado para resetear la maquina
            tr = TriggerFactory.createTrigger(ButtonEvent.BUTTON_EVENT_TYPE,  "btnNo");
            st.addTrigger("ESTADO8",  tr);
            tr.addAction(new StateChange("ESTADO1"));            
            
            /**
             * Listo el pollo, arrancamos...
             */
            StateHandlerFactory.getStateHandler().save("cfg/MAQ_ESTADOS_PROTOTYPE.xml");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    } 
}
