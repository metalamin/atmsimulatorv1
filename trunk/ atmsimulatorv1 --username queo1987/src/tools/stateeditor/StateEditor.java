package tools.stateeditor;

import config.SystemConfig;
import domain.state.StateBean;
import domain.state.StateConstants;
import domain.state.StateHandlerBean;
import domain.state.TriggerBean;
import infrastructure.dataaccess.sequential.SequentialFactory;
import tools.screeneditor.graphics.ExampleFileFilter;
import util.GeneralException;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;
import domain.implementation.StateChange;
import tools.stateeditor.beans.StateHandlerBeanManager;
import tools.stateeditor.graphics.GraphEd;
import tools.stateeditor.observer.Observer;
import tools.stateeditor.observer.Subject;

/**
 * Panel principal para la edicion
 * de estados.
 */
public class StateEditor extends javax.swing.JFrame implements Observer
{
    private Vector estados;
    static private String TRIGGERS= "Trigger";
    static private String ACTIONS= "Action";
    private GraphEd myGraph;
    private HashMap mapEstados;
    private StateBean actualState;
    private EditionPanelTriggers ept;
    private EditonPanelActions epa;
    private static StateEditor inst;
    private Rectangle r;
    
    private StateEditor()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setDefaultLookAndFeelDecorated(true);
        initComponents();
        this.setTitle("STATE EDITOR");
        labelEstado.setText("STATES  ");
        this.setBounds(r);
        myGraph = new GraphEd();
        PanelPrincipal.add(myGraph);
        mapEstados = new HashMap();
        ept = new EditionPanelTriggers("TRIGGERS",TRIGGERS);
        epa = new EditonPanelActions(ept, "ACTIONS ",ACTIONS);
        ept.setParent(this);
        epa.setParent(this);        
        PanelTrigger.add(ept);
        PanelAction.add(epa);
        Subject.getInstance().addObserver(this, Subject.TABLETRIGGERCHANGED);
        Subject.getInstance().addObserver(this, Subject.TABLEACTIONCHANGED);
        Subject.getInstance().addObserver(this, Subject.SELECTTRIGGERCHANGED);
        Subject.getInstance().addObserver(this, Subject.STATEGRAPHCHANGED);
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
    
    public static StateEditor getInstance()
    {
        if(inst == null)
            inst = new StateEditor();
        return inst;
    }
    
    public TriggerBean getActualTrigger()
    {
        return (TriggerBean)ept.getActualComponent();
    }
    
    public void update(Object up, String type)
    {
       Vector vec = null;
       System.out.println("UPDATE STATEEDTIRO "+type+"CLASS "+up.getClass());
       if(type.equals(Subject.SELECTTRIGGERCHANGED))
       {
           epa.setActualParentComponent(ept.getActualComponent());
       }
       else if (type.equals(Subject.TABLETRIGGERCHANGED))
       {
           if(TriggerBean.class.isAssignableFrom(up.getClass()))
           {
                //System.out.println("TRIGGER");       
                //System.out.println(((TriggerBean)up).getThrower());
                //vec = getActualState().getTriggers();
                //vec.add(up);
                //System.out.println(vec);
           }
           //else 
       }
       else if(type.equals(Subject.TABLEACTIONCHANGED))
       {
           /*if((logica.state.Action.class.isAssignableFrom(up.getClass())))
            {*/
                //Entonces el ep es un panel de actions
                System.out.println("SE MODIFICÓ UN ACTIONS");        
                //vec = ((TriggerBean)ept.getActualComponent()).getActions();
                if(StateChange.class.isInstance(up))
                {
                    //Debo agregarlo graficamente.
                    System.out.println("STATECHANGE");        
                    myGraph.agregarLink(epa.getActualComponent(),getActualState().getName(),((StateChange)up).getState());
                }                
            /*}*/
       }
       else if(type.equals(Subject.STATEGRAPHCHANGED))
       {
           if(((DefaultComboBoxModel)comboEstados.getModel()).getIndexOf(up)>=0 && ((DefaultComboBoxModel)comboEstados.getModel()).getSelectedItem()!= up)
           {
               System.out.println("CAMBIO DE ESTADO EN GRAPH");
               comboEstados.setSelectedItem(up);
           }
       }
    }
    
    public void addChildAction()
    {
            Object obj = epa.getActualComponent();
            //Entonces el ep es un panel de actions
            System.out.println("ADD ACTIONS STATEEDITOR");        
            Vector vec = ((TriggerBean)ept.getActualComponent()).getActions();
            System.out.println(vec);
            //if (!vec.contains(obj))
            //{
            //    vec.add(obj); 
                        
            //}
    }
        
    
    public void addChildTrigger(Object obj)
    {
        //Object obj = ept.getActualComponent();
        Vector vec = null;
        
        System.out.println("TRIGGER");        
        System.out.println(((TriggerBean)obj).getThrower());
        vec = getActualState().getTriggers();
        vec.add(obj);
        System.out.println(vec);
        epa.addActualParentComponent((TriggerBean)obj);
    }
    
    public void removeLink(Object obj)
    {
        myGraph.removeLink(obj);
    }
    
    public void removeChildAction()
    {
        Object obj = epa.getActualComponent();
        Vector vec = null;
        //Entonces el ep es un panel de actions
        System.out.println("ACTIONS");        
        //vec = ((TriggerBean)ept.getActualComponent()).getActions();
        //vec.remove(obj); 
        if(StateChange.class.isInstance(obj))
        {
            removeLink(obj);
        }
        //Subject.getInstance().notify(Subject.ADDACTION, vec);
    }
    
    public void removeChildTrigger()
    {
        Object obj = ept.getActualComponent();
        Vector vec = null;
        
            System.out.println("TRIGGER REMOVE");        
            System.out.println(((TriggerBean)obj).getThrower());
            vec = getActualState().getTriggers();
            vec.remove(obj);
            epa.removeAllObjects(obj);
            System.out.println(vec);
        
            //Entonces el ep es un panel de actions
           /* System.out.println("ACTIONS");        
            vec = ((TriggerBean)ept.getActualComponent()).getActions();
            vec.remove(obj); */
            //Subject.getInstance().notify(Subject.ADDACTION, vec);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        PanelPrincipal = new javax.swing.JPanel();
        PanelEstados = new javax.swing.JPanel();
        labelEstado = new javax.swing.JLabel();
        comboEstados = new javax.swing.JComboBox();
        textInputName = new javax.swing.JTextField();
        buttonNew = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelTrigger = new javax.swing.JPanel();
        PanelAction = new javax.swing.JPanel();
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        menubar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        ItemNuevo = new javax.swing.JMenuItem();
        Load = new javax.swing.JMenuItem();
        ItemSave = new javax.swing.JMenuItem();
        ItemExit = new javax.swing.JMenuItem();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        PanelPrincipal.setLayout(new java.awt.BorderLayout());

        PanelPrincipal.setBackground(new java.awt.Color(0, 153, 153));
        PanelPrincipal.setMinimumSize(new java.awt.Dimension((int)(r.getWidth()*0.65),(int)r.getHeight()));
        PanelPrincipal.setMaximumSize(new java.awt.Dimension((int)(r.getWidth()*0.65),(int)r.getHeight()));
        PanelPrincipal.setPreferredSize(new java.awt.Dimension((int)(r.getWidth()*0.65),(int)r.getHeight()));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(PanelPrincipal, gridBagConstraints);

        PanelEstados.setLayout(new java.awt.GridBagLayout());

        PanelEstados.setMinimumSize(new java.awt.Dimension(323, 75));
        PanelEstados.setPreferredSize(new java.awt.Dimension(323, 75));
        PanelEstados.setMinimumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.2)));
        PanelEstados.setMaximumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.2)));
        PanelEstados.setPreferredSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.2)));

        labelEstado.setFont(new java.awt.Font("Tahoma", 1, 13));
        labelEstado.setText("STATES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 0, 0);
        PanelEstados.add(labelEstado, gridBagConstraints);

        comboEstados.setMaximumSize(new java.awt.Dimension(70, 20));
        comboEstados.setMinimumSize(new java.awt.Dimension(70, 20));
        comboEstados.setPreferredSize(new java.awt.Dimension(70, 20));
        comboEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadosActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        PanelEstados.add(comboEstados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        PanelEstados.add(textInputName, gridBagConstraints);

        buttonNew.setText("Add");
        buttonNew.setMargin(new java.awt.Insets(2, 5, 2, 5));
        buttonNew.setMaximumSize(new java.awt.Dimension(63, 23));
        buttonNew.setMinimumSize(new java.awt.Dimension(63, 23));
        buttonNew.setPreferredSize(new java.awt.Dimension(63, 23));
        buttonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 16);
        PanelEstados.add(buttonNew, gridBagConstraints);

        buttonDelete.setText("Remove");
        buttonDelete.setMargin(new java.awt.Insets(2, 5, 2, 5));
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 16);
        PanelEstados.add(buttonDelete, gridBagConstraints);

        jLabel1.setText("Actual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        PanelEstados.add(jLabel1, gridBagConstraints);

        jLabel2.setText("New");
        jLabel2.setMaximumSize(new java.awt.Dimension(53, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(53, 14));
        jLabel2.setPreferredSize(new java.awt.Dimension(53, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        PanelEstados.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(PanelEstados, gridBagConstraints);

        PanelTrigger.setLayout(new java.awt.BorderLayout());

        PanelTrigger.setBackground(new java.awt.Color(153, 0, 153));
        PanelTrigger.setMinimumSize(new java.awt.Dimension(323, 100));
        PanelTrigger.setPreferredSize(new java.awt.Dimension(323, 100));
        PanelTrigger.setMinimumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));
        PanelTrigger.setMaximumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));
        PanelTrigger.setPreferredSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(PanelTrigger, gridBagConstraints);

        PanelAction.setLayout(new java.awt.BorderLayout());

        PanelAction.setBackground(new java.awt.Color(153, 153, 0));
        PanelAction.setMinimumSize(new java.awt.Dimension(323, 100));
        PanelAction.setPreferredSize(new java.awt.Dimension(323, 100));
        PanelAction.setMinimumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));
        PanelAction.setMaximumSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));
        PanelAction.setPreferredSize(new java.awt.Dimension((int)(r.getWidth()*0.35),(int)(r.getHeight()*0.4)));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(PanelAction, gridBagConstraints);

        menubar.setName("menu");
        menuFile.setLabel("File");
        ItemNuevo.setText("New");
        ItemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemNuevoActionPerformed(evt);
            }
        });

        menuFile.add(ItemNuevo);

        Load.setText("Open...");
        Load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadActionPerformed(evt);
            }
        });

        menuFile.add(Load);

        ItemSave.setText("Save As...");
        ItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemSaveActionPerformed(evt);
            }
        });

        menuFile.add(ItemSave);

        ItemExit.setLabel("Exit");
        ItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemExitActionPerformed(evt);
            }
        });

        menuFile.add(ItemExit);

        menubar.add(menuFile);

        setJMenuBar(menubar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemExitActionPerformed
// TODO add your handling code here:
    this.dispose();
    System.exit(0);
    }//GEN-LAST:event_ItemExitActionPerformed
    private void removeAllWorked()
    {
        myGraph.removeAllCells();
        while(comboEstados.getItemCount() > 0)
        {
            deleteActualState();
        }
        mapEstados = new HashMap();
    }
    private void ItemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemNuevoActionPerformed
// TODO add your handling code here:
        removeAllWorked();
        //Creo el estado inicial
        StateBean sInicial = new StateBean();
        sInicial.setName(StateConstants.INITIAL_STATE);
        addStateBean(sInicial);
        
        myGraph.insert(new Point(10,100),sInicial.getName(), Color.RED);
        TriggerBean tri = new TriggerBean();
        tri.setThrower(StateConstants.STARTUP_THROWER);
        tri.setType(StateConstants.STARTUP_TYPE);
        epa.addActualParentComponent(tri);
        ept.addNewTrigger(tri);
        addChildTrigger(tri);
        //Ahora el end trigger
        tri = new TriggerBean();
        tri.setThrower(StateConstants.END_THROWER);
        tri.setType(StateConstants.END_TYPE);
        epa.addActualParentComponent(tri);
        ept.addNewTrigger(tri);
        addChildTrigger(tri);
        setActualState(sInicial);
        //Creo el estado global
        StateBean sGlobal = new StateBean();
        sGlobal.setName(StateConstants.GLOBAL_STATE);
        addStateBean(sGlobal);
        
        myGraph.insert(new Point(100,10),sGlobal.getName(), new Color(0,128,64));
        tri = new TriggerBean();
        tri.setThrower(StateConstants.STARTUP_THROWER);
        tri.setType(StateConstants.STARTUP_TYPE);
        epa.addActualParentComponent(tri);
        ept.addNewTrigger(tri);
        addChildTrigger(tri);
        //Ahora el end trigger
        tri = new TriggerBean();
        tri.setThrower(StateConstants.END_THROWER);
        tri.setType(StateConstants.END_TYPE);
        epa.addActualParentComponent(tri);
        ept.addNewTrigger(tri);
        addChildTrigger(tri);
        setActualState(sGlobal);
    }//GEN-LAST:event_ItemNuevoActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
// TODO add your handling code here:
        //myGraph.removeAllCells();
        myGraph.deleteState(actualState.getName());
        deleteActualState();
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void deleteActualState()
    {        
        ept.deleteParent();
        mapEstados.remove(actualState.getName());
        comboEstados.removeItemAt(comboEstados.getSelectedIndex());
        if(comboEstados.getItemCount() > 0)
        {
            comboEstados.setSelectedIndex(0);
        }
    }
    
    private void LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadActionPerformed
// TODO add your handling code here:
    JFileChooser jfc = new JFileChooser();
    ExampleFileFilter filter = new ExampleFileFilter();
    filter.addExtension("xml");
    filter.setDescription("XML files");
    jfc.setFileFilter(filter);
    
    int valor = jfc.showOpenDialog(this);
    try
    {
        if(valor == jfc.APPROVE_OPTION)
        {
            File filemio = jfc.getSelectedFile();
            String file = filemio.getPath();
            SequentialFactory.getSequentialReader().connect(filemio.getPath());
            StateHandlerBean smb = (StateHandlerBean)SequentialFactory.getSequentialReader().read();
            removeAllWorked();
            try{
            myGraph.loadCells(file+".conf");    
            }
            catch(Exception ex2)
            {
               JOptionPane.showMessageDialog(this,"No existe el archivo "+file+".conf","ERROR",JOptionPane.ERROR_MESSAGE);              
               return;
               //ItemNuevoActionPerformed(null);    
            }            
            loadMapsAndComponents(smb);
            if(((DefaultComboBoxModel)comboEstados.getModel()).getSize() > 0)
                ((DefaultComboBoxModel)comboEstados.getModel()).setSelectedItem(((DefaultComboBoxModel)comboEstados.getModel()).getElementAt(0));
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    
    }//GEN-LAST:event_LoadActionPerformed

    private void ItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemSaveActionPerformed
// TODO add your handling code here:
      try
      {
          JFileChooser jfc = new JFileChooser();
          ExampleFileFilter filter = new ExampleFileFilter();
          filter.addExtension("xml");
          filter.setDescription("XML files");
          jfc.setFileFilter(filter);
          int valor = jfc.showSaveDialog(this);
          StateHandlerBeanManager.getInstance().clean();
          System.out.println(StateHandlerBeanManager.getInstance().getBean().getStates());
          if(valor == jfc.APPROVE_OPTION)
          {
            String arch = jfc.getSelectedFile().getPath();
            if(arch!=null)
            {
               Iterator it = mapEstados.values().iterator();
               while(it.hasNext())
               {
                   Object nxt = it.next();
                   StateBean sb = (StateBean)nxt;
                   System.out.println("STATE = "+sb.getName());
                   Iterator trigs = sb.getTriggers().iterator();
                   while(trigs.hasNext())
                   {
                       TriggerBean tb = (TriggerBean)trigs.next();
                       System.out.println("TRIGGER = "+tb);
                       Iterator it2 = tb.getActions().iterator();
                       while(it2.hasNext())
                       {
                           System.out.println("ACTION = "+it2.next());
                       }
                   }
                   StateHandlerBeanManager.getInstance().getBean().getStates().add(nxt);
               }
               if(arch.substring(arch.length()-4).equals(".xml"))
                {
                    //System.out.println("YA PUSO XML");
                }
                else
                {
                    //System.out.println("NO PUSO .XML "+arch);
                    arch+=".xml";
                }
               StateHandlerBeanManager.getInstance().save(arch);
               myGraph.saveCells(arch+".conf");          
            }
         }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
    }//GEN-LAST:event_ItemSaveActionPerformed

    private void comboEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadosActionPerformed
// TODO add your handling code here:
        if(comboEstados.getItemCount()>1)
        {   
            String nom = (String)comboEstados.getSelectedItem();
            setActualState((StateBean)mapEstados.get(nom));
        }        
    }//GEN-LAST:event_comboEstadosActionPerformed
    
    private void buttonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewActionPerformed
        /**
         * Codigo de atencion para
         * agregar un nuevo estado.
         */
        String nom_est = textInputName.getText();
        if (nom_est.equals(""))
        {
            showWarningMessage("El nombre no puede ser vacío.");
        }
        else if (estados.contains(nom_est))
        {
            showWarningMessage("Ya se agregó un estado con ese nombre.");
        }
        else
        {
            //Agrego el estado
            StateBean sb = new StateBean();
            sb.setName(nom_est);
            addStateBean(sb);
            TriggerBean tri = new TriggerBean();
            tri.setThrower(StateConstants.STARTUP_THROWER);
            tri.setType(StateConstants.STARTUP_TYPE);
            epa.addActualParentComponent(tri);
            ept.addNewTrigger(tri);
            addChildTrigger(tri);
            //Ahora el end trigger
            tri = new TriggerBean();
            tri.setThrower(StateConstants.END_THROWER);
            tri.setType(StateConstants.END_TYPE);
            epa.addActualParentComponent(tri);
            ept.addNewTrigger(tri);
            addChildTrigger(tri);
            myGraph.insert(new Point(10,10),nom_est);
            //StateHandlerBeanManager.getInstance().getBean().getStates().add(sb);
            textInputName.setText("");   
            setActualState(sb);
        }
    }//GEN-LAST:event_buttonNewActionPerformed
    
    private void addStateBean(StateBean sb)
    {
        epa.addState(sb);
        ept.addActualParentComponent(sb);
        setActualState(sb);
        String nom_est = sb.getName();
        mapEstados.put(nom_est, sb);            
        comboEstados.addItem(nom_est);
        comboEstados.getModel().setSelectedItem(nom_est);                
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemExit;
    private javax.swing.JMenuItem ItemNuevo;
    private javax.swing.JMenuItem ItemSave;
    private javax.swing.JMenuItem Load;
    private javax.swing.JPanel PanelAction;
    private javax.swing.JPanel PanelEstados;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel PanelTrigger;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonNew;
    private javax.swing.JComboBox comboEstados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JTextField textInputName;
    // End of variables declaration//GEN-END:variables
    
    private void showWarningMessage(String msg)
    {
        JOptionPane.showMessageDialog(this, msg, "Atención", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    SystemConfig.getInstance().configure();
                    StateEditor.getInstance().setVisible(true);
                    StateEditor.getInstance().ItemNuevoActionPerformed(null);
                }
                catch (GeneralException gex)
                {
                    gex.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    public StateBean getActualState() {
        return actualState;
    }

    public void setActualState(StateBean actualState) {
        this.actualState = actualState;
        epa.clearSelection();
        epa.setState(actualState);
        ept.setActualParentComponent(actualState);        
    }
    
    private void loadMapsAndComponents(StateHandlerBean smb)
    {
        
        Vector vec = new Vector(smb.getStates());
        int i = 0;
        System.out.println("AGREGO LOS ESTADOS");
        while(i < vec.size())
        {
            StateBean sb = (StateBean)vec.get(i);
            System.out.println(sb.getName());
            addStateBean(sb);
            i++;
        }
        i = 0;
        System.out.println("AGREGO LOS TRIGGERS");
        while(i < vec.size())
        {
            StateBean sb = (StateBean)vec.get(i);
            setActualState(sb);
            
            System.out.println(sb.getName());
            Vector trigs = (Vector)sb.getTriggers().clone();            
            trigs.add(sb.getStartupTrigger());
            trigs.add(sb.getEndTrigger());
            System.out.println(trigs.size());                    
            int j = 0;
            while(j < trigs.size())
            {
                Object tri = trigs.get(j);
                ept.addNewTrigger(tri);
                epa.addActualParentComponent((TriggerBean)tri);
                TriggerBean tb = (TriggerBean)tri;
                Vector actions = tb.getActions(); 
                System.out.println("ESTAS SON MIS ACTIONS "+actions.size());
                int k = 0;
                while(k < actions.size())
                {
                    Object obj = actions.get(k);
                    System.out.println("ESTA ACTION ES = "+obj);
                    //epa.addNewAction(obj);
                    //Si es un stateChange hay que agregar los links
                    if(StateChange.class.isInstance(obj))
                    {
                        //Debo agregarlo graficamente.                              
                        myGraph.agregarLink(obj,sb.getName(),((StateChange)obj).getState());
                    }
                    k++;
                }                
                j++;
               }
            /*if(i==vec.size()-1)
            {
                //quiero hacer esto solo una vez
                setActualState(sb);
            }*/
            i++;
        }
    }   
        
}
