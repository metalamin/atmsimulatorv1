package infrastructure.devices.controllers.tio;

import infrastructure.devices.controllers.IDeviceController;
import domain.state.Event;
import domain.statemachine.IUpdateListener;



public interface ITioController
    extends IDeviceController
{
    public boolean addUpdateListener(IUpdateListener updateListener);
    
    public void notifyUpdateListeners(Event ev);
    
//    public abstract int beep(int i, int j)
//        throws JxfsException;

//    public abstract int lightDisplay(boolean flag)
//        throws JxfsException;
//
//    public abstract int setLED(int i, int j)
//        throws JxfsException;
//
//    public abstract int getLED(int i)
//        throws JxfsException;
//
//    public abstract int clearScreen(int i, int j, int k, int l)
//        throws JxfsException;
//
//    public abstract int writeDisplayData(int i, int j, int k, int l, String s)
//        throws JxfsException;
//
//    public abstract int readKeyboardData(int i, int j, int k, int l, int i1, int j1, int k1, 
//            boolean flag, boolean flag1, boolean flag2)
//        throws JxfsException;
//
//    public abstract boolean isTextAttributeSupported(int i)
//        throws JxfsException;
//
//    public abstract boolean isCursorSupported()
//        throws JxfsException;
//
//    public abstract JxfsTIOResolution getResolution()
//        throws JxfsException;
//
//    public abstract void setResolution(JxfsTIOResolution jxfstioresolution)
//        throws JxfsException;
//
//    /**
//     * @deprecated Method getAvailableResolution is deprecated
//     */
//
//    public abstract Vector getAvailableResolution()
//        throws JxfsException;
//
//    public abstract Vector getAvailableResolutions()
//        throws JxfsException;
//
//    public abstract boolean isDisplayLightSupported()
//        throws JxfsException;
//
//    public abstract boolean isBeepSupported()
//        throws JxfsException;
//
//    public abstract int getMaxLED()
//        throws JxfsException;
//
//    public abstract boolean isKeyboardSupported()
//        throws JxfsException;
//
//    public abstract boolean isKeyboardLockSupported()
//        throws JxfsException;

}
