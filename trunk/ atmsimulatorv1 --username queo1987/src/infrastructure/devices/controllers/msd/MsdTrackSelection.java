
package infrastructure.devices.controllers.msd;


// Referenced classes of package com.jxfs.control.msd:
//            MsdTracks

public class MsdTrackSelection extends MsdTracks
{

    public MsdTrackSelection(boolean flag, boolean flag1, boolean flag2)
    {
        super(flag, flag1, flag2);
    }

    public void setTrack1(boolean flag)
    {
        super.setTrack1(flag);
    }

    public void setTrack2(boolean flag)
    {
        super.setTrack2(flag);
    }

    public void setTrack3(boolean flag)
    {
        super.setTrack3(flag);
    }

    public void setAllTracks()
    {
        setTrack1(true);
        setTrack2(true);
        setTrack3(true);
    }

    public void setNoTracks()
    {
        setTrack1(false);
        setTrack2(false);
        setTrack3(false);
    }

    public String getDebugInfo()
    {
        return "JxfsMSDTrackSelection(track1:" + isTrack1() + ",track2:" + isTrack2() + ",track3:" + isTrack3() + ")@" + Integer.toHexString(hashCode());
    }

    public String toString()
    {
        return getDebugInfo();
    }

}
