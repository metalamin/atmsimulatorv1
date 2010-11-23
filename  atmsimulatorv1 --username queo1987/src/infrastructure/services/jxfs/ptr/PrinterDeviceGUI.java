
package infrastructure.services.jxfs.ptr;

import com.jxfs.general.JxfsDeviceManager;
import com.jxfs.general.JxfsLogger;
import infrastructure.services.jxfs.general.IWindowEventListener;
import infrastructure.services.jxfs.general.GeneralDeviceGUI;
import config.GlobalConfig;
import util.GeneralException;
import infrastructure.devices.controllers.ptr.IPtrConst;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.*;
import presentation.IGUIBuilder;

public class PrinterDeviceGUI
    implements WindowConstants, IGUIBuilder, Serializable
{

    public PrinterDeviceGUI(PrinterService printerService, JxfsDeviceManager jxfsdevicemanager, JxfsLogger jxfslogger)
    {
        gui_listeners_ = new Vector();

        service_ = printerService;
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            System.err.println("Error loading look and feel: " + exception.toString());
        }       
        l_ = jxfslogger;
        dm_ = jxfsdevicemanager;

        try{
            GlobalConfig.getInstance().addProperty(IPtrConst.PTR_THROWER, this);
        }
        catch(GeneralException jxfsexception)
        {
            System.out.println("Error saving PTRDeviceGUI like GUIBuilder in the repository: " + jxfsexception.getMessage() );
        }        
        
    }

    public void dispose()
    {
        frame_.dispose();
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

        javax.swing.JButton jButton1;
        javax.swing.JLabel jLabel1;
        javax.swing.JPanel jPanel1;
        javax.swing.JScrollPane jScrollPane1;
        
        frame_ = new JFrame("Printer");
        frame_.getContentPane().setLayout(new BorderLayout());
        frame_.setBounds(20, 20, 300, 200);

        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        exceptionbutton_ = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Printer Device");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        exceptionbutton_.setIcon(new javax.swing.ImageIcon("cfg/resources/ExceptionIcon2VerySmall.JPG"));
        exceptionbutton_.setText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel1.add(exceptionbutton_, gridBagConstraints);

        frame_.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);        
        
        exceptionbutton_.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                exceptionGui_ = new GeneralDeviceGUI("", 0, 0, 0, 0, 0, 0);
                trackbox = new JCheckBox[30];
//    private boolean flushFail = false;
//    private boolean noForms = false;
//    private boolean noMedia = false;
//    private boolean formNotFound = false;
//    private boolean mediaInvalid = false;
//    private boolean mediaNotFound = false;
//    private boolean mediaOverflow = false;
//    private boolean paperOut = false;

//                trackbox[0] = exceptionGui_.addCheckBox("Flush Fail", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        flushFail = trackbox[0].isSelected();
//                    }
//                });
//                trackbox[0].setSelected(flushFail);
                
//                trackbox[1] = exceptionGui_.addCheckBox("No Forms", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        noForms = trackbox[1].isSelected();
//                    }
//                });
//                trackbox[1].setSelected(noForms);
                
//                trackbox[2] = exceptionGui_.addCheckBox("No Media", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        noMedia = trackbox[2].isSelected();
//                    }
//                });
//                trackbox[2].setSelected(noMedia);

//                trackbox[3] = exceptionGui_.addCheckBox("Form Not Found", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        formNotFound = trackbox[3].isSelected();
//                    }
//                });
//                trackbox[3].setSelected(formNotFound);
                
//                trackbox[4] = exceptionGui_.addCheckBox("Media Invalid", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        mediaInvalid = trackbox[4].isSelected();
//                    }
//                });
//                trackbox[4].setSelected(mediaInvalid);
//
//                trackbox[5] = exceptionGui_.addCheckBox("Media Not Found", ' ', new ActionListener() {
//                    public void actionPerformed(ActionEvent actionevent)
//                    {
//                        mediaNotFound = trackbox[5].isSelected();
//                    }
//                });
//                trackbox[5].setSelected(mediaNotFound);

                trackbox[6] = exceptionGui_.addCheckBox("Media Overflow", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        mediaOverflow = trackbox[6].isSelected();
                    }
                });
                trackbox[6].setSelected(mediaOverflow);

                trackbox[7] = exceptionGui_.addCheckBox("Paper Out", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        paperOut = trackbox[7].isSelected();
                    }
                });
                trackbox[7].setSelected(paperOut);

                trackbox[8] = exceptionGui_.addCheckBox("No Media Present", ' ', new ActionListener() {
                    public void actionPerformed(ActionEvent actionevent)
                    {
                        noMediaPresent = trackbox[8].isSelected();
                    }
                });
                trackbox[8].setSelected(noMediaPresent);
                
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
            }

            public void componentResized(ComponentEvent componentevent)
            {
                Dimension dimension = frame_.getSize();
            }

        };
        frame_.addWindowListener(windowadapter);
        frame_.addComponentListener(componentadapter);
        return frame_.getContentPane();
    }
    
    public void printForm(String formName, String mediumName, String fieldContent[]){
        String as1[] = new String[fieldContent.length + 1];
        as1[0] = IPtrConst.TCKT_DELIMITER;
        for(int k = 0; k < fieldContent.length; k++)
            as1[k] = fieldContent[k];
        as1[fieldContent.length] = IPtrConst.TCKT_DELIMITER + "\n";
        for(int k = 0; k < as1.length; k++)
            jTextArea1.setText(jTextArea1.getText()+as1[k]);
    }
    
    private static final String ORIGIN = "PTR_DS";
    private PrinterService service_;
    private JFrame frame_;
    private JPanel jpanel;
    private javax.swing.JTextArea jTextArea1;
    private Vector gui_listeners_;
    private JxfsLogger l_;
    private JxfsDeviceManager dm_;
    // exception gui
    private GeneralDeviceGUI exceptionGui_;
    private JButton exceptionbutton_;    
    private JCheckBox[] trackbox;
    // supported exceptions
//    public static final int JXFS_E_PTR_EXTEND_NOT_SUPPORTED = 3001;
//    public static final int JXFS_E_PTR_FIELD_ERROR = 3002;
//    public static final int JXFS_E_PTR_FIELD_INVALID = 3003;
//    public static final int JXFS_E_PTR_FIELD_NOT_FOUND = 3004;
//    public static final int JXFS_E_PTR_FIELD_SPEC_FAILURE = 3005;
//    public static final int JXFS_E_PTR_FLUSH_FAIL = 3006;
    private boolean flushFail = false;
//    public static final int JXFS_E_PTR_FORM_INVALID = 3007;
//    public static final int JXFS_E_PTR_NOFORMS = 3008;
    private boolean noForms = false;
//    public static final int JXFS_E_PTR_NOMEDIA = 3009;
    private boolean noMedia = false;
//    public static final int JXFS_E_PTR_FORM_NOT_FOUND = 3010;
    private boolean formNotFound = false;
//    public static final int JXFS_E_PTR_MEDIA_INVALID = 3011;
    private boolean mediaInvalid = false;
//    public static final int JXFS_E_PTR_MEDIA_NOT_FOUND = 3012;
    private boolean mediaNotFound = false;
//    public static final int JXFS_E_PTR_MEDIA_OVERFLOW = 3013;
    private boolean mediaOverflow = false;
//    public static final int JXFS_E_PTR_MEDIA_SKEWED = 3014;
//    public static final int JXFS_E_PTR_MEDIA_TURN_FAIL = 3015;
//    public static final int JXFS_E_PTR_NO_MEDIA_PRESENT = 3016;
    private boolean noMediaPresent = false;
//    public static final int JXFS_E_PTR_RETRACT_BIN_FULL = 3017;
//    public static final int JXFS_E_PTR_FIELD_GRAPHIC = 3018;
//    public static final int JXFS_E_PTR_FIELD_HW_ERROR = 3019;
//    public static final int JXFS_E_PTR_FIELD_NOT_READ = 3020;
//    public static final int JXFS_E_PTR_FIELD_NOT_WRITE = 3021;
//    public static final int JXFS_E_PTR_FIELD_OVERFLOW = 3022;
//    public static final int JXFS_E_PTR_FIELD_REQUIRED = 3023;
//    public static final int JXFS_E_PTR_FIELD_STATIC_OVWR = 3024;
//    public static final int JXFS_E_PTR_FIELD_TYPE_NOT_SUPPORTED = 3025;
//    public static final int JXFS_E_PTR_PAPEROUT = 3038;
    private boolean paperOut = false;

    public boolean isFlushFail() {
        return flushFail;
    }

    public void setFlushFail(boolean flushFail) {
        this.flushFail = flushFail;
    }

    public boolean isNoForms() {
        return noForms;
    }

    public void setNoForms(boolean noForms) {
        this.noForms = noForms;
    }

    public boolean isNoMedia() {
        return noMedia;
    }

    public void setNoMedia(boolean noMedia) {
        this.noMedia = noMedia;
    }

    public boolean isFormNotFound() {
        return formNotFound;
    }

    public void setFormNotFound(boolean formNotFound) {
        this.formNotFound = formNotFound;
    }

    public boolean isMediaInvalid() {
        return mediaInvalid;
    }

    public void setMediaInvalid(boolean mediaInvalid) {
        this.mediaInvalid = mediaInvalid;
    }

    public boolean isMediaNotFound() {
        return mediaNotFound;
    }

    public void setMediaNotFound(boolean mediaNotFound) {
        this.mediaNotFound = mediaNotFound;
    }

    public boolean isMediaOverflow() {
        return mediaOverflow;
    }

    public void setMediaOverflow(boolean mediaOverflow) {
        this.mediaOverflow = mediaOverflow;
    }

    public boolean isPaperOut() {
        return paperOut;
    }

    public void setPaperOut(boolean paperOut) {
        this.paperOut = paperOut;
    }

    public boolean isNoMediaPresent() {
        return noMediaPresent;
    }

    public void setNoMediaPresent(boolean noMediaPresent) {
        this.noMediaPresent = noMediaPresent;
    }
    

}

