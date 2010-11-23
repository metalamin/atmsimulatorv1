package tools.stateeditor.components;

/**
 * Factory
 */
public class ComponentCreatorHandlerFactory
{
    public static ComponentCreatorHandler getComponentCreatorHandler()
    {
        return ComponentCreatorHandlerImpl.getInstance();
    }
    
}
