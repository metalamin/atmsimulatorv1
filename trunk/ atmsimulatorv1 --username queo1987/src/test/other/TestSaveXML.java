package test.other;
import config.*;
import infrastructure.dataaccess.sequential.SequentialFactory;
import java.util.Iterator;
import java.util.Vector;
import domain.state.StateBean;
import domain.state.StateConstants;
import domain.state.StateHandlerBean;
import domain.state.TriggerBean;
import presentation.display.DisplayAction;
import tools.stateeditor.beans.StateHandlerBeanManager;

/**
 *
 */
public class TestSaveXML
{
    public static void main(String[] args) 
    {
        try
        {
            SystemConfig.getInstance().configure();
            DisplayAction d = new DisplayAction();
            d.setScreen("pantalla1.xml");
            DisplayAction d2 = new DisplayAction();
            d2.setScreen("pantalla2.xml");
            
            TriggerBean tri1 = new TriggerBean();
            tri1.setThrower(StateConstants.STARTUP_THROWER);
            tri1.setType(StateConstants.STARTUP_TYPE);
            Vector actions1 = new Vector();
            actions1.add(d);
            tri1.setActions(actions1);
            StateBean sb = new StateBean();
            sb.setName("ESTADO1");
            Vector trigs1 = new Vector();
            trigs1.add(tri1);
            sb.setTriggers(trigs1);
            
            TriggerBean tri2 = new TriggerBean();
            tri2.setThrower(StateConstants.STARTUP_THROWER);
            tri2.setType(StateConstants.STARTUP_TYPE);
            Vector actions2 = new Vector();
            actions2.add(d2);
            tri2.setActions(actions2);
            
            StateBean sb2 = new StateBean();
            sb2.setName("ESTADO2");
            Vector trigs2 = new Vector();
            trigs2.add(tri2);
            sb2.setTriggers(trigs2);
            
            StateHandlerBeanManager.getInstance().getBean().getStates().add(sb);
            StateHandlerBeanManager.getInstance().getBean().getStates().add(sb2);
            
            /*Trigger trr = TriggerFactory.createTrigger("RESET TYPE", "RESET THROWER");
            Trigger trs = TriggerFactory.createTrigger("STOP TYPE", "STOP THROWER");
            StateHandlerFactory.getStateHandler().addState("ESTADO");
            StateHandlerFactory.getStateHandler().addState("ESTADO DOS");
            StateHandlerFactory.getStateHandler()*/
            //StateHandlerFactory.getStateHandler().addTrigger("ESTADO", trr);
            //StateHandlerFactory.getStateHandler().addTrigger("ESTADO", trs);
            /*
            TimeOut t_o = new TimeOut("1", "2", 10000);
            t_o.setState("ESTADO");
            //for(int io = 0; io < 5; io++)
            //{
                t_o.addAction(new Delay((long)10));
            //}
            
            trr.addAction(new Delay(5));
            */
            //t_o.addResetTrigger("RESET TYPE", "RESET THROWER");
            //t_o.addStopTrigger("STOP TYPE", "STOP THROWER");
            StateHandlerBeanManager.getInstance().save("PRUEBAJERO.xml");
            SequentialFactory.getSequentialReader().connect("PRUEBAJERO.xml");
            StateHandlerBean smb = (StateHandlerBean)SequentialFactory.getSequentialReader().read();//StateHandlerFactory.getStateHandler().addTrigger("ESTADO", t_o);
            Iterator it = smb.getStates().iterator();
             while(it.hasNext())
               {
                   Object nxt = it.next();
                   StateBean ssb = (StateBean)nxt;
                   System.out.println("STATE = "+ssb.getName());
                   Iterator trigs = ssb.getTriggers().iterator();
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
            
            /*StateHandlerBeanManager.getInstance().getBean().getStates().add(ti.get(0));
            StateHandlerBeanManager.getInstance().getBean().getStates().add(ti.get(1));*/
            StateHandlerBeanManager.getInstance().save("PRUEBAALBERTO2.xml");
            
            //StateHandlerFactory.getStateHandler().save("MAQ_ESTADOS_TEST.xml");
            //StateHandlerFactory.getStateHandler().load("MAQ_ESTADOS_TEST.xml");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    } 
}
