package presentation.display;

import presentation.screen.IButtonComponent;
import presentation.screen.IComponent;

/**
 *
 * @author Programacion
 */
public class ButtonBehavior implements Behavior{
    
    /** Creates a new instance of ButtonBehavior */
    public ButtonBehavior() {
    }
    
    public void setBehavior(IComponent ibc)
    {
        ((IButtonComponent)ibc).addActionListener(new ButtonListener(ibc.getName()));
    }
    
}
