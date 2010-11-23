
package infrastructure.services.jxfs.tio;

import com.jxfs.control.tio.*;
import com.jxfs.events.*;
import com.jxfs.forum.support.*;
import com.jxfs.forum.tools.version.VersionControl;
import com.jxfs.general.*;
import com.jxfs.service.IJxfsTIOService;
import com.jxfs.service.tio.AJxfsTIOService;
import java.util.Enumeration;
import java.util.Vector;

public class TextInOutService extends AJxfsTIOService
    implements IJxfsTIOService, IJxfsTIOConst, IQueueResponse, IQProblemListener
{

    public TextInOutService()
    {
        openCount = 0;
        shuttingDownIsActive = false;
        resolution_ = null;
        availResolutions_ = null;
        l = JxfsLogger.getReference();
        l.registerModule("TIO_DS", "Demo - Text Input/Output Device Service");
        int i = 1;
        try
        {
            qc = new QueueControl(i, "TIO_DS", this);
        }
        catch(JxfsException jxfsexception)
        {
            if(l.isLogActive("TIO_DS", 82))
                l.writeLog(this, "TIO_DS", 82, "Error while building the QueueControl.");
        }
        status = new JxfsTIOStatus();
        resolution_ = new JxfsTIOResolution(40, 4);
        availResolutions_ = new Vector(2);
        availResolutions_.addElement(new JxfsTIOResolution(40, 4));
        availResolutions_.addElement(new JxfsTIOResolution(20, 4));
        onlineLEDStatus = 8007;
        inputLEDStatus = 8007;
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
        return new JxfsVersion(VersionControl.getReference().getMajorFDINumber(), VersionControl.getReference().getMinorFDINumber(), VersionControl.getReference().getBuildNumber(), VersionControl.getReference().getJxfsMajorNumber(), VersionControl.getReference().getJxfsMinorNumber(), "MelnageTIOService (C) Copyright International Business Machines Corporation, 2000, 2001. All rights reserved.");
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
        l.deregisterModule("TIO_DS");
    }

    public int beep(int i, int j, int k)
        throws JxfsException
    {
        checkShutdown(k);
        TextInOutBeepServiceJob beepservicejob = new TextInOutBeepServiceJob(this, i, j, k);
        int i1 = qc.addJob(beepservicejob);
        return i1;
    }

    public int lightDisplay(boolean flag, int i)
        throws JxfsException
    {
        checkShutdown(i);
        TextInOutLightDisplayServiceJob lightdisplayservicejob = new TextInOutLightDisplayServiceJob(this, flag, i);
        int j = qc.addJob(lightdisplayservicejob);
        return j;
    }

    public int setLED(int i, int j, int k)
        throws JxfsException
    {
        checkShutdown(k);
        if(i > 0 && i <= getMaxLED(k) || i < 0 && i >= -10)
        {
            TextInOutSetLedServiceJob setledservicejob = new TextInOutSetLedServiceJob(this, i, j, k);
            int i1 = qc.addJob(setledservicejob);
            return i1;
        }
        throw new JxfsException(1022);
    }

    public int getLED(int i, int j)
        throws JxfsException
    {
        int k = 8007;
        checkShutdown(j);
        if(i > 0 && i <= getMaxLED(j) || i < 0 && i >= -10)
        {
            switch(i)
            {
            case -3: 
            case 1: // '\001'
                k = onlineLEDStatus;
                break;

            case -5: 
            case 2: // '\002'
                k = inputLEDStatus;
                break;
            }
            return k;
        }
        throw new JxfsException(1018);
    }

    public int clearScreen(int i, int j, int k, int i1, int j1)
        throws JxfsException
    {
        checkShutdown(j1);
        TextInOutClearScreenServiceJob clearscreenservicejob = new TextInOutClearScreenServiceJob(this, i, j, k, i1, j1);
        int k1 = qc.addJob(clearscreenservicejob);
        return k1;
    }

    public int writeDisplayData(int i, int j, int k, int i1, String s, int j1)
        throws JxfsException
    {
        checkShutdown(j1);
        isTextAttributeSupported(i1, j1);
        if(j > getResolution(j1).getColumns() || k > getResolution(j1).getRows())
            throw new JxfsException(1022);
        TextInOutWriteDisplayDataServiceJob writedisplaydataservicejob = new TextInOutWriteDisplayDataServiceJob(this, i, j, k, i1, s, j1);
        int k1 = qc.addJob(writedisplaydataservicejob);
        return k1;
    }

    public int readKeyboardData(int i, int j, int k, int i1, int j1, int k1, int l1, 
            boolean flag, boolean flag1, boolean flag2, int i2)
        throws JxfsException
    {
        checkShutdown(i2);
        isTextAttributeSupported(k1, i2);
        if(flag && !isCursorSupported(i2))
            throw new JxfsException(1022);
        if(k > getResolution(i2).getColumns() || i1 > getResolution(i2).getRows())
            throw new JxfsException(1022);
        TextInOutReadKeyboardDataServiceJob readkeyboarddataservicejob = new TextInOutReadKeyboardDataServiceJob(this, i, j, k, i1, j1, k1, l1, flag, flag1, flag2, i2);
        int j2 = qc.addJob(readkeyboarddataservicejob);
        return j2;
    }

    public boolean isTextAttributeSupported(int i, int j)
        throws JxfsException
    {
        int k = 0;
        if(i == 0)
            k++;
        if(k > 0)
        {
            return true;
        }
        throw new JxfsException(1022);
    }

    public boolean isCursorSupported(int i)
        throws JxfsException
    {
        boolean flag = false;
        return flag;
    }

    public JxfsTIOResolution getResolution(int i)
        throws JxfsException
    {
        return resolution_;
    }

    public void setResolution(JxfsTIOResolution jxfstioresolution, int i)
        throws JxfsException
    {
        for(Enumeration enumeration = availResolutions_.elements(); enumeration.hasMoreElements();)
        {
            JxfsTIOResolution jxfstioresolution1 = (JxfsTIOResolution)enumeration.nextElement();
            if(jxfstioresolution1.getColumns() == jxfstioresolution.getColumns() && jxfstioresolution1.getRows() == jxfstioresolution.getRows())
            {
                resolution_ = jxfstioresolution;
                return;
            }
        }

        throw new JxfsException(1022);
    }

    public Vector getAvailableResolutions(int i)
        throws JxfsException
    {
        return availResolutions_;
    }

    public Vector getAvailableResolution(int i)
        throws JxfsException
    {
        return availResolutions_;
    }

    public boolean isDisplayLightSupported(int i)
        throws JxfsException
    {
        boolean flag = true;
        return flag;
    }

    public boolean isBeepSupported(int i)
        throws JxfsException
    {
        boolean flag = true;
        return flag;
    }

    public int getMaxLED(int i)
        throws JxfsException
    {
        byte byte0 = 2;
        return byte0;
    }

    public boolean isKeyboardSupported(int i)
        throws JxfsException
    {
        boolean flag = true;
        return flag;
    }

    public boolean isKeyboardLockSupported(int i)
        throws JxfsException
    {
        boolean flag = false;
        return flag;
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

    public synchronized void beepInternal(IJxfsServiceJob ijxfsservicejob, int i, int j)
    {
        devGUI.beep();
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void lightDisplayInternal(IJxfsServiceJob ijxfsservicejob, boolean flag)
    {
        devGUI.setDisplayLight(flag);
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void setLEDInternal(IJxfsServiceJob ijxfsservicejob, int i, int j)
    {
        switch(i)
        {
        case -4: 
        case -2: 
        case -1: 
        case 0: // '\0'
        default:
            break;

        case -3: 
        case 1: // '\001'
            onlineLEDStatus = j;
            if(j == 8007)
                devGUI.setOnlineLED(false);
            else
                devGUI.setOnlineLED(true);
            break;

        case -5: 
        case 2: // '\002'
            inputLEDStatus = j;
            if(j == 8007)
                devGUI.setInputLED(false);
            else
                devGUI.setInputLED(true);
            break;
        }
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void clearScreenInternal(IJxfsServiceJob ijxfsservicejob, int i, int j, int k, int i1)
    {
        devGUI.clearScreen();
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void writeDisplayDataInternal(IJxfsServiceJob ijxfsservicejob, int i, int j, int k, int i1, String s)
    {
        devGUI.setText(j, k, s);
        sendOperationComplete(ijxfsservicejob);
    }

    public synchronized void readKeyboardDataInternal(IJxfsServiceJob ijxfsservicejob, int i, int j, int k, int i1, int j1, int k1, 
            int l1, boolean flag, boolean flush, boolean autoEnd)
    {
        int result = 0;
        if(flush)
            devGUI.clearScreen();
        String s = devGUI.readData(j1, true);
        String value = new String("");
        int i2 = 0;
        if(autoEnd)
            for(; s.length() < i /*&& i2 < 30*/; s = devGUI.readData(j1, false))
            {
//                if(++i2 > 25)
//                    devGUI.beep();
                try
                {
                    Thread.currentThread();
                    Thread.sleep(200L);
                }
                catch(InterruptedException interruptedexception) { 
                    interruptedexception.printStackTrace();
                }
                if (!value.equals(s)){
                    Vector vector = getKeys(s);
                    result = checkUserExceptions(IJxfsTIOConst.JXFS_O_TIO_READ);
                    sendIntermediate(ijxfsservicejob, vector, result);
                    value = s;
                }
            }

        else
            while(!devGUI.enterKeySet())  //  && i2 < 30
            {
//                if(++i2 > 25)
//                    devGUI.beep();
                try
                {
                    Thread.currentThread();
                    Thread.sleep(200L);
                }
                catch(InterruptedException interruptedexception1) { }
                s = devGUI.readData(j1, false);
                if (!value.equals(s)){
                    Vector vector = getKeys(s);
//                    if (vector.size() <= i){
                        result = checkUserExceptions(IJxfsTIOConst.JXFS_O_TIO_READ);
                        if (result != 0) devGUI.setEnterKey(true);
                        sendIntermediate(ijxfsservicejob, vector, result);
                        value = s;
//                    }
//                    else{
//                        devGUI.beep();
//                    }
                        
                }
            }
        devGUI.setBttnEnabled(false);
        
        result = checkUserExceptions(IJxfsTIOConst.JXFS_O_TIO_READ);
        if(value.length() == i || devGUI.enterKeySet())
        {
            Vector vector = getKeys(s);
            sendOperationComplete(ijxfsservicejob, vector, result);
        } else
        {
            Vector vector1 = getKeys(s);
            result = (result == 0) ? result : 1;
            sendOperationComplete(ijxfsservicejob, vector1, result);
        }
    }

    private int checkUserExceptions(int operationType){
        int result = 0;
        switch (operationType){
            case IJxfsTIOConst.JXFS_O_TIO_READ:
                result = devGUI.isLedException() ? JXFS_E_TIO_LED : result;
                result = devGUI.isReadException() ? JXFS_E_TIO_READ : result;
                break;
            default:   
                result = 0;
        }
        return result;
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
        devGUI = new TextInOutDeviceGui(this, JxfsDeviceManager.getReference(), l);
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
        }
    }

    private void sendOperationComplete(IJxfsServiceJob ijxfsservicejob, Vector vector, int i)
    {
        try
        {
            JxfsOperationCompleteEvent jxfsoperationcompleteevent;
            if(i == 0)
                jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), 0, vector);
            else
                jxfsoperationcompleteevent = new JxfsOperationCompleteEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), i);
            qc.getControl(ijxfsservicejob.getControlId()).fireOperationCompleteEvent(jxfsoperationcompleteevent);
        }
        catch(JxfsException jxfsexception)
        {
            jxfsexception.printStackTrace();
        }
    }

    private void sendIntermediate(IJxfsServiceJob ijxfsservicejob, Vector vector, int i)
    {
        try
        {
            JxfsIntermediateEvent jxfsintermediateevent;
            if(i == 0)
                jxfsintermediateevent = new JxfsIntermediateEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), 0, vector);
            else{
                devGUI.setBttnEnabled(false);
                jxfsintermediateevent = new JxfsIntermediateEvent(this, ijxfsservicejob.getOperationID(), ijxfsservicejob.getIdentificationID(), i);
            }
            qc.getControl(ijxfsservicejob.getControlId()).fireIntermediateEvent(jxfsintermediateevent);
        }
        catch(JxfsException jxfsexception)
        {
            if(l.isLogActive("TIO_DS", 103))
                l.writeLog(this, "TIO_DS", 103, "Request with operationId " + ijxfsservicejob.getOperationID() + " and identification_id " + ijxfsservicejob.getIdentificationID() + " of Device Control " + ijxfsservicejob.getControlId() + " was canceled in method cancelJob of class QueueControl." + " Event could not be sent!");
        }
    }
    
    private Vector getKeys(String s)
    {
        char ac[] = s.toCharArray();
        Vector vector = new Vector(s.length());
        for(int i = 0; i < s.length(); i++)
            switch(ac[i])
            {
            case 48: // '0'
                vector.addElement(new Integer(0));
                break;

            case 49: // '1'
                vector.addElement(new Integer(1));
                break;

            case 50: // '2'
                vector.addElement(new Integer(2));
                break;

            case 51: // '3'
                vector.addElement(new Integer(3));
                break;

            case 52: // '4'
                vector.addElement(new Integer(4));
                break;

            case 53: // '5'
                vector.addElement(new Integer(5));
                break;

            case 54: // '6'
                vector.addElement(new Integer(6));
                break;

            case 55: // '7'
                vector.addElement(new Integer(7));
                break;

            case 56: // '8'
                vector.addElement(new Integer(8));
                break;

            case 57: // '9'
                vector.addElement(new Integer(9));
                break;
            }

        return vector;
    }

    public void badState(IJxfsServiceJob ijxfsservicejob, int i)
    {
    }

    public void badJob(IJxfsServiceJob ijxfsservicejob, Exception exception)
    {
    }

    private static final String ORIGIN = "TIO_DS";
    private static final String DESCRIPTION = "Text Input/Output Device Service";
    private QueueControl qc;
    private int openCount;
    private final int INTERNAL_TIMEOUT = 2000;
    private boolean shuttingDownIsActive;
    private JxfsLocalDeviceInformation localDeviceInfo;
    private JxfsTIOStatus status;
    private TextInOutDeviceGui devGUI;
    JxfsLogger l;
    private JxfsTIOResolution resolution_;
    private Vector availResolutions_;
    private int onlineLEDStatus;
    private int inputLEDStatus;
}
