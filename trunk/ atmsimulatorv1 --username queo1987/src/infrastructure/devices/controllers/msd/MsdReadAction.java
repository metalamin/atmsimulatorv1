package infrastructure.devices.controllers.msd;


import infrastructure.dataaccess.broker.BrokerFactory;
import util.GeneralException;
import domain.state.Action;
import domain.state.Event;
import infrastructure.devices.controllers.jxfs.JxfsMsdController;

/**
 * Action de leer la banda magnética de la tarjeta
 * en el dispositivo de lectura.
 */
public class MsdReadAction implements Action
{
   
    public MsdReadAction()
    {
        
    }
    
    public void update(Event ev) throws GeneralException
    {
       JxfsMsdController.getInstance().read();
    }

}
