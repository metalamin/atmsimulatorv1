package config;

/**
 * Configuración general de todo el sistema.
 */
public class MainConfigAllServer
{
    public static void main(String[] args)
    {
        MainSetProperties.main(null);
        MainDefaultConfiguratorsServer.main(null);
        MainCommContextServer.main(null);
        MainDefaultBeaners.main(null);
        MainDefaultComponentCreators.main(null);
        MainDefaultObjectCreators.main(null);
        MainDeviceContext.main(null);
    }
    
}
