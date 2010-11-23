package test.other;

import config.SystemConfig;
import domain.implementation.DebugAction;
import domain.implementation.StateChange;
import domain.state.StateBean;
import domain.state.StateHandlerBean;
import domain.state.TriggerBean;
import tools.stateeditor.beans.StateHandlerBeanManager;

public class BeanManagerTest
{
    public static void main(String[] args)
    {
        try
        {
            SystemConfig.getInstance().configure();
            StateHandlerBeanManager shbm = StateHandlerBeanManager.getInstance();
            StateHandlerBean shb = shbm.getBean();
            
            StateBean sb = new StateBean();
            sb.setName("ESTADO1");
            TriggerBean tb = new TriggerBean();
            tb.getActions().add(new DebugAction("ENTRANDO!"));
            tb.getActions().add(new StateChange("ESTADO2"));
            sb.setStartupTrigger(tb);
            
            StateBean sb2 = new StateBean();
            sb2.setName("ESTADO2");
            TriggerBean tb2 = new TriggerBean();
            tb2.getActions().add(new DebugAction("ENTRANDO AL 2"));
            sb2.setStartupTrigger(tb2);
            
            shb.getStates().add(sb2);
            shb.getStates().add(sb);
            shbm.save("pruebabeans.xml");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
