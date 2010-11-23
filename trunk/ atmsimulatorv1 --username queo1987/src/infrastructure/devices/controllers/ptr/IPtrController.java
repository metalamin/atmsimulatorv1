package infrastructure.devices.controllers.ptr;

import com.jxfs.control.ptr.JxfsPtrCtrlMediaCapability;
import com.jxfs.control.ptr.JxfsPtrExtentCapability;
import com.jxfs.control.ptr.JxfsPtrFormsConfig;
import com.jxfs.control.ptr.JxfsPtrStatus;
import com.jxfs.control.ptr.JxfsPtrWriteFormCapability;
import infrastructure.devices.controllers.IDeviceController;
import infrastructure.devices.events.DeviceException;



public interface IPtrController
    extends IDeviceController
{
//    public abstract boolean isCompound()
//        throws DeviceException;
//
//    public abstract JxfsPtrCtrlMediaCapability getCtrlMediaCapability()
//        throws DeviceException;
//
//    public abstract JxfsPtrExtentCapability getExtentCapability()
//        throws DeviceException;
//
//    public abstract JxfsPtrFormsConfig getFormsConfig()
//        throws DeviceException;
//
//    public abstract void setFormsConfig(JxfsPtrFormsConfig jxfsptrformsconfig)
//        throws DeviceException;
//
//    public abstract JxfsPtrStatus getPtrStatus()
//        throws DeviceException;
//
//    public abstract JxfsPtrWriteFormCapability getWriteFormCapability()
//        throws DeviceException;
//
//    public abstract int ctrlMedia(int i)
//        throws DeviceException;
//
//    public abstract int getFormList()
//        throws DeviceException;
//
//    public abstract int mediaExtents()
//        throws DeviceException;
//
//    public abstract int getMediaList()
//        throws DeviceException;

    public void printForm(String formName, String mediumName, String fieldContent[])
        throws DeviceException;

//    public abstract int printRawData(byte abyte0[], boolean flag)
//        throws DeviceException;
//
//    public abstract int getFieldDescription(String as[], String s)
//        throws DeviceException;
//
//    public abstract int getFormDescription(String s)
//        throws DeviceException;
//
//    public abstract int getMediaDescription(String s)
//        throws DeviceException;
//
//    public abstract int resetPrinter()
//        throws DeviceException;
}
