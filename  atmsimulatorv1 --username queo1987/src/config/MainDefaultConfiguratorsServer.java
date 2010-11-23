package config;

public class MainDefaultConfiguratorsServer
{
    public static void main(String[] args)
    {
        try
        {
            //Todos los configuradores del universo
            BeanersConfigurator bcfg = new BeanersConfigurator();
            bcfg.setConnectionName("cfg/beaners.xml");
            
            ComponentCreatorConfigurator ccfg = new ComponentCreatorConfigurator();
            ccfg.setConnectionName("cfg/componentcreators.xml");
            
            ObjectCreatorConfigurator ocfg = new ObjectCreatorConfigurator();
            ocfg.setConnectionName("cfg/objectcreators.xml");
            
            CommContextConfigurator cccfg = new CommContextConfigurator();
            cccfg.setConnectionName("cfg/comm_context_server.xml");
            
            SystemConfig.getInstance().setConfiguratorsConnectionName("cfg/configurators_server.xml");

            SystemConfig.getInstance().addConfigurator("BEANERS", bcfg);
            SystemConfig.getInstance().addConfigurator("COMPONENTS", ccfg);
            SystemConfig.getInstance().addConfigurator("OBJECTS", ocfg);
            SystemConfig.getInstance().addConfigurator("COMM_CONTEXT", cccfg);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
