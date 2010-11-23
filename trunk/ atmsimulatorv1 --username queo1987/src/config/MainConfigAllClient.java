package config;

/**
 * Configuración general de todo el sistema.
 */
public class MainConfigAllClient
{
    public static void main(String[] args)
    {
        MainSetProperties.main(null);
        MainDefaultConfigurators.main(null);
        MainCommContext.main(null);
        MainDefaultBeaners.main(null);
        MainDefaultComponentCreators.main(null);
        MainDefaultObjectCreators.main(null);
        MainDeviceContext.main(null);
    }
    
}
