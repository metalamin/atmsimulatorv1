package infrastructure.devices;

/**
 * Factory para el device handler.
 */
public class DeviceHandlerFactory
{
    public static DeviceHandler getDeviceHandler()
    {
        return DeviceHandlerImpl.getInstance();
    }
}
