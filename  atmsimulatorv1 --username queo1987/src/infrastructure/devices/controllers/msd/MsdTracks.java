// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsdTracks.java

package infrastructure.devices.controllers.msd;

import com.jxfs.general.JxfsLogger;
import com.jxfs.general.JxfsType;

public class MsdTracks extends JxfsType
{

    public MsdTracks(boolean flag, boolean flag1, boolean flag2)
    {
        track1 = false;
        track2 = false;
        track3 = false;
        if(JxfsLogger.getReference().isLogActive("MSD_DC", 80))
            JxfsLogger.getReference().writeLog(this, "MSD_DC", 80, "JxfsMSDTracks(" + flag + "," + flag1 + "," + flag2 + ")");
        track1 = flag;
        track2 = flag1;
        track3 = flag2;
    }

    public boolean isTrack1()
    {
        return track1;
    }

    public boolean isTrack2()
    {
        return track2;
    }

    public boolean isTrack3()
    {
        return track3;
    }

    protected void setTrack1(boolean flag)
    {
        if(JxfsLogger.getReference().isLogActive("MSD_DC", 80))
            JxfsLogger.getReference().writeLog(this, "MSD_DC", 80, "JxfsMSDTracks.setTrack1(" + track1 + ")");
        track1 = flag;
    }

    protected void setTrack2(boolean flag)
    {
        if(JxfsLogger.getReference().isLogActive("MSD_DC", 80))
            JxfsLogger.getReference().writeLog(this, "MSD_DC", 80, "JxfsMSDTracks.setTrack2(" + track2 + ")");
        track2 = flag;
    }

    protected void setTrack3(boolean flag)
    {
        if(JxfsLogger.getReference().isLogActive("MSD_DC", 80))
            JxfsLogger.getReference().writeLog(this, "MSD_DC", 80, "JxfsMSDTracks.setTrack3(" + track3 + ")");
        track3 = flag;
    }

    public boolean allTracks()
    {
        return track1 && track2 && track3;
    }

    public boolean noTracks()
    {
        return !track1 && !track2 && !track3;
    }

    public String toString()
    {
        return "JxfsMSDTracks(track1:" + isTrack1() + ",track2:" + isTrack2() + ",track3:" + isTrack3() + ")@" + Integer.toHexString(hashCode());
    }

    public String getDebugInfo()
    {
        return toString();
    }

    private boolean track1;
    private boolean track2;
    private boolean track3;
}
