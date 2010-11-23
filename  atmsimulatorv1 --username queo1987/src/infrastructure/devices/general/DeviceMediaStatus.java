// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceMediaStatus.java

package infrastructure.devices.general;


// Referenced classes of package com.jxfs.general:
//            JxfsType, JxfsLogger

public final class DeviceMediaStatus extends DeviceDataType
    implements Cloneable
{

    public DeviceMediaStatus(int i)
    {
        mediaState = 0;
        mediaState = i;
    }

    public void setMediaState(int i)
    {
        mediaState = i;
    }

    public int getMediaState()
    {
        return mediaState;
    }

    public boolean isUnknown()
    {
        return (mediaState & 8) > 0;
    }

    public boolean isJammed()
    {
        return (mediaState & 2) > 0;
    }

    public boolean isPresent()
    {
        return (mediaState & 4) > 0;
    }

    public boolean isNotPresent()
    {
        return !isPresent();
    }

    public boolean isNotSupported()
    {
        return (mediaState & 0x10) > 0;
    }

    public boolean isEjected()
    {
        return (mediaState & 1) > 0;
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

    public String getDebugInfo()
    {
        return this + "\n  Unknown: " + isUnknown() + "\n  Jammed: " + isJammed() + "\n  Present: " + isPresent() + "\n  Not supported: " + isNotSupported() + "\n  Ejected: " + isEjected();
    }

    public String toString()
    {
        return "DeviceMediaStatus(" + mediaState + "-" + (isEjected() ? "E" : "e") + (isJammed() ? "J" : "j") + (isPresent() ? "P" : "p") + (isNotSupported() ? "N" : "n") + (isUnknown() ? "U" : "u") + ")@" + Integer.toHexString(hashCode());
    }

    public static final int DEV_S_MEDIA_EJECTED = 1;
    public static final int DEV_S_MEDIA_JAMMED = 2;
    public static final int DEV_S_MEDIA_UNKNOWN = 8;
    public static final int DEV_S_MEDIA_PRESENT = 4;
    public static final int DEV_S_MEDIA_NOT_PRESENT = 0;
    public static final int DEV_S_MEDIA_NOTSUPPORTED = 16;
    private int mediaState;
}
