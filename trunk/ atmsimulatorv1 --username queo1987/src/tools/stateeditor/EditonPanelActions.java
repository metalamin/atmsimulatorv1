package tools.stateeditor;
import domain.state.StateBean;
import domain.state.TriggerBean;
import infrastructure.dataaccess.InvalidIDException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import domain.implementation.StateChange;
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
public class EditonPanelActions extends javax.swing.JPanel implements Observer
{
    // El tipo de componentes del panel.
    private String type;
    // Los nombres accesibles.
    private Vector availNames;
    //Contiene hashmap identificado con el estado actual
    private HashMap estados;
    // Los componentes visuales que se van creando.
    private HashMap createdGraphComponents;
    // Los componentes que se van creando.
    private HashMap createdComponents;
    // Los nombres de los componentes que se van creando.
    private HashMap createdNames;
    //El componente seleccionado actualmente
    private Object actualComp;
    //El trigger actual
    private Object actualTrigger;
    //Objecto al que se le hará un update cuando haya un cambio en el actual
    private StateEditor parent;
    //Objeto que sera el padre de este panel, este es un panel dependiente por eso requiere un link al padre
    private EditionPanelTriggers triggerPane;
    //El objeto grafico seleccionado actualmente
    private TableProperties comp;

    /**
     * Creates new form EditionPanelDep
     */
    public EditonPanelActions(EditionPanelTriggers epi,String title, String type)
    {
        initComponents();
        triggerPane = epi;
        labelTitulo.setText(title);
        this.type = type;
        availNames = new Vector(ObjectCreatorHandlerFactory.getObjectCreatorHandler().getAvailableNames(this.type));
        comboDisponibles.setModel(new DefaultComboBoxModel(availNames));
        estados = new HashMap();
        createdComponents = new HashMap();
        createdGraphComponents = new HashMap();
        createdNames = new HashMap();
        //comboActual.setModel(new DefaultComboBoxModel(new Vector()));
        listActual.setModel(new DefaultListModel());
        String Obstype = "TABLE"+type.toUpperCase()+"CHANGED";
        System.out.println(Obstype);
        System.out.println(Subject.TABLETRIGGERCHANGED);
        Subject.getInstance().addObserver(this, Obstype);
    }
    public String getType()
    {
        return type;
    }

    public Object getActualComponent()
    {
        return actualComp;
    }

     public void setSelected(int index)
    {
        Vector createdComps = (Vector)createdComponents.get(triggerPane.getActualComponent());
        Vector vecGraph = (Vector)createdGraphComponents.get(triggerPane.getActualComponent());
        Object o = listActual.getModel().getElementAt(index);
        //comboActual.setSelectedIndex(index);
        listActual.setSelectedIndex(index);
        actualComp = createdComps.elementAt(index);
        if(comp != null)
            comp.removeObservers();
        comp = (TableProperties)vecGraph.elementAt(index);
        comp.addObservers();
        panelEdicion.getViewport().setView(comp.getTable());
    }

    public void addState(StateBean sb)
    {
        Vector vec = new Vector();
        vec.add(new HashMap());
        vec.add(new HashMap());
        vec.add(new HashMap());
        estados.put(sb,vec);
        setState(sb);
    }

    public void setState(StateBean sb)
    {
        Vector vec = (Vector)estados.get(sb);
        createdComponents = (HashMap)vec.get(0);
        createdGraphComponents = (HashMap)vec.get(1);
        createdNames = (HashMap)vec.get(2);

    }

    public void setParent(StateEditor par)
    {
        parent = par;
    }

    public void update(Object up, String type)
    {
        int index = listActual.getSelectedIndex();
        //((DefaultComboBoxModel)comboActual.getModel()).removeElementAt(comboActual.getSelectedIndex());
        //((DefaultComboBoxModel)comboActual.getModel()).addElement(up.toString());
        //comboActual.setSelectedIndex(index);
        ((DefaultListModel)listActual.getModel()).removeElementAt(listActual.getSelectedIndex());
        ((DefaultListModel)listActual.getModel()).add(index,up.toString());
        listActual.setSelectedIndex(index);
        System.out.println("EDTION TYPE ACTIONS = "+type);
    }

    public void removeAllObjects(Object tri)
    {
        Iterator it = ((Vector)createdComponents.remove(tri)).iterator();
        while(it.hasNext())
        {
            Object obj = it.next();
            if(StateChange.class.isInstance(obj))
                parent.removeLink(obj);
        }
        createdGraphComponents.remove(tri);
        createdNames.remove(tri);
        //comboActual.setModel(new DefaultComboBoxModel(new Vector()));
        listActual.setModel(new DefaultListModel());
        clearSelection();
    }

    public void addActualParentComponent(TriggerBean o)
    {

        createdComponents.put(o,new Vector());
        createdGraphComponents.put(o, new Vector());
        Vector vec = new Vector();
        createdNames.put(o, vec);
        //comboActual.setModel(new DefaultComboBoxModel(vec));
        DefaultListModel dlm = new DefaultListModel();
        listActual.setModel(dlm);
        panelEdicion.getViewport().setView(null);
        actualTrigger = o;
        Iterator it = o.getActions().iterator();
        while(it.hasNext())
        {
            addNewAction(it.next());
        }
        Vector actions = (Vector)createdComponents.get(o);
        o.setActions(actions);
    }

    public void setActualParentComponent(Object o)
    {
        //createdComponents.put(o,new Vector());
        //createdGraphComponents.put(o, new Vector());
        if(actualTrigger != o)
        {
            System.out.println("CAMBIO ACTUAL TRIGGER");
            clearSelection();
            Vector vec = (Vector)createdNames.get(o);
            //comboActual.setModel(new DefaultComboBoxModel(vec));
            DefaultListModel dlm = new DefaultListModel();
            Iterator it = vec.iterator();
            while(it.hasNext())
            {
                dlm.addElement(it.next());
            }
            listActual.setModel(dlm);
            actualTrigger = o;
            if(vec.size()>0)
                setSelected(0);
        }
    }

    public void clearSelection()
    {
        //comboActual.setModel(new DefaultComboBoxModel(new Vector()));
        listActual.setModel(new DefaultListModel());
        if(comp!=null)
            comp.removeObservers();
        panelEdicion.getViewport().setView(null);
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
        labelDisponibles = new javax.swing.JLabel();
        comboDisponibles = new javax.swing.JComboBox();
        panelEdicion = new javax.swing.JScrollPane();
        buttonAgregarNuevo = new javax.swing.JButton();
        separatorMedio = new javax.swing.JSeparator();
        separatorArriba = new javax.swing.JSeparator();
        butQuitar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listActual = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 13));
        labelTitulo.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        add(labelTitulo, gridBagConstraints);

        labelActual.setText("Created");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        add(labelActual, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 0);
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 16);
        add(butQuitar, gridBagConstraints);

        listActual.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listActual.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listActualValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(listActual);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(jScrollPane1, gridBagConstraints);

        jButton1.setText("move up");
        jButton1.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton1.setMaximumSize(new java.awt.Dimension(63, 23));
        jButton1.setMinimumSize(new java.awt.Dimension(63, 23));
        jButton1.setPreferredSize(new java.awt.Dimension(63, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 16);
        add(jButton1, gridBagConstraints);

        jButton2.setText("move down");
        jButton2.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2.setMaximumSize(new java.awt.Dimension(63, 23));
        jButton2.setMinimumSize(new java.awt.Dimension(63, 23));
        jButton2.setPreferredSize(new java.awt.Dimension(63, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 0, 16);
        add(jButton2, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        int index = listActual.getSelectedIndex();
        if(index >=0 && index < (((DefaultListModel)listActual.getModel()).size()-1))
        {
            Object o = ((DefaultListModel)listActual.getModel()).getElementAt(index);
            ((DefaultListModel)listActual.getModel()).removeElementAt(index);
            ((DefaultListModel)listActual.getModel()).add(index+1,o);
            Object obj = triggerPane.getActualComponent();
            Vector vecComps = (Vector)createdComponents.get(obj);
            o = vecComps.remove(index);
            vecComps.add(index+1,o);
            Vector vecGraph = (Vector)createdGraphComponents.get(obj);
            o = vecGraph.remove(index);
            vecGraph.add(index+1,o);
            ((TriggerBean)actualTrigger).setActions(vecComps);
            listActual.setSelectedIndex(index+1);
            Subject.getInstance().notify(Subject.ACTIONSCHANGED, vecComps);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        int index = listActual.getSelectedIndex();
        if(index >0)
        {
            Object o = ((DefaultListModel)listActual.getModel()).getElementAt(index);
            ((DefaultListModel)listActual.getModel()).removeElementAt(index);
            ((DefaultListModel)listActual.getModel()).add(index-1,o);
            Object obj = triggerPane.getActualComponent();
            Vector vecComps = (Vector)createdComponents.get(obj);
            o = vecComps.remove(index);
            vecComps.add(index-1,o);
            Vector vecGraph = (Vector)createdGraphComponents.get(obj);
            o = vecGraph.remove(index);
            vecGraph.add(index-1,o);
            ((TriggerBean)actualTrigger).setActions(vecComps);
            listActual.setSelectedIndex(index-1);
            Subject.getInstance().notify(Subject.ACTIONSCHANGED, vecComps);

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void listActualValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listActualValueChanged
// TODO add your handling code here:
         /**
         * Codigo de atencion a la
         * navegacion sobre la combo.
         */
        if (((DefaultListModel)listActual.getModel()).getSize()>0 && (listActual.getSelectedIndex() >=0))
        {
            Vector vec = (Vector)createdComponents.get(actualTrigger);
            System.out.println(actualTrigger);
            System.out.println(vec);
            System.out.println(listActual.getSelectedIndex());

            actualComp = vec.elementAt(listActual.getSelectedIndex());
            vec = (Vector)createdGraphComponents.get(actualTrigger);
            if(comp!=null)
                comp.removeObservers();
            comp = (TableProperties)vec.elementAt(listActual.getSelectedIndex());
            comp.addObservers();
            panelEdicion.getViewport().setView(null);
            panelEdicion.getViewport().setView(comp.getTable());
        }
    }//GEN-LAST:event_listActualValueChanged

    private void butQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butQuitarActionPerformed
// TODO add your handling code here:
        if (!createdComponents.isEmpty())
        {
            int index = listActual.getSelectedIndex();
            if(index >= 0)
            {
                Object obj = triggerPane.getActualComponent();
                Vector vecComps = (Vector)createdComponents.get(obj);
                vecComps.remove(index);
                Vector vecGraph = (Vector)createdGraphComponents.get(obj);
                vecGraph.remove(index);
                ((TriggerBean)actualTrigger).setActions(vecComps);
                //HAY QUE HACER ESTO ANTES DE CAMBIAR EL ELEMENTO ACTUAL
                //((TriggerBean)triggerPane.getActualComponent()).;
                parent.removeChildAction();
                Vector vec = (Vector)createdNames.get(obj);
                vec.remove(index);
                ((DefaultListModel)listActual.getModel()).removeElementAt(index);
                Subject.getInstance().notify(Subject.ACTIONSCHANGED, vec);
                if(vec.size()>0)
                {
                    setSelected(0);
                    /*Object o = comboActual.getModel().getElementAt(0);
                    comboActual.setSelectedIndex(0);
                    actualComp = vecComps.elementAt(0);
                    Component comp = (Component)vecGraph.elementAt(0);
                    panelEdicion.getViewport().setView(comp);*/
                }
                else
                {
                    //para variar por garchas de swing hago esto
                    clearSelection();
                    //comboActual.setModel(new DefaultComboBoxModel(vec));
                    DefaultListModel dlm = new DefaultListModel();
                    Iterator it = vec.iterator();
                    while(it.hasNext())
                    {
                        dlm.addElement(it.next());
                    }
                    listActual.setModel(dlm);

                }
            }
        }

    }//GEN-LAST:event_butQuitarActionPerformed
    public void addNewAction(Object action)
    {
        if(comp!=null)
            comp.removeObservers();
        comp = (TableProperties)ComponentCreatorHandlerFactory.getComponentCreatorHandler().createComponent(action);
        System.out.println("CREE EL COMPONENTE");
        Object tri = actualTrigger;
        Vector vec = (Vector)createdGraphComponents.get(tri);
        vec.add(comp);
        createdGraphComponents.put(tri, vec);
        vec = (Vector)createdComponents.get(tri);
        vec.add(action);
        ((TriggerBean)actualTrigger).setActions(vec);
        createdComponents.put(tri, vec);
        Subject.getInstance().notify(Subject.ACTIONSCHANGED, vec);
        vec = (Vector)createdNames.get(tri);
        vec.add(action.toString());
        ((DefaultListModel)listActual.getModel()).addElement(action.toString());
        createdNames.put(tri,vec);
        /*comboActual.setSelectedItem(action.toString());
        panelEdicion.getViewport().setView(null);
        panelEdicion.getViewport().setView(comp);*/
        //parent.addChildAction();
        System.out.println("ME JUI DE ADD COMP EDITION PANEL");
    }
    private void buttonAgregarNuevoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonAgregarNuevoActionPerformed
    {//GEN-HEADEREND:event_buttonAgregarNuevoActionPerformed
        /**
         * Codigo de atencion para
         * "Agregar nuevo componente"
         */
        String nombre = (String)comboDisponibles.getSelectedItem();

        try
        {

            actualComp = ObjectCreatorHandlerFactory.getObjectCreatorHandler().create(this.type, nombre);
            System.out.println("ACTUALCOMP = "+actualComp);
        }
        catch (InvalidIDException iiex)
        {
            iiex.printStackTrace();
        }
        addNewAction(actualComp);
        /*Object tri = triggerPane.getActualComponent();
        Vector vec = (Vector)createdGraphComponents.get(tri);
        vec.add(comp);
        createdGraphComponents.put(tri, vec);
        vec = (Vector)createdComponents.get(tri);
        vec.add(actualComp);
        createdComponents.put(tri, vec);
        vec = (Vector)createdNames.get(tri);
        vec.add(nombre);
        createdNames.put(tri,vec);*/
        //comboActual.setSelectedItem(actualComp.toString());
        listActual.setSelectedValue(actualComp.toString(),true);
        panelEdicion.getViewport().setView(null);
        panelEdicion.getViewport().setView(comp.getTable());
        parent.addChildAction();
        System.out.println("ME JUI DE ADD COMP EDITION PANEL");

    }//GEN-LAST:event_buttonAgregarNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butQuitar;
    private javax.swing.JButton buttonAgregarNuevo;
    private javax.swing.JComboBox comboDisponibles;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelActual;
    private javax.swing.JLabel labelDisponibles;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JList listActual;
    private javax.swing.JScrollPane panelEdicion;
    private javax.swing.JSeparator separatorArriba;
    private javax.swing.JSeparator separatorMedio;
    // End of variables declaration//GEN-END:variables

}
