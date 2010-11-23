package infrastructure.services.comm.channel.tcp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import infrastructure.services.comm.channel.Channel;
import infrastructure.services.comm.CommException;
import infrastructure.services.comm.message.Message;
import infrastructure.services.comm.message.Packager;

/**
 * Cliente TCP.
 * Implementacion de Channel para el lado del cliente
 * en una comunicacion via sockets.
 */
class TCPClient implements Channel
{
    //El host con el que me voy a comunicar.
    private String host;
    //El numero de puerto por el que me voy a comunicar.
    private int port;
    //El socket por el que me voy a comunicar
    private Socket socket;
    //Packager
    private Packager packager;
    //Stream de salida
    private ObjectOutputStream oos;
    //Stream de entrada
    private ObjectInputStream ois;    
    
    public TCPClient(String host, int port)
    {
        this.host = host;
        this.port = port;
        this.packager = new DefaultTCPPackager();
    }
    
    /**
     * Conexion.
     */
    public void connect() throws CommException
    {
        try
        {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (ConnectException cex)
        {
            throw new CommException("Error en la conexión", cex);
        }
        catch (UnknownHostException uhe)
        {
            throw new CommException("Host desconocido", uhe);
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
            socket.close();
        }
        catch (IOException iex)
        {
            throw new CommException("Error al cerrar el socket", iex);
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
            throw new CommException("Error en la transmisión del mensaje", iex);
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
        /*
         * @TODO Sincronize with Albert
         */
        return (socket != null && socket.isConnected());
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
        return this.packager;
    }
    
}
