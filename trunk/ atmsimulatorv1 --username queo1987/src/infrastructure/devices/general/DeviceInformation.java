// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceInformation.java

package infrastructure.devices.general;

import com.jxfs.events.JxfsException;

// Referenced classes of package com.jxfs.general:
//            JxfsType, JxfsVersion

public abstract class DeviceInformation extends DeviceDataType
    implements Cloneable
{

    public DeviceInformation(String s, String s1, String s2, String s3, String s4, String s5)
    {
        deviceName = s;
        communicationInfo = s1;
        controlName = s2;
        clientCommName = s3;
        deviceKey = s4;
        if(s5 == null)
            description = "";
        else
            description = s5;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDeviceKey()
    {
        return deviceKey;
    }

    public String getCommunicationInfo()
    {
        return communicationInfo;
    }

    public String getControlName()
    {
        return controlName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    /**
     * @deprecated Method getLocalDeviceName is deprecated
     */

    public String getLocalDeviceName()
    {
        return deviceName;
    }

    public String getClientCommName()
    {
        return clientCommName;
    }

    public String getFirmwareVersion()
    {
        return "Firmware Version: 1.0";
    }

    public byte[] getFirmware()
        //throws DeviceException
    {
        return null;
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError();
        }
    }

    public String toString()
    {
        return getDeviceName() + " (" + getClass().getName() + ")";
    }

    public String getDebugInfo()
    {
        return this + "\n Description: " + getDescription() + "\n Control name: " + getControlName() + "\n Client communication name: " + getClientCommName() + "\n Communication info: " + getCommunicationInfo() + "\n Device key: " + getDeviceKey();
    }

    private final String deviceName;
    private final String communicationInfo;
    private final String controlName;
    private final String clientCommName;
    private final String description;
    private final String deviceKey;
}
