package config;
import infrastructure.dataaccess.broker.BeanerHandler;
import infrastructure.dataaccess.broker.BeanerHandlerFactory;
import infrastructure.dataaccess.broker.NullBeaner;

/**
 * Esta main class simplemente carga los
 * Beaners por default que se tengan.
 * Es una clase  DE UN SOLO USO.
 */
public class MainDefaultBeaners
{
    public static void main(String[] args)
    {
        try
        {
            BeanerHandler beaners = BeanerHandlerFactory.getBeanerHandler();
            SystemConfig.getInstance().configure();
            /**
             * OK. Todos los Beaners conocidos por el hombre.
             */
            beaners.addBeaner(domain.implementation.TimeOut.class.getName(), new domain.implementation.TimeOutBeaner());
            beaners.addBeaner(domain.implementation.TimeOutBean.class.getName(), new domain.implementation.TimeOutBeaner());
            beaners.addBeaner("domain.state.TriggerImpl", new domain.state.TriggerBeaner());
            beaners.addBeaner(domain.state.TriggerBean.class.getName(), new domain.state.TriggerBeaner());
            beaners.addBeaner("domain.state.State", new domain.state.StateBeaner());
            beaners.addBeaner("domain.state.StateHandlerImpl", new domain.state.StateHandlerBeaner());
            beaners.addBeaner("domain.state.StateHandlerBean", new domain.state.StateHandlerBeaner());
            beaners.addBeaner("presentation.screen.Screen", new presentation.screen.ScreenBeaner());
            beaners.addBeaner("presentation.screen.ScreenBean", new presentation.screen.ScreenBeaner());
            beaners.addBeaner("domain.implementation.Reset", new NullBeaner());
            beaners.addBeaner("domain.implementation.Stop", new NullBeaner());
            beaners.addBeaner("domain.implementation.CountAction", new NullBeaner());
            
            /** 
             *  @TODO Ver con Alberto
             */
            beaners.addBeaner(domain.implementation.CountTrigger.class.getName(), new domain.implementation.CountBeaner());
            beaners.addBeaner(domain.implementation.CountBean.class.getName(), new domain.implementation.CountBeaner());
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
