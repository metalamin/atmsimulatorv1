package presentation.display;

import java.util.HashMap;
import presentation.screen.Button;
import presentation.screen.IComponent;

public class DictBehavior 
{
    
    private HashMap map;
    public DictBehavior()
    {
        loadBehaviors();
    }
    
    private void loadBehaviors()
    {
        map = new HashMap();
        /**
         * Esto no va aca.
         */
        map.put(Button.class.getName(), new ButtonBehavior());    
    }
    
    public void setBehavior(IComponent ic)
    {
        Object o = map.get(ic.getClass().getName());
        if(o != null)
            ((Behavior)o).setBehavior(ic);
    }
    
}
