package test.other;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.channel.tcp.TCPClientInfo;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;
import infrastructure.services.comm.channel.tcp.TCPServerInfo;

public class TestCommTCPReceiver
{
    public static void main(String[] args)
    {
        try
        {
            Map context = new HashMap();
            Collection channels = new Vector();
            //Se crean las estructuras...
            
            TCPServerInfo tcpsi = new TCPServerInfo();
            tcpsi.setPort(8090);
            tcpsi.setName("SECUNDARIO");
            channels.add(tcpsi);
            
            context.put(TCPCommConstants.INFOSTRUCTURES, channels);
            
            TCPCommFactory tcf = new TCPCommFactory();
            tcf.setContext(context);
            
            Channel ch = tcf.createChannel("SECUNDARIO");
            ch.connect();
            
            while (true)
            {
                Message mssg = ch.receive();
                System.out.println("RECIBI " + mssg.getElement("ELEMENT"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
