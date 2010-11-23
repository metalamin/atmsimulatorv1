
package infrastructure.services.jxfs.tio;

import com.jxfs.control.tio.JxfsTIOResolution;
import com.jxfs.events.JxfsException;
import com.jxfs.general.JxfsDeviceManager;
import com.jxfs.general.JxfsLogger;
import infrastructure.services.jxfs.general.IWindowEventListener;
import infrastructure.services.jxfs.general.GeneralDeviceGUI;
import config.GlobalConfig;
import util.GeneralException;
import infrastructure.devices.controllers.tio.ITioConst;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import presentation.IGUIBuilder;

public class TextInOutDeviceGui
    implements WindowConstants, IGUIBuilder, Serializable
{

    public TextInOutDeviceGui(TextInOutService textInOutService, JxfsDeviceManager jxfsdevicemanager, JxfsLogger jxfslogger)
    {
        gui_listeners_ = new Vector();
        text_ = "";
        maxTextLength_ = 0;
        echoMode_ = 0;
        enterPressed_ = false;
        service_ = textInOutService;
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            System.err.println("Error loading look and feel: " + exception);
        }       
        l_ = jxfslogger;
        dm_ = jxfsdevicemanager;
        try
        {
            imagepath_ = (String)jxfsdevicemanager.getValueForKey("imagepath");
            posx_ = (Integer)jxfsdevicemanager.getValueForKey("tioguipos_x");
            posy_ = (Integer)jxfsdevicemanager.getValueForKey("tioguipos_y");
            sizex_ = (Integer)jxfsdevicemanager.getValueForKey("tioguisize_x");
            sizey_ = (Integer)jxfsdevicemanager.getValueForKey("tioguisize_y");
        }
        catch(JxfsException jxfsexception)
        {
            imagepath_ = "images";
            posx_ = new Integer(400);
            posy_ = new Integer(200);
            sizex_ = new Integer(200);
            sizey_ = new Integer(200);
        }

        try{
            GlobalConfig.getInstance().addProperty(ITioConst.TIO_THROWER, this);
        }
        catch(GeneralException jxfsexception)
        {
            System.out.println("Error saving TextInOutDeviceGui like a GUIBuilder in the repository: " + jxfsexception.getMessage() );
        }        
        
    }

    public void dispose()
    {
        try
        {
            dm_.setValueForKey("tioguipos_x", posx_);
            dm_.setValueForKey("tioguipos_y", posy_);
            dm_.setValueForKey("tioguisize_x", sizex_);
            dm_.setValueForKey("tioguisize_y", sizey_);
        }
        catch(JxfsException jxfsexception) { }
        frame_.dispose();
    }

    public void beep()
    {
        char c = '\007';
        status_bar_.setText("Beeping ...");
        System.out.print(c);
        status_bar_.setText("");
    }

    public void setDisplayLight(boolean flag)
    {
        if(flag)
        {
            status_bar_.setText("Switched display light on.");
            textArea_.setBackground(Color.yellow.darker());
        } else
        {
            status_bar_.setText("Switched display light off.");
            textArea_.setBackground(Color.lightGray);
        }
        setBttnBackground(flag);
    }

    public void setInputLED(boolean flag)
    {
        if(flag)
            input_.setIcon(greenLEDOn_);
        else
            input_.setIcon(greenLEDOff_);
    }

    public void setOnlineLED(boolean flag)
    {
        if(flag)
            online_.setIcon(greenLEDOn_);
        else
            online_.setIcon(greenLEDOff_);
    }

    public void clearScreen()
    {
        text_ = "";
        textArea_.setText(text_);
        status_bar_.setText("Display was cleared.");
    }

    public void deleteKey()
    {
        if(text_.length() > 0)
            text_ = text_.substring(0, text_.length() - 1);
        textArea_.setText(text_);
        textArea_.invalidate();
    }

    public void appendKey(String s)
    {
        switch(echoMode_)
        {
        default:
            break;

        case 8014: 
            text_ = text_ + s;
            if(text_.length() > maxTextLength_)
                text_ = text_.substring(0, maxTextLength_);
            textArea_.setText(text_);
            textArea_.invalidate();
            break;

        case 8016: 
            text_ = text_ + "*";
            if(text_.length() > maxTextLength_)
                text_ = text_.substring(0, maxTextLength_);
            textArea_.setText(text_);
            textArea_.invalidate();
            break;
        }
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
        textArea_.setText(text_);
        textArea_.invalidate();
    }

    public String readData(int i, boolean flag)
    {
        if(flag)
        {
            setBttnEnabled(true);
            readData_ = "";
            status_bar_.setText("Waiting for input ...");
            echoMode_ = i;
            enterPressed_ = false;
        }
        return readData_;
    }

    public boolean enterKeySet()
    {
        return enterPressed_;
    }

    public void setEnterKey(boolean pressed)
    {
        enterPressed_ = pressed;
    }    
    
    public void setBttnEnabled(boolean flag)
    {
        bttn0_.setEnabled(flag);
        bttn1_.setEnabled(flag);
        bttn2_.setEnabled(flag);
        bttn3_.setEnabled(flag);
        bttn4_.setEnabled(flag);
        bttn5_.setEnabled(flag);
        bttn6_.setEnabled(flag);
        bttn7_.setEnabled(flag);
        bttn8_.setEnabled(flag);
        bttn9_.setEnabled(flag);
        bttnbs_.setEnabled(flag);
        bttnenter_.setEnabled(flag);
    }

    private void setBttnBackground(boolean flag)
    {
        if(flag)
        {
            bttn0_.setBackground(Color.yellow.darker());
            bttn1_.setBackground(Color.yellow.darker());
            bttn2_.setBackground(Color.yellow.darker());
            bttn3_.setBackground(Color.yellow.darker());
            bttn4_.setBackground(Color.yellow.darker());
            bttn5_.setBackground(Color.yellow.darker());
            bttn6_.setBackground(Color.yellow.darker());
            bttn7_.setBackground(Color.yellow.darker());
            bttn8_.setBackground(Color.yellow.darker());
            bttn9_.setBackground(Color.yellow.darker());
            bttnbs_.setBackground(Color.yellow.darker());
            bttnenter_.setBackground(Color.yellow.darker());
        } else
        {
            bttn0_.setBackground(Color.lightGray);
            bttn1_.setBackground(Color.lightGray);
            bttn2_.setBackground(Color.lightGray);
            bttn3_.setBackground(Color.lightGray);
            bttn4_.setBackground(Color.lightGray);
            bttn5_.setBackground(Color.lightGray);
            bttn6_.setBackground(Color.lightGray);
            bttn7_.setBackground(Color.lightGray);
            bttn8_.setBackground(Color.lightGray);
            bttn9_.setBackground(Color.lightGray);
            bttnbs_.setBackground(Color.lightGray);
            bttnenter_.setBackground(Color.lightGray);
        }
    }

    public JFrame getFrame()
    {
        return frame_;
    }

    public void refresh()
    {
        frame_.repaint();
        frame_.setVisible(true);
    }

    public Component createGUI(int top, int left, int bottom, int right){
        frame_ = new JFrame("Text Input/Output Service");
        frame_.getContentPane().setLayout(new BorderLayout());
        try
        {
            JxfsTIOResolution jxfstioresolution = service_.getResolution(0);
            textArea_ = new JTextArea(jxfstioresolution.getRows(), jxfstioresolution.getColumns());
            maxTextLength_ = 0;
            for(int i = 0; i < jxfstioresolution.getRows(); i++)
                maxTextLength_ = maxTextLength_ + jxfstioresolution.getColumns();

        }
        catch(JxfsException jxfsexception1)
        {
            textArea_ = new JTextArea(4, 40);
            maxTextLength_ = 160;
        }
        textArea_.setLineWrap(true);
        textArea_.invalidate();
        textArea_.setBorder(new EtchedBorder(1));
        textArea_.setBackground(Color.lightGray);
        textArea_.setEditable(false);
        frame_.getContentPane().add(textArea_, "North");
        status_bar_ = new JTextField() {

            public boolean isFocusTraversable()
            {
                return false;
            }

        };
        status_bar_.setEditable(false);
        status_bar_.setBackground(Color.gray);
        status_bar_.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        status_bar_.setText("Opened");
        frame_.getContentPane().add(status_bar_, "South");
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 1));
        greenLEDOn_ = new ImageIcon(imagepath_ + "/greenLEDon.gif", "Logo");
        greenLEDOff_ = new ImageIcon(imagepath_ + "/greenLEDoff.gif", "Logo");
        online_ = new JRadioButton("Online", greenLEDOn_);
        jpanel.add(online_);
        input_ = new JRadioButton("Input", greenLEDOff_);
        jpanel.add(input_);
        frame_.getContentPane().add(jpanel, "West");
        bttn0_ = new JButton("0");
        bttn0_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "0";
                appendKey("0");
                status_bar_.setText("Key 0 pressed.");
            }

        });
        bttn1_ = new JButton("1");
        bttn1_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "1";
                appendKey("1");
                status_bar_.setText("Key 1 pressed.");
            }

        });
        bttn2_ = new JButton("2");
        bttn2_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "2";
                appendKey("2");
                status_bar_.setText("Key 2 pressed.");
            }

        });
        bttn3_ = new JButton("3");
        bttn3_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "3";
                appendKey("3");
                status_bar_.setText("Key 3 pressed.");
            }

        });
        bttn4_ = new JButton("4");
        bttn4_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "4";
                appendKey("4");
                status_bar_.setText("Key 4 pressed.");
            }

        });
        bttn5_ = new JButton("5");
        bttn5_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "5";
                appendKey("5");
                status_bar_.setText("Key 5 pressed.");
            }

        });
        bttn6_ = new JButton("6");
        bttn6_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "6";
                appendKey("6");
                status_bar_.setText("Key 6 pressed.");
            }

        });
        bttn7_ = new JButton("7");
        bttn7_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "7";
                appendKey("7");
                status_bar_.setText("Key 7 pressed.");
            }

        });
        bttn8_ = new JButton("8");
        bttn8_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "8";
                appendKey("8");
                status_bar_.setText("Key 8 pressed.");
            }

        });
        bttn9_ = new JButton("9");
        bttn9_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                readData_ = readData_ + "9";
                appendKey("9");
                status_bar_.setText("Key 9 pressed.");
            }

        });
        bttnbs_ = new JButton("Del");
        bttnbs_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                if(readData_.length() > 0)
                {
                    readData_ = readData_.substring(0, readData_.length() - 1);
                    deleteKey();
                }
                status_bar_.setText("Del key pressed.");
            }

        });
        bttnenter_ = new JButton("Enter");
        bttnenter_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                enterPressed_ = true;
                status_bar_.setText("Enter key pressed.");
            }

        });

        exceptionbutton_ = new JButton("");
        exceptionbutton_.setIcon(new javax.swing.ImageIcon("cfg/resources/ExceptionIcon2VerySmall.JPG"));
        exceptionbutton_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                exceptionGui_ = new GeneralDeviceGUI("", 0, 0, 0, 0, 0, 0);
                trackbox = new JCheckBox[30];

//                trackbox[0] = exceptionGui_.addCheckBox("Beep Error", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        beepException = trackbox[0].isSelected();
//                    }
//                });
//                trackbox[0].setSelected(beepException);
//                
//                trackbox[1] = exceptionGui_.addCheckBox("Light Error", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        lightException = trackbox[1].isSelected();
//                    }
//                });
//                trackbox[1].setSelected(lightException);
//                
                trackbox[2] = exceptionGui_.addCheckBox("Led Error", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        ledException = trackbox[2].isSelected();
                    }
                });
                trackbox[2].setSelected(ledException);
//
//                trackbox[3] = exceptionGui_.addCheckBox("Clear Error", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        clearException = trackbox[3].isSelected();
//                    }
//                });
//                trackbox[3].setSelected(clearException);
//                
//                trackbox[4] = exceptionGui_.addCheckBox("Display Error", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        displayException = trackbox[4].isSelected();
//                    }
//                });
//                trackbox[4].setSelected(displayException);

                trackbox[5] = exceptionGui_.addCheckBox("Read Error", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        readException = trackbox[5].isSelected();
                    }
                });
                trackbox[5].setSelected(readException);

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
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(new GridLayout(5, 3, 20, 10));
        jpanel1.add(bttn1_);
        jpanel1.add(bttn2_);
        jpanel1.add(bttn3_);
        jpanel1.add(bttn4_);
        jpanel1.add(bttn5_);
        jpanel1.add(bttn6_);
        jpanel1.add(bttn7_);
        jpanel1.add(bttn8_);
        jpanel1.add(bttn9_);
        jpanel1.add(bttnbs_);
        jpanel1.add(bttn0_);
        jpanel1.add(bttnenter_);
        jpanel1.add(exceptionbutton_);
        frame_.getContentPane().add(jpanel1, "Center");
        setBttnEnabled(false);
        frame_.setDefaultCloseOperation(2);
        WindowAdapter windowadapter = new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                for(int j = 0; j < gui_listeners_.size(); j++)
                    ((IWindowEventListener)gui_listeners_.elementAt(j)).CloseWindowEvent();

            }

        };
        ComponentAdapter componentadapter = new ComponentAdapter() {

            public void componentMoved(ComponentEvent componentevent)
            {
                Point point = frame_.getLocation();
                posx_ = new Integer(point.x);
                posy_ = new Integer(point.y);
            }

            public void componentResized(ComponentEvent componentevent)
            {
                Dimension dimension = frame_.getSize();
                sizex_ = new Integer(dimension.width);
                sizey_ = new Integer(dimension.height);
            }

        };
        frame_.addWindowListener(windowadapter);
        frame_.addComponentListener(componentadapter);
        frame_.setSize(sizex_.intValue(), sizey_.intValue());
        frame_.setLocation(posx_.intValue(), posy_.intValue());        
        return frame_.getContentPane();
    }
    
    private static final String ORIGIN = "TextInOutDeviceService";
    private TextInOutService service_;
    private JFrame frame_;
    private JTextArea textArea_;
    private JTextField status_bar_;
    private JRadioButton online_;
    private JRadioButton input_;
    private JButton bttn0_;
    private JButton bttn1_;
    private JButton bttn2_;
    private JButton bttn3_;
    private JButton bttn4_;
    private JButton bttn5_;
    private JButton bttn6_;
    private JButton bttn7_;
    private JButton bttn8_;
    private JButton bttn9_;
    private JButton bttnbs_;
    private JButton bttnenter_;
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
    // exception gui
    private GeneralDeviceGUI exceptionGui_;
    private JButton exceptionbutton_;    
    private JCheckBox[] trackbox;
    // supported exceptions
//    public static final int JXFS_E_TIO_BEEP = 8017;
    private boolean beepException = false;
//    public static final int JXFS_E_TIO_LIGHT = 8018;
    private boolean lightException = false;
//    public static final int JXFS_E_TIO_LED = 8019;
    private boolean ledException = false;
//    public static final int JXFS_E_TIO_CLEAR = 8020;
    private boolean clearException = false;
//    public static final int JXFS_E_TIO_DISPLAY = 8021;
    private boolean displayException = false;
//    public static final int JXFS_E_TIO_READ = 8022;
    private boolean readException = false;

    public boolean isBeepException() {
        return beepException;
    }

    public void setBeepException(boolean beepException) {
        this.beepException = beepException;
    }

    public boolean isLightException() {
        return lightException;
    }

    public void setLightException(boolean lightException) {
        this.lightException = lightException;
    }

    public boolean isLedException() {
        return ledException;
    }

    public void setLedException(boolean ledException) {
        this.ledException = ledException;
    }

    public boolean isClearException() {
        return clearException;
    }

    public void setClearException(boolean clearException) {
        this.clearException = clearException;
    }

    public boolean isDisplayException() {
        return displayException;
    }

    public void setDisplayException(boolean displayException) {
        this.displayException = displayException;
    }

    public boolean isReadException() {
        return readException;
    }

    public void setReadException(boolean readException) {
        this.readException = readException;
    }
}
