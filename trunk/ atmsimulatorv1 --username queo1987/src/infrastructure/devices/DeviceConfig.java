package infrastructure.devices;

import infrastructure.dataaccess.PersistenceException;
import infrastructure.devices.general.IDeviceManager;
import java.util.HashMap;
import java.util.Map;
import infrastructure.dataaccess.Connectable;
import infrastructure.dataaccess.PersistentCollectionFactory;

/**
 * Configuracion de los dispositivos jxfs.
 */
public final class DeviceConfig implements Connectable
{
    private static DeviceConfig inst = null;
    
    private Map config;
    
    private DeviceConfig() 
    {
        config = new HashMap();
    }
    
    public static DeviceConfig getInstance()
    {
        if (inst == null)
        {
            inst = new DeviceConfig();
        }
        return inst;
    }
    
    /**
     * Pasa a trabajar con una nueva conexion...
     */
    public void setConnectionName(String name)
    {
        /**
         * Se saca la info...
         */
        try
        {
            Map aux = PersistentCollectionFactory.getInstance().getPersistentMap(name);
            config = aux;
        }
        catch (PersistenceException gex)
        {
            throw gex;
        }
    }
    
    public void setContext(Map context)
    {
        config.put(DeviceConstants.CONTEXT, context);
    }
    
    public void setDeviceManager(String deviceManagerClass)
    {
        config.put(DeviceConstants.DEVICE_MANAGER, deviceManagerClass);
    }
    
    public void doConfig(DeviceHandler deviceHandler)
    {
        IDeviceManager deviceManager = null;
        
        String deviceManagerClass = (String)config.get(DeviceConstants.DEVICE_MANAGER);
        
        try {
            Class classDefinition = Class.forName(deviceManagerClass);
            deviceManager = (IDeviceManager) classDefinition.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        Map context = (Map)config.get(DeviceConstants.CONTEXT);
        deviceManager.configure(context);
        
        deviceHandler.setDeviceManager(deviceManager);
    }
}
