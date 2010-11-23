package presentation.display;

import infrastructure.dataaccess.broker.BrokerFactory;
import util.GeneralException;
import java.awt.Rectangle;
import domain.state.Action;
import domain.state.Event;

import presentation.screen.Screen;

/**
 * Action de desplegar una pantalla
 * en el Display.
 */
public class DisplayAction implements Action
{
    private String pantalla;
    private Rectangle bounds = null;
    
    public DisplayAction()
    {
        
    }
    
    public DisplayAction(String pantalla) 
    {
        this.setScreen(pantalla);
    }
    
    public void update(Event ev) throws GeneralException
    {
        Screen p = (Screen)BrokerFactory.getBroker().load(pantalla);
        //if(bounds == null)
        //Si se descomenta esto, comentar el de abajo...
        //LEVANTA SIMULADOR
        Display.getInstance().doDisplaySimulator(p);
        //LEVANTA "NORMAL"
        //Display.getInstance().doDisplay(p);
        /*else
            Display.getInstance().doDisplaySimulator(p, bounds);*/
    }

    public String getScreen()
    {
        return pantalla;
    }

    public void setScreen(String pantalla)
    {
        this.pantalla = pantalla;
    }
        
    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
    
    public String toString()
    {
        return "Display (" + getScreen() + ")";
    }
}
