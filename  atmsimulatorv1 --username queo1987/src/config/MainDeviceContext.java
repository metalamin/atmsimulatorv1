package config;

import infrastructure.devices.DeviceConfig;
import infrastructure.devices.controllers.jxfs.JxfsImplConst;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.tio.ITioConst;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Seteo de contexto para las comunicaciones,
 * y seteo de las factorys.
 */
public class MainDeviceContext {
    public static void main(String[] args)
    {
        try
        {
            SystemConfig.getInstance().configure();
            //El contexto se configura segun cliente o servidor...
            Map context = new HashMap();

            Vector devices=new Vector();
            Map deviceInfo;

            deviceInfo = new HashMap();
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_CLASS, "infrastructure.devices.controllers.jxfs.JxfsTioController");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_NAME, "TextInOut");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_TITLE, "TextInOut");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_IMAGEPATH, "tiopath");
            deviceInfo.put(ITioConst.TIO_FLUSH, true);
            deviceInfo.put(ITioConst.TIO_AUTOEND, false);
            deviceInfo.put(ITioConst.TIO_DISPLAY_LIGHT_ON, true);
            deviceInfo.put(ITioConst.TIO_MODE, 0);
            deviceInfo.put(ITioConst.TIO_NUM_OF_CHARS, 4);
            devices.add(deviceInfo);

            deviceInfo = new HashMap();
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_CLASS, "infrastructure.devices.controllers.jxfs.JxfsMsdController");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_NAME, "MagStripe");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_TITLE, "MagStripe");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_IMAGEPATH, "magstripepath");
            deviceInfo.put(IMsdConst.MSD_TRACK1_SELECTED, true);
            deviceInfo.put(IMsdConst.MSD_TRACK2_SELECTED, true);
            deviceInfo.put(IMsdConst.MSD_TRACK3_SELECTED, true);
            devices.add(deviceInfo);

            deviceInfo = new HashMap();
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_CLASS, "infrastructure.devices.controllers.jxfs.JxfsPtrController");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_NAME, "DocuPrinter");
            deviceInfo.put(JxfsImplConst.JXFS_DEVICE_IMAGEPATH, "printerpath");
            devices.add(deviceInfo);            
            
            context.put(JxfsImplConst.JXFS_DEVICES_INFO, devices);
            context.put(JxfsImplConst.JXFS_WORKSTATION_NAME, "developerWorks");
            context.put(JxfsImplConst.JXFS_CONFIG_KEY, "jxfsClient");
            context.put(JxfsImplConst.JXFS_SDM_NAME, "com.jxfs.forum.communication.rmi.SpecificDeviceManagerRMI");
            context.put(JxfsImplConst.JXFS_SDM_ADDPARMCOUNT, 1);
            context.put(JxfsImplConst.JXFS_SDM_ADDPARM, "2323;localhost"); // for more than one parameter, separate with ","
            context.put(JxfsImplConst.JXFS_LOG_FILE_NAME, "logs/jxfsClient.log");
            
            DeviceConfig dc = DeviceConfig.getInstance();
            dc.setDeviceManager("infrastructure.devices.controllers.jxfs.DeviceManager");
            dc.setContext(context);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
