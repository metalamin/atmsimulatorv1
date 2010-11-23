/*
 * IDeviceController.java
 *
 * Created on 16 de octubre de 2005, 12:15 PM
 *
 */
package infrastructure.devices.controllers;
import infrastructure.devices.events.*;
import java.util.Map;
import domain.state.Event;
import domain.statemachine.IUpdateListener;
/**
 * @author Arya Baher
 * General interface for device controllers
 */
public interface IDeviceController {
    
    public void configure(Map context);
    
    public abstract int open()
        throws DeviceException;

    public abstract int close()
        throws DeviceException;

    public abstract boolean claim(int i)
        throws DeviceException;

    public abstract boolean release(int i)
        throws DeviceException;

//    public abstract void cancel(int i)
//        throws DeviceException;

//    public abstract DeviceStatus getStatus()
//        throws DeviceException;

//    public abstract String getDeviceName();

//    public abstract String getPhysicalDeviceDescription()
//        throws DeviceException;
//
//    public abstract String getPhysicalDeviceName()
//        throws DeviceException;

//    public abstract boolean updateFirmware()
//        throws DeviceException;
//
//    public abstract int getFirmwareStatus()
//        throws DeviceException;

//    public abstract DeviceVersion getDeviceFirmwareVersion()
//        throws DeviceException;
//
//    public abstract DeviceVersion getRepositoryFirmwareVersion()
//        throws DeviceException;

//    public abstract boolean isPowerSaveModeSupported()
//        throws DeviceException;
//
//    public abstract int wakeUpFromPowerSave()
//        throws DeviceException;

//    public abstract int directIO(int i, JxfsType jxfstype)
//        throws DeviceException;

//    public abstract void deregisterDevice()
//        throws DeviceException;

//    public abstract void registerService(DeviceBaseService idevicebaseservice, DeviceInformation deviceinformation)
//        throws DeviceException;

//    public abstract void connectionFailure();

    public boolean addUpdateListener(IUpdateListener updatelistener);
    
    public void notifyUpdateListeners(Event ev);
    
//    public abstract boolean removeUpdateListener(IUpdateListener updatelistener);    
    
//    public abstract boolean addOperationCompleteListener(IDeviceOperationCompleteListener operationcompletelistener);
//
//    public abstract boolean addIntermediateListener(IDeviceIntermediateListener intermediatelistener);
//
//    public abstract boolean addStatusListener(IDeviceStatusListener statuslistener);
//
//    public abstract boolean removeOperationCompleteListener(IDeviceOperationCompleteListener operationcompletelistener);
//
//    public abstract boolean removeIntermediateListener(IDeviceIntermediateListener intermediatelistener);
//
//    public abstract boolean removeStatusListener(IDeviceStatusListener statuslistener);

   
}
