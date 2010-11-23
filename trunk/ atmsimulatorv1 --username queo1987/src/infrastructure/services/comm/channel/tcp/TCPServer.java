package infrastructure.services.comm.channel.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.CommException;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Server TCP.
 * Implementacion de Channel que acepta conexiones
 * via sockets.
 * Esta implementacion ACEPTA UNA SOLA CONEXION...
 * por cosas mas complicadas, disque el interno 
 * correspondiente.
 */
class TCPServer implements Channel
{
    //Packager
    private Packager packager;
    //El puerto por el que se escucha
    private int port;
    //Stream de entrada
    private ObjectInputStream ois;
    //Stream de salida
    private ObjectOutputStream oos;
    //ServerSocket
    private ServerSocket ssocket;
    //ClientSocket
    private Socket csocket;
    
    public TCPServer(int port)
    {
        this.port = port;
        packager = new DefaultTCPPackager();
    }
    
    /**
     * Conexion.
     */
    public void connect() throws CommException
    {
        try
        {
            ssocket = new ServerSocket(port);
            csocket = ssocket.accept();
            oos = new ObjectOutputStream(csocket.getOutputStream());
            ois = new ObjectInputStream(csocket.getInputStream());
        }
        catch (ConnectException cex)
        {
            cex.printStackTrace();
            throw new CommException("Error en la conexión", cex);
        }
        catch (IOException iex)
        {
            iex.printStackTrace();
            throw new CommException("Error en la conexión", iex);
        }
    }
    
    /**
     * Desconexion.
     */
    public void disconnect() throws CommException
    {
        try
        {
            oos.close();
            ois.close();
            csocket.close();
            ssocket.close();
        }
        catch (IOException iex)
        {
            throw new CommException("Error al cerrar la conexión", iex);
        }
    }
    
    /**
     * Envio de mensajes.
     */
    public void send(Message msg) throws CommException
    {
        try
        {
            oos.writeObject(packager.pack(msg));
        }
        catch (IOException iex)
        {
            throw new CommException("Error en el envío del mensaje", iex);
        }
    }
    
    /**
     * Recepcion de mensajes.
     */
    public Message receive() throws CommException
    {
        try
        {
            Object mssg = ois.readObject();
            return packager.unpack(mssg);

        }
        catch (IOException iex)
        {
            throw new CommException("Error en la recepción", iex);
        }
        catch (ClassNotFoundException cnfx)
        {
            throw new CommException("Error en el levantado de una clase", cnfx);
        }
    }
    
    public boolean isConnected()
    {
        return (csocket != null && csocket.isConnected());
    }    
    
    /**
     * Seteo del packager.
     */
    public void setPackager(Packager pckgr)
    {
        this.packager = pckgr;
    }
    
    /**
     * Devolucion del packager.
     */
    public Packager getPackager()
    {
        return packager;
    }
}
