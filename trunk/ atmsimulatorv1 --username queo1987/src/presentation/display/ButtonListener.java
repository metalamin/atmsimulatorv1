package presentation.display;

import util.GeneralException;
import domain.statemachine.StateMachineFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener de los botones.
 */
class ButtonListener implements ActionListener
{
    private String name;
    
    public ButtonListener(String button_name) 
    {
        name = button_name;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            StateMachineFactory.getStateMachine().update(new ButtonEvent(name));
        }
        catch (GeneralException gex)
        {
            /**
             * VER BIEN QUE HACER ACA!
             */
        }
    }
    
}
