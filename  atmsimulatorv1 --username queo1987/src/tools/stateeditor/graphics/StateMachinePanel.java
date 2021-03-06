package tools.stateeditor.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Panel que representa los estados
 * de la maquina de estados.
 */
public class StateMachinePanel extends javax.swing.JPanel
{
    private Vector estados;
    
    /** Creates new form StateMachinePanel */
    public StateMachinePanel()
    {
        initComponents();
        estados = new Vector();
        comboEstados.setModel(new DefaultComboBoxModel(estados));
        textInputName.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            "enter");
        textInputName.getActionMap().put("enter",
            new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    buttonNewActionPerformed(e);
                }
            }
        );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        labelEstado = new javax.swing.JLabel();
        comboEstados = new javax.swing.JComboBox();
        textInputName = new javax.swing.JTextField();
        buttonNew = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        labelEstado.setText("Estados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        add(labelEstado, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 59);
        add(comboEstados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 199;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 10, 0, 59);
        add(textInputName, gridBagConstraints);

        buttonNew.setText("Ingresar nuevo");
        buttonNew.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonNewActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 10, 26, 0);
        add(buttonNew, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents

    private void buttonNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonNewActionPerformed
    {//GEN-HEADEREND:event_buttonNewActionPerformed
        /**
         * Codigo de atencion para
         * agregar un nuevo estado.
         */
        String nom_est = textInputName.getText();
        if (nom_est.equals(""))
        {
            showWarningMessage("El nombre no puede ser vac�o.");
        }
        else if (estados.contains(nom_est))
        {
            showWarningMessage("Ya se agreg� un estado con ese nombre.");
        }
        else
        {
            comboEstados.addItem(nom_est);
            comboEstados.getModel().setSelectedItem(nom_est);
            textInputName.setText("");
        }
    }//GEN-LAST:event_buttonNewActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonNew;
    private javax.swing.JComboBox comboEstados;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JTextField textInputName;
    // End of variables declaration//GEN-END:variables
    
    private void showWarningMessage(String msg)
    {
        JOptionPane.showMessageDialog(this, msg, "Atenci�n", JOptionPane.WARNING_MESSAGE);            
    }
}
