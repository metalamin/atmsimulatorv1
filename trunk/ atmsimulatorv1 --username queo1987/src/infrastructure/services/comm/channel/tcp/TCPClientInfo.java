package infrastructure.services.comm.channel.tcp;

/**
 * Info de Client
 */
public class TCPClientInfo extends TCPChannelInfo
{
    private String host;
    
    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }
    
    public String getType()
    {
        return TCPCommConstants.CLIENT_TYPE;
    }
}
