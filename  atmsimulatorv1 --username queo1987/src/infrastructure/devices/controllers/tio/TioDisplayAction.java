package infrastructure.devices.controllers.tio;
import presentation.display.Simulator;
import util.GeneralException;
import java.awt.BorderLayout;
import domain.state.Action;
import domain.state.Event;
import presentation.IGUIBuilder;

/**
 * Action de leer la banda magnética de la tarjeta
 * en el dispositivo de lectura.
 */
public class TioDisplayAction implements Action
{
   
    public TioDisplayAction()
    {
        
    }
    
    public void update(Event ev) throws GeneralException
    {
        IGUIBuilder guiBuilder = (IGUIBuilder)ev.getContext().get(ev.getThrower());
        if (guiBuilder != null){
            Simulator.getInstance().getPanel(3).add(guiBuilder.createGUI(10, 2, 20, 70),BorderLayout.CENTER);
            Simulator.getInstance().setVisible(true);
        }
    }

}
