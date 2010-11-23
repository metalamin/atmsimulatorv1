package infrastructure.devices;

import infrastructure.devices.general.IDeviceManager;


/**
 * Implementacion del handler de dispositivos para
 * la maquina de estados.
 * Singleton.
 */
final class DeviceHandlerImpl implements DeviceHandler
{
    //Singleton.
    private static DeviceHandlerImpl inst = null;
    
    //Factory y manager de dispositivos
    private IDeviceManager deviceManager;
    
    private DeviceHandlerImpl()
    {
        deviceManager = null;
        DeviceConfig.getInstance().doConfig(this);
    }
    
    public static DeviceHandlerImpl getInstance()
    {
        if (inst == null)
        {
            inst = new DeviceHandlerImpl();
        }
        return inst;
    }
    
    public void setDeviceManager(IDeviceManager dm)
    {
        this.deviceManager = dm;
    }

    public IDeviceManager getDeviceManager()
    {
        return this.deviceManager;
    }
}
