package config;

import infrastructure.communication.CommunicationConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import infrastructure.services.comm.message.ifx.IFXMessageFactory;
import infrastructure.services.comm.channel.tcp.TCPClientInfo;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;
import infrastructure.services.comm.channel.tcp.TCPServerInfo;

/**
 * Seteo de contexto para las comunicaciones,
 * y seteo de las factorys.
 */
public class MainCommContextServer {
    public static void main(String[] args)
    {
        try
        {
            //El contexto se configura segun cliente o servidor...
            Map context = new HashMap();
            Collection channels = new Vector();
            //Se crean las estructuras...
            TCPClientInfo cli = new TCPClientInfo();
            cli.setHost("localhost");
            cli.setName("HOST_COMMAND_CHANNEL");
            cli.setPort(8090);
            channels.add(cli);
            
            TCPServerInfo tcpsi = new TCPServerInfo();
            tcpsi.setPort(8070);
            tcpsi.setName("ATM_REQUEST_CHANNEL");
            channels.add(tcpsi);

            context.put(TCPCommConstants.INFOSTRUCTURES, channels);
            
            SystemConfig.getInstance().setConnectionName("cfg/config_server.xml");
            SystemConfig.getInstance().setConfiguratorsConnectionName("cfg/configurators_server.xml");            
            SystemConfig.getInstance().configure();
            
            CommunicationConfig cc = CommunicationConfig.getInstance();
            cc.setCommFactory(new TCPCommFactory());
            cc.setContext(context);
            cc.setMessageFactory(new IFXMessageFactory());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
