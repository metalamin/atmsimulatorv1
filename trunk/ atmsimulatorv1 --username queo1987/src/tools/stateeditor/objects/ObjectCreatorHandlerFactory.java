package tools.stateeditor.objects;



/**
 * Factory para el ObjectCreatorHandler.
 */
public class ObjectCreatorHandlerFactory
{
    public static ObjectCreatorHandler getObjectCreatorHandler()
    {
        return ObjectCreatorHandlerImpl.getInstance(); 
    }
}
