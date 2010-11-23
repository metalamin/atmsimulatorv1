package test.other;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.channel.rpc.RPCChannel;
import infrastructure.services.comm.channel.rpc.RPCMessage;

public class TestCommRPCSender
{
    public static void main(String[] args)
    {
        try
        {
            RPCChannel ch = new RPCChannel("localhost", 8080, "localhost", 8070);
            ch.connect();
            
            Message mssg = new RPCMessage();
            int contador = 1;
            while (true)
            {
                System.in.read();
                String mens = "Mensaje " + contador;
                contador++;
                System.out.println("ENVIO: " + mens);
                mssg.setElement("ELEMENT", mens);
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
