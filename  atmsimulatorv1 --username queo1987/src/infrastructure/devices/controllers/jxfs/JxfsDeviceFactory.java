/*
 * JxfsDeviceFactory.java
 *
 * Created on 21 de enero de 2006, 01:51 AM
 *
 */

package infrastructure.devices.controllers.jxfs;

import infrastructure.devices.controllers.IDeviceController;
import infrastructure.devices.events.DeviceException;
import java.util.Map;

/**
 * @author Arya Baher
 */
public class JxfsDeviceFactory {
    
    public static IDeviceController createDevice(Map context) throws DeviceException {

        IDeviceController device = null;
        
        String deviceClass = (String)context.get(JxfsImplConst.JXFS_DEVICE_CLASS);
        
        try {
            Class classDefinition = Class.forName(deviceClass);
            device = (IDeviceController) classDefinition.newInstance();
        } catch (Exception e) {
            throw new DeviceException(e);
        } 
        
        device.configure(context);
        
        return device;
    }
    
    
    
}
