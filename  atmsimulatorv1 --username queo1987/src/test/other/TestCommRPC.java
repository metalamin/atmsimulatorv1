package test.other;

import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.channel.rpc.RPCChannel;
import infrastructure.services.comm.channel.rpc.RPCMessage;

public class TestCommRPC
{
    public static void main(String[] args)
    {
        try
        {
            RPCChannel ch1 = new RPCChannel("localhost", 8070, "localhost", 8080);
            RPCChannel ch2 = new RPCChannel("localhost", 8080, "localhost", 8070);
            
            System.out.println("Conectando...");
            ch1.connect();
            ch2.connect();
            for (int i=0;i<5;i++)
            {
                System.out.println("Enviando mensaje 1->2");
                RPCMessage rpcm = new RPCMessage();
                rpcm.setElement("VALOR", "HOLA " + i);
                ch1.send(rpcm);
                Message mssg = ch2.receive();
                System.out.println("RECIBI: " + mssg.getElement("VALOR"));
                System.out.println("Enviando mensaje 2->1");
                rpcm = new RPCMessage();
                rpcm.setElement("VALOR", "HOLA " + i);
                ch2.send(rpcm);
                mssg = ch1.receive();
                System.out.println("RECIBI: " + mssg.getElement("VALOR"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }
}
