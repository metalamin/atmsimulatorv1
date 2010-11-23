package test.other;

import infrastructure.communication.CommunicationConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import domain.implementation.StateChange;
import domain.state.Action;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.message.ifx.IFXMessageFactory;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;
import infrastructure.services.comm.channel.tcp.TCPServerInfo;
import infrastructure.dataaccess.XMLCollectionWriter;

/**
 * @TODO Sincronizar con Alberto
 */

public class TestTCPServer {
    public static void main(String[] args) {
        try {

            Map context = new HashMap();
            Collection channels = new Vector();
            //Se crean las estructuras...
            
            TCPServerInfo tcpsi = new TCPServerInfo();
            tcpsi.setPort(8080);
            tcpsi.setName("ATM_REQUEST_CHANNEL");
            channels.add(tcpsi);
            
            context.put(TCPCommConstants.INFOSTRUCTURES, channels);
            
            TCPCommFactory tcf = new TCPCommFactory();
            tcf.setContext(context);
            CommunicationConfig.getInstance().setCommFactory(tcf);
            
            MessageFactory mf = new IFXMessageFactory();
            CommunicationConfig.getInstance().setMessageFactory(mf);
            
            Channel ch = tcf.createChannel("ATM_REQUEST_CHANNEL");
            
            ch.setPackager(mf.createPackager());
            
            while (true) {

                try {
                    ch.connect();
                    
                    System.out.println("Application Connected");
                    
                    while (true) {
                        Message mssg = ch.receive();
                    /*    mssg.setElement("MediaSvcRq[0].RqUID","3a64839c-17ff-40c8-8747-33683bb728b7");                        
                        mssg.setElement("MediaSvcRq[0].DevInqRq[0].RqUID","3a64839c-17ff-40c8-8747-33683bb728b7");
                     */
                        //Acciones que van a ir asocadas al mensaje...
                        //Action accionAsociada = new DebugAction("Esta es la accion asociada");
                        Action accionAsociada = new StateChange("ANOTHER_OPERATION");
                        //Action otraMas = new DebugAction("Esta es OTRA accion asociada");
                        /**ty
                         * De esta manera se pasan las acciones a
                         * String...en realidad, lo super correcto 
                         * seria hacer "add" del bean asociado.
                         */
                        XMLCollectionWriter xmlcw = new XMLCollectionWriter();
                        xmlcw.addObject(accionAsociada);
                        //xmlcw.addObject(otraMas);
                        String formattedAction = xmlcw.toString();
                        mssg.setElement("MediaSvcRq[0].RqUID","3a64839c-17ff-40c8-8747-33683bb728b7");
                        mssg.setElement("MediaSvcRq[0].DevActionRq[0].RqUID","3a64839c-17ff-40c8-8747-33683bb728b7");
                        mssg.setElement("MediaSvcRq[0].DevActionRq[0].ActionsInfo[0]", formattedAction);
                        // Aca setear el DevType con las Actions que queremos ejecutar en el ATM
//                        1 <DevInqRq>
//                        2 <RqUID>f81d4fae-7dec-11d0-a765-00a0c91e6bf5</RqUID>
//                        3 <DevType>Idc</DevType>
//                        4 </DevInqRq>
                        System.out.println(mssg.toString());
                        ch.send(mssg);
                    }
                } catch (Exception ex) {
                    System.out.println("Application Disconnected");
                    ch.disconnect();
                }
            }
        } catch (Exception ex) {
            System.out.println("Server Shutdown");
        }
    }
}
