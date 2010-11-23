package config;

import domain.implementation.DebugAction;
import domain.implementation.Delay;
import domain.implementation.StateChange;
import domain.implementation.TimeOutBean;
import domain.state.TriggerBean;
import presentation.display.DisplayAction;
import presentation.display.GetTextAction;
import presentation.display.SetTextAction;
import tools.stateeditor.components.ComponentCreatorHandler;
import tools.stateeditor.components.ComponentCreatorHandlerFactory;
import tools.stateeditor.components.TablePropertiesComponentCreator;

/**
 * Carga de los ComponentCreators
 */
public class MainDefaultComponentCreators
{
    public static void main(String[] args)
    {
        try
        {
            SystemConfig.getInstance().configure();
            ComponentCreatorHandler hnd = ComponentCreatorHandlerFactory.getComponentCreatorHandler();
            //Se agregan los creadores...
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
