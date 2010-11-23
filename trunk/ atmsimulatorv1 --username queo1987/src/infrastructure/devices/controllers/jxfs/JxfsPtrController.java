
package infrastructure.devices.controllers.jxfs;

import com.jxfs.control.ptr.IJxfsPrinterConst;
import com.jxfs.control.ptr.JxfsPrinter;
import com.jxfs.events.IJxfsOperationCompleteListener;
import com.jxfs.events.IJxfsStatusListener;
import com.jxfs.events.JxfsException;
import com.jxfs.events.JxfsOperationCompleteEvent;
import com.jxfs.events.JxfsStatusEvent;
import com.jxfs.events.OCPtrFormListEvent;
import com.jxfs.events.OCPtrFormListListener;
import com.jxfs.events.OCPtrMediaListEvent;
import com.jxfs.events.OCPtrMediaListListener;
import com.jxfs.general.IJxfsConst;
import com.jxfs.general.JxfsDeviceManager;
import config.GlobalConfig;
import domain.state.Event;
import domain.statemachine.IUpdateListener;
import infrastructure.devices.controllers.ptr.IPtrConst;
import infrastructure.devices.controllers.ptr.IPtrController;
import infrastructure.devices.events.DeviceEvent;
import infrastructure.devices.general.IDeviceConst;
import infrastructure.services.jxfs.general.IWindowEventListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import presentation.IGUIBuilder;
import util.GeneralException;

public class JxfsPtrController
    implements IPtrController, IJxfsOperationCompleteListener, OCPtrFormListListener, OCPtrMediaListListener, IJxfsStatusListener, IWindowEventListener, IJxfsConst
{

    public void configure(Map context)
    {

        JxfsPrinter jxfsprinter;
        JxfsDeviceManager jxfsdevicemanager;
        String s;
        String s1;
        
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
        
        try{
            ptrname_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_NAME);
            ptr_ = (JxfsPrinter)JxfsDeviceManager.getReference().getDevice(ptrname_);
        }catch(JxfsException e){
            e.printStackTrace();
        }
        
        claimed_ = false;
        opened_ = false;
        Jxfs_imagepath_ = (String)context.get(JxfsImplConst.JXFS_DEVICE_IMAGEPATH);
        
        ptr_.addOperationCompleteListener(this);
        ptr_.addStatusListener(this);
    }

    // Queda pendiente la operacion Cancel.
    
    public int open() {
        try {
            synchronized(JxfsPtrController.this) {
                callID_ = ptr_.open();
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
             notifyUpdateListeners(ev);
        };
        return callID_;
    }

    public int close() {
        try {
            callID_ = ptr_.close();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return callID_;
    }       

    public boolean claim(int miliseconds) {
        try {
            if(ptr_.claim(miliseconds)) {
                claimed_ = true;
            } else {
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }

    public boolean release(int miliseconds) {
        try {
            ptr_.release(miliseconds);
            claimed_ = false;
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
        return claimed_;
    }    
    
    public void getFormList() {
        try {
            ptr_.getFormList();
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }

    public void printForm(String formName, String mediumName, String field) {
        try {
            synchronized(JxfsPtrController.this) {
                String [] fieldContent = new String[1];
                fieldContent[0]=field;
                ptr_.printForm(formName, mediumName, fieldContent);
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }
    
    public void printForm(String formName, String mediumName, String fieldContent[]) {
        try {
            synchronized(JxfsPtrController.this) {
                ptr_.printForm(formName, mediumName, fieldContent);
            }
        } catch(JxfsException jxfsexception) {
            Event ev = new DeviceEvent(IPtrConst.PTR_THROWER, IPtrConst.PTR_EXCEPTION_TYPE);
            notifyUpdateListeners(ev);
        }
    }
    
    public synchronized void operationCompleteOccurred(JxfsOperationCompleteEvent jxfsoperationcompleteevent)
    {
        Event ev;        
        int i = jxfsoperationcompleteevent.getOperationID();
        ocID_ = jxfsoperationcompleteevent.getIdentificationID();
        switch(i)
        {
        case IJxfsPrinterConst.JXFS_O_PTR_WRITE_FORM_DATA: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsConst.JXFS_RC_SUCCESSFUL: // '\0'
                ev = new DeviceEvent(IPtrConst.O_PTR_PRINTED, IPtrConst.PTR_THROWER);
                notifyUpdateListeners(ev);
                break;

            case IJxfsConst.JXFS_E_CANCELLED: //1021
                break;

            case IJxfsPrinterConst.JXFS_E_PTR_NO_MEDIA_PRESENT: 
                ev = new DeviceEvent(IPtrConst.E_PTR_NO_MEDIA_PRESENT, IPtrConst.PTR_THROWER);
                notifyUpdateListeners(ev);
                break;

            case IJxfsPrinterConst.JXFS_E_PTR_MEDIA_OVERFLOW: // '\0'
                ev = new DeviceEvent(IPtrConst.E_PTR_MEDIA_OVERFLOW, IPtrConst.PTR_THROWER);
                notifyUpdateListeners(ev);
                break;

            case IJxfsPrinterConst.JXFS_E_PTR_PAPEROUT: // '\0'
                ev = new DeviceEvent(IPtrConst.E_PTR_PAPEROUT, IPtrConst.PTR_THROWER);
                notifyUpdateListeners(ev);
                break;
                
            default:
                System.out.println("Print with Job ID: %1 failed.");
                break;
            }
            break;

        case IJxfsConst.JXFS_O_OPEN: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsConst.JXFS_RC_SUCCESSFUL: // '\0'
                try{
                    IGUIBuilder guiBuilder = (IGUIBuilder)GlobalConfig.getInstance().getProperty(IPtrConst.PTR_THROWER);
                    ev = new DeviceEvent(IDeviceConst.O_OPEN, IPtrConst.PTR_THROWER);
                    ev.getContext().put(IPtrConst.PTR_THROWER, guiBuilder);
                    notifyUpdateListeners(ev);                 
                    opened_ = true;
                }
                catch(GeneralException e){
                    e.printStackTrace();
                }                
                break;

            case IJxfsConst.JXFS_E_CANCELLED: 
                break;

            default:
                break;
            }
            break;

        case IJxfsConst.JXFS_O_CLOSE: 
            switch(jxfsoperationcompleteevent.getResult())
            {
            case IJxfsConst.JXFS_RC_SUCCESSFUL: // '\0'
                opened_ = false;
                break;

            case IJxfsConst.JXFS_E_CANCELLED: 
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
            System.out.println("Unknown operation complete event occured!" + i);
            break;
        }
    }

    public void oCPtrFormListOccurred(OCPtrFormListEvent ocptrformlistevent)
    {
        ocID_ = ocptrformlistevent.getIdentificationID();
        switch(ocptrformlistevent.getResult())
        {
        case 0: // '\0'
            break;

        case 1021: 
            break;

        case 3008: 
            break;

        default:
            break;
        }
    }

    public void oCPtrMediaListOccurred(OCPtrMediaListEvent ocptrmedialistevent)
    {
        ocID_ = ocptrmedialistevent.getIdentificationID();
        switch(ocptrmedialistevent.getResult())
        {
        case 0: // '\0'
            break;

        case 1021: 
            break;

        case 3009: 
            break;

        default:
            break;
        }
    }

    public void statusOccurred(JxfsStatusEvent jxfsstatusevent)
    {
    }

    public void CloseWindowEvent()
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
                    callID_ = ptr_.close();
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
                ptr_.deregisterDevice();
            }
            catch(JxfsException jxfsexception1)
            {
            }
        }
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

    private JxfsPrinter ptr_;
    private String title_;
    private boolean claimed_;
    private boolean opened_;
    private JxfsDeviceManager mgr_;
    int event_counter_;
    private String Jxfs_imagepath_;
    private int callID_;
    private int ocID_;
    private boolean gui_closed_;
    private Boolean CloseCompleted;
    private Vector updateListeners=new Vector();    
    private String ptrname_;

}
