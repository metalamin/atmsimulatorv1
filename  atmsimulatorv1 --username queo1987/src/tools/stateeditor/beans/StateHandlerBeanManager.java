package tools.stateeditor.beans;
import infrastructure.dataaccess.broker.BeanerFactory;
import infrastructure.dataaccess.DataAccessException;
import util.GeneralException;
import java.util.Iterator;
import domain.state.StateBean;
import domain.state.StateHandlerBean;
import domain.state.StateHandlerFactory;

/**
 * Esta clase es la encargada de almacenar
 * el statehandlerbean, para que se vaya 
 * creando correctamente, y luego crear
 * la maquina de estados.
 */
public final class StateHandlerBeanManager
{
    private static StateHandlerBeanManager inst = null;
    private StateHandlerBean shb; 
    
    private StateHandlerBeanManager()
    {
        clean();
    }
    
    public static StateHandlerBeanManager getInstance()
    {
        if (inst == null)
        {
            inst = new StateHandlerBeanManager();
        }
        return inst;
    }
    
    public StateHandlerBean getBean()
    {
        return shb;
    }
    
    /**
     * Borra el shb, reemplazandolo por uno
     * nuevo.
     */
    public void clean()
    {
        shb = new StateHandlerBean();
    }
    
    /**
     * Salva la maquina de estados.
     * Si todo esta OK, borra el shb, crenado uno
     * nuevo.
     */    
    public void save(String name) throws DataAccessException, GeneralException
    {
        BeanerFactory.getBeaner(shb).fromBean(shb);
        StateHandlerFactory.getStateHandler().save(name);
        //OK, se borra
        clean();
    }
}
