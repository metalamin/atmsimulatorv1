// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceStatus.java

package infrastructure.devices.general;


// Referenced classes of package com.jxfs.general:
//            JxfsType

public class DeviceStatus extends DeviceDataType
    implements Cloneable
{

    public DeviceStatus()
    {
        open = false;
        claimPending = false;
        claimed = false;
        busy = false;
        hardwareError = false;
        userActionError = false;
        powerSave = false;
    }

    public void setOpen(boolean flag)
    {
        open = flag;
    }

    public void setClaimPending(boolean flag)
    {
        claimPending = flag;
    }

    public void setClaimed(boolean flag)
    {
        claimed = flag;
    }

    public void setBusy(boolean flag)
    {
        busy = flag;
    }

    public void setHardwareError(boolean flag)
    {
        hardwareError = flag;
    }

    public void setUserActionError(boolean flag)
    {
        userActionError = flag;
    }

    public void setPowerSave(boolean flag)
    {
        powerSave = flag;
    }

    public boolean isOpen()
    {
        return open;
    }

    public boolean isClaimPending()
    {
        return claimPending;
    }

    public boolean isClaimed()
    {
        return claimed;
    }

    public boolean isBusy()
    {
        return busy;
    }

    public boolean isHardwareError()
    {
        return hardwareError;
    }

    public boolean isUserActionError()
    {
        return userActionError;
    }

    public boolean isPowerSave()
    {
        return powerSave;
    }

    public boolean isWorking()
    {
        return isOpen() && !isHardwareError() && !isUserActionError();
    }

    public boolean equals(Object obj)
    {
        return obj != null && (obj instanceof DeviceStatus) && ((DeviceStatus)obj).isOpen() == open && ((DeviceStatus)obj).isClaimed() == claimed && ((DeviceStatus)obj).isClaimPending() == claimPending && ((DeviceStatus)obj).isBusy() == busy && ((DeviceStatus)obj).isHardwareError() == hardwareError && ((DeviceStatus)obj).isUserActionError() == userActionError && ((DeviceStatus)obj).isPowerSave() == powerSave;
    }

    public int hashCode()
    {
        return (open ? 1 : 0) + (claimed ? 2 : 0) + (claimPending ? 4 : 0) + (busy ? 8 : 0) + (hardwareError ? 16 : 0) + (userActionError ? 32 : 0) + (powerSave ? 64 : 0);
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            return null;
        }
    }

    public String toString()
    {
        return "DeviceStatus(" + (isOpen() ? "O" : "o") + (isClaimed() ? "C" : "c") + (isClaimPending() ? "P" : "p") + (isBusy() ? "B" : "b") + (isHardwareError() ? "H" : "h") + (isUserActionError() ? "U" : "u") + (isPowerSave() ? "S" : "s") + (isWorking() ? "W" : "w") + ")@" + Integer.toHexString(hashCode());
    }

    public String getDebugInfo()
    {
        String s = System.getProperty("line.separator");
        return getClass().getName() + ": " + this + s + "  Open: " + isOpen() + s + "  Claimed: " + isClaimed() + s + "  Claim pending: " + isClaimPending() + s + "  Busy: " + isBusy() + s + "  Hardware error: " + isHardwareError() + s + "  User action error: " + isUserActionError() + s + "  Power save: " + isPowerSave() + s + "  Working: " + isWorking();
    }

    private boolean open;
    private boolean claimPending;
    private boolean claimed;
    private boolean busy;
    private boolean hardwareError;
    private boolean userActionError;
    private boolean powerSave;
}
