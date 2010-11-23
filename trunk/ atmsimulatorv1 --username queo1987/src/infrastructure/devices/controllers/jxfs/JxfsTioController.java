
package infrastructure.devices.controllers.jxfs;

import com.jxfs.control.tio.IJxfsTIOConst;
import com.jxfs.control.tio.JxfsTIO;
import com.jxfs.control.tio.JxfsTIOResolution;
import com.jxfs.events.IJxfsIntermediateListener;
import com.jxfs.events.IJxfsOperationCompleteListener;
import com.jxfs.events.IJxfsStatusListener;
import com.jxfs.events.JxfsException;
import com.jxfs.events.JxfsIntermediateEvent;
import com.jxfs.events.JxfsOperationCompleteEvent;
import com.jxfs.events.JxfsStatusEvent;
import com.jxfs.general.IJxfsConst;
import com.jxfs.general.JxfsDeviceManager;
import config.GlobalConfig;
import domain.state.Event;
import domain.statemachine.IUpdateListener;
import infrastructure.devices.controllers.tio.ITioConst;
import infrastructure.devices.controllers.tio.ITioController;
import infrastructure.devices.events.DeviceEvent;
import infrastructure.devices.general.IDeviceConst;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import presentation.IGUIBuilder;
import util.GeneralException;
    
public class JxfsTioController
    implements ITioController, IJxfsOperationCompleteListener, IJxfsStatusListener, IJxfsConst, IJxfsIntermediateListener
{

    public void configure(Map context)
    {
        event_counter_ = 0;
        callID_ = 0;
        ocID_ = -1;
        setLEDID_ = -1;
        setLEDID2_ = -1;
        gui_closed_ = false;
        CloseCompleted = new Boolean(false);
        byte byte0 = 3;
        int i = 1;
        byte byte1 = 10;
        byte byte2 = 2;
        byte byte3 = 20;
        byte byte4 = 70;
        try{
            tioname_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_NAME);
            tio_ = (JxfsTIO)JxfsDeviceManager.getReference().getDevice(tioname_);
        }catch(JxfsException e){
            e.printStackTrace();
        }
        title_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_TITLE);
        Jxfs_imagepath_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_IMAGEPATH);
        
        claimed_ = false;
        opened_ = false;

        flushSelected_ = context.get(ITioConst.TIO_FLUSH)==null ? true : (Boolean)context.get(ITioConst.TIO_FLUSH);
        autoEndSelected_ = context.get(ITioConst.TIO_AUTOEND)==null ? false : (Boolean)context.get(ITioConst.TIO_AUTOEND);
        displayLightOn_ = context.get(ITioConst.TIO_DISPLAY_LIGHT_ON)==null ? true : (Boolean)context.get(ITioConst.TIO_DISPLAY_LIGHT_ON);
        modeSelected_ = context.get(ITioConst.TIO_MODE)==null ? 0 : (Integer)context.get(ITioConst.TIO_MODE);
        numOfChars_ = context.get(ITioConst.TIO_NUM_OF_CHARS)==null ? 0 : (Integer)context.get(ITioConst.TIO_NUM_OF_CHARS);

        tio_.addOperationCompleteListener(this);
        tio_.addIntermediateListener(this);
        tio_.addStatusListener(this);
        
        try
        {
            JxfsTIOResolution jxfstioresolution = tio_.getResolution();
        }
        catch(JxfsException jxfsexception){ 
            jxfsexception.printStackTrace();
        }

        tioController = this;

    }

    public int open() {
        try {
            synchronized(JxfsTioController.this) {
                callID_ = tio_.open();
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
             notifyUpdateListeners(ev);
        };
        return callID_;
    }

    public int close() {
        try {
            callID_ = tio_.close();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return callID_;
    }       

    public boolean claim(int miliseconds) {
        try {
            if(tio_.claim(miliseconds)) {
                claimed_ = true;
            } else {
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }

    public void read() {
        try 
        {
            try 
            {
                setLEDID_ = tio_.setLED(-5, 8008);
            }
            catch(JxfsException jxfsexception1)
            {
                Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
                notifyUpdateListeners(ev);
            }
            char c = '\0';
            switch(modeSelected_)
            {
            case 1: // '\001'
                c = '\u1F4F';
                break;

            case 2: // '\002'
                c = '\u1F50';
                break;

            default:
                c = '\u1F4E';
                break;
            }
            synchronized(JxfsTioController.this)
            {
                callID_ = tio_.readKeyboardData(numOfChars_, 8012, 1, 0, c, 0, 8, false, flushSelected_, autoEndSelected_);
            }
        }
        catch(JxfsException jxfsexception2)
        {
            Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    };

    public boolean release(int miliseconds) {
        try {
            tio_.release(miliseconds);
            claimed_ = false;
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }    
    
    public synchronized void intermediateOccurred(JxfsIntermediateEvent jxfsintermediateevent){
        Event ev;
        String value = new String("");
        int i = jxfsintermediateevent.getOperationID();
        ocID_ = jxfsintermediateevent.getIdentificationID();
        switch(i)
        {
        case IJxfsTIOConst.JXFS_O_TIO_READ: 
            try
            {
                setLEDID2_ = tio_.setLED(IJxfsTIOConst.JXFS_TIO_LED_NORMAL, IJxfsTIOConst.JXFS_TIO_LED_OFF);
            }
            catch(JxfsException jxfsexception)
            {
                jxfsexception.printStackTrace();
            }
            switch(jxfsintermediateevent.getReason())
            {
             case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: //IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                Vector vector = (Vector)jxfsintermediateevent.getData();
                if(vector == null)
                {
                    
                } else
                {
                    for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
                    {
                        Integer integer = (Integer)enumeration.nextElement();
                        value = value.concat(integer.toString());
                    }
                }
                ev = new DeviceEvent(ITioConst.O_TIO_READ_KEY, ITioConst.TIO_THROWER);
                ev.getContext().put("TEXT", value);
                notifyUpdateListeners(ev);
                break;

             case IJxfsTIOConst.JXFS_E_TIO_READ:
                ev = new DeviceEvent(ITioConst.E_TIO_READ, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;

             case IJxfsTIOConst.JXFS_E_TIO_LED:
                ev = new DeviceEvent(ITioConst.E_TIO_LED, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;
                
             case IJxfsConst.JXFS_E_CANCELLED: 
                ev = new DeviceEvent(ITioConst.TIO_EXCEPTION_TYPE, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;

             default:
                ev = new DeviceEvent(ITioConst.TIO_EXCEPTION_TYPE, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_CLEAR: 
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_LIGHT: 
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_BEEP: 
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_LED: 
            if(ocID_ == setLEDID_ || ocID_ == setLEDID2_)
                break;
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_OPEN: 
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                try{
                    IGUIBuilder guiBuilder = (IGUIBuilder)GlobalConfig.getInstance().getProperty(ITioConst.TIO_THROWER);
                    ev = new DeviceEvent(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
                    ev.getContext().put(ITioConst.TIO_THROWER, guiBuilder);
                    notifyUpdateListeners(ev);                   
                    opened_ = true;
                }
                catch(GeneralException e){
                    e.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsConst.JXFS_O_CLOSE: 
            switch(jxfsintermediateevent.getReason())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                opened_ = false;
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
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
    
    public synchronized void operationCompleteOccurred(JxfsOperationCompleteEvent jxfsoperationcompleteevent)
    {
        Event ev;
        String value = new String("");        
        int i = jxfsoperationcompleteevent.getOperationID();
        ocID_ = jxfsoperationcompleteevent.getIdentificationID();
        switch(i)
        {
        case IJxfsTIOConst.JXFS_O_TIO_READ: 
            try
            {
                setLEDID2_ = tio_.setLED(IJxfsTIOConst.JXFS_TIO_LED_NORMAL, IJxfsTIOConst.JXFS_TIO_LED_OFF);
            }
            catch(JxfsException jxfsexception)
            {
                jxfsexception.printStackTrace();
            }
            switch(jxfsoperationcompleteevent.getResult())
            {
             case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                Vector vector = (Vector)jxfsoperationcompleteevent.getData();
                if(vector == null)
                {
                    
                } else
                {
                    for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
                    {
                        Integer integer = (Integer)enumeration.nextElement();
                        value = value.concat(integer.toString());
                    }
                }
                ev = new DeviceEvent(ITioConst.O_TIO_READ, ITioConst.TIO_THROWER);
                ev.getContext().put("TEXT", value);
                notifyUpdateListeners(ev);
                break;

             case IJxfsTIOConst.JXFS_E_TIO_READ:
                ev = new DeviceEvent(ITioConst.E_TIO_READ, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;

             case IJxfsTIOConst.JXFS_E_TIO_LED:
                ev = new DeviceEvent(ITioConst.E_TIO_LED, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;

             case IJxfsConst.JXFS_E_CANCELLED: 
                ev = new DeviceEvent(ITioConst.TIO_EXCEPTION_TYPE, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                 break;

             default:
                ev = new DeviceEvent(ITioConst.TIO_EXCEPTION_TYPE, ITioConst.TIO_THROWER);
                notifyUpdateListeners(ev);
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_CLEAR: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_LIGHT: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_BEEP: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_TIO_LED: 
            if(ocID_ == setLEDID_ || ocID_ == setLEDID2_)
                break;
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsTIOConst.JXFS_O_OPEN: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                try{
                    IGUIBuilder guiBuilder = (IGUIBuilder)GlobalConfig.getInstance().getProperty(ITioConst.TIO_THROWER);
                    ev = new DeviceEvent(IDeviceConst.O_OPEN, ITioConst.TIO_THROWER);
                    ev.getContext().put(ITioConst.TIO_THROWER, guiBuilder);
                    notifyUpdateListeners(ev);                   
                    opened_ = true;
                }
                catch(GeneralException e){
                    e.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsConst.JXFS_O_CLOSE: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsTIOConst.JXFS_TIO_TEXT_NORMAL: // '\0'
                opened_ = false;
                break;

            case IJxfsTIOConst.JXFS_E_CANCELLED: 
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
        Event ev = new DeviceEvent(ITioConst.TIO_THROWER, ITioConst.TIO_STATUS_TYPE);
        notifyUpdateListeners(ev);
    }
    
    public static JxfsTioController getInstance(){
        return tioController;
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
                e.printStackTrace();
            }
        };
    }

    private JxfsTIO tio_;
    private String tioname_;
    private String title_;
    private boolean claimed_;
    private boolean opened_;
    int event_counter_;
    private String Jxfs_imagepath_;
    private boolean displayLightOn_;
    private boolean displayCursorSelected_;
    private boolean flushSelected_;
    private boolean autoEndSelected_;
    private int modeSelected_;
    private int numOfChars_;
    private int callID_;
    private int ocID_;
    private int setLEDID_;
    private int setLEDID2_;
    private boolean gui_closed_;
    private Boolean CloseCompleted;
    private Vector updateListeners=new Vector();
    private static JxfsTioController tioController;
}
