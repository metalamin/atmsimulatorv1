package infrastructure.devices.controllers.jxfs;

import com.jxfs.control.IJxfsBaseControl;
import com.jxfs.events.*;
import com.jxfs.forum.general.*;
import com.jxfs.forum.logging.StdLogFilter;
import com.jxfs.forum.logging.StdLoggerFile;
import com.jxfs.general.*;
import util.GeneralException;
import java.io.Serializable;
import java.util.*;
import infrastructure.devices.controllers.*;
import infrastructure.devices.general.IDeviceManager;
import domain.state.Event;
import domain.statemachine.IUpdateListener;

public class DeviceManager implements IDeviceManager, IUpdateListener
{

    public DeviceManager()
    {
        configKey = "";
        workstationName = "";
        initialized = false;
        deviceManager = this;
    }

    public static DeviceManager getReference()
    {
        if (deviceManager == null){
            deviceManager = new DeviceManager();
        }
        return deviceManager;
    }

    public synchronized void configure(Map context)
    {
        this.context = context;

        workstation_name =   (String) context.get(JxfsImplConst.JXFS_WORKSTATION_NAME);
        config_key       =   (String) context.get(JxfsImplConst.JXFS_CONFIG_KEY);
        
        logfilename      =   (String) context.get(JxfsImplConst.JXFS_LOG_FILE_NAME);
        initializeLogger(logfilename);
        
        sdm_name         =   (String) context.get(JxfsImplConst.JXFS_SDM_NAME);
        sdm_addparmcount = 0;
        sdm_addparms = "";
        try
        {
            sdm_addparmcount = ((Integer)context.get(JxfsImplConst.JXFS_SDM_ADDPARMCOUNT)).intValue();
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        sdm_addparms = (String)context.get(JxfsImplConst.JXFS_SDM_ADDPARM);
        
        String params = sdm_name + "," + workstation_name + "," + config_key;
        if(sdm_addparmcount > 0)
            params += "," + sdm_addparms;
        
        for(int k = 0; k <= 10;)
        {
            initialize(params);
            break;
        }
        //initialize(params);

        Vector deviceList = (Vector) context.get(JxfsImplConst.JXFS_DEVICES_INFO);
        addDevices(deviceList);
        
        myLogger.writeLog(this, sdm_name, 104, "successfully generated the JxfsDeviceManager");
        
    }    
    
    public void initializeLogger(String logFileName)
    {
        if(logFileName.length() > 0)
        {
            stdLogFile_ = new StdLoggerFile();
            if(!stdLogFile_.initialize(logFileName))
                System.out.println("Error during StdLoggerFile initialization for " + logFileName);
            StdLogFilter stdlogfilter = new StdLogFilter();
            myLogger.addFilter(stdlogfilter);
            myLogger.addLogListener(stdLogFile_);
        }
        myLogger.registerModule(sdm_name, sdm_name + ".registerModule");
    }
    
    public synchronized void initialize(String s)
    {
        initializeSDM(s);
        initializeDSs();
    }

    public synchronized void initializeSDM(String s)
    {
        try{
            JxfsDeviceManager.getReference().initializeSDM(s);
        }
        catch(JxfsException e){
            e.printStackTrace();
        }
    }

    public synchronized void initializeDSs()
    {
        try{
            JxfsDeviceManager.getReference().initializeDSs();
        }
        catch(JxfsException e){
            e.printStackTrace();
        }
    }

    public Vector getDeviceList(int i)
    {
        return DeviceManager.getReference().getDeviceList(i);
    }

    public DeviceManager getSpecificDeviceManager()
    {
        return this;
    }

    public Vector getDeviceListFor(Class class1, int i)
    {
        return DeviceManager.getReference().getDeviceListFor(class1, i);
    }

    public Vector getRunningServices()
    {
        return DeviceManager.getReference().getRunningServices();
    }

    public void update(Event ev) throws GeneralException{
        Iterator i=updateListeners.iterator();
        while (i.hasNext()){
            ((IUpdateListener)i.next()).update(ev);
        };        
    }

    public boolean addUpdateListener(IUpdateListener updateListener){
        boolean result=false;
        try{
            updateListeners.add(updateListener);
            result = true;
        }
        catch(Exception e){
            result = false;
        }
        return result;
    }
    
    public void addDevices(Vector devices){
        if (devices != null){
            Iterator i = devices.iterator();
            while (i.hasNext()){
                addDevice((Map)i.next());
            }
        }
    }
    
    public void addDevice(Map context)
    {
        String deviceName = (String)context.get(JxfsImplConst.JXFS_DEVICE_NAME);
        String deviceClass = (String)context.get(JxfsImplConst.JXFS_DEVICE_CLASS);
        
        IDeviceController deviceController = (IDeviceController)devices.get(deviceName);
        
        if (deviceController == null){
            
            IJxfsBaseControl ijxfsbasecontrol = null;
            try{
                ijxfsbasecontrol = JxfsDeviceManager.getReference().getDevice(deviceName);
                context.put(JxfsImplConst.JXFS_KERNEL_DEVICE_OBJ, ijxfsbasecontrol);
                deviceController = JxfsDeviceFactory.createDevice(context);
            }
            catch(Exception e){
                e.printStackTrace();
            // Generar un evento de excepcion para reportar el error
            }
        }
        deviceController.addUpdateListener(this);
        devices.put(deviceName, deviceController);
    }
    
    public IDeviceController getDevice(String deviceName)
    {
        IDeviceController deviceController = (IDeviceController)devices.get(deviceName);
        return deviceController;
    }

    public Serializable getValueForKey(String s)
    {
        Serializable serializable = null;
        try{
            serializable = JxfsDeviceManager.getReference().getValueForKey(s);
        }
        catch(JxfsException e){
            
        }
        return serializable;
    }

    public void setValueForKey(String s, Serializable serializable)
    {
        try{
            JxfsDeviceManager.getReference().setValueForKey(s, serializable);
        }
        catch(JxfsException e){
            
        }
    }

    public void addStatusListener(IJxfsStatusListener ijxfsstatuslistener)
    {
        DeviceManager.getReference().addStatusListener(ijxfsstatuslistener);
    }

    public void removeStatusListener(IJxfsStatusListener ijxfsstatuslistener)
    {
        DeviceManager.getReference().removeStatusListener(ijxfsstatuslistener);
    }

    public void addKeyValueChangeListener(IJxfsKeyValueChangeListener ijxfskeyvaluechangelistener, String s)
    {
        try{
            JxfsDeviceManager.getReference().addKeyValueChangeListener(ijxfskeyvaluechangelistener, s);
        }
        catch(JxfsException e){
            
        }
    }

    public void removeKeyValueChangeListener(IJxfsKeyValueChangeListener ijxfskeyvaluechangelistener)
        throws JxfsException
    {
        DeviceManager.getReference().removeKeyValueChangeListener(ijxfskeyvaluechangelistener);
    }

    public String getDeviceManagerVersion()
    {
        String s = null;
        return DeviceManager.getReference().getDeviceManagerVersion().toString();
    }

    public void start(String s)
    {
        try{
            JxfsDeviceManager.getReference().start(s);
        }
        catch(JxfsException e){
            
        }        
    }

    public void stop(String s)
    {
        try{
            JxfsDeviceManager.getReference().stop(s);
        }
        catch(JxfsException e){
            
        }        
    }

    public synchronized void shutdown()
    {
        DeviceManager.getReference().shutdown();
    }

    public synchronized void remoteShutdown()
    {
        try{
            JxfsDeviceManager.getReference().remoteShutdown();
        }
        catch(JxfsException e){
            
        }        
    }

    public Vector getErrorLogListeners()
    {
        Vector v = null;
        try{
            v = JxfsDeviceManager.getReference().getErrorLogListeners();
        }
        catch(JxfsException e){
            
        }        
        return v;
    }
    
    public Vector getLogListeners()
    {
        Vector v = null;
        try{
            v = JxfsDeviceManager.getReference().getLogListeners();
        }
        catch(JxfsException e){
            
        }
        return v;
    }

    public int addErrorLogListener(String s, String s1)
    {
        int i = 0;
        try{
            i = JxfsDeviceManager.getReference().addErrorLogListener(s, s1).getId();
        }
        catch(JxfsException e){
            
        }
        return i;
    }

    public int addLogListener(String s, String s1)
    {
        int i = 0;
        try{
            i = JxfsDeviceManager.getReference().addLogListener(s, s1).getId();
        }
        catch(JxfsException e){

        }
        return i;
    }

    public boolean removeErrorLogListener(int i)
    {
        boolean b = false;
        try{
            b = JxfsDeviceManager.getReference().removeErrorLogListener(i);
        }
        catch(JxfsException e){

        }
        return b;        
    }

    public boolean removeLogListener(int i)
    {
        boolean b = false;
        try{
            b = JxfsDeviceManager.getReference().removeLogListener(i);
        }
        catch(JxfsException e){

        }
        return b;        
    }

    public int addFilter(String s, String s1)
    {
        int i = 0;
        try{
            i = JxfsDeviceManager.getReference().addFilter(s, s1).getId();
        }
        catch(JxfsException e){

        }
        return i;        
    }

    public boolean removeFilter(int i)
    {
        boolean b = false;
        try{
            b = JxfsDeviceManager.getReference().removeFilter(i);
        }
        catch(JxfsException e){

        }
        return b;
    }

    public void removeAllFilters()
    {
        try{
            JxfsDeviceManager.getReference().removeAllFilters();
        }
        catch(JxfsException e){

        }
    }

    public Vector getFilters()
    {
        Vector v = null;
        try{
            v = JxfsDeviceManager.getReference().getFilters();
        }
        catch(JxfsException e){
            
        }
        return v;        
    }

    public Hashtable getOrigins()
    {
        Hashtable h = null;
        try{
            h = JxfsDeviceManager.getReference().getOrigins();
        }
        catch(JxfsException e){

        }
        return h;
    }

    public void applicationKeyValueChanged(String s, Serializable serializable)
    {
        try{
            JxfsDeviceManager.getReference().applicationKeyValueChanged(s, serializable);
        }
        catch(JxfsException e){
        }
    }

    public void deviceKeyValueChanged(String s, JxfsLocalDeviceInformation jxfslocaldeviceinformation, Serializable serializable)
    {
        try{
            JxfsDeviceManager.getReference().deviceKeyValueChanged(s, jxfslocaldeviceinformation, serializable);
        }
        catch(JxfsException e){
        }
    }
    
    public String toString()
    {
        return "Device Manager - " + (initialized ? configKey + "@" + workstationName : "(not initialized)");
    }

    public String getDescription() {
        return sdm_name;
    }
    
    private String configKey;
    private String workstationName;
    private ISpecificDeviceManager specificDeviceManager;
    private static DeviceManager deviceManager = new DeviceManager();
    private static JxfsLogger myLogger = JxfsLogger.getReference();    
    private Map devices = new HashMap();
    private boolean initialized;
    private StdLoggerFile stdLogFile_;
    private Map context;
    private String workstation_name;
    private String config_key;
    private String logfilename;
    private String sdm_name;
    private int sdm_addparmcount;
    private String sdm_addparms;
    private Vector updateListeners=new Vector();
}
