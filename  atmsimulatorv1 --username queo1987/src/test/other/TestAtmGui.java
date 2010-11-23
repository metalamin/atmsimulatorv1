package test.other;

import com.jxfs.forum.i18n.LocalizedErrorMessages;
import com.jxfs.forum.logging.StdLoggerFile;
import com.jxfs.general.*;
import infrastructure.communication.CommunicationHandlerFactory;
import config.SystemConfig;
import infrastructure.devices.DeviceConfig;
import infrastructure.devices.DeviceHandlerFactory;
import infrastructure.devices.controllers.IDeviceController;
import infrastructure.devices.controllers.jxfs.JxfsImplConst;
import infrastructure.devices.controllers.msd.IMsdConst;
import infrastructure.devices.controllers.tio.ITioConst;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import infrastructure.devices.general.IDeviceManager;
import domain.state.StateHandlerFactory;
import domain.statemachine.StateMachineFactory;
import infrastructure.services.comm.message.MessageFactory;
import infrastructure.services.comm.message.ifx.IFXMessageFactory;
import infrastructure.services.comm.message.ifx.IFXPackager;
import infrastructure.services.comm.channel.tcp.TCPCommConstants;
import infrastructure.services.comm.channel.tcp.TCPCommFactory;


public class TestAtmGui extends JFrame
        implements /*IGUIEventCallbacks,*/ IJxfsLogListener, IJxfsConst
{
    
    public TestAtmGui()
    {
        stdLogFile_ = null;
        wndw_count_ = 0;
        shaddow_ = null;
        ta_ = null;
        appletFlag = false;
        atmGui = this;
    }
    
    public static TestAtmGui getInstance()
    {
        return atmGui;
    }
    
    public static void main(String args[])
    {
        String s = "";
        String workstation_name = workstation_name__;
        String config_key = config_key__;
        String sdm_name = sdm_name__;
        int sdm_addparmcount = sdm_addparmcount__;
        String sdm_addparms = sdm_addparms__;
        try
        {
            String s1;
            if(args.length > 0)
                s1 = args[0];
            else
                s1 = "cfg/demo.cfg";
            Properties properties = new Properties();
            try
            {
                SystemConfig.getInstance().configure();
                properties.load(new FileInputStream(s1));
                workstation_name = properties.getProperty("workstation_name", workstation_name__).trim();
                config_key = properties.getProperty("config_key", config_key__).trim();
                logfilename = properties.getProperty("log_file_name", "").trim();
                sdm_name = properties.getProperty("sdm_name", sdm_name__).trim();
                try
                {
                    sdm_addparmcount = (new Integer(properties.getProperty("sdm_addparmcount", "0"))).intValue();
                }
                catch(NumberFormatException numberformatexception)
                {
                    System.out.println(LocalizedErrorMessages.getFormattedErrorMessage("JXFS217", "JXFS004", new Object[] {
                        "sdm_addparmcount", s1
                    }));
                    System.exit(1);
                }
                for(int j = 1; j <= sdm_addparmcount; j++)
                {
                    Integer integer = new Integer(j);
                    sdm_addparms = sdm_addparms + "," + properties.getProperty("sdm_addparm_" + integer.toString(), "").trim();
                }
                
                String s6 = properties.getProperty("imagepath", imagepath__).trim();
                imagepath__ = s6;
                String s7 = properties.getProperty("viewprogram", viewprogram__).trim();
                viewprogram__ = s7;
            }
            catch(IOException ioexception)
            {
                System.out.println(LocalizedErrorMessages.getFormattedErrorMessage("JXFS218", "JXFS004", new Object[] {
                    s1
                }));
                System.exit(1);
            }
        }
        catch(Exception exception)
        {
            System.out.println("Exception: " + exception);
            exception.printStackTrace();
        }
        
        Vector devices=new Vector();
        Map deviceInfo;
        
        deviceInfo = new HashMap();
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_CLASS, "infrastructure.devices.controllers.implementation.jxfs.JxfsTioController");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_NAME, "TextInOut");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_TITLE, "TextInOut");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_IMAGEPATH, "petecopath");
        deviceInfo.put(ITioConst.TIO_FLUSH, true);
        deviceInfo.put(ITioConst.TIO_AUTOEND, true);
        deviceInfo.put(ITioConst.TIO_DISPLAY_LIGHT_ON, true);
        deviceInfo.put(ITioConst.TIO_MODE, 0);
        deviceInfo.put(ITioConst.TIO_NUM_OF_CHARS, 4);
        devices.add(deviceInfo);
        
        deviceInfo = new HashMap();
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_CLASS, "infrastructure.devices.controllers.implementation.jxfs.JxfsMsdController");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_NAME, "MagStripe");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_TITLE, "MagStripe");
        deviceInfo.put(JxfsImplConst.JXFS_DEVICE_IMAGEPATH, "petecopath");
        deviceInfo.put(IMsdConst.MSD_TRACK1_SELECTED, true);
        deviceInfo.put(IMsdConst.MSD_TRACK2_SELECTED, true);
        deviceInfo.put(IMsdConst.MSD_TRACK3_SELECTED, true);
        devices.add(deviceInfo);
        
        atmGui = new TestAtmGui();
        atmGui.initialization(workstation_name, config_key, sdm_name, sdm_addparmcount, sdm_addparms, logfilename, devices);
        
        atmGui.setSize(400, 400);
        atmGui.setDefaultCloseOperation(0);
        atmGui.getContentPane().setLayout(new BorderLayout());
        
        //atmGui.show();
        try
        {
//            // crea e inicializa un communication factory
//            Map context = new HashMap();
//            context.put(TCPCommConstants.PORT, new Integer(8080));
//            context.put(TCPCommConstants.HOST, "localhost");
//            context.put(TCPCommConstants.TYPE, TCPCommConstants.CLIENT_TYPE);
//            context.put(TCPCommConstants.PACKAGER, new IFXPackager());
//            TCPCommFactory tcf = new TCPCommFactory();
//            tcf.setContext(context);
//            
//            // crea e inicializa un message factory
//            MessageFactory mf = new IFXMessageFactory();
//
//            // setea los factorys al handler del sistema (singleton)
//            CommunicationHandlerFactory.getCommunicationHandler().setCommFactory(tcf);
//            CommunicationHandlerFactory.getCommunicationHandler().setMessageFactory(mf);

            
            StateHandlerFactory.getStateHandler().load("cfg/MAQ_ESTADOS_PROTOTYPE.xml");
            StateMachineFactory.getStateMachine().startup("ESTADO_alfa");
            
            myManager.getDevice("TextInOut").open();
            myManager.getDevice("MagStripe").open();
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        };
        
    }
    
    public String getAppletInfo()
    {
        return "MelangeDemo - J/XFS Demo Applet (C) Copyright International Business Machines Corporation, 2000, 2001. All rights reserved.";
    }
    
    public void GUICloseEvent()
    {
        endDemo();
    }
    //workstation_name, config_key, sdm_name, sdm_addparmcount, sdm_addparms, jxfs_logfilename
    public void initialization(String workstation_name, String config_key, String sdm_name,
                                int sdm_addparmcount, String sdm_addparms, String jxfs_logfilename, Vector devices)
    {
        Map context = new HashMap();
        context.put(JxfsImplConst.JXFS_WORKSTATION_NAME, workstation_name);
        context.put(JxfsImplConst.JXFS_CONFIG_KEY, config_key);
        context.put(JxfsImplConst.JXFS_SDM_NAME, sdm_name);
        context.put(JxfsImplConst.JXFS_SDM_ADDPARMCOUNT, sdm_addparmcount);
        context.put(JxfsImplConst.JXFS_SDM_ADDPARM, sdm_addparms);
        context.put(JxfsImplConst.JXFS_LOG_FILE_NAME, jxfs_logfilename);
        context.put(JxfsImplConst.JXFS_DEVICES_INFO, devices);
        byte byte0 = 2;
        int j = 1;
        byte byte1 = 10;
        byte byte2 = 10;
        byte byte3 = 20;
        byte byte4 = 10;
        
        // OJO, ESTO ES PROVISORIO, EN REALIDAD EL CONTEXT TIENE QUE IR EN EL DEVICE_CONTEXT.xml
        DeviceConfig.getInstance().setContext(context);
        
        // Aca es donde recien se configura realmente el device manager
        // porque el getDeviceHandler invoca al doConfig del DeviceConfig
        myManager = DeviceHandlerFactory.getDeviceHandler().getDeviceManager();
        myManager.addUpdateListener(StateMachineFactory.getStateMachine());
        
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            if(!appletFlag)
                System.err.println("Error loading L&F: " + exception);
        }
        
        myLogger.writeLog(this, "MelangeDemo", 104, "successfully generated the JxfsDeviceManager");

        /*      getDeviceButton_ = msrgui_.addDefaultButton("Get Device", 'd', new ActionListener() {
   
            public void actionPerformed(ActionEvent actionevent)
            {
                String s6 = ((String)box_.getSelectedItem()).trim();
                if(box_.getItemCount() <= 0)
                    return;
        //        msrgui_.setStatusText("Getting device ... " + s6);
                String s7 = "";
                byte byte5 = 0;
                try
                {
                    com.jxfs.control.IJxfsBaseControl ijxfsbasecontrol = TestAtmGui.myManager.getDevice((String)shaddow_.get(s6));
                    String s8 = ijxfsbasecontrol.getClass().getName();
                    if(ijxfsbasecontrol instanceof JxfsAlarm)
                        byte5 = 1;
                    if(ijxfsbasecontrol instanceof JxfsMagStripe)
                        byte5 = 2;
                    if(ijxfsbasecontrol instanceof JxfsChipCard)
                        byte5 = 3;
                    if(ijxfsbasecontrol instanceof JxfsTIO)
                        byte5 = 4;
                    if(ijxfsbasecontrol instanceof JxfsPINKeypad)
                        byte5 = 5;
                    if(ijxfsbasecontrol instanceof JxfsDocumentPrinter)
                        byte5 = 6;
                    if(ijxfsbasecontrol instanceof JxfsCashDispenser)
                        byte5 = 7;
                    switch(byte5)
                    {
                    case 1: // '\001'
                        ALMControlWindow almcontrolwindow = new ALMControlWindow((JxfsAlarm)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 2: // '\002'
                        MSDControlWindow msdcontrolwindow = new MSDControlWindow((JxfsMagStripe)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 3: // '\003'
                        CCDControlWindow ccdcontrolwindow = new CCDControlWindow((JxfsChipCard)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 4: // '\004'
                        TIOControlWindow tiocontrolwindow = new TIOControlWindow((JxfsTIO)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 5: // '\005'
                        //PINControlWindow pincontrolwindow = new PINControlWindow((JxfsPINKeypad)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
//                        boolean autoEndSelected_;
//                        boolean beepOnPress_;
//                        int minLength_;
//                        int maxLength_;
//                        // INITIALIZATION
//                        autoEndSelected_ = true;
//                        beepOnPress_ = true;
//                        minLength_ = 1;
//                        maxLength_ = 4;
//                        // PIN KEYPAD DECLARATION
//                        JxfsPINKeypad pin_ = (JxfsPINKeypad)ijxfsbasecontrol;
//                        // OPEN
//                        int callID_ = pin_.open();
//                        // READ
//                        JxfsPINFDKeysSelection jxfspinfdkeysselection = new JxfsPINFDKeysSelection(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
//                        JxfsPINFKeySet jxfspinfkeyset = pin_.getSupportedFKeys();
//                        JxfsPINFKeysSelection jxfspinfkeysselection = new JxfsPINFKeysSelection(jxfspinfkeyset.isFk0(), jxfspinfkeyset.isFk1(), jxfspinfkeyset.isFk2(), jxfspinfkeyset.isFk3(), jxfspinfkeyset.isFk4(), jxfspinfkeyset.isFk5(), jxfspinfkeyset.isFk6(), jxfspinfkeyset.isFk7(), jxfspinfkeyset.isFk8(), jxfspinfkeyset.isFk9(), jxfspinfkeyset.isFkEnter(), jxfspinfkeyset.isFkCancel(), jxfspinfkeyset.isFkClear(), jxfspinfkeyset.isFkBackspace(), jxfspinfkeyset.isFkHelp(), jxfspinfkeyset.isFkDecPoint(), jxfspinfkeyset.isFk00(), jxfspinfkeyset.isFk000());
//                        JxfsPINFKeysSelection jxfspinfkeysselection1 = new JxfsPINFKeysSelection(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
//                        boolean flag = autoEndSelected_;
//                        if(jxfspinfkeysselection.isFkEnter() || jxfspinfkeysselection.isFkCancel())
//                        {
//                            jxfspinfkeysselection1.setFkEnter(jxfspinfkeysselection.isFkEnter());
//                            jxfspinfkeysselection1.setFkCancel(jxfspinfkeysselection.isFkCancel());
//                        }
//                        else
//                        {
//                            flag = true;
//                        }
//                        JxfsPINReadMode jxfspinreadmode = new JxfsPINReadMode(jxfspinfdkeysselection, jxfspinfkeysselection, jxfspinfdkeysselection, jxfspinfkeysselection1, flag, beepOnPress_, 5055, maxLength_, minLength_);
//                        synchronized(TestAtmGui.this)
//                        {
//                            callID_ = pin_.readData(jxfspinreadmode);
//                            //setCancelButton(true);
//                            //gui_.setStatusText(MelangeDemoConst.getStatusText("Reading data with Job ID: %1 ...", callID_));
//                        }
   
                        PINController pincontroller = new PINController((JxfsPINKeypad)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        pincontroller.open();
                        pincontroller.read();
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 6: // '\006'
                        PTRControlWindow ptrcontrolwindow = new PTRControlWindow((JxfsDocumentPrinter)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    case 7: // '\007'
                        CDRControlWindow cdrcontrolwindow = new CDRControlWindow((JxfsCashDispenser)ijxfsbasecontrol, TestAtmGui.myManager, box_.getSelectedItem() + ": " + ++wndw_count_, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText(s8 + " - " + "Getting device ready.");
                        break;
   
                    default:
                        InfoBox.showMessageDialog(msrgui_.getFrame(), "Device not supported by demo!" + s8, "Getting device ...", 4, 1, TestAtmGui.Jxfs_imagepath__);
                        msrgui_.setStatusText("Device not supported by demo!");
                        break;
                    }
                }
                catch(JxfsException jxfsexception1)
                {
                    InfoBox.showMessageDialog(msrgui_.getFrame(), MelangeDemoConst.checkJxfsException(jxfsexception1), "Getting device ...", 4, 1, TestAtmGui.Jxfs_imagepath__);
                    msrgui_.setStatusText("Sorry, device not available: ");
                }
            }
   
        });

   */
        myLogger.writeLog(this, "MelangeDemo", 104, "MelangeDemo calls DeviceManager.deviceList()");
        //Vector vector = myManager.getDeviceList(912);
        myLogger.writeLog(this, "MelangeDemo", 104, "MelangeDemo call DeviceManager.deviceList() returns.");
        //     shaddow_ = fillDeviceBox(vector, shaddow_);
        //     msrgui_.show();
    }
    
    public void errorLogOccurred(String s, String s1, long l, long l1, String s2,
            String s3, String s4, String s5, Date date)
    {
        if(ta_ != null)
        {
            ta_.insert("\n", 0);
            ta_.insert(date.toString() + " " + s1 + " " + s2 + " " + s5, 0);
        }
    }
    
    public boolean initialize(String s)
    {
        return true;
    }
    
    public void shutdown()
    {
    }
    
    public String getDescription()
    {
        return "MelangeDemo";
    }
    
    public void logOccurred(String s, String s1, int i, String s2, String s3, Date date)
    {
        if(ta_ != null)
        {
            ta_.insert("\n", 0);
            ta_.insert(date.toString() + " " + Integer.toString(i) + " " + s1 + " " + s2 + " " + s3, 0);
        }
    }
    
    private Hashtable fillDeviceBox(Vector vector, Hashtable hashtable)
    {
        if(hashtable != null)
            hashtable.clear();
        else
            hashtable = new Hashtable();
        if(vector != null)
        {
            for(int i = 0; i < vector.size(); i++)
            {
                String s = ((JxfsDeviceInformation)vector.elementAt(i)).getDeviceName();
                String s1 = ((JxfsDeviceInformation)vector.elementAt(i)).getDescription();
                if(s1.length() > 0)
                {
                    String s2 = s1 + " (" + s + ")";
                    box_.addItem(s2);
                    hashtable.put(s2, s);
                }
                else
                {
                    box_.addItem(s);
                    hashtable.put(s, s);
                }
            }
            
        }
        else
        {
            hashtable = null;
        }
        getDeviceButton_.setEnabled(box_.getItemCount() > 0);
        return hashtable;
    }
    
    private void endDemo()
    {
        myLogger.deregisterModule("MelangeDemo");
        myManager.shutdown();
        if(!appletFlag)
            System.exit(0);
    }
    
    private static TestAtmGui atmGui;
    private static String workstation_name__ = "Default_Workstation";
    private static String config_key__ = "Demo Application";
    private static String sdm_name__ = "NoSpecificDeviceManager";
    private static int sdm_addparmcount__ = 0;
    private static String sdm_addparms__ = "";
    private static String logfilename__;
    private static String imagepath__ = "com/jxfs/samples/application/images";
    private static String viewprogram__ = "notepad.exe";
    private static IDeviceManager myManager;
    private static JxfsLogger myLogger = JxfsLogger.getReference();
    private static String logfilename;
    private StdLoggerFile stdLogFile_;
    //private SimpleDCGUI msrgui_;
    private JComboBox box_;
    private JButton getDeviceButton_;
    private Dialog aboutBox;
    private int wndw_count_;
    private Hashtable shaddow_;
    private JTextArea ta_;
    private JxfsId logId_;
    private boolean appletFlag;
    private static IDeviceController ijxfsbasecontrol;
    static
    {
        logfilename__ = "";
        logfilename = logfilename__;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
