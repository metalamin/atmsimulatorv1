package infrastructure.devices.controllers.msd;
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
public class MsdDisplayAction implements Action
{
   
    public MsdDisplayAction()
    {
        
    }
    
    public void update(Event ev) throws GeneralException
    {
        IGUIBuilder guiBuilder = (IGUIBuilder)ev.getContext().get(ev.getThrower());
        if (guiBuilder != null){
            //Simulator.getInstance().getPanel(2).add(guiBuilder.createGUI(10, 2, 20, 70),BorderLayout.CENTER);
            Simulator.getInstance().getPanel(4).add(guiBuilder.createGUI(10, 2, 60, 150),BorderLayout.CENTER);
            Simulator.getInstance().setVisible(true);
        }
    }

}
