package infrastructure.dataaccess.broker;

public class BrokerFactory 
{
    public static Broker getBroker()
    {
        return BrokerImpl.getInstance();
    }
}
