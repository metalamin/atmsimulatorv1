package infrastructure.services.comm.channel.tcp;

/**
 * Bean que representa la informacion
 * de un canal.
 */
public abstract class TCPChannelInfo
{
    //Puerto de comunicaciones.
    private int port;
    //Nombre del canal.
    private String name;
    
    public TCPChannelInfo()
    {
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public abstract String getType();
}
