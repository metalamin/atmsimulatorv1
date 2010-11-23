package infrastructure.devices;

import infrastructure.devices.general.IDeviceManager;

/**
 * Handler de dispositivos para la maquina
 * de estados.
 */
public interface DeviceHandler
{
    //Seteo del device manager
    public void setDeviceManager(IDeviceManager deviceManager);
    public IDeviceManager getDeviceManager();
    
}
