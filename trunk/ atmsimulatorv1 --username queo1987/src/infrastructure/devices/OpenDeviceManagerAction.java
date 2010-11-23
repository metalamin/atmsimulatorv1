package infrastructure.devices;
import util.GeneralException;
import infrastructure.devices.events.DeviceException;
import infrastructure.devices.general.IDeviceManager;
import domain.state.Action;
import domain.state.Event;
import domain.statemachine.StateMachineFactory;


/**
 * Register state machine in the device manager
 */
public class OpenDeviceManagerAction implements Action
{
  
    public OpenDeviceManagerAction()
    {
    }
    
    public void update(Event ev) throws GeneralException
    {
        try{
            IDeviceManager idm = DeviceHandlerFactory.getDeviceHandler().getDeviceManager();
            idm.addUpdateListener(StateMachineFactory.getStateMachine());
        }
        catch (Exception e){
            throw new GeneralException(e);
        }
    }
    
    public String toString()
    {
        return "Open Device Manager";
    }
}
