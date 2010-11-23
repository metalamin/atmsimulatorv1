
package infrastructure.services.jxfs.ptr;

import com.jxfs.control.ptr.*;
import com.jxfs.events.*;
import com.jxfs.forum.i18n.LocalizedErrorMessages;
import com.jxfs.forum.support.*;
import com.jxfs.forum.tools.version.VersionControl;
import com.jxfs.general.*;
import com.jxfs.service.IJxfsPrinterService;
import com.jxfs.service.ptr.AJxfsPrinterService;
import infrastructure.devices.controllers.ptr.IPtrConst;
import java.awt.*;
import javax.swing.*;

public class PrinterService extends AJxfsPrinterService
    implements IJxfsPrinterService, IJxfsPrinterConst, IQueueResponse, IQProblemListener
{

    public PrinterService()
    {
        openCount = 0;
        shuttingDownIsActive = false;
        formsConfig = null;
        mediaStatus_ = null;
        paperStatus_ = null;
        tonerStatus_ = null;
        ptrWriteFormCapability_ = null;
        int i = 1;
        try
        {
            qc = new QueueControl(i, "PrinterDeviceService", this);
        }
        catch(JxfsException jxfsexception)
        {
            jxfsexception.printStackTrace();
        }
    }

    private QueueControl getQueue()
    {
        return qc;
    }

    private JxfsStatus getState()
    {
        return state;
    }

    public int open(int i)
        throws JxfsException
    {
        checkShutdown(i);
        int j = getQueue().open(new OpenJob(this, i));
        return j;
    }

    public int close(int i)
        throws JxfsException
    {
        checkShutdown(i);
        int j = getQueue().close(new CloseJob(this, i));
        return j;
    }

    public boolean claim(int i, int j)
        throws JxfsException
    {
        checkShutdown(j);
        getState().setClaimPending(true);
        boolean flag;
        try
        {
            flag = getQueue().claim(i, j, this);
        }
        catch(JxfsException jxfsexception)
        {
            getState().setClaimPending(false);
            throw jxfsexception;
        }
        if(flag)
            getState().setClaimed(true);
        return flag;
    }

    public boolean release(int i, int j)
        throws JxfsException
    {
        checkShutdown(j);
        boolean flag;
        try
        {
            flag = getQueue().release(i, j, this);
        }
        catch(JxfsException jxfsexception)
        {
            throw jxfsexception;
        }
        if(flag)
            getState().setClaimed(false);
        return flag;
    }

    public void cancel(int i, int j)
        throws JxfsException
    {
        qc.shutdown();
        IJxfsServiceJob ijxfsservicejob = qc.cancelJob(i);
        if(ijxfsservicejob != null)
        {
            JxfsOperationCompleteEvent jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), i, 1021);
            qc.getControl(ijxfsservicejob.getControlId()).fireOperationCompleteEvent(jxfsoperationcompleteevent);
        }
    }

    public JxfsStatus getStatus(int i)
        throws JxfsException
    {
        checkShutdown(i);
        return (JxfsStatus)getState().clone();
    }

    public JxfsVersion getDeviceServiceVersion(int i)
        throws JxfsException
    {
        checkShutdown(i);
        return new JxfsVersion(VersionControl.getReference().getMajorFDINumber(), VersionControl.getReference().getMinorFDINumber(), VersionControl.getReference().getBuildNumber(), VersionControl.getReference().getJxfsMajorNumber(), VersionControl.getReference().getJxfsMinorNumber(), "PrinterService is a stub for printer service which provides some usefull functionalities simulating a real hardware device.");
    }

    public String getPhysicalDeviceDescription(int i)
        throws JxfsException
    {
        throw new JxfsException(1011);
    }

    public String getPhysicalDeviceName(int i)
        throws JxfsException
    {
        throw new JxfsException(1011);
    }

    public boolean updateFirmware(int i)
        throws JxfsException
    {
        throw new JxfsException(1011);
    }

    public int getFirmwareStatus(int i)
        throws JxfsException
    {
        throw new JxfsException(1011);
    }

    public JxfsVersion getDeviceFirmwareVersion(int i)
        throws JxfsException
    {
        throw new JxfsException(1022);
    }

    public JxfsVersion getRepositoryFirmwareVersion(int i)
        throws JxfsException
    {
        throw new JxfsException(1022);
    }

    public boolean isPowerSaveModeSupported(int i)
        throws JxfsException
    {
        checkShutdown(i);
        if(!getQueue().isRegistered(i) || shuttingDownIsActive)
            throw new JxfsException(1001);
        if(!getQueue().isOpen(i))
            throw new JxfsException(1002);
        else
            return false;
    }

    public int wakeUpFromPowerSave(int i)
        throws JxfsException
    {
        byte byte0 = -1;
        checkShutdown(i);
        if(!getQueue().isRegistered(i) || shuttingDownIsActive)
            throw new JxfsException(1001);
        if(!getQueue().isOpen(i))
            throw new JxfsException(1002);
        else
            return byte0;
    }

    public int directIO(int i, JxfsType jxfstype, int j)
        throws JxfsException
    {
        checkShutdown(j);
        throw new JxfsException(1022);
    }

    public void initialize(JxfsLocalDeviceInformation jxfslocaldeviceinformation)
        throws JxfsException
    {
        localDeviceInfo = jxfslocaldeviceinformation;
        formsConfig = new JxfsPtrFormsConfig(3105, 3104, 0, 0, 80, 63);
        mediaStatus_ = new JxfsMediaStatus(4);
        paperStatus_ = new JxfsThresholdStatus(1);
        tonerStatus_ = new JxfsThresholdStatus(1);
        ptrWriteFormCapability_ = new JxfsPtrWriteFormCapability(0x1000000);
    }

    public int registerControl(String s, IJxfsEventNotification ijxfseventnotification)
        throws JxfsException
    {
        int i = getQueue().registerControl(ijxfseventnotification, s);
        return i;
    }

    public void deregisterControl(int i)
        throws JxfsException
    {
        getQueue().deregisterControl(i);
    }

    public void connectionFailure(int i)
    {
        try
        {
            release(2000, i);
        }
        catch(JxfsException jxfsexception) { }
        try
        {
            close(i);
        }
        catch(JxfsException jxfsexception1) { }
        try
        {
            deregisterControl(i);
        }
        catch(JxfsException jxfsexception2) { }
    }

    public void shutdown()
        throws JxfsException
    {
        shuttingDownIsActive = true;
        getQueue().shutdown();
        shuttingDownIsActive = false;
    }

    public boolean isCompound(int i)
        throws JxfsException
    {
        String s = "isCompound";
        boolean flag = false;
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return flag;
    }

    public JxfsPtrCtrlMediaCapability getCtrlMediaCapability(int i)
        throws JxfsException
    {
        String s = "getCtrlMediaCapability";
        JxfsPtrCtrlMediaCapability jxfsptrctrlmediacapability = new JxfsPtrCtrlMediaCapability(0);
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return jxfsptrctrlmediacapability;
    }

    public JxfsPtrExtentCapability getExtentCapability(int i)
        throws JxfsException
    {
        String s = "getExtentCapability";
        JxfsPtrExtentCapability jxfsptrextentcapability = new JxfsPtrExtentCapability(1024);
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return jxfsptrextentcapability;
    }

    public JxfsPtrFormsConfig getFormsConfig(int i)
        throws JxfsException
    {
        String s = "getFormsConfig";
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return formsConfig;
    }

    public void setFormsConfig(JxfsPtrFormsConfig jxfsptrformsconfig, int i)
        throws JxfsException
    {
        String s = "setFormsConfig";
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        formsConfig = jxfsptrformsconfig;
    }

    public JxfsPtrStatus getPtrStatus(int i)
        throws JxfsException
    {
        String s = "getPtrStatus";
        JxfsPtrStatus jxfsptrstatus = new JxfsPtrStatus(mediaStatus_, paperStatus_, tonerStatus_);
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return jxfsptrstatus;
    }

    public JxfsPtrWriteFormCapability getWriteFormCapability(int i)
        throws JxfsException
    {
        String s = "getWriteFormCapability";
        checkShutdown(i);
        if(!getQueue().isRegistered(i))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(i))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        return ptrWriteFormCapability_;
    }

    public int ctrlMedia(int i, int j)
        throws JxfsException
    {
        String s = "ctrlMedia";
        if(isTraceActive(101))
            trace(101, "Entering request " + s + "(). Control id is: " + j + "\n  mediaControl is: " + i);
        checkShutdown(j);
        if(!getQueue().isRegistered(j))
        {
            JxfsException jxfsexception = new JxfsException(1001, LocalizedErrorMessages.getFormattedMessage("JXFS046", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception, "JXFS_E_UNREGISTERED", s);
            throw jxfsexception;
        }
        if(!getQueue().isOpen(j))
        {
            JxfsException jxfsexception1 = new JxfsException(1002, LocalizedErrorMessages.getFormattedMessage("JXFS054", new Object[] {
                getClass().getName()
            }));
            traceException(jxfsexception1, "JXFS_E_CLOSED", s);
            throw jxfsexception1;
        }
        int k = 0;
        return k;
    }

    public int getFormList(int i)
        throws JxfsException
    {
        String s = "getFormList";
        int j = getQueue().addJob(new PrinterServiceJob(i, 0) {

            public void execute()
                throws JxfsException
            {
                IJxfsEventNotification ijxfseventnotification = getQueue().getControl(getControlId());
                if(ijxfseventnotification != null)
                {
                    OCPtrFormListEvent ocptrformlistevent = new OCPtrFormListEvent(PrinterService.this, getIdentificationID(), 0, new String[] {
                        "Default"
                    });
                    try
                    {
                        ijxfseventnotification.fireOperationCompleteEvent(ocptrformlistevent);
                    }
                    catch(JxfsException jxfsexception)
                    {
                        traceCaughtException(jxfsexception, "getFormList()/execute");
                        throw jxfsexception;
                    }
                }
            }

            public boolean cancel()
            {
                return false;
            }

            public String getLogOrigin()
            {
                return "PrinterDeviceService";
            }

        });
        return j;
    }

    public int mediaExtents(int i)
        throws JxfsException
    {
        String s = "mediaExtents";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s);
        throw jxfsexception;
    }

    public int getMediaList(int i)
        throws JxfsException
    {
        String s = "getMediaList";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s);
        throw jxfsexception;
    }

    private int checkUserExceptions(int operationType){
        int result = 0;
        switch (operationType){
            case IJxfsPrinterConst.JXFS_O_PTR_WRITE_FORM_DATA:
                result = devGUI.isMediaOverflow() ? JXFS_E_PTR_MEDIA_OVERFLOW : result;
                result = devGUI.isPaperOut() ? JXFS_E_PTR_PAPEROUT : result;
                result = devGUI.isNoMediaPresent() ? JXFS_E_PTR_NO_MEDIA_PRESENT : result;
                break;
            default:   
                result = 0;
        }
        return result;
    }
    
    public int printForm(String formName, String mediumName, String fieldContent[], int i)
        throws JxfsException
    {
        String s2 = "printForm";
        class PrintFormJob extends PrinterServiceJob
        {

            public void execute()
                throws JxfsException
            {
                IJxfsEventNotification ijxfseventnotification = getQueue().getControl(getControlId());
                if(ijxfseventnotification != null)
                {
                    int completedResult = checkUserExceptions(IJxfsPrinterConst.JXFS_O_PTR_WRITE_FORM_DATA);
                    
                    int intermediateResult = checkUserExceptions(IJxfsPrinterConst.JXFS_O_PTR_WRITE_FORM_DATA);
                    intermediateResult = (intermediateResult == 0) ? 3121 : intermediateResult;
                    
                    JxfsOperationCompleteEvent jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(PrinterService.this, getOperationID(), getIdentificationID(), completedResult);
                    JxfsIntermediateEvent jxfsintermediateevent = new JxfsIntermediateEvent(PrinterService.this, getOperationID(), getIdentificationID(), intermediateResult);
                    try
                    {
                        ijxfseventnotification.fireIntermediateEvent(jxfsintermediateevent);

                        if (completedResult == 0){
                            devGUI.printForm(formName, mediumName, fieldContent);
                        }
                        
                        ijxfseventnotification.fireOperationCompleteEvent(jxfsoperationcompleteevent);
                    }
                    catch(JxfsException jxfsexception)
                    {
                        traceCaughtException(jxfsexception, "printForm()/execute");
                        throw jxfsexception;
                    }
                }
            }

            public boolean cancel()
            {
                return false;
            }

            public String getLogOrigin()
            {
                return "PrinterDeviceService";
            }

            private final String formName;
            private final String mediumName;
            private final String fieldContent[];

            public PrintFormJob(String formName, String mediumName, String fieldContent[], int i)
            {
                super(i, 3085);
                this.formName = formName;
                this.mediumName = mediumName;
                this.fieldContent = fieldContent;
            }
        }

        int j = getQueue().addJob(new PrintFormJob(formName, mediumName, fieldContent, i));
        return j;
    }

    public int printRawData(byte abyte0[], boolean flag, int i)
        throws JxfsException
    {
        String s = "printRawData";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s);
        throw jxfsexception;
    }

    public int getFieldDescription(String as[], String s, int i)
        throws JxfsException
    {
        String s1 = "getFieldDescription";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s1
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s1);
        throw jxfsexception;
    }

    public int getFormDescription(String s, int i)
        throws JxfsException
    {
        String s1 = "getFormDescription";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s1
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s1);
        throw jxfsexception;
    }

    public int getMediaDescription(String s, int i)
        throws JxfsException
    {
        String s1 = "getMediaDescription";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s1
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s1);
        throw jxfsexception;
    }

    public int resetPrinter(int i)
        throws JxfsException
    {
        String s = "resetPrinter";
        JxfsException jxfsexception = new JxfsException(1022, LocalizedErrorMessages.getFormattedMessage("JXFS053", new Object[] {
            s
        }));
        traceException(jxfsexception, "JXFS_E_NOT_SUPPORTED", s);
        throw jxfsexception;
    }

    public synchronized void openInternal(IJxfsServiceJob ijxfsservicejob)
        throws JxfsException
    {
        openCount++;
        if(openCount == 1)
        {
            openPhysically();
            getState().setOpen(true);
        }
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void closeInternal(IJxfsServiceJob ijxfsservicejob)
    {
        if(openCount == 1)
        {
            closePhysically();
            getState().setOpen(false);
        }
        openCount--;
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void claimInternal(int i)
    {
    }

    public synchronized void releaseInternal(int i)
    {
    }

    public boolean checkInternal()
    {
        return true;
    }

    private void checkShutdown(int i)
        throws JxfsException
    {
        if(shuttingDownIsActive)
        {
            throw new JxfsException(1001);
        } else
        {
            return;
        }
    }

    private void sendOperationComplete(IJxfsServiceJob ijxfsservicejob)
    {
        JxfsOperationCompleteEvent jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), 0);
        try
        {
            getQueue().getControl(ijxfsservicejob.getControlId()).fireOperationCompleteEvent(jxfsoperationcompleteevent);
        }
        catch(JxfsException jxfsexception)
        {
        }
    }

    public void finalize()
    {
        JxfsLogger.getReference().deregisterModule("PrinterDeviceService");
    }

    private void openPhysically()
    {
        if(isTraceActive(103))
            trace(103, "Opening the device physically.");
        char c = '\007';
        System.out.print(c);
        devGUI = new PrinterDeviceGUI(this, JxfsDeviceManager.getReference(), l);
    }

    private void closePhysically()
    {
        char c = '\007';
        System.out.print(c);
    }

    private void traceException(JxfsException jxfsexception, String s, String s1)
    {
    }

    private void traceCaughtException(Exception exception, String s)
    {
    }

    private static boolean isTraceActive(int i)
    {
        return JxfsLogger.getReference().isLogActive("PrinterDeviceService", i);
    }

    private void trace(int i, String s)
    {
        JxfsLogger.getReference().writeLog(this, "PrinterDeviceService", i, getClass().getName() + ":\n " + s);
    }

    public void badState(IJxfsServiceJob ijxfsservicejob, int i)
    {
    }

    public void badJob(IJxfsServiceJob ijxfsservicejob, Exception exception)
    {
    }

    private static final String ORIGIN = "PrinterDeviceService";
    private int openCount;
    private final int INTERNAL_TIMEOUT = 2000;
    private boolean shuttingDownIsActive;
    private JxfsLocalDeviceInformation localDeviceInfo;
    private final JxfsStatus state = new JxfsStatus();
    private JxfsPtrFormsConfig formsConfig;
    private JxfsMediaStatus mediaStatus_;
    private JxfsThresholdStatus paperStatus_;
    private JxfsThresholdStatus tonerStatus_;
    private JxfsPtrWriteFormCapability ptrWriteFormCapability_;
    JxfsLogger l;
    private QueueControl qc;
    private PrinterDeviceGUI devGUI;

}
