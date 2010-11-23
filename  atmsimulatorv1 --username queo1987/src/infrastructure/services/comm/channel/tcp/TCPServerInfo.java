package infrastructure.services.comm.channel.tcp;

/**
 * Info de servidor...
 */
public class TCPServerInfo extends TCPChannelInfo
{
    public String getType()
    {
        return TCPCommConstants.SERVER_TYPE;
    }
}
