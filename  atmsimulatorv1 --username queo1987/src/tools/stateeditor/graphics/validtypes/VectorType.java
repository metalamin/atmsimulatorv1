package tools.stateeditor.graphics.validtypes;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import domain.state.StateBean;
import tools.stateeditor.StateEditor;
import tools.stateeditor.graphics.ListSelectionDialog;
import tools.stateeditor.beans.StateHandlerBeanManager;
import tools.stateeditor.observer.Subject;
import util.ClassUtils;

/**
 *
 * @author jeronimo
 */
public class VectorType implements ValidTypesBehaviorInterface {

    /** Creates a new instance of FontMenuType */
    public VectorType() {
    }

    public String setComponentOnTable(Object o, String s, int renglon)
    {
        System.out.println("SOY VEECTOR CREANDO BOTON");
        String col = "";//((Font)o).getName()+" "+((Font)o).getSize();
        JButton jb = new JButton();
        jb.setBounds(new Rectangle(200, renglon, 19, 16));
        jb.setText(s);
        jb.addActionListener(new MyButtonActionListener(renglon,s));
        Subject.getInstance().addLateObject(Subject.BUTTONADDED, jb);
        return o.toString();
    }

    /* public Object[] getArguments(int row, Object o)
    {
         Object[] arg = {o};
         return arg;
     }*/

     class MyButtonActionListener implements java.awt.event.ActionListener
     {
         private int num;
         private String s;
         public MyButtonActionListener(int ren,String so)
         {
             num = ren/16;
             System.out.println("CREE NUM="+num);
             s=so;
         }
         public void actionPerformed(ActionEvent e) {

            try{
                System.out.println("ME APRETARON "+s);
             Iterator it = StateHandlerBeanManager.getInstance().getBean().getStates().iterator();
             StateBean o = null;
             /*StateBean actual = StateEditor.getInstance().getActualState();
             while(it.hasNext())
             {
                 o = (StateBean)it.next();
                 if(o.equals(actual))
                 break;
             }
             Vector vec = o.getTriggers();*/
             String fName = "get"+s;
             Method met = StateEditor.getInstance().getActualTrigger().getClass().getMethod(fName,(Class[])null);
             Vector vecTri = (Vector)met.invoke(StateEditor.getInstance().getActualTrigger(),(Object[])null);
             System.out.println(vecTri);        
             HashMap map = ClassUtils.getInstance().loadPropertyMap(s,vecTri);
             System.out.println(s);
                  
             if(map!=null)
             {
                 ListSelectionDialog jd = new ListSelectionDialog(StateEditor.getInstance(),s,true,map,vecTri);

                 //jd.setBounds(200, num,200,100);//jl.a
                 jd.pack();
                 jd.setVisible(true);
                 if(jd.isOk())
                 {
                     System.out.println("HOLA");
                     Vector vec = jd.getSelectedValues();
                     Vector vec2 = new Vector();
                     vec2.add(new Integer(num));
                     System.out.println(num);
                     vec2.add(vec);
                     Subject.getInstance().notify(Subject.PROPCHANGED, vec2);
                 }
                 else
                 {
                     System.out.println("NO HOLA");
                 }
             }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            //Subject.getInstance().notify(Subject.PROPCHANGED, new Integer(num));
          }
     }
}
