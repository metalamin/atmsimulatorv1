package infrastructure.devices.controllers;
import util.GeneralException;
import infrastructure.devices.DeviceConstants;
import infrastructure.devices.DeviceHandlerFactory;
import infrastructure.devices.events.DeviceException;
import infrastructure.devices.general.IDeviceManager;
import domain.state.Action;
import domain.state.Event;


/**
 * Open a specific device
 */
public class OpenDeviceAction implements Action
{
    private String deviceName;
    
    public OpenDeviceAction()
    {
    }
    
    public void update(Event ev) throws GeneralException
    {
        String eventDeviceName=null;
        if (ev!=null && ev.getContext()!=null){
            eventDeviceName = (String)ev.getContext().get(DeviceConstants.DEVICE_NAME);
        }
        try{
            // get device name
            deviceName = (eventDeviceName!=null) ? eventDeviceName : deviceName;
            // get device manager
            IDeviceManager idm = DeviceHandlerFactory.getDeviceHandler().getDeviceManager(); 
            // get specific device and open it! 
            idm.getDevice(deviceName).open();
        }
        catch (DeviceException e){
            throw new GeneralException(e);
        }
    }
    
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }

    public String getDeviceName(){
        return deviceName;
    }    
    
    public String toString()
    {
        return "Open Device ( " + deviceName + ")";
    }    
}
