package infrastructure.services.jxfs.msd;

import infrastructure.services.jxfs.general.IWindowEventListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GeneralMagneticStripeGui
    implements WindowConstants
{

    public GeneralMagneticStripeGui(String s, int i, int j, int top, int left, int bottom, int right)
    {
        gui_listeners_ = new Vector();
        frame_ = new JFrame(s);
        frame_.setDefaultCloseOperation(0);
        frame_.getContentPane().setLayout(new BorderLayout());
        p_flds_ = new JPanel();
        p_flds_.setLayout(new BoxLayout(p_flds_, 1));
        p_flds_.setBorder(new EmptyBorder(top, left, bottom, right));
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(new BorderLayout());
        p_ctrls_ = new JPanel();
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        status_bar_ = new JTextField() {

            public boolean isFocusTraversable()
            {
                return false;
            }

        };
        status_bar_.setEditable(false);
        status_bar_.setBackground(jpanel.getBackground());
        status_bar_.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jpanel.add(status_bar_, "Center");
        status_bar_.setText("Status: ");

        jpanelMain = new JPanel();
        jpanelMain.setLayout(new BorderLayout());
        jpanelMain.add(p_flds_, "Center");
        jpanelMain.add(jpanel1, "South");
        
        //frame_.getContentPane().add(p_flds_, "Center");
        //frame_.getContentPane().add(jpanel1, "South");
        frame_.getContentPane().add(jpanelMain);
        
        jpanel1.add(p_ctrls_, "Center");
        jpanel1.add(jpanel, "South");
        frame_.setDefaultCloseOperation(0);
        WindowAdapter windowadapter = new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                for(int k1 = 0; k1 < gui_listeners_.size(); k1++)
                    ((IWindowEventListener)gui_listeners_.elementAt(k1)).CloseWindowEvent();

            }

        };
        frame_.addWindowListener(windowadapter);
    }

    public JComboBox addBox(String s)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        //jpanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        jpanel.setBorder(new EmptyBorder(400, 200, 400, 400));
        JLabel jlabel = new JLabel(s);
        JComboBox jcombobox = new JComboBox();
        jpanel.add(jlabel);
        jpanel.add(jcombobox);
        p_flds_.add(jpanel);
        return jcombobox;
    }

    public JComboBox addBox(String s, ActionListener actionlistener)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        jpanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        JLabel jlabel = new JLabel(s);
        JComboBox jcombobox = new JComboBox();
        jcombobox.addActionListener(actionlistener);
        jpanel.add(jlabel);
        jpanel.add(jcombobox);
        p_flds_.add(jpanel);
        return jcombobox;
    }

    public JButton addButton(String s, char c, ActionListener actionlistener)
    {
        button_ = new JButton(s);
        button_.setMnemonic(c);
        button_.addActionListener(actionlistener);
        p_ctrls_.add(button_);
        return button_;
    }

    public JButton addDefaultButton(String s, char c, ActionListener actionlistener)
    {
        addButton(s, c, actionlistener);
        default_button_ = button_;
        return button_;
    }

    public JCheckBox addCheckBox(String s, char c, ActionListener actionlistener)
    {
        check_box_ = new JCheckBox(s);
        check_box_.setMnemonic(c);
        check_box_.addActionListener(actionlistener);
        p_flds_.add(check_box_);
        return check_box_;
    }

    public JTextField addField(String s, int i)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        jpanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        JLabel jlabel = new JLabel(s);
        JTextField jtextfield = new JTextField(i);
        jpanel.add(jlabel);
        jpanel.add(jtextfield);
        p_flds_.add(jpanel);
        return jtextfield;
    }

    public JTextField addTxt(String s)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        jpanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        JTextField jtextfield = new JTextField();
        jtextfield.setEditable(false);
        jtextfield.setBackground(jpanel.getBackground());
        jtextfield.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jtextfield.setText(s);
        jpanel.add(jtextfield);
        p_flds_.add(jpanel);
        return jtextfield;
    }

    public JTextArea addTxtArea(String s, int i, int j)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        JTextArea jtextarea = new JTextArea(i, j);
        if(s != null)
        {
            JLabel jlabel = new JLabel(s);
            jpanel.add(jlabel);
        }
        jpanel.add(jtextarea);
        p_flds_.add(jpanel);
        return jtextarea;
    }

    public JTextArea addTxtArea(int i, int j)
    {
        JTextArea jtextarea = new JTextArea(i, j);
        jtextarea.setEditable(false);
        JScrollPane jscrollpane = new JScrollPane(jtextarea);
        p_flds_.add(jscrollpane);
        return jtextarea;
    }

    public void addGUIListener(IWindowEventListener iguieventcallbacks)
    {
        gui_listeners_.addElement(iguieventcallbacks);
    }

    public JList addList(String s, String s1, int i, int j)
    {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        JList jlist = new JList();
        jlist.setVisibleRowCount(j);
        jlist.setFixedCellWidth(i);
        JLabel jlabel = new JLabel(s);
        jpanel.add(jlabel, s1);
        jpanel.add(new JScrollPane(jlist), "Center");
        p_flds_.add(jpanel);
        return jlist;
    }

    public void show()
    {
        frame_.pack();
        frame_.getRootPane().setDefaultButton(default_button_);
        default_button_.requestFocus();
        frame_.setVisible(true);
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

    public synchronized void setStatusText(String s)
    {
        status_bar_.setText("Status: " + s);
    }


    private JFrame frame_;
    private JPanel p_flds_;
    private JPanel p_ctrls_;
    private JPanel jpanelMain;
    private JTextField status_bar_;
    private JCheckBox check_box_;
    private JButton button_;
    private JButton default_button_;
    private static GeneralMagneticStripeGui g__;
    private static JComboBox box__;
    private Vector gui_listeners_;



}
