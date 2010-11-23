package test.other;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import domain.implementation.DebugAction;
import domain.state.Action;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.channel.rpc.RPCMessageFactory;
import infrastructure.services.comm.channel.tcp.TCPClientInfo;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;
import infrastructure.dataaccess.XMLCollectionWriter;


public class TestCommTCPSender
{
    public static void main(String[] args)
    {
        try
        {
            Map context = new HashMap();
            Collection channels = new Vector();
            //Se crean las estructuras...
            TCPClientInfo cli = new TCPClientInfo();
            cli.setHost("localhost");
            cli.setName("PRIMARIO");
            cli.setPort(8080);
            channels.add(cli);
            
            context.put(TCPCommConstants.INFOSTRUCTURES, channels);
            
            TCPCommFactory tcf = new TCPCommFactory();
            tcf.setContext(context);
            
            Channel ch = tcf.createChannel("PRIMARIO");
            ch.connect();
            
            MessageFactory mf = new RPCMessageFactory();
            
            //Acciones que van a ir asocadas al mensaje...
            Action accionAsociada = new DebugAction("Esta es la accion asociada");
            Action otraMas = new DebugAction("Esta es OTRA accion asociada");
            /**
             * De esta manera se pasan las acciones a
             * String...en realidad, lo super correcto 
             * seria hacer "add" del bean asociado.
             */
            XMLCollectionWriter xmlcw = new XMLCollectionWriter();
            xmlcw.addObject(accionAsociada);
            xmlcw.addObject(otraMas);
            String formattedAction = xmlcw.toString();
            
            int contador = 1;
            while (true)
            {
                Message mssg = mf.createMessage();
                System.in.read();
                String mens = "Mensaje " + contador;
                contador++;
                System.out.println("ENVIO: " + mens);
                mssg.setElement("ELEMENT", mens);
                //Se asocian las acciones
                mssg.setElement("ACCIONASOCIADA", formattedAction);
                ch.send(mssg);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
