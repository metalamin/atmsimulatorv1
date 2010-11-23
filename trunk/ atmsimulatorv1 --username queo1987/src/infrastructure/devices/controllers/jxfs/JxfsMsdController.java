
package infrastructure.devices.controllers.jxfs;

import com.jxfs.control.msd.IJxfsMSDConst;
import com.jxfs.control.msd.JxfsMSDReadData;
import com.jxfs.control.msd.JxfsMSDTrackSelection;
import com.jxfs.control.msd.JxfsMagStripe;
import com.jxfs.control.msd.JxfsMotorizedCardConst;
import com.jxfs.events.IJxfsOperationCompleteListener;
import com.jxfs.events.IJxfsStatusListener;
import com.jxfs.events.JxfsException;
import com.jxfs.events.JxfsOperationCompleteEvent;
import com.jxfs.events.JxfsStatusEvent;
import com.jxfs.general.IJxfsConst;
import com.jxfs.general.JxfsDeviceManager;
import config.GlobalConfig;
import domain.state.Event;
import domain.statemachine.IUpdateListener;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.msd.IMsdController;
import infrastructure.devices.events.DeviceEvent;
import infrastructure.devices.general.IDeviceConst;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import presentation.IGUIBuilder;
import util.GeneralException;

public class JxfsMsdController
    implements IMsdController,
        IJxfsOperationCompleteListener, IJxfsStatusListener, IJxfsConst
               
{
    public void configure(Map context)
    {
        event_counter_ = 0;
        callID_ = 0;
        ocID_ = -1;
        gui_closed_ = false;
        CloseCompleted = new Boolean(false);
        byte byte0 = 3;
        int i = 1;
        byte byte1 = 10;
        byte byte2 = 2;
        byte byte3 = 20;
        byte byte4 = 70;
        claimed_ = false;
        opened_ = false;
        
        try{
            magstripename_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_NAME);
            msd_ = (JxfsMagStripe)JxfsDeviceManager.getReference().getDevice(magstripename_);
        }catch(JxfsException e){
            
        }

        title_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_TITLE);
        Jxfs_imagepath_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_IMAGEPATH);

        track1Selected_ = context.get(IMsdConst.MSD_TRACK1_SELECTED)==null ? true : (Boolean)context.get(IMsdConst.MSD_TRACK1_SELECTED);
        track2Selected_ = context.get(IMsdConst.MSD_TRACK2_SELECTED)==null ? true : (Boolean)context.get(IMsdConst.MSD_TRACK2_SELECTED);
        track3Selected_ = context.get(IMsdConst.MSD_TRACK3_SELECTED)==null ? true : (Boolean)context.get(IMsdConst.MSD_TRACK3_SELECTED);
        
        msd_.addOperationCompleteListener(this);
        msd_.addStatusListener(this);
        
        msdController = this;
    }
 
    public int open() {
        try{
            synchronized(JxfsMsdController.this){
                callID_ = msd_.open();
            }
        }
        catch(JxfsException jxfsexception){
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
             notifyUpdateListeners(ev);
        };
        return callID_;
    }

    public boolean claim(int miliseconds) {
        try {
            if(msd_.claim(miliseconds)) {
                claimed_ = true;
            } else {
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }
    
    public void read() {
        try 
        {
            JxfsMSDTrackSelection jxfsmsdtrackselection = new JxfsMSDTrackSelection(track1Selected_, track2Selected_, track3Selected_);
            synchronized(JxfsMsdController.this)
            {
                callID_ = msd_.readData(jxfsmsdtrackselection);
            }
        }
        catch(JxfsException jxfsexception)
        {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    };

    public void write() {
        try 
        {
                    Vector vector = new Vector(3);
                    if(track1Selected_)
                        vector.addElement(new String("1").getBytes());
                    else
                        vector.addElement(null);
                    if(track2Selected_)
                        vector.addElement(new String("2").getBytes());
                    else
                        vector.addElement(null);
                    if(track3Selected_)
                        vector.addElement(new String("3").getBytes());
                    else
                        vector.addElement(null);
                    synchronized(JxfsMsdController.this)
                    {
                        callID_ = msd_.writeData(vector, false);
                    }
        }
        catch(JxfsException jxfsexception)
        {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    };

    public boolean release(int miliseconds) {
        try {
            msd_.release(miliseconds);
            claimed_ = false;
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }        

    public void cancel() {
        try {
            msd_.cancel(callID_);
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }

    public int close() {
        try {
            callID_ = msd_.close();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return callID_;
    }       

    public void eject() {
        try {
            callID_ = msd_.ejectCard();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }           

    public void retain() {
        try {
            callID_ = msd_.retainCard();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }         
    
    public synchronized void operationCompleteOccurred(JxfsOperationCompleteEvent jxfsoperationcompleteevent)
    {
        String track1="";
        String track2="";
        String track3="";
        Event ev;
        int i = jxfsoperationcompleteevent.getOperationID();
        ocID_ = jxfsoperationcompleteevent.getIdentificationID();
        switch(i)
        {
        case IJxfsMSDConst.JXFS_O_MSD_READDATA: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsConst.JXFS_RC_SUCCESSFUL: // '\0'
                String s = "";
                JxfsMSDReadData jxfsmsdreaddata = (JxfsMSDReadData)jxfsoperationcompleteevent.getData();
                Vector vector = jxfsmsdreaddata.getDataRead();
                if(jxfsmsdreaddata.getTracksRead().isTrack1())
                {
                    track1 = new String((String)vector.elementAt(0));
                }
                if(jxfsmsdreaddata.getTracksRead().isTrack2())
                {
                    track2 = new String((String)vector.elementAt(1));
                }
                if(jxfsmsdreaddata.getTracksRead().isTrack3())
                {
                    track3 = new String((String)vector.elementAt(2));
                }
                ev = new DeviceEvent(IMsdConst.O_MSD_READDATA, IMsdConst.MSD_THROWER);
                ev.getContext().put(IMsdConst.MSD_TRACK1, track1);
                ev.getContext().put(IMsdConst.MSD_TRACK2, track2);
                ev.getContext().put(IMsdConst.MSD_TRACK3, track3);
                notifyUpdateListeners(ev);
                break;

            case IJxfsConst.JXFS_E_CANCELLED: 
                ev = new DeviceEvent(IDeviceConst.E_CANCELLED, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);                
                break;

            case IJxfsMSDConst.JXFS_E_MSD_NOMEDIA: 
                ev = new DeviceEvent(IMsdConst.E_MSD_NOMEDIA, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);                
                break;

            case IJxfsMSDConst.JXFS_E_MSD_READFAILURE: 
                ev = new DeviceEvent(IMsdConst.E_MSD_READFAILURE, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);                
                break;
                
            default:
                ev = new DeviceEvent(IMsdConst.MSD_EXCEPTION_TYPE, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);                
                break;
            }
            break;

        case IJxfsConst.JXFS_O_OPEN: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case 0: // '\0'
                try{
                    IGUIBuilder guiBuilder = (IGUIBuilder)GlobalConfig.getInstance().getProperty(IMsdConst.MSD_THROWER);
                    ev = new DeviceEvent(IDeviceConst.O_OPEN, IMsdConst.MSD_THROWER);
                    ev.getContext().put(IMsdConst.MSD_THROWER, guiBuilder);
                    notifyUpdateListeners(ev);                   
                    opened_ = true;
                }
                catch(GeneralException e){
                    e.printStackTrace();
                }
                
                break;

            case IJxfsConst.JXFS_E_CANCELLED: 
                ev = new DeviceEvent(IDeviceConst.E_CANCELLED, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);                
                break;

            default:
                ev = new DeviceEvent(IMsdConst.MSD_EXCEPTION_TYPE, IMsdConst.MSD_THROWER);
                notifyUpdateListeners(ev);   
                break;
            }
            break;

        case IJxfsConst.JXFS_O_CLOSE: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case 0: // '\0'
                opened_ = false;
                break;

            case 1021: 
                break;

            default:
                break;
            }
            synchronized(CloseCompleted)
            {
                CloseCompleted.notifyAll();
            }
            break;

        default:
            break;
        }
    }

    public void statusOccurred(JxfsStatusEvent jxfsstatusevent)
    {
        Event ev = new DeviceEvent(IMsdConst.MSD_THROWER, IMsdConst.MSD_STATUS_TYPE);
        notifyUpdateListeners(ev);
    }

    public void GUICloseEvent()
    {
        int i;
        if(claimed_)
        {
        } else
        {
            gui_closed_ = true;
            if(opened_)
            {
                try
                {
                    callID_ = msd_.close();
                }
                catch(JxfsException jxfsexception)
                {
                }
                synchronized(CloseCompleted)
                {
                    try
                    {
                        CloseCompleted.wait(10000L);
                    }
                    catch(InterruptedException interruptedexception) { }
                }
            }
            try
            {
                msd_.deregisterDevice();
            }
            catch(JxfsException jxfsexception1)
            {
            }
        }
    }

    private void setRWButtons()
    {
        boolean flag;
        if(track1Selected_ || track2Selected_ || track3Selected_)
            flag = true;
        else
            flag = false;
    }

    public static JxfsMsdController getInstance(){
        return msdController;
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
    
    public void notifyUpdateListeners(Event ev){
        Iterator i=updateListeners.iterator();
        while (i.hasNext()){
            try{
                ((IUpdateListener)i.next()).update(ev);
            }
            catch(GeneralException e){
                // VER QUE HACER ACA
            }
        };
    }
    
    private JxfsMagStripe msd_;
    private String magstripename_;
    private String title_;
    private boolean claimed_;
    private boolean opened_;
    int event_counter_;
    private String Jxfs_imagepath_;
    private boolean track1Selected_;
    private boolean track2Selected_;
    private boolean track3Selected_;
    private int callID_;
    private int ocID_;
    private boolean gui_closed_;
    private Boolean CloseCompleted;
    private Vector updateListeners=new Vector();
    private static JxfsMsdController msdController;    

}
