package tools.stateeditor;
import domain.state.TriggerBean;
import infrastructure.dataaccess.InvalidIDException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import domain.implementation.TriggerValues;
import tools.stateeditor.StateEditor;
import tools.stateeditor.components.ComponentCreatorHandlerFactory;
import tools.stateeditor.components.TableProperties;
import tools.stateeditor.objects.ObjectCreatorHandlerFactory;
import tools.stateeditor.observer.Observer;
import tools.stateeditor.observer.Subject;


/**
 * Panel general para la edicion.
 * A partir de este panel, se puede
 * editar y crear cualqiuer cosa a
 * partir de componentes.
 */
public class EditionPanelTriggers extends javax.swing.JPanel implements Observer
{
    // El tipo de componentes del panel.
    private String type;
    // Los nombres accesibles.
    private Vector availNames;
    // Los componentes visuales que se van creando.
    private HashMap createdGraphComponents;
    // Los componentes que se van creando.
    private HashMap createdComponents;
    // Los nombres de los componentes que se van creando.
    private HashMap createdNames;
    //El componente seleccionado actualmente
    private Object actualComp;
    //Objeto al que se le hará un update cuando haya un cambio en el actual
    private StateEditor parent;
    //El componente grafico seleccionado actualmente
    private TableProperties comp;
    /**
     * Creates new form EditionPanelIndep
     */
    public EditionPanelTriggers(String title, String type)
    {
        initComponents();
        labelTitulo.setText(title);
        this.type = type;
        availNames = new Vector(ObjectCreatorHandlerFactory.getObjectCreatorHandler().getAvailableNames(this.type));
        comboDisponibles.setModel(new DefaultComboBoxModel(availNames));
        createdComponents = new HashMap();
        createdGraphComponents = new HashMap();
        createdNames = new HashMap();
        comboActual.setModel(new DefaultComboBoxModel(new Vector()));
        /*String Obstype = "TABLE"+type.toUpperCase()+"CHANGED";
        System.out.println(Obstype);
        System.out.println(Subject.TABLETRIGGERCHANGED);
        */Subject.getInstance().addObserver(this, Subject.TABLETRIGGERCHANGED);
        Subject.getInstance().addObserver(this,Subject.TABLEACTIONCHANGED);
    }
    public String getType()
    {
        return type;
    }

    public Object getActualComponent()
    {
        return actualComp;
    }

    public void setParent(StateEditor par)
    {
        parent = par;
    }

    public void setSelected(int index)
    {
        Vector createdComps = (Vector)createdComponents.get(parent.getActualState());
        Vector vecGraph = (Vector)createdGraphComponents.get(parent.getActualState());
        Object o = comboActual.getModel().getElementAt(index);
        comboActual.setSelectedIndex(index);
        actualComp = createdComps.elementAt(index);
        if(comp!=null)
            comp.removeObservers();
        comp = (TableProperties)vecGraph.elementAt(index);
        panelEdicion.getViewport().setView(comp.getTable());
        comp.addObservers();
        Subject.getInstance().notify(Subject.SELECTTRIGGERCHANGED, actualComp);
    }

    public void addActualParentComponent(Object o)
    {
        createdComponents.put(o,new Vector());
        createdGraphComponents.put(o, new Vector());
        Vector vec = new Vector();
        createdNames.put(o, vec);
        comboActual.setModel(new DefaultComboBoxModel(vec));
        panelEdicion.getViewport().setView(null);
    }

    public void deleteParent()
    {
        while(comboActual.getItemCount() > 0)
        {
            butQuitarActionPerformed(null);
        }
    }

    public void setActualParentComponent(Object o)
    {
        //createdComponents.put(o,new Vector());
        //createdGraphComponents.put(o, new Vector());
        clearSelection();
        Vector vec = (Vector)createdNames.get(o);
        comboActual.setModel(new DefaultComboBoxModel(vec));
        if(vec.size()>0)
            setSelected(0);
    }

    public void clearSelection()
    {
        comboActual.setModel(new DefaultComboBoxModel(new Vector()));
        if(comp!=null)
            comp.removeObservers();
        panelEdicion.getViewport().setView(null);
    }

    public void update(Object up, String type)
    {
        System.out.println("EDTION TYPE = "+type);
        if(type.equals(Subject.TABLETRIGGERCHANGED))
        {
            TriggerBean tb = StateEditor.getInstance().getActualTrigger();
            int i = comboActual.getSelectedIndex();
            if(i>=0)
            {
                Vector vec = (Vector)createdNames.get(parent.getActualState());
                System.out.println(i);
                vec.remove(i);
                TriggerValues tv = new TriggerValues(tb.getType(),tb.getThrower());
                vec.add(i, tv);
                comboActual.setSelectedItem(tv);
            }
        }
        panelEdicion.repaint();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        labelTitulo = new javax.swing.JLabel();
        labelActual = new javax.swing.JLabel();
        comboActual = new javax.swing.JComboBox();
        labelDisponibles = new javax.swing.JLabel();
        comboDisponibles = new javax.swing.JComboBox();
        panelEdicion = new javax.swing.JScrollPane();
        buttonAgregarNuevo = new javax.swing.JButton();
        separatorMedio = new javax.swing.JSeparator();
        separatorArriba = new javax.swing.JSeparator();
        butQuitar = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 13));
        labelTitulo.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        add(labelTitulo, gridBagConstraints);

        labelActual.setText("Actual");
        labelActual.setMaximumSize(new java.awt.Dimension(48, 14));
        labelActual.setMinimumSize(new java.awt.Dimension(48, 14));
        labelActual.setPreferredSize(new java.awt.Dimension(48, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        add(labelActual, gridBagConstraints);

        comboActual.setMinimumSize(new java.awt.Dimension(109, 21));
        comboActual.setPreferredSize(new java.awt.Dimension(109, 21));
        comboActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboActualActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        add(comboActual, gridBagConstraints);

        labelDisponibles.setText("Availables");
        labelDisponibles.setMinimumSize(new java.awt.Dimension(48, 15));
        labelDisponibles.setPreferredSize(new java.awt.Dimension(48, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        add(labelDisponibles, gridBagConstraints);

        comboDisponibles.setMinimumSize(new java.awt.Dimension(109, 21));
        comboDisponibles.setPreferredSize(new java.awt.Dimension(109, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        add(comboDisponibles, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 5, 16);
        add(panelEdicion, gridBagConstraints);

        buttonAgregarNuevo.setText("Add");
        buttonAgregarNuevo.setMargin(new java.awt.Insets(2, 5, 2, 5));
        buttonAgregarNuevo.setMaximumSize(new java.awt.Dimension(63, 23));
        buttonAgregarNuevo.setMinimumSize(new java.awt.Dimension(63, 23));
        buttonAgregarNuevo.setPreferredSize(new java.awt.Dimension(63, 23));
        buttonAgregarNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAgregarNuevoActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 16);
        add(buttonAgregarNuevo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 16);
        add(separatorMedio, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 369;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 16);
        add(separatorArriba, gridBagConstraints);

        butQuitar.setText("Remove");
        butQuitar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        butQuitar.setMaximumSize(new java.awt.Dimension(63, 23));
        butQuitar.setMinimumSize(new java.awt.Dimension(63, 23));
        butQuitar.setPreferredSize(new java.awt.Dimension(63, 23));
        butQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butQuitarActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 16);
        add(butQuitar, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void butQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butQuitarActionPerformed
    // TODO add your handling code here:
    Vector createdComps = (Vector)createdComponents.get(parent.getActualState());
        if (!createdComps.isEmpty())
        {
            int index = comboActual.getSelectedIndex();
            if(index >= 0)
            {
                createdComps.remove(index);
                Vector vecGraph = (Vector)createdGraphComponents.get(parent.getActualState());
                vecGraph.remove(index);
                Vector vec = (Vector)createdNames.get(parent.getActualState());
                vec.remove(index);
                System.out.println(vec);
                //HAY QUE HACER ESTO ANTES DE CAMBIAR EL ELEMENTO ACTUAL
                parent.removeChildTrigger();
                comp.removeObservers();
                panelEdicion.getViewport().setView(null);

                if(vec.size()>0)
                {
                    setSelected(0);
                }
                else
                {
                    //para variar por garchas de swing hago esto
                    comboActual.setModel(new DefaultComboBoxModel(new Vector()));
                    comboActual.setModel(new DefaultComboBoxModel(vec));
                }
            }
        }

    }//GEN-LAST:event_butQuitarActionPerformed

    private void comboActualActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_comboActualActionPerformed
    {//GEN-HEADEREND:event_comboActualActionPerformed
        /**
         * Codigo de atencion a la
         * navegacion sobre la combo.
         */
        Vector createdComps = (Vector)createdComponents.get(parent.getActualState());
        //Vector createdGraph = (Vector)createdGraphComponents.get(parent.getActualState());
        if (!createdComps.isEmpty())
        {
           setSelected(comboActual.getSelectedIndex());
            /* actualComp = createdComps.elementAt(comboActual.getSelectedIndex());
            Component comp = (Component)createdGraph.elementAt(comboActual.getSelectedIndex());
            panelEdicion.getViewport().setView(comp);
            Subject.getInstance().notify(Subject.SELECTTRIGGERCHANGED, actualComp);*/
        }
    }//GEN-LAST:event_comboActualActionPerformed

    public void addNewTrigger(Object tb)
    {
        actualComp = tb;
        if(comp!=null)
            comp.removeObservers();
        comp = (TableProperties)ComponentCreatorHandlerFactory.getComponentCreatorHandler().createComponent(tb);
        System.out.println("CREE EL COMPONENTE ADD NEW TRIGGER");
        //comp.addObservers();
        Vector createdComps = (Vector)createdComponents.get(parent.getActualState());
        Vector createdGraph = (Vector)createdGraphComponents.get(parent.getActualState());
        Vector createdNoms = (Vector)createdNames.get(parent.getActualState());
        createdGraph.add(comp);
        createdComps.add(tb);
        if(TriggerBean.class.isAssignableFrom(tb.getClass()))
        {
            TriggerBean tbb = (TriggerBean)tb;
            TriggerValues tv = new TriggerValues(tbb.getType(), tbb.getThrower());
            createdNoms.add(tv);
            System.out.println("AGREGUE EL TRIGEERVALUE");
        }
        else
        {
            System.out.println("SOY OTRA COSA ADD NEW TRIGGER");
        }
        System.out.println("ME JUI DE ADD NEW TRIGGER");
    }

    private void buttonAgregarNuevoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonAgregarNuevoActionPerformed
    {//GEN-HEADEREND:event_buttonAgregarNuevoActionPerformed
        /**
         * Codigo de atencion para
         * "Agregar nuevo componente"
         */
        String nombre = (String)comboDisponibles.getSelectedItem();
        //Component comp = null;
        //Trigger tr = null;
        //Action ac = null;
        try
        {
            actualComp = ObjectCreatorHandlerFactory.getObjectCreatorHandler().create(this.type, nombre);
            System.out.println("ACTUALCOMP = "+actualComp);
            System.out.println(((TriggerBean)actualComp).getActions());
        }
        catch (InvalidIDException iiex)
        {
            iiex.printStackTrace();
        }
        addNewTrigger(actualComp);
        /*    comp = ComponentCreatorHandlerFactory.getComponentCreatorHandler().createComponent(actualComp);
            System.out.println("CREE EL COMPONENTE");

        Vector createdComps = (Vector)createdComponents.get(parent.getActualState());
        Vector createdGraph = (Vector)createdGraphComponents.get(parent.getActualState());
        */Vector createdNoms = (Vector)createdNames.get(parent.getActualState());
        //createdGraph.add(comp);
        //createdComps.add(actualComp);
        if(TriggerBean.class.isAssignableFrom(actualComp.getClass()))
        {
            TriggerBean tb = (TriggerBean)actualComp;
            /*TriggerValues tv = new TriggerValues(tb.getType(), tb.getThrower());
            createdNoms.add(tv);*/
            parent.addChildTrigger(tb);
            comboActual.setSelectedIndex(comboActual.getItemCount()-1);
            System.out.println("AGREGUE EL TRIGEERVALUE");
        }
        else
        {
            System.out.println("SOY OTRA COSA");
            createdNoms.add(nombre);
            comboActual.setSelectedItem(nombre);
        }
        panelEdicion.getViewport().setView(null);
        panelEdicion.getViewport().setView(comp.getTable());
        System.out.println("ME JUI DE ADD COMP EDITION PANEL");

    }//GEN-LAST:event_buttonAgregarNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butQuitar;
    private javax.swing.JButton buttonAgregarNuevo;
    private javax.swing.JComboBox comboActual;
    private javax.swing.JComboBox comboDisponibles;
    private javax.swing.JLabel labelActual;
    private javax.swing.JLabel labelDisponibles;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JScrollPane panelEdicion;
    private javax.swing.JSeparator separatorArriba;
    private javax.swing.JSeparator separatorMedio;
    // End of variables declaration//GEN-END:variables

}
