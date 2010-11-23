package tools.stateeditor.components;


/**
 * ComponentCreator para una tabla de propiedades.
 */
public class TablePropertiesComponentCreator implements ComponentCreator
{
    private static TablePropertiesComponentCreator inst = null;
    private TableProperties tp;
    private TablePropertiesComponentCreator()
    {
        tp = null;
    }
    
    public static TablePropertiesComponentCreator getInstance()
    {
        if (inst == null)
        {
            inst = new TablePropertiesComponentCreator();
        }
        return inst;
    }
    
    public Object createComponent(Object base)
    {
        if (tp != null)
        {
            tp.removeObservers();
        }            
        tp = new TableProperties(base); 
        return tp;            
    }
}
