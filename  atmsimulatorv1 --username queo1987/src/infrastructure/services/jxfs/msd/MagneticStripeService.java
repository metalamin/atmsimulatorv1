
package infrastructure.services.jxfs.msd;

import com.jxfs.control.msd.*;
import com.jxfs.events.*;
import com.jxfs.forum.support.*;
import com.jxfs.forum.tools.version.VersionControl;
import com.jxfs.general.*;
import com.jxfs.service.IJxfsMagStripeService;
import com.jxfs.service.msd.AJxfsMagStripeService;
import java.util.Vector;

public class MagneticStripeService extends AJxfsMagStripeService
    implements IJxfsMagStripeService, IJxfsMSDConst, IQueueResponse, IMagneticStripe, IQProblemListener
{

    public MagneticStripeService()
    {
        openCount = 0;
        shuttingDownIsActive = false;
        track1_ = "";
        track2_ = "";
        track3_ = "";
        l = JxfsLogger.getReference();
        l.registerModule(ORIGIN, DESCRIPTION);
        int i = 1;
        try
        {
            qc = new QueueControl(i, ORIGIN, this);
        }
        catch(JxfsException jxfsexception)
        {
            jxfsexception.printStackTrace();
        }
        status = new JxfsStatus();
    }

    public int open(int i)
        throws JxfsException
    {
        checkShutdown(i);
        int j = qc.open(new OpenJob(this, i));
        return j;
    }

    public int close(int i)
        throws JxfsException
    {
        checkShutdown(i);
        int j = qc.close(new CloseJob(this, i));
        return j;
    }

    public boolean claim(int i, int j)
        throws JxfsException
    {
        checkShutdown(j);
        status.setClaimPending(true);
        boolean flag;
        try
        {
            flag = qc.claim(i, j, this);
        }
        catch(JxfsException jxfsexception)
        {
            status.setClaimPending(false);
            throw jxfsexception;
        }
        if(flag)
            status.setClaimed(true);
        return flag;
    }

    public boolean release(int i, int j)
        throws JxfsException
    {
        checkShutdown(j);
        boolean flag;
        try
        {
            flag = qc.release(i, j, this);
        }
        catch(JxfsException jxfsexception)
        {
            throw jxfsexception;
        }
        if(flag)
            status.setClaimed(false);
        return flag;
    }

    public void cancel(int i, int j)
        throws JxfsException
    {
        checkShutdown(j);
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
        return status;
    }

    public JxfsVersion getDeviceServiceVersion(int i)
        throws JxfsException
    {
        checkShutdown(i);
        return new JxfsVersion(VersionControl.getReference().getMajorFDINumber(), VersionControl.getReference().getMinorFDINumber(), VersionControl.getReference().getBuildNumber(), VersionControl.getReference().getJxfsMajorNumber(), VersionControl.getReference().getJxfsMinorNumber(), "JxfsMagStripe (C) Copyright International Business Machines Corporation, 2000, 2001. All rights reserved.");
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
        if(!qc.isRegistered(i) || shuttingDownIsActive)
            throw new JxfsException(1001);
        if(!qc.isOpen(i))
            throw new JxfsException(1002);
        else
            return false;
    }

    public int wakeUpFromPowerSave(int i)
        throws JxfsException
    {
        byte byte0 = -1;
        checkShutdown(i);
        if(!qc.isRegistered(i) || shuttingDownIsActive)
            throw new JxfsException(1001);
        if(!qc.isOpen(i))
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
    }

    public int registerControl(String s, IJxfsEventNotification ijxfseventnotification)
        throws JxfsException
    {
        int i = qc.registerControl(ijxfseventnotification, s);
        return i;
    }

    public void deregisterControl(int i)
        throws JxfsException
    {
        qc.deregisterControl(i);
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
        qc.shutdown();
        shuttingDownIsActive = false;
        l.deregisterModule(ORIGIN);
    }

    public int readData(JxfsMSDTrackSelection jxfsmsdtrackselection, int i)
        throws JxfsException
    {
        checkShutdown(i);
        MagneticStripeReadServiceJob readservicejob = new MagneticStripeReadServiceJob(this, jxfsmsdtrackselection, i);
        int j = qc.addJob(readservicejob);
        return j;
    }

    public int writeData(Vector vector, boolean flag, int i)
        throws JxfsException
    {
        String s = null;
        String s1 = null;
        String s2 = null;
        if(vector.elementAt(0) != null)
            s = new String((byte[])vector.elementAt(0));
        if(vector.elementAt(1) != null)
            s1 = new String((byte[])vector.elementAt(1));
        if(vector.elementAt(2) != null)
            s2 = new String((byte[])vector.elementAt(2));
        checkShutdown(i);
        MagneticStripeWriteServiceJob writeservicejob = new MagneticStripeWriteServiceJob(this, s, s1, s2, i);
        int j = qc.addJob(writeservicejob);
        return j;
    }

    public int getDeviceType(int i)
        throws JxfsException
    {
        return 4001;
    }

    public JxfsMediaStatus getMediaStatus(int i)
        throws JxfsException
    {
        return new JxfsMediaStatus(0);
    }

    public JxfsMSDTracks getSupportedReadTracks(int i)
        throws JxfsException
    {
        return new JxfsMSDTracks(true, true, true);
    }

    public JxfsMSDTracks getSupportedWriteTracks(int i)
        throws JxfsException
    {
        return new JxfsMSDTracks(true, true, true);
    }

    public synchronized void openInternal(IJxfsServiceJob ijxfsservicejob)
        throws JxfsException
    {
        openCount++;
        if(openCount == 1)
        {
            initializePhysically();
            status.setOpen(true);
        }
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void closeInternal(IJxfsServiceJob ijxfsservicejob)
    {
        if(openCount == 1)
        {
            finalizePhysically();
            status.setOpen(false);
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

    public synchronized void readInternal(MagneticStripeReadServiceJob readservicejob, JxfsMSDTrackSelection jxfsmsdtrackselection)
    {
        status.setBusy(true);
        
        JxfsMSDTracks jxfsmsdtracks = new JxfsMSDTracks(jxfsmsdtrackselection.isTrack1(), jxfsmsdtrackselection.isTrack2(), jxfsmsdtrackselection.isTrack3());
        devGUI.setTrack1Enabled(jxfsmsdtrackselection.isTrack1());
        devGUI.setTrack2Enabled(jxfsmsdtrackselection.isTrack2());
        devGUI.setTrack3Enabled(jxfsmsdtrackselection.isTrack3());        
        devGUI.setBttnEnabled(true);

        while(!
                devGUI.enterKeySet()) 
        {
            try{
                Thread.currentThread();
                Thread.sleep(1000L);
            }
            catch(Exception e){
                // error
            }
        }
        
        Vector vector = new Vector(3);
        if(jxfsmsdtrackselection.isTrack1())
            vector.addElement(devGUI.readTrack1());
        else
            vector.addElement(null);
        if(jxfsmsdtrackselection.isTrack2())
            vector.addElement(devGUI.readTrack2());
        else
            vector.addElement(null);
        if(jxfsmsdtrackselection.isTrack3())
            vector.addElement(devGUI.readTrack3());
        else
            vector.addElement(null);

        devGUI.clearTracks();
        devGUI.setTrack1Enabled(false);
        devGUI.setTrack2Enabled(false);
        devGUI.setTrack3Enabled(false);
        devGUI.setBttnEnabled(false);
        
        JxfsMSDReadData jxfsmsdreaddata = new JxfsMSDReadData(vector, jxfsmsdtracks, 0, 0, 0);
        status.setBusy(false);
        
        sendOperationComplete(readservicejob, jxfsmsdreaddata, checkUserExceptions(IJxfsMSDConst.JXFS_O_MSD_READDATA));
        
    }

    private int checkUserExceptions(int operationType){
        int result = 0;
        switch (operationType){
            case IJxfsMSDConst.JXFS_O_MSD_READDATA:
                result = devGUI.isNoMedia() ? JXFS_E_MSD_NOMEDIA : result;
                result = devGUI.isReadFailure() ? JXFS_E_MSD_READFAILURE : result;
                break;
            default:   
                result = 0;
        }
        return result;
    }
    
    public synchronized void writeInternal(MagneticStripeWriteServiceJob writeservicejob, String s, String s1, String s2)
    {
        status.setBusy(true);
        if(s != null)
            track1_ = s;
        if(s1 != null)
            track2_ = s1;
        if(s2 != null)
            track3_ = s2;
        status.setBusy(false);
        sendOperationComplete(writeservicejob);
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

    private void initializePhysically()
    {
        char c = '\007';
        devGUI = new MagneticStripeDeviceGUI(this, JxfsDeviceManager.getReference(), l);
        System.out.print(c);
    }

    private void finalizePhysically()
    {
        char c = '\007';
        devGUI.dispose();
        devGUI = null;        
        System.out.print(c);
    }

    private void sendOperationComplete(IJxfsServiceJob ijxfsservicejob)
    {
        JxfsOperationCompleteEvent jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), 0);
        try
        {
            qc.getControl(ijxfsservicejob.getControlId()).fireOperationCompleteEvent(jxfsoperationcompleteevent);
        }
        catch(JxfsException jxfsexception)
        {
            jxfsexception.printStackTrace();
        }
    }

    private void sendOperationComplete(IJxfsServiceJob ijxfsservicejob, JxfsMSDReadData jxfsmsdreaddata, int i)
    {
        try
        {
            JxfsOperationCompleteEvent jxfsoperationcompleteevent;
            if(i == 0)
                jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), 0, jxfsmsdreaddata);
            else
                jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), i);
            qc.getControl(ijxfsservicejob.getControlId()).fireOperationCompleteEvent(jxfsoperationcompleteevent);
        }
        catch(JxfsException jxfsexception)
        {
            jxfsexception.printStackTrace();
        }
    }

    public void badState(IJxfsServiceJob ijxfsservicejob, int i)
    {
    }

    public void badJob(IJxfsServiceJob ijxfsservicejob, Exception exception)
    {
    }

    private static final String ORIGIN = "MagneticStripeDeviceService";
    private static final String DESCRIPTION = "Magnetic Stripe Device Service";
    private QueueControl qc;
    private int openCount;
    private final int INTERNAL_TIMEOUT = 3000;
    private boolean shuttingDownIsActive;
    private JxfsLocalDeviceInformation localDeviceInfo;
    private JxfsStatus status;
    private JxfsLogger l;
    private String track1_;
    private String track2_;
    private String track3_;
    private MagneticStripeDeviceGUI devGUI;    
}
