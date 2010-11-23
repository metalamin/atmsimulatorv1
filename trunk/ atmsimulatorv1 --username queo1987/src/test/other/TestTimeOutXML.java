package test.other;

import config.SystemConfig;
import infrastructure.dataaccess.broker.BrokerFactory;
import domain.implementation.Delay;
import domain.implementation.TimeOut;
import domain.state.StateHandlerFactory;
import domain.state.Trigger;
import domain.state.TriggerFactory;

/**
 * Test de escritura...
 */
public class TestTimeOutXML 
{
    public static void main(String[] args) 
    {
        try
        {
            SystemConfig.getInstance().configure();
            Trigger trr = TriggerFactory.createTrigger("RESET TYPE", "RESET THROWER");
            Trigger trs = TriggerFactory.createTrigger("STOP TYPE", "STOP THROWER");
            StateHandlerFactory.getStateHandler().addState("ESTADO");
            StateHandlerFactory.getStateHandler().addTrigger("ESTADO", trr);
            StateHandlerFactory.getStateHandler().addTrigger("ESTADO", trs);
            
            TimeOut t_o = new TimeOut("1", "2", 1);
            t_o.setState("ESTADO");
            for(int io = 0; io < 5; io++)
            {
                t_o.addAction(new Delay((long)io));
            }
            
            trr.addAction(new Delay(5));
            
            t_o.addResetTrigger("RESET TYPE", "RESET THROWER");
            t_o.addStopTrigger("STOP TYPE", "STOP THROWER");
            
            BrokerFactory.getBroker().save("TimeOut.xml", t_o);
            BrokerFactory.getBroker().save("TriggerImpl.xml", trr);
            
            t_o = (TimeOut)BrokerFactory.getBroker().load("TimeOut.xml");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}
