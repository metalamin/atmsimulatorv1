package test.other;

import infrastructure.communication.CommunicationConfig;
import infrastructure.communication.CommunicationConstants;
import infrastructure.communication.action.ConnectAction;
import infrastructure.communication.action.ExternalCommandAction;
import infrastructure.communication.action.SendMessageAction;
import config.SystemConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import domain.implementation.CountTrigger;
import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.implementation.SystemExit;
import domain.state.StateConstants;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;
import domain.statemachine.StateMachineFactory;
import infrastructure.services.comm.channel.CommFactory;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.channel.rpc.RPCMessageFactory;
import infrastructure.services.comm.channel.tcp.TCPClientInfo;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;
import infrastructure.services.comm.channel.tcp.TCPServerInfo;

/**
 * Esto arma una maquina de estados a
 * pedal que se queda escuchando en un canal,
 * y despliega "LLEGO MENSAJE" cada vez que
 * un mensaje llega.
 */
public class TestMaquinaDeEstadosComunicada
{
    public static void main(String[] args)
    {
        try
        {
            //Estructura del factory de comunicacion
            //Esto se va a armar a traves de configuraciones...
            Map context = new HashMap();
            Collection channels = new Vector();
            //Se crean las estructuras...
            //Hago de SERVER DEL CANAL PRIMARIO
            TCPServerInfo tcpsi = new TCPServerInfo();
            tcpsi.setPort(8080);
            tcpsi.setName("PRIMARIO");
            channels.add(tcpsi);
            
            //Y CLIENT DEL CANAL SECUNDARIO
            TCPClientInfo tcpc = new TCPClientInfo();
            tcpc.setPort(8090);
            tcpc.setName("SECUNDARIO");
            tcpc.setHost("localhost");
            channels.add(tcpc);

            context.put(TCPCommConstants.INFOSTRUCTURES, channels);            
            CommFactory cf = new TCPCommFactory();
            
            MessageFactory mf = new RPCMessageFactory();
            CommunicationConfig.getInstance().setCommFactory(cf);
            CommunicationConfig.getInstance().setMessageFactory(mf);
            CommunicationConfig.getInstance().setContext(context);
            
            //Se arma la maquina de estados
            SystemConfig.getInstance().configure();
            
            StateHandlerFactory.getStateHandler().addState("ESTADO 1");
            //Trigger que va a responder al mensaje...
            Trigger trig = TriggerFactory.createTrigger(
                    CommunicationConstants.COMMUNICATION_TYPE,
                    "PRIMARIO");
            //Se le agrega la accion de debug...simple testeo...
            trig.addAction(new DebugAction("LLEGO MENSAJE!!!"));
            //Se va a MANDAR EL MENSAJE QUE LLEGO POR EL OTRO CANAL
            trig.addAction(new SendMessageAction("SECUNDARIO", CommunicationConstants.MESSAGE + ".OUT"));
            
            /**
             * Se agrega una accion en el estado. Basicamente, dice:
             * "Ejecuto la accion asociada al tag "ACCIONASOCIADA".
             * Esto podria import en el estado global, pero me re
             * embolaba hacer un canal nuevo.
             */
            trig.addAction(new ExternalCommandAction("ACCIONASOCIADA")); 
            StateHandlerFactory.getStateHandler().addTrigger("ESTADO 1", trig);
 
            StateHandlerFactory.getStateHandler().addAction("ESTADO 1", 
                    StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER,
                    0, new DebugAction("ARRANCO"));
            
            StateHandlerFactory.getStateHandler().addAction("ESTADO 1", 
                    StateConstants.STARTUP_TYPE, StateConstants.STARTUP_THROWER,
                    0, new ConnectAction());
            
          /**
           * Si se desconecta el canal primario o el secundario, 
           * se va a informar. Para ello, se crea un contador de 
           * desconexiones...a las 5, salta como un ajo
           */
            Trigger trgds = TriggerFactory.createTrigger(
                    CommunicationConstants.DISCONNECT_TYPE,
                    "PRIMARIO");
            trgds.addAction(new DebugAction("Se desconecto el canal primario"));
            
            trgds.addAction(new ConnectAction());
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, trgds);
            
            Trigger trgds2 = TriggerFactory.createTrigger(
                    CommunicationConstants.DISCONNECT_TYPE,
                    "SECUNDARIO");
            trgds2.addAction(new DebugAction("Se desconecto el canal secundario"));
            trgds2.addAction(new ConnectAction());
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, trgds2);
            
            CountTrigger ctrg = new CountTrigger("COUNT", "COUNT", 5);
            ctrg.addAction(new DebugAction("ESTO APESTA, ME VOY!"));
            ctrg.addAction(1, new Delay(1000));
            ctrg.addAction(2, new SystemExit()); 
            StateHandlerFactory.getStateHandler().addTrigger(StateConstants.GLOBAL_STATE, ctrg);
            
            ctrg.addCountTrigger(CommunicationConstants.DISCONNECT_TYPE,
                    "PRIMARIO");
            ctrg.addCountTrigger(
                    CommunicationConstants.DISCONNECT_TYPE,
                    "SECUNDARIO");
            
            StateMachineFactory.getStateMachine().startup("ESTADO 1");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
