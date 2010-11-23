package infrastructure.devices.controllers.tio;

import infrastructure.dataaccess.broker.BrokerFactory;
import util.GeneralException;
import domain.state.Action;
import domain.state.Event;

import presentation.screen.Screen;
import infrastructure.devices.controllers.jxfs.JxfsTioController;

/**
 * Action de desplegar una pantalla
 * en el Display.
 */
public class TioReadAction implements Action
{
    private String pin;
    
    public TioReadAction()
    {
        
    }
    
    public TioReadAction(String pin) 
    {
        this.setPin(pin);
    }
    
    public void update(Event ev) throws GeneralException
    {
        JxfsTioController.getInstance().read();
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }
}
