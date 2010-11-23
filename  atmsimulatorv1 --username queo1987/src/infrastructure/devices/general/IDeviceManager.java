package infrastructure.devices.general;

import java.util.Vector;
import infrastructure.devices.controllers.IDeviceController;
import java.util.Map;
import domain.statemachine.IUpdateListener;


public interface IDeviceManager
{
    public void configure(Map context);

//    public void initialize(String s);

//    public void initializeSDM(String s);
//
//    public void initializeDSs();
    
//    public Vector getDeviceList(int i);

//    public IDeviceManager getSpecificDeviceManager();

//    public Vector getDeviceListFor(Class class1, int i);

//    public abstract Vector getRunningServices();

    /**
     * 'devices' is a vector of contexts, each context has information 
     *           about one specific device
     */
    public void addDevices(Vector devices);

    /**
     * 'context' has information about a specific device
     */
    public void addDevice(Map context);
    
    public IDeviceController getDevice(String deviceName);
    
    public boolean addUpdateListener(IUpdateListener updateListener);

//    public abstract Serializable getValueForKey(String s);
//
//    public abstract void setValueForKey(String s, Serializable serializable);

    //public abstract void addStatusListener(IJxfsStatusListener ijxfsstatuslistener);

    //public abstract void removeStatusListener(IJxfsStatusListener ijxfsstatuslistener);

//    public void addKeyValueChangeListener(IJxfsKeyValueChangeListener ijxfskeyvaluechangelistener, String s)
//    {
//        try{
//            JxfsDeviceManager.getReference().addKeyValueChangeListener(ijxfskeyvaluechangelistener, s);
//        }
//        catch(JxfsException e){
//            
//        }
//    }
//
//    public void removeKeyValueChangeListener(IJxfsKeyValueChangeListener ijxfskeyvaluechangelistener)
//        throws JxfsException
//    {
//        JxfsDeviceManager.getReference().removeKeyValueChangeListener(ijxfskeyvaluechangelistener);
//    }

//    public String getDeviceManagerVersion();
//
//    public void start(String s);
//
//    public void stop(String s);
//
    public void shutdown();
//    
//    public void remoteShutdown();
//
//    public Vector getErrorLogListeners();
//    
//    public Vector getLogListeners();
//
//    public int addErrorLogListener(String s, String s1);
//
//    public int addLogListener(String s, String s1);
//
//    public boolean removeErrorLogListener(int i);
//
//    public boolean removeLogListener(int i);
//
//    public int addFilter(String s, String s1);
//    
//    public boolean removeFilter(int i);
//
//    public void removeAllFilters();
//
//    public Vector getFilters();
//
//    public Hashtable getOrigins();
//
//    public void applicationKeyValueChanged(String s, Serializable serializable);

//    public void deviceKeyValueChanged(String s, JxfsLocalDeviceInformation jxfslocaldeviceinformation, Serializable serializable)
//    {
//        try{
//            JxfsDeviceManager.getReference().deviceKeyValueChanged(s, jxfslocaldeviceinformation, serializable);
//        }
//        catch(JxfsException e){
//        }
//    }

    public abstract String toString();

}
