
package infrastructure.services.jxfs.msd;

import com.jxfs.events.JxfsException;
import com.jxfs.general.JxfsDeviceManager;
import com.jxfs.general.JxfsLogger;
import infrastructure.services.jxfs.general.GeneralDeviceGUI;
import config.GlobalConfig;
import util.GeneralException;
import infrastructure.devices.controllers.msd.IMsdConst;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.*;
import presentation.IGUIBuilder;

public class MagneticStripeDeviceGUI
    implements WindowConstants, IGUIBuilder, Serializable
{

    public MagneticStripeDeviceGUI(MagneticStripeService msdservice, JxfsDeviceManager jxfsdevicemanager, JxfsLogger jxfslogger)
    {
        byte byte0 = 3;
        byte top = 10;
        byte left = 2;
        byte bottom = 20;
        byte right = 70;
        int i = 1;
        String title_ = "Magnetic Stripe";
        
        gui_listeners_ = new Vector();
        text_ = "";
        maxTextLength_ = 0;
        echoMode_ = 0;
        enterPressed_ = false;
        service_ = msdservice;
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            System.err.println("Error loading L&F: " + exception);
        }
        l_ = jxfslogger;
        dm_ = jxfsdevicemanager;
        
        msdDeviceGui = this;

        try{
            GlobalConfig.getInstance().addProperty(IMsdConst.MSD_THROWER, this);
        }
        catch(GeneralException jxfsexception)
        {
            System.out.println("Error al guardar MSDDeviceGUI como GUIBuilder en el repositorio: " + jxfsexception.getMessage() );
        }        
    }
    
    public void dispose()
    {
    }

    public void setText(int i, int j, String s)
    {
        int k = i * j;
        if(k > 1)
        {
            for(; text_.length() < i * j - 1; text_ = text_ + " ");
            text_ = text_.substring(0, i * j) + s;
        } else
        {
            text_ = s;
        }
        if(text_.length() > maxTextLength_)
            text_ = text_.substring(0, maxTextLength_);
    }

    public String readData(int i, boolean flag)
    {
        if(flag)
        {
            setBttnEnabled(true);
            readData_ = "";
            echoMode_ = i;
            enterPressed_ = false;
        }
        return readData_;
    }


    public String readTrack1()
    {
        return trackfield_1_.getText();
    }    

    public String readTrack2()
    {
        return trackfield_2_.getText();
    }    

    public String readTrack3()
    {
        return trackfield_3_.getText();
    }

    public void clearTracks()
    {
        trackfield_1_.setText("");
        trackfield_2_.setText("");
        trackfield_3_.setText("");
    }    

    public boolean enterKeySet()
    {
        return enterPressed_;
    }

    public void unsetEnterKey()
    {
        enterPressed_ = false;
    }
    
    public void setBttnEnabled(boolean flag)
    {
        unsetEnterKey();
        readbutton_.setEnabled(flag);
        //exceptionbutton_.setEnabled(flag);        
        
    }

    public void setTrack1Enabled(boolean flag)
    {
        track1Selected_ = flag;
        trackfield_1_.setEnabled(flag);
        trackfield_1_.setOpaque(flag);
    }

    public void setTrack2Enabled(boolean flag)
    {
        track2Selected_ = flag;
        trackfield_2_.setEnabled(flag);
        trackfield_2_.setOpaque(flag);
    }

    public void setTrack3Enabled(boolean flag)
    {
        track3Selected_ = flag;
        trackfield_3_.setEnabled(flag);
        trackfield_3_.setOpaque(flag);
    }
    
    private void setBttnBackground(boolean flag)
    {
        if(flag)
        {
        } else
        {
        }
    }
    
    private void setRWButtons()
    {
        boolean flag;
        if(track1Selected_ || track2Selected_ || track3Selected_)
            flag = true;
        else
            flag = false;
        readbutton_.setEnabled(flag);
    }
    
    public Component getComponent(){
        return gui_.getFrame().getContentPane();
    }

    public static MagneticStripeDeviceGUI getInstance(){
        return msdDeviceGui;
    }
    
    public Component createGUI(int top, int left, int bottom, int right){
        byte byte0 = 3;
        int i = 1;
        String title_ = "Magnetic Stripe";        
        gui_ =  new GeneralMagneticStripeGui(title_, byte0, i, top, left, bottom, right);

        trackfield_1_ = gui_.addField("Track 1: ", 100);
        setTrack1Enabled(false);
        
        trackfield_2_ = gui_.addField("Track 2: ", 100);
        setTrack2Enabled(false);

        trackfield_3_ = gui_.addField("Track 3: ", 100);
        setTrack3Enabled(false);
        
        readbutton_ = gui_.addDefaultButton("Enter", 'r', new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                enterPressed_ = true;
            }
        });
        
        exceptionbutton_ = gui_.addDefaultButton("", '!', new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                exceptionGui_ = new GeneralDeviceGUI("", 0, 0, 0, 0, 0, 0);
                trackbox = new JCheckBox[30];

                trackbox[0] = exceptionGui_.addCheckBox("Read Failure", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        readFailure = trackbox[0].isSelected();
                    }
                });
                trackbox[0].setSelected(readFailure);
                
                trackbox[1] = exceptionGui_.addCheckBox("No Media Exception", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        noMedia = trackbox[1].isSelected();
                    }
                });
                trackbox[1].setSelected(noMedia);
                
                // OK BUTTON
                exceptionGui_.addDefaultButton("Ok", 'o', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        exceptionGui_.dispose();
                    }
                });
                exceptionGui_.show();
            }
        }); 
        exceptionbutton_.setIcon(new javax.swing.ImageIcon("cfg/resources/ExceptionIcon2VerySmall.JPG"));
        setRWButtons();
        return msdDeviceGui.getComponent();
    }
    
    private static final String ORIGIN = "MagneticStripeDeviceService";
    private MagneticStripeService service_;
    private GeneralMagneticStripeGui gui_;
    private JTextField trackfield_1_;
    private JTextField trackfield_2_;
    private JTextField trackfield_3_;
    private JButton readbutton_;    
    private JLabel groupLabel_;
    private Vector gui_listeners_;
    private JxfsLogger l_;
    private String imagepath_;
    private Integer posx_;
    private Integer posy_;
    private Integer sizex_;
    private Integer sizey_;
    private JxfsDeviceManager dm_;
    private ImageIcon greenLEDOn_;
    private ImageIcon greenLEDOff_;
    private String text_;
    private String readData_;
    private int maxTextLength_;
    private int echoMode_;
    private boolean enterPressed_;
    private boolean track1Selected_;
    private boolean track2Selected_;
    private boolean track3Selected_;
    private static MagneticStripeDeviceGUI msdDeviceGui;
    // exception gui
    private GeneralDeviceGUI exceptionGui_;
    private JButton exceptionbutton_;    
    private JCheckBox[] trackbox;
    // supported exceptions
//    public static final int JXFS_E_MSD_READFAILURE = 4007;
    private boolean readFailure = false;
//    public static final int JXFS_O_MSD_WRITEDATA = 4008;
//    public static final int JXFS_E_MSD_WRITEFAILURE = 4009;
//    public static final int JXFS_E_MSD_NOMEDIA = 4010;
    private boolean noMedia = false;
//    public static final int JXFS_E_MSD_INVALIDMEDIA = 4011;
    private boolean invalidMedia = false;
//    public static final int JXFS_E_MSD_MEDIAJAMMED = 4012;
//    public static final int JXFS_E_MSD_SHUTTERFAIL = 4013;
//    public static final int JXFS_I_MSD_NO_MEDIA_PRESENT = 4014;
//    public static final int JXFS_I_MSD_MEDIA_INSERTED = 4015;
//    public static final int JXFS_E_MSD_NOTSUPPORTEDTRACK = 4016;
    private boolean notSupportedTrack = false;
//    public static final int JXFS_E_MSD_NOTRACKS = 4017;
    private boolean noTracks = false;
//    public static final int JXFS_E_MSD_BADDATA = 4018;
//    public static final int JXFS_E_MSD_NOTSUPPORTEDCAP = 4056;
//    public static final int JXFS_E_MSD_PARITY = 4057;
    private boolean parityError = false;
//    public static final int JXFS_E_MSD_READ_EOF = 4058;
    private boolean readEof = false;
//    public static final int JXFS_E_MSD_NO_STRIPE = 4059;
    private boolean noStripe = false;
//    public static final int JXFS_E_MSD_READ_OTHER = 4060;
    private boolean readOther = false;

    public boolean isReadFailure() {
        return readFailure;
    }

    public void setReadFailure(boolean readFailure) {
        this.readFailure = readFailure;
    }

    public boolean isNoMedia() {
        return noMedia;
    }

    public void setNoMedia(boolean noMedia) {
        this.noMedia = noMedia;
    }

    public boolean isInvalidMedia() {
        return invalidMedia;
    }

    public void setInvalidMedia(boolean invalidMedia) {
        this.invalidMedia = invalidMedia;
    }

    public boolean isNotSupportedTrack() {
        return notSupportedTrack;
    }

    public void setNotSupportedTrack(boolean notSupportedTrack) {
        this.notSupportedTrack = notSupportedTrack;
    }

    public boolean isNoTracks() {
        return noTracks;
    }

    public void setNoTracks(boolean notTracks) {
        this.noTracks = noTracks;
    }

    public boolean isParityError() {
        return parityError;
    }

    public void setParityError(boolean parityError) {
        this.parityError = parityError;
    }

    public boolean isReadEof() {
        return readEof;
    }

    public void setReadEof(boolean readEof) {
        this.readEof = readEof;
    }

    public boolean isNoStripe() {
        return noStripe;
    }

    public void setNoStripe(boolean noStripe) {
        this.noStripe = noStripe;
    }

    public boolean isReadOther() {
        return readOther;
    }

    public void setReadOther(boolean readOther) {
        this.readOther = readOther;
    }








}
