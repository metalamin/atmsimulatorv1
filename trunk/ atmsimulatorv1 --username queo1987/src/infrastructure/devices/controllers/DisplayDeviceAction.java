package infrastructure.devices.controllers;
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
public class DisplayDeviceAction implements Action
{
    public static String POSITION = "POSITION";
    private Integer guiPosition;
    
    public DisplayDeviceAction()
    {
        
    }
 
    public DisplayDeviceAction(Integer guiPosition)
    {
        this.guiPosition = guiPosition;
    }
    
    public void update(Event ev) throws GeneralException
    {
        IGUIBuilder guiBuilder = (IGUIBuilder)ev.getContext().get(ev.getThrower());
        Integer position = (Integer)ev.getContext().get(POSITION);
        if (position != null){
            setGuiPosition(position);
        }
        if (guiBuilder != null){
            Simulator.getInstance().getPanel(getGuiPosition()).add(guiBuilder.createGUI(10, 2, 20, 70),BorderLayout.CENTER);
            Simulator.getInstance().setVisible(true);
        }
    }

    public Integer getGuiPosition() {
        return guiPosition;
    }

    public void setGuiPosition(Integer guiPosition) {
        this.guiPosition = guiPosition;
    }
    
    public String toString()
    {
        return "Display Device ( " + guiPosition + ")";
    }
}
