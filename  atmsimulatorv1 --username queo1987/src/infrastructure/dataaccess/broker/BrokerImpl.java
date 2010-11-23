package infrastructure.dataaccess.broker;

import infrastructure.dataaccess.sequential.SequentialFactory;
import infrastructure.dataaccess.sequential.SequentialReader;
import infrastructure.dataaccess.sequential.SequentialWriter;
import infrastructure.dataaccess.DataAccessException;
import util.GeneralException;

/**
 * Implementacion del broker
 */
final class BrokerImpl implements Broker
{
    private static BrokerImpl inst = null;
    
    private BrokerImpl() 
    {
    }
    
    public static BrokerImpl getInstance()
    {
        if (inst == null)
            inst = new BrokerImpl();
        
        return inst;
    }
    
    private Beaner getBeaner(Object name)
    {
        return BeanerFactory.getBeaner(name);
    }
    
    public void save(String name, Object obj) throws DataAccessException
    {
        Beaner b = getBeaner(obj);
        SequentialWriter sw = SequentialFactory.getSequentialWriter();
        sw.connect(name);
        sw.write(b.toBean(obj));
        sw.close();
    }
    
    public Object load(String name) throws DataAccessException, GeneralException
    {
        SequentialReader sw = SequentialFactory.getSequentialReader();
        sw.connect(name);
        Object bean = sw.read();
        sw.close();
        Beaner b = getBeaner(bean);
        return b.fromBean(bean);
    }
}
