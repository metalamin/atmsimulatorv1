package test.other;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.channel.rpc.RPCChannel;

public class TestCommRPCReceiver
{
    public static void main(String[] args)
    {
        try
        {
            RPCChannel ch = new RPCChannel("localhost", 8070, "localhost", 8080);
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
